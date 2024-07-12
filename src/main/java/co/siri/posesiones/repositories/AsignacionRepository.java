package co.siri.posesiones.repositories;

import co.siri.posesiones.entities.AsignacionTramiteAnalistas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AsignacionRepository extends JpaRepository<AsignacionTramiteAnalistas, Long> {

    @Query(value = "SELECT  CASE WHEN (COUNT(1) > 0) THEN 'true' ELSE 'false' END FROM asignaciontramiteanalista WHERE IDTRAMITEPOSESION =:idTramitePosesion AND IDANALISTATRAMITEPOSESION = :idAnalistaTramitePosesion", nativeQuery = true)
    boolean findAsignacionByIdTramitePosesionAndIdAnalistaTramitePosesion(Long idTramitePosesion, Long idAnalistaTramitePosesion);
}
