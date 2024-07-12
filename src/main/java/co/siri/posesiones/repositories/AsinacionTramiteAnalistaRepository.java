package co.siri.posesiones.repositories;

import co.siri.posesiones.entities.AsignacionTramiteAnalistas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AsinacionTramiteAnalistaRepository  extends JpaRepository<AsignacionTramiteAnalistas, Long> {

    @Query(value = "SELECT * FROM ASIGNACIONTRAMITEANALISTA WHERE IDTRAMITEPOSESION = :idTramitePosesion", nativeQuery = true)
    AsignacionTramiteAnalistas findByIdTramitePosesion(@Param("idTramitePosesion") Long idTramitePosesion);
}
