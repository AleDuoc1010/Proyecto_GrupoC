package grupoC.mascotas.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import grupoC.mascotas.model.ImagenMascota;
import grupoC.mascotas.service.ImagenMascotaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/imagenes")
@RequiredArgsConstructor
public class ImagenMascotaController {

    private final ImagenMascotaService imagenService;

@PostMapping("/subir")
    public ResponseEntity<?> subirImagen(
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("mascotaId") Long mascotaId,
            @RequestParam(value = "contexto", defaultValue = "PERFIL") String contexto,
            @RequestParam(value = "reporteId", required = false) Long reporteId,
            @RequestParam(value = "esPrincipal", defaultValue = "false") Boolean esPrincipal) {
        
        try {
            ImagenMascota nuevaImagen = imagenService.guardarImagen(archivo, mascotaId, contexto, reporteId, esPrincipal);
            return new ResponseEntity<>(nuevaImagen, HttpStatus.CREATED);
            
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar la imagen en el servidor: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error procesando la solicitud: " + e.getMessage());
        }
    }

}
