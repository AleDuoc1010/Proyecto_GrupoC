package grupoC.mascotas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import grupoC.mascotas.model.Reporte;

public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    List<Reporte> findAllByUsuarioUuid(String usuarioUuid);

    List<Reporte> findAllByMascotaId(Long mascotaId);

}
