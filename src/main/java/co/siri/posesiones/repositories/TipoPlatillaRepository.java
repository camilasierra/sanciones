package co.siri.posesiones.repositories;

import co.siri.posesiones.entities.TipoPlantillaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoPlatillaRepository extends JpaRepository<TipoPlantillaEntity, Long> {

    public Optional<TipoPlantillaEntity> findByNombre(String nombre);
}
