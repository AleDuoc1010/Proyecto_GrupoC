package grupoC.mascotas.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import grupoC.mascotas.model.ImagenMascota;
import grupoC.mascotas.repository.ImagenMascotaRepository;

@Service
public class ImagenMascotaService {

    @Autowired
    private ImagenMascotaRepository imagenRepository;

    private final String DIRECTORIO_SUBIDA = "/app/uploads/imagenes/";
    private final String BASE_URL = "http://localhost:8081/imagenes/";

    public ImagenMascota guardarImagen(MultipartFile archivo, Long mascotaId, String contexto, Long reporteId, Boolean esPrincipal) throws IOException {
        if (archivo.isEmpty()) {
            throw new IOException("El archivo está vacío");
        }

        Path rutaDirectorio = Paths.get(DIRECTORIO_SUBIDA);
        if (!Files.exists(rutaDirectorio)) {
            Files.createDirectories(rutaDirectorio);
        }

        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();
        Path rutaDestino = rutaDirectorio.resolve(nombreArchivo);

        Files.copy(archivo.getInputStream(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);

        ImagenMascota imagen = new ImagenMascota();
        imagen.setMascotaId(mascotaId);
        imagen.setUrl(BASE_URL + nombreArchivo);
        imagen.setContexto(contexto != null ? contexto : "PERFIL");
        imagen.setReporteId(reporteId);
        imagen.setEsPrincipal(esPrincipal != null ? esPrincipal : false);

        return imagenRepository.save(imagen);
    }

}
