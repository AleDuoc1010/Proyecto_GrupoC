package grupoC.motor_coincidencias.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "coincidencias")
@Data
public class Coincidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reportePerdidoId;
    private Long reporteEncontradoId;

    private Double porcentajeSimilitud;

    @Enumerated(EnumType.STRING)
    private EstadoCoincidencia estado;

    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoCoincidencia.PENDIENTE;
        }
    }

}
