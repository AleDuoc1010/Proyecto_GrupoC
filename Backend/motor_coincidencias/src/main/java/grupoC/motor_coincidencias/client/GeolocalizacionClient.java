package grupoC.motor_coincidencias.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import grupoC.motor_coincidencias.dto.UbicacionExternoDto;

@FeignClient(name = "geolocalizacion-service", url = "${GEOLOCALIZACION_API_URL:http://localhost:8082}", configuration = grupoC.motor_coincidencias.config.FeignClientAuthConfig.class)
public interface GeolocalizacionClient {

    @GetMapping("/api/geolocalizacion/marcadores/reporte/{reporteId}")
    UbicacionExternoDto obtenerUbicacionPorReporte(@PathVariable("reporteId") Long reporteId);

}
