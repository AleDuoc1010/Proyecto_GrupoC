package grupoC.mascotas.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import grupoC.mascotas.dto.MascotaRequestDTO;
import grupoC.mascotas.dto.MascotaResponseDTO;
import grupoC.mascotas.model.Mascota;
import grupoC.mascotas.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MascotaService {

private final MascotaRepository mascotaRepository;

    public MascotaResponseDTO registrarMascota(MascotaRequestDTO dto, String usuarioUuid) {
        Mascota mascota = new Mascota();
        mascota.setNombre(dto.nombre());
        mascota.setEspecie(dto.especie());
        mascota.setRaza(dto.raza());
        mascota.setEdad(dto.edad());
        mascota.setSexo(dto.sexo());
        mascota.setColor(dto.color());
        mascota.setDescripcion(dto.descripcion());
        mascota.setEstado(dto.estado());
        mascota.setUsuarioUuid(usuarioUuid);
        
        Mascota mascotaGuardada = mascotaRepository.save(mascota);
        return mapToDto(mascotaGuardada);
    }

    public List<MascotaResponseDTO> obtenerTodas(){
        return mascotaRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<MascotaResponseDTO> obtenerPorUsuario(String usuarioUuid) {
        return mascotaRepository.findAllByUsuarioUuid(usuarioUuid)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public MascotaResponseDTO obtenerPorId(Long id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + id));
        return mapToDto(mascota);
    }

    private MascotaResponseDTO mapToDto(Mascota mascota) {
        return new MascotaResponseDTO(
                mascota.getId(),
                mascota.getUsuarioUuid(),
                mascota.getNombre(),
                mascota.getEspecie(),
                mascota.getRaza(),
                mascota.getEdad(),
                mascota.getSexo(),
                mascota.getColor(),
                mascota.getDescripcion(),
                mascota.getEstado()
        );
    }

}
