package grupoC.mascotas.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import grupoC.mascotas.dto.MascotaRequestDTO;
import grupoC.mascotas.dto.MascotaResponseDTO;
import grupoC.mascotas.service.MascotaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mascotas")
@RequiredArgsConstructor
public class MascotaController {

private final MascotaService mascotaService;

    @PostMapping
    public ResponseEntity<MascotaResponseDTO> crearMascota(@RequestBody MascotaRequestDTO mascotaDto, Principal principal) {
        String usuarioUuid = principal.getName(); 
        MascotaResponseDTO nuevaMascota = mascotaService.registrarMascota(mascotaDto, usuarioUuid);
        return new ResponseEntity<>(nuevaMascota, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MascotaResponseDTO>> obtenerMascotas() {
        return ResponseEntity.ok(mascotaService.obtenerTodas());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> obtenerMascotaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.obtenerPorId(id));
    }

}
