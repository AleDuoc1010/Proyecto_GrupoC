package grupoC.geolocalizacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grupoC.geolocalizacion.model.MarcadorEspacial;

@Repository
public interface MarcadorEspacialRepository extends JpaRepository<MarcadorEspacial, Long> {

    Optional<MarcadorEspacial> findByReporteId(Long reporteId);

}
