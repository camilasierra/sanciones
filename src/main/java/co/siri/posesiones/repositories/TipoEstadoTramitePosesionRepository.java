package co.siri.posesiones.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.siri.posesiones.entities.TipoEstadoTramitePosesion;

/**
 * Repositorio de la entidad TipoEstadoTramitePosesion
 */
@Repository
public interface TipoEstadoTramitePosesionRepository extends JpaRepository<TipoEstadoTramitePosesion, Long> {

        @Query(value = "SELECT * FROM TIPOESTADOTRAMITEPOSESION WHERE ACTIVO = 1", nativeQuery = true)
        List<TipoEstadoTramitePosesion> findAllActive();
}
