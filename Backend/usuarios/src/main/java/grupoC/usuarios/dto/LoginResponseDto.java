package grupoC.usuarios.dto;

public record LoginResponseDto(
    String token,
    UsuarioResponseDto usuario
) {}
