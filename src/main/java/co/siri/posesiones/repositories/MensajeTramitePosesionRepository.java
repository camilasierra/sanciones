package co.siri.posesiones.repositories;

import co.siri.posesiones.entities.MensajeTramitePosesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar las consultas relacionadas con los mensajes de trámite de posesión.
 * Proporciona métodos para obtener mensajes basados en varios filtros.
 *
 * <p>Autor: John Macias</p>
 */
@Repository
public interface MensajeTramitePosesionRepository extends JpaRepository<MensajeTramitePosesion, Long> {

    /**
     * Obtiene una lista de mensajes de trámite de posesión basados en los filtros proporcionados.
     *
     * @param idTramite el ID del trámite de posesión, puede ser nulo
     * @param idTipoMensaje el ID del tipo de mensaje, puede ser nulo
     * @param idSeccionMensaje el ID de la sección del mensaje, puede ser nulo
     * @return una lista de mensajes de trámite de posesión
     */
    @Query(value =
            """
            SELECT *
            FROM MENSAJETRAMITEPOSESION MTP
            WHERE 
            (MTP.IDTRAMITEPOSESION = :idTramite OR :idTramite IS NULL)
            AND (MTP.IDTIPOMENSAJETRAMITEPOSESION = :idTipoMensaje OR :idTipoMensaje IS NULL)
            AND (MTP.IDTIPOSECCIONTRAMITEPOSESION = :idSeccionMensaje OR :idSeccionMensaje IS NULL)
            ORDER BY MTP.IDTRAMITEPOSESION DESC
            """, nativeQuery = true)
    List<MensajeTramitePosesion> obtenerMensajesTramite(
            @Param("idTramite") Long idTramite,
            @Param("idTipoMensaje") Long idTipoMensaje,
            @Param("idSeccionMensaje") Long idSeccionMensaje);
}

