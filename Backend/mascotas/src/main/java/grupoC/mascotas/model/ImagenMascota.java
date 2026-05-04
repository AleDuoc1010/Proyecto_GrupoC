package grupoC.mascotas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "imagenes_mascotas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImagenMascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long mascotaId;

    @Column(nullable = false)
    private String url;

    @Column(nullable =  false)
    private String contexto;

    @Column(nullable = true)
    private Long reporteId;

    @Column(nullable = false)
    private boolean esPrincipal;

}
