package grupoC.motor_coincidencias.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grupoC.motor_coincidencias.model.Coincidencia;

@Repository
public interface CoincidenciaRepository extends JpaRepository<Coincidencia, Long> {

    boolean existsByReportePerdidoIdAndReporteEncontradoId(Long perdidoId, Long encontradoId);

    List<Coincidencia> findByReportePerdidoId(Long perdidoId);
    List<Coincidencia> findByReporteEncontradoId(Long encontradoId);
}
