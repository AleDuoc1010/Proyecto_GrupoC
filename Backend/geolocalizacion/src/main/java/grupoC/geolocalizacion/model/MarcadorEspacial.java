package grupoC.geolocalizacion.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "marcador_espacial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarcadorEspacial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reporte_id", nullable = false)
    private Long reporteId;

    @Column(name = "tipo_marcador", nullable = false)
    private String tipoMarcador;

    @Column(name = "latitud", nullable = false)
    private Double latitud;

    @Column(name = "longitud", nullable = false)
    private Double longitud;

    @Column(name = "fecha_hora_registro", nullable = false)
    private LocalDateTime fechaHoraRegistro;
}
