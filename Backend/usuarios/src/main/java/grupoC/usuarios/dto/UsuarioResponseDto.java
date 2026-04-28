package grupoC.usuarios.dto;

import grupoC.usuarios.model.Rol;

public record UsuarioResponseDto(
    String uuid,
    String nombre,
    String email,
    String phone,
    Rol rol
) {

}
