package grupoC.geolocalizacion.dto;

public record MarcadorRequestDTO(
    Long reporteId,
    String tipoMarcador,
    Double latitud,
    Double longitud
) {}
