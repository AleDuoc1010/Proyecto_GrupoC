package grupoC.mascotas.dto;

import grupoC.mascotas.model.Estado;

public record MascotaRequestDTO(
        String nombre,
        String especie,
        String raza,
        int edad,
        String sexo,
        String color,
        String descripcion,
        Estado estado
) {}
