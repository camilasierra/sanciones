package co.siri.posesiones.repositories;

import co.siri.posesiones.entities.TipoEstadoTramitePosesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VerTramiteEnComiteRepository extends JpaRepository<TipoEstadoTramitePosesion, Long> {

    /*Listado  de estados posibles para mostrar*/
    @Query(value = "SELECT t.*, t.rowid FROM TIPOESTADOTRAMITEPOSESION t WHERE t.idtipoestadotramiteposesion IN (6, 8, 10, 16, 17, 18) ORDER BY orden", nativeQuery = true)
    List<TipoEstadoTramitePosesion> obtenerEstadosTramite();
}
