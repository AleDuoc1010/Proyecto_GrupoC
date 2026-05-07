package grupoC.motor_coincidencias.service;

import java.util.Map;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import grupoC.motor_coincidencias.client.GeolocalizacionClient;
import grupoC.motor_coincidencias.client.MascotaClient;
import grupoC.motor_coincidencias.client.ReporteClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;
import grupoC.motor_coincidencias.security.JwtTokenProvider;
import grupoC.motor_coincidencias.dto.MascotaExternoDto;
import grupoC.motor_coincidencias.dto.ReporteExternoDto;
import grupoC.motor_coincidencias.dto.UbicacionExternoDto;
import grupoC.motor_coincidencias.model.Coincidencia;
import grupoC.motor_coincidencias.repository.CoincidenciaRepository;

@Service
public class MotorCoincidenciasService {

    private final MascotaClient mascotaClient;
    private final ReporteClient reporteClient;
    private final GeolocalizacionClient geoClient;
    private final CoincidenciaRepository repository;
    private final JwtTokenProvider jwtTokenProvider;
    private final String mascotasApiUrl;
    private final String geolocalizacionApiUrl;

    public MotorCoincidenciasService(MascotaClient mascotaClient, ReporteClient reporteClient, GeolocalizacionClient geoClient, CoincidenciaRepository repository,
            JwtTokenProvider jwtTokenProvider,
            @Value("${MASCOTAS_API_URL:http://api-mascotas:8081}") String mascotasApiUrl,
            @Value("${GEOLOCALIZACION_API_URL:http://api-geolocalizacion:8082}") String geolocalizacionApiUrl) {
        this.mascotaClient = mascotaClient;
        this.reporteClient = reporteClient;
        this.geoClient = geoClient;
        this.repository = repository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mascotasApiUrl = mascotasApiUrl;
        this.geolocalizacionApiUrl = geolocalizacionApiUrl;
    }

@Scheduled(fixedRate = 120000)
@Transactional
public void ejecutarProcesoDeCoincidencias() {
    System.out.println("Motor: Iniciando búsqueda...");

        // Prefer calling external services with explicit Authorization header using RestTemplate
        RestTemplate rest = new RestTemplate();
        String token = jwtTokenProvider.generateServiceToken("motor-service");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        ResponseEntity<List<ReporteExternoDto>> respReportes = rest.exchange(
            mascotasApiUrl + "/reportes",
            HttpMethod.GET,
            new HttpEntity<>(headers),
            new ParameterizedTypeReference<List<ReporteExternoDto>>() {}
        );
        List<ReporteExternoDto> reportes = respReportes.getBody();

        ResponseEntity<List<MascotaExternoDto>> respMascotas = rest.exchange(
            mascotasApiUrl + "/mascotas",
            HttpMethod.GET,
            new HttpEntity<>(headers),
            new ParameterizedTypeReference<List<MascotaExternoDto>>() {}
        );
        List<MascotaExternoDto> mascotas = respMascotas.getBody();
    Map<Long, MascotaExternoDto> mascotasPorId = mascotas.stream()
            .collect(Collectors.toMap(MascotaExternoDto::getId, Function.identity(), (existente, reemplazo) -> existente));

    System.out.println("DEBUG: Reportes totales recibidos: " + reportes.size());
    System.out.println("DEBUG: Mascotas totales recibidas: " + mascotas.size());

    List<ReporteExternoDto> perdidas = reportes.stream()
            .filter(r -> "PERDIDA".equalsIgnoreCase(r.getEstado())).collect(Collectors.toList());
    List<ReporteExternoDto> encontradas = reportes.stream()
            .filter(r -> "ENCONTRADA".equalsIgnoreCase(r.getEstado())).collect(Collectors.toList());

    System.out.println("DEBUG: Perdidas: " + perdidas.size() + " | Encontradas: " + encontradas.size());

    for (ReporteExternoDto p : perdidas) {
        MascotaExternoDto mascotaPerdida = mascotasPorId.get(p.getMascotaId());
        if (mascotaPerdida == null) {
            System.out.println("DEBUG: No se encontró la mascota para el reporte perdido " + p.getId());
            continue;
        }

        for (ReporteExternoDto e : encontradas) {
            MascotaExternoDto mascotaEncontrada = mascotasPorId.get(e.getMascotaId());
            if (mascotaEncontrada == null) {
                System.out.println("DEBUG: No se encontró la mascota para el reporte encontrado " + e.getId());
                continue;
            }
            
            System.out.println("DEBUG: Comparando P:" + p.getId() + " con E:" + e.getId());

            if (repository.existsByReportePerdidoIdAndReporteEncontradoId(p.getId(), e.getId())) {
                System.out.println("DEBUG: Match ya existe en DB, saltando...");
                continue;
            }

            if (sonCompatibles(mascotaPerdida, mascotaEncontrada)) {
                try {
                    System.out.println("DEBUG: Pidiendo ubicación a Geo para reportes: " + p.getId() + " y " + e.getId());

                    RestTemplate restTemp = new RestTemplate();
                    HttpHeaders h = new HttpHeaders();
                    h.set("Authorization", "Bearer " + token);

                    ResponseEntity<UbicacionExternoDto> respP = restTemp.exchange(
                        geolocalizacionApiUrl + "/api/geolocalizacion/marcadores/reporte/" + p.getId(),
                        HttpMethod.GET,
                        new HttpEntity<>(h),
                        UbicacionExternoDto.class
                    );
                    ResponseEntity<UbicacionExternoDto> respE = restTemp.exchange(
                        geolocalizacionApiUrl + "/api/geolocalizacion/marcadores/reporte/" + e.getId(),
                        HttpMethod.GET,
                        new HttpEntity<>(h),
                        UbicacionExternoDto.class
                    );

                    UbicacionExternoDto locP = respP.getBody();
                    UbicacionExternoDto locE = respE.getBody();

                    double distancia = calcularDistancia(locP.getLatitudOfuscada(), locP.getLongitudOfuscada(), locE.getLatitudOfuscada(), locE.getLongitudOfuscada());
                    System.out.println("DEBUG: Distancia calculada: " + distancia + " km");

                    if (distancia <= 10.0) {
                        Coincidencia coincidencia = new Coincidencia();
                        coincidencia.setReportePerdidoId(p.getId());
                        coincidencia.setReporteEncontradoId(e.getId());
                        coincidencia.setPorcentajeSimilitud(calcularSimilitud(distancia));
                        repository.save(coincidencia);
                        try {
                            repository.flush();
                        } catch (Exception flushEx) {
                            System.out.println("WARN: flush falló: " + flushEx.getMessage());
                        }
                        System.out.println("¡MATCH GUARDADO!");
                    }
                } catch (Exception ex) {
                    System.out.println("ERROR DEBUG: No se pudo obtener ubicación o calcular distancia: " + ex.getMessage());
                }
            }
        }
    }
}
    

    

    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    private boolean sonCompatibles(MascotaExternoDto perdida, MascotaExternoDto encontrada) {
        return perdida.getEspecie() != null
                && encontrada.getEspecie() != null
                && perdida.getRaza() != null
                && encontrada.getRaza() != null
                && perdida.getEspecie().equalsIgnoreCase(encontrada.getEspecie())
                && perdida.getRaza().equalsIgnoreCase(encontrada.getRaza());
    }

    private double calcularSimilitud(double distanciaKm) {
        return Math.max(0.0, 100.0 - (distanciaKm * 5.0));
    }



    

}
