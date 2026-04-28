package grupoC.mascotas.repository;

import grupoC.mascotas.model.Estado;
import grupoC.mascotas.model.Mascota;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    Optional<Mascota> findByNombreAndEstado(String nombre, Estado estado);

    List<Mascota> findAllByNombreAndEstado(String nombre, Estado estado);

    List<Mascota> findAllByUsuarioUuid(String usuarioUuid);
}
