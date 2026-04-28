package grupoC.mascotas.dto;

import grupoC.mascotas.model.Estado;

public record ReporteRequestDTO(
    Long mascotaId,
    String tipoReporte,
    Estado estado
) {

}
