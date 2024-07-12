package co.siri.posesiones.repositories;

import co.siri.posesiones.entities.MensajeRevisionTramite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRevisionTramiteRepository  extends JpaRepository<MensajeRevisionTramite, Long> {
    @Query(value = "SELECT mrt.idmensajerevisiontramite, mrt.idtramiteposesion, DBMS_LOB.SUBSTR(mrt.texto, 4000, 1) AS texto, mrt.idtipodestinomensaje, mrt.indborrado,\n" +
            "            atp.NOMBRE , mrt.ROL , mrt.FECHANOTIFICACION\n" +
            "            FROM MENSAJEREVISIONTRAMITE mrt\n" +
            "            JOIN asignaciontramiteanalista ata ON mrt.idtramiteposesion = ata.idtramiteposesion\n" +
            "            JOIN analistatramiteposesion atp ON ata.idanalistatramiteposesion = atp.idanalistatramiteposesion\n" +
            "            WHERE mrt.idtramiteposesion = :idTramitePosesion ORDER BY mrt.IDMENSAJEREVISIONTRAMITE DESC", nativeQuery = true)
    List<Object[]> getMensajesRevisionTramite(@Param("idTramitePosesion") int idTramitePosesion);

}
