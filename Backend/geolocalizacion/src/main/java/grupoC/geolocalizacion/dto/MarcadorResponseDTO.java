package grupoC.geolocalizacion.dto;

import java.time.LocalDateTime;

public record MarcadorResponseDTO(
    Long id,
    Long reporteId,
    String tipoMarcador,
    Double latitudOfuscada,
    Double longitudOfuscada,
    LocalDateTime fechaHoraRegistro
) {}
