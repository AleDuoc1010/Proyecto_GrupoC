package grupoC.mascotas.dto;

import java.time.LocalDateTime;

import grupoC.mascotas.model.Estado;

public record ReporteResponseDTO(
    Long id,
    Long mascotaId,
    String usuarioUuid,
    String tipoReporte,
    LocalDateTime fechaSuceso,
    Estado estado
) {

}
