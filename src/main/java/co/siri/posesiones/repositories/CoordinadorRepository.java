package co.siri.posesiones.repositories;

import co.siri.posesiones.dtos.EscritorioTramitesResponseDTO;
import co.siri.posesiones.entities.TramitePosesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoordinadorRepository extends JpaRepository<TramitePosesion, Long> {
    @Query(value = "SELECT DISTINCT \n" +
            "vp.ENTIDADPUBLICA AS entidadPublica,\n" +
            "tp.IDTIPOESTADOTRAMITEPOSESION AS idTipoEstadoTramitePosesion, \n" +
            "tp.IDTRAMITEPOSESION AS idTramitePosesion, \n" +
            "tp.numeroradicacion AS numeroRadicado, \n" +
            "ent.IDENTIDAD AS idEntidad, \n" +
            "ent.SIGLA AS entidad,\n" +
            "t.NOMBRE  AS cargo, \n" +
            "p.PRIMERNOMBRE AS primerNombre, \n" +
            "p.SEGUNDONOMBRE AS segundoNombre,\n" +
            "p.PRIMERAPELLIDO AS primerApellido, \n" +
            "p.SEGUNDOAPELLIDO AS segundoApellido, \n" +
            "DOC.NUMERO AS identificacion, \n" +
            "tpet.NOMBRE AS estadoDelTramite, \n" +
            "uig.DIASTERMINo AS diasTermino, \n" +
            "uig.DIASHABILESSFC AS diasHabilesSFC,\n" +
            "uig.DIASTERMINO - uig.DIASHABILESSFC AS vencimiento, \n" +
            "tp.INDNUEVO  AS nuevo \n" +
            "FROM \n" +
            "  tramiteposesion tp  \n" +
            "  left join V_PRIORIDADTRAMITEPOSESION vp on tp.idtramiteposesion = vp.IDTRAMITEPOSESION \n" +
            "  INNER JOIN PERSONA p ON tp.IDPERSONA = p.IDPERSONA \n" +
            "  INNER JOIN TIPOCARGO t  ON tp.IDTIPOCARGO  =t.IDTIPOCARGO  \n" +
            "  INNER JOIN TIPOESTADOTRAMITEPOSESION tpet ON tp.IDTIPOESTADOTRAMITEPOSESION = tpet.IDTIPOESTADOTRAMITEPOSESION \n" +
            "  INNER JOIN ENTIDAD ent ON tp.IDENTIDAD = ent.IDENTIDAD \n" +
            "  INNER JOIN DOCUMENTOPERSONA doc ON p.IDPERSONA = doc.IDPERSONA \n" +
            "  INNER JOIN v_ig_tramiteposesion_ultimo uig ON tp.idtramiteposesion = uig.idtramiteposesion\n" +
            "  INNER JOIN ASIGNACIONTRAMITEANALISTA a ON tp.IDTRAMITEPOSESION = a.IDTRAMITEPOSESION \n" +
            "  INNER JOIN ANALISTATRAMITEPOSESION atp ON a.IDANALISTATRAMITEPOSESION = ATP.IDANALISTATRAMITEPOSESION \n" +
            "WHERE \n" +
            "(DOC.NUMERO  LIKE '%' || :identificacion || '%' OR :identificacion IS NULL)\n" +
            "AND (TRANSLATE(UPPER(tp.NUMERORADICACION), 'ÁÉÍÓÚ', 'AEIOU') LIKE UPPER('%' || TRANSLATE(:numeroradicado, 'ÁÉÍÓÚ', 'AEIOU') || '%') OR :numeroradicado IS NULL) \n" +
            "   AND (TRANSLATE(UPPER(ent.SIGLA), 'ÁÉÍÓÚ', 'AEIOU') LIKE '%' || TRANSLATE(UPPER(:entidad), 'ÁÉÍÓÚ', 'AEIOU') || '%' OR :entidad IS NULL)\n\n" +
            "AND (TRANSLATE(UPPER(p.PRIMERNOMBRE || p.SEGUNDONOMBRE || p.PRIMERAPELLIDO || p.SEGUNDOAPELLIDO || ( p.PRIMERNOMBRE || p.PRIMERAPELLIDO )), 'ÁÉÍÓÚ', 'AEIOU') \n" +
            "\tLIKE UPPER('%' || :candidato || '%') OR :candidato IS NULL)\n" +
            "AND (TRANSLATE(UPPER(tpet.NOMBRE), 'ÁÉÍÓÚ', 'AEIOU') LIKE TRANSLATE(UPPER(:estadoTramite), 'ÁÉÍÓÚ', 'AEIOU') OR :estadoTramite IS NULL) \n" +
            "AND (t.NOMBRE = :cargo OR :cargo IS NULL) \n" +
            "AND tp.idTipoCargo BETWEEN 1 AND 5\n" +
            "AND tpet.idTipoEstadoTramitePosesion BETWEEN 1 AND 5\n" +
            "    order by vp.entidadpublica desc nulls last, vencimiento ASC", nativeQuery = true)
    List<EscritorioTramitesResponseDTO> asignados(
            @Param("identificacion") String identificacion,
            @Param("numeroradicado") String numeroradicado,
            @Param("entidad") String entidad,
            @Param("candidato") String candidato,
            @Param("estadoTramite") String estadoTramite,
            @Param("cargo") String cargo
    );

    @Query(value = "SELECT DISTINCT \n" +
            "vp.ENTIDADPUBLICA AS entidadPublica,\n" +
            "tp.IDTIPOESTADOTRAMITEPOSESION AS idTipoEstadoTramitePosesion, \n" +
            "tp.IDTRAMITEPOSESION AS idTramitePosesion, \n" +
            "tp.numeroradicacion AS numeroRadicado, \n" +
            "ent.IDENTIDAD AS idEntidad, \n" +
            "ent.SIGLA AS entidad,\n" +
            "t.NOMBRE  AS cargo, \n" +
            "p.PRIMERNOMBRE AS primerNombre, \n" +
            "p.SEGUNDONOMBRE AS segundoNombre,\n" +
            "p.PRIMERAPELLIDO AS primerApellido, \n" +
            "p.SEGUNDOAPELLIDO AS segundoApellido,\n" +
            "DOC.NUMERO AS identificacion, \n" +
            "tpet.NOMBRE AS estadoDelTramite, \n" +
            "uig.DIASTERMINo AS diasTermino, \n" +
            "uig.DIASHABILESSFC AS diasHabilesSFC,\n" +
            "uig.DIASTERMINO - uig.DIASHABILESSFC AS vencimiento, \n" +
            "   tp.INDNUEVO  AS nuevo\n" +
            "FROM \n" +
            "  tramiteposesion tp  \n" +
            "  left join V_PRIORIDADTRAMITEPOSESION vp on tp.idtramiteposesion = vp.IDTRAMITEPOSESION \n" +
            "  INNER JOIN PERSONA p ON tp.IDPERSONA = p.IDPERSONA \n" +
            "  INNER JOIN TIPOCARGO t  ON tp.IDTIPOCARGO  =t.IDTIPOCARGO  \n" +
            "  INNER JOIN TIPOESTADOTRAMITEPOSESION tpet ON tp.IDTIPOESTADOTRAMITEPOSESION = tpet.IDTIPOESTADOTRAMITEPOSESION \n" +
            "  INNER JOIN ENTIDAD ent ON tp.IDENTIDAD = ent.IDENTIDAD \n" +
            "  INNER JOIN DOCUMENTOPERSONA doc ON p.IDPERSONA = doc.IDPERSONA \n" +
            "  INNER JOIN v_ig_tramiteposesion_ultimo uig ON tp.idtramiteposesion = uig.idtramiteposesion \n" +
            "WHERE\n" +
            "tp.IDTRAMITEPOSESION NOT IN (\n" +
            "   SELECT tp.IDTRAMITEPOSESION  FROM TRAMITEPOSESION tp\n" +
            "   INNER JOIN ASIGNACIONTRAMITEANALISTA a ON tp.IDTRAMITEPOSESION = a.IDTRAMITEPOSESION \n" +
            "   INNER JOIN ANALISTATRAMITEPOSESION atp ON a.IDANALISTATRAMITEPOSESION = ATP.IDANALISTATRAMITEPOSESION) \n" +
            "AND (DOC.NUMERO  LIKE '%' || :identificacion || '%' OR :identificacion IS NULL)\n" +
            "AND (TRANSLATE(UPPER(tp.NUMERORADICACION), 'ÁÉÍÓÚ', 'AEIOU') LIKE UPPER('%' || TRANSLATE(:numeroradicado, 'ÁÉÍÓÚ', 'AEIOU') || '%') OR :numeroradicado IS NULL)\n" +
            "   AND (TRANSLATE(UPPER(ent.SIGLA), 'ÁÉÍÓÚ', 'AEIOU') LIKE '%' || TRANSLATE(UPPER(:entidad), 'ÁÉÍÓÚ', 'AEIOU') || '%' OR :entidad IS NULL)\n\n" +
            "   AND (TRANSLATE(UPPER(p.PRIMERNOMBRE || p.SEGUNDONOMBRE || p.PRIMERAPELLIDO || p.SEGUNDOAPELLIDO || (p.PRIMERNOMBRE || p.PRIMERAPELLIDO)), 'ÁÉÍÓÚ', 'AEIOU') \n" +
            "    LIKE '%' || TRANSLATE(UPPER(:candidato), 'ÁÉÍÓÚ', 'AEIOU') || '%' OR :candidato IS NULL) \n" +
            "AND (TRANSLATE(UPPER(tpet.NOMBRE), 'ÁÉÍÓÚ', 'AEIOU') LIKE TRANSLATE(UPPER(:estadoTramite), 'ÁÉÍÓÚ', 'AEIOU') OR :estadoTramite IS NULL) \n" +
            "AND (t.NOMBRE = :cargo OR :cargo IS NULL) \n" +
            "AND tp.idTipoCargo BETWEEN 1 AND 5\n" +
            "AND tpet.idTipoEstadoTramitePosesion BETWEEN 1 AND 5\n" +
            "    order by vp.entidadpublica desc nulls last, vencimiento ASC", nativeQuery = true)
    List<EscritorioTramitesResponseDTO> noAsignados(
            @Param("identificacion") String identificacion,
            @Param("numeroradicado") String numeroradicado,
            @Param("entidad") String entidad,
            @Param("candidato") String candidato,
            @Param("estadoTramite") String estadoTramite,
            @Param("cargo") String cargo
    );

    @Query(value = "SELECT DISTINCT \n" +
            "vp.ENTIDADPUBLICA AS entidadPublica,\n" +
            "tp.IDTIPOESTADOTRAMITEPOSESION AS idTipoEstadoTramitePosesion, \n" +
            "tp.IDTRAMITEPOSESION AS idTramitePosesion, \n" +
            "tp.numeroradicacion AS numeroRadicado, \n" +
            "ent.IDENTIDAD AS idEntidad, \n" +
            "ent.SIGLA AS entidad,\n" +
            "t.NOMBRE  AS cargo, \n" +
            "p.PRIMERNOMBRE AS primerNombre, \n" +
            "p.SEGUNDONOMBRE AS segundoNombre,\n" +
            "p.PRIMERAPELLIDO AS primerApellido, \n" +
            "p.SEGUNDOAPELLIDO AS segundoApellido, \n" +
            "DOC.NUMERO AS identificacion, \n" +
            "tpet.NOMBRE AS estadoDelTramite, \n" +
            "uig.DIASTERMINo AS diasTermino, \n" +
            "uig.DIASHABILESSFC AS diasHabilesSFC,\n" +
            "uig.DIASTERMINO - uig.DIASHABILESSFC AS vencimiento, \n" +
            "tp.INDNUEVO  AS nuevo \n" +
            "FROM \n" +
            "  tramiteposesion tp  \n" +
            "  left join V_PRIORIDADTRAMITEPOSESION vp on tp.idtramiteposesion = vp.IDTRAMITEPOSESION \n" +
            "  INNER JOIN PERSONA p ON tp.IDPERSONA = p.IDPERSONA \n" +
            "  INNER JOIN TIPOCARGO t  ON tp.IDTIPOCARGO  =t.IDTIPOCARGO  \n" +
            "  INNER JOIN TIPOESTADOTRAMITEPOSESION tpet ON tp.IDTIPOESTADOTRAMITEPOSESION = tpet.IDTIPOESTADOTRAMITEPOSESION \n" +
            "  INNER JOIN ENTIDAD ent ON tp.IDENTIDAD = ent.IDENTIDAD \n" +
            "  INNER JOIN DOCUMENTOPERSONA doc ON p.IDPERSONA = doc.IDPERSONA \n" +
            "  INNER JOIN v_ig_tramiteposesion_ultimo uig ON tp.idtramiteposesion = uig.idtramiteposesion\n" +
            "  INNER JOIN ASIGNACIONTRAMITEANALISTA a ON tp.IDTRAMITEPOSESION = a.IDTRAMITEPOSESION \n" +
            "  INNER JOIN ANALISTATRAMITEPOSESION atp ON a.IDANALISTATRAMITEPOSESION = ATP.IDANALISTATRAMITEPOSESION \n" +
            "WHERE \n" +
            "(DOC.NUMERO  LIKE '%' || :identificacion || '%' OR :identificacion IS NULL)\n" +
            "AND (TRANSLATE(UPPER(tp.NUMERORADICACION), 'ÁÉÍÓÚ', 'AEIOU') LIKE UPPER('%' || TRANSLATE(:numeroradicado, 'ÁÉÍÓÚ', 'AEIOU') || '%') OR :numeroradicado IS NULL) \n" +
            "   AND (TRANSLATE(UPPER(ent.SIGLA), 'ÁÉÍÓÚ', 'AEIOU') LIKE '%' || TRANSLATE(UPPER(:entidad), 'ÁÉÍÓÚ', 'AEIOU') || '%' OR :entidad IS NULL)\n\n" +
            "AND (TRANSLATE(UPPER(p.PRIMERNOMBRE || p.SEGUNDONOMBRE || p.PRIMERAPELLIDO || p.SEGUNDOAPELLIDO || ( p.PRIMERNOMBRE || p.PRIMERAPELLIDO )), 'ÁÉÍÓÚ', 'AEIOU') \n" +
            "\tLIKE UPPER('%' || :candidato || '%') OR :candidato IS NULL)\n" +
            "AND (TRANSLATE(UPPER(tpet.NOMBRE), 'ÁÉÍÓÚ', 'AEIOU') LIKE TRANSLATE(UPPER(:estadoTramite), 'ÁÉÍÓÚ', 'AEIOU') OR :estadoTramite IS NULL) \n" +
            "AND (t.NOMBRE = :cargo OR :cargo IS NULL) \n" +
            "AND tp.idTipoCargo BETWEEN 1 AND 5\n" +
            "AND tpet.idTipoEstadoTramitePosesion BETWEEN 1 AND 5\n" +
            "    order by vp.entidadpublica desc nulls last, vencimiento ASC", nativeQuery = true)
    List<EscritorioTramitesResponseDTO> tramitesPrioridadesAnalista(
            @Param("identificacion") String identificacion,
            @Param("numeroradicado") String numeroradicado,
            @Param("entidad") String entidad,
            @Param("candidato") String candidato,
            @Param("estadoTramite") String estadoTramite,
            @Param("cargo") String cargo
    );
}
