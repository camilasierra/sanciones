package co.siri.posesiones.repositories;

import co.siri.posesiones.dtos.MensajeEnTramiteDto;
import co.siri.posesiones.entities.MensajeRevisionTramite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<MensajeRevisionTramite, Long>  {

    @Query(value = "SELECT * FROM MENSAJEREVISIONTRAMITE mrt WHERE mrt.INDBORRADO = 'S'", nativeQuery = true)
    List<MensajeRevisionTramite> mensajesEnBorrado();

    /*@Query(value = "SELECT mrt.idmensajerevisiontramite, mrt.idtramiteposesion, mrt.texto, mrt.idtipodestinomensaje \n" +
            "        FROM mensajerevisiontramite mrt WHERE (mrt.idtipodestinomensaje IN (1,2) OR mrt.idtipodestinomensaje = 4)", nativeQuery = true)*/
    /*@Query(value = "SELECT * FROM mensajerevisiontramite mrt WHERE mrt.idtipodestinomensaje = :idTipoDestinoMensaje)", nativeQuery = true)
    List<MensajeEnTramiteDto> mensajesEnTramite(String idTipoDestinoMensaje);*/

    List<MensajeRevisionTramite> findMensajeRevisionTramiteByIdTipoDestinoMensajeOrderByIdMensajeRevisionTramiteDesc(Long idTipoDestino);

    /*@Query(value = "UPDATE MENSAJEREVISIONTRAMITE SET INDBORRADO = 'S' WHERE IDMENSAJEREVISIONTRAMITE = :indBorrado", nativeQuery = true)
    void eliminarMensaje(@Param("indBorrado") String indBorrado);*/

}
