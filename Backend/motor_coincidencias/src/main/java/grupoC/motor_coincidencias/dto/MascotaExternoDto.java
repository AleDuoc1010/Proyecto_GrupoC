package grupoC.motor_coincidencias.dto;

import lombok.Data;

@Data
public class MascotaExternoDto {

    private Long id;
    private Long reporteId;
    private String nombre;
    private String especie;
    private String raza;
    private Integer edad;
    private String sexo;
    private String color;
    private String estado;

}
