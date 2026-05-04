package grupoC.mascotas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import grupoC.mascotas.model.ImagenMascota;

public interface ImagenMascotaRepository extends JpaRepository<ImagenMascota, Long> {

    List<ImagenMascota> findByMascotaId(Long mascotaId);

}
