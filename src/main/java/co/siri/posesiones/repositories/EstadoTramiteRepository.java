package co.siri.posesiones.repositories;

import co.siri.posesiones.entities.TipoEstadoTramitePosesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstadoTramiteRepository extends JpaRepository<TipoEstadoTramitePosesion, Long> {
    @Query(value = "SELECT * \n" +
            "   FROM TIPOESTADOTRAMITEPOSESION tep \n" +
            "WHERE   tep.IDTIPOESTADOTRAMITEPOSESION BETWEEN 1 AND 5 \n" +
            "   AND tep.ACTIVO =1 \n" +
            "   ORDER BY ORDEN ", nativeQuery = true)
    List<TipoEstadoTramitePosesion> listTramite();

}
