package co.siri.posesiones.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.siri.posesiones.dtos.TextoEvaluacionProjection;
import co.siri.posesiones.entities.TextoEvaluacionTramite;

/**
 * Repositorio para gestionar las consultas relacionadas con los textos de evaluación.
 * Proporciona métodos para obtener textos de evaluación basados en varios filtros.
 *
 * <p>Autor: John Macias</p>
 */
@Repository
public interface TextoEvaluacionTramiteRepository extends JpaRepository<TextoEvaluacionTramite, Long> {

    /**
     * Obtiene una lista de los textos de evaluación basados en los filtros proporcionados.
     *
     * @param idTramite el ID del trámite de posesión, puede ser nulo
     * @param idTipoTexto el ID del tipo de texto, puede ser nulo
     * @param idSeccionTexto el ID de la sección del mensaje, puede ser nulo
     * @return una lista de los textos de evaluación
     */
    @Query(value =
            """
            SELECT
            TSTP.nombre AS titulo,
            TET.IDTEXTOEVALUACIONTRAMITE AS idTextoTramite,
            TET.IDTRAMITEPOSESION AS idTramitePosesion,
            TET.IDTIPOTEXTOEVALUACIONTRAMITE AS idTipoTextoTramite,
            TET.IDTIPOSECCIONTRAMITEPOSESION AS idTipoSeccionTramite,
            TET.TEXTO AS texto,
            TET.IDUSUARIO AS idUsuario,
            TET.IPCLIENTE AS ipCliente
            FROM TEXTOEVALUACIONTRAMITE TET
            JOIN tipotextoevaluaciontramite TTET on TET.idtipotextoevaluaciontramite = TTET.idtipotextoevaluaciontramite
            JOIN TIPOSECCIONTRAMITEPOSESION TSTP on TET.idtiposecciontramiteposesion = TSTP.idtiposecciontramiteposesion
            WHERE 
            (TET.IDTRAMITEPOSESION = :idTramite OR :idTramite IS NULL)
            AND (TET.IDTIPOTEXTOEVALUACIONTRAMITE = :idTipoTexto OR :idTipoTexto IS NULL)
            AND (TET.IDTIPOSECCIONTRAMITEPOSESION = :idSeccionTexto OR :idSeccionTexto IS NULL)
            ORDER BY TSTP.ORDEN
            """, nativeQuery = true)
    List<TextoEvaluacionProjection> obtenerMensajesTramite(
            @Param("idTramite") Long idTramite,
            @Param("idTipoTexto") Long idTipoTexto,
            @Param("idSeccionTexto") Long idSeccionTexto);
    
    List<TextoEvaluacionTramite> findByIdTramitePosesionAndIdTipoTextoTramite(Long idTramitePosesion,Long idTipoTextoTramite);
}