package co.siri.posesiones.repositories;

import co.siri.posesiones.dtos.AnalistaTramitePosesionDTO;
import co.siri.posesiones.dtos.AnalistaTramitesCargaDTO;
import co.siri.posesiones.dtos.CargaAnalistaDTO;
import co.siri.posesiones.dtos.EscritorioTramitesResponseDTO;
import co.siri.posesiones.entities.AnalistaTramitePosesion;
import co.siri.posesiones.entities.TramitePosesion;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalistaRepository extends JpaRepository<TramitePosesion, Long> {
    @Query(value = "SELECT DISTINCT \n" +
            "   vp.ENTIDADPUBLICA AS entidadPublica, \n" +
            "   tp.IDTIPOESTADOTRAMITEPOSESION AS idTipoEstadoTramitePosesion, \n" +
            "   tp.IDTRAMITEPOSESION AS idTramitePosesion, \n" +
            "   tp.numeroradicacion AS numeroRadicado, \n" +
            "   ent.IDENTIDAD AS idEntidad, \n" +
            "   ent.SIGLA AS entidad, \n" +
            "   t.NOMBRE  AS cargo, \n" +
            "   p.PRIMERNOMBRE AS primerNombre, \n" +
            "   p.SEGUNDONOMBRE AS segundoNombre,\n" +
            "   p.PRIMERAPELLIDO AS primerApellido, \n" +
            "   p.SEGUNDOAPELLIDO AS segundoApellido, \n" +
            "   doc.NUMERO AS identificacion,\n" +
            "   tpet.NOMBRE AS estadoDelTramite, \n" +
            "   uig.DIASTERMINo AS diasTermino, \n" +
            "   uig.DIASHABILESSFC AS diasHabilesSFC, \n" +
            "   uig.DIASTERMINO - uig.DIASHABILESSFC AS vencimiento, \n" +
            "   tp.INDNUEVO  AS nuevo\n" +
            "FROM \n" +
            "   tramiteposesion tp \n" +
            "   left join V_PRIORIDADTRAMITEPOSESION vp on tp.idtramiteposesion = vp.IDTRAMITEPOSESION \n" +
            "   INNER JOIN PERSONA p ON tp.IDPERSONA = p.IDPERSONA \n" +
            "   INNER JOIN TIPOCARGO t  ON tp.IDTIPOCARGO  =t.IDTIPOCARGO \n" +
            "   INNER JOIN TIPOESTADOTRAMITEPOSESION tpet ON tp.IDTIPOESTADOTRAMITEPOSESION = tpet.IDTIPOESTADOTRAMITEPOSESION \n" +
            "   INNER JOIN ENTIDAD ent ON tp.IDENTIDAD = ent.IDENTIDAD \n" +
            "   INNER JOIN DOCUMENTOPERSONA doc ON p.IDPERSONA = doc.IDPERSONA \n" +
            "   INNER JOIN v_ig_tramiteposesion_ultimo uig ON tp.idtramiteposesion = uig.idtramiteposesion \n" +
            "   INNER JOIN ASIGNACIONTRAMITEANALISTA a ON tp.IDTRAMITEPOSESION = a.IDTRAMITEPOSESION \n" +
            "   INNER JOIN ANALISTATRAMITEPOSESION atp ON a.IDANALISTATRAMITEPOSESION = ATP.IDANALISTATRAMITEPOSESION \n" +
            "WHERE \n" +
            "   ((tp.IDTIPOESTADOTRAMITEPOSESION  between 2 and 5) or \n" +
            "        (tp.idtipoestadotramiteposesion = 1 and tp.numeroradicacion is not null)) \n" +
            "   AND atp.LOGIN = :login \n" +
            "   AND (DOC.NUMERO  LIKE '%' || :identificacion || '%' OR :identificacion IS NULL)\n" +
            "   AND (TRANSLATE(UPPER(tp.NUMERORADICACION), 'ÁÉÍÓÚáéíóú', 'AEIOUaeiou') LIKE UPPER('%' || TRANSLATE(:numeroradicado, 'ÁÉÍÓÚ', 'AEIOU') || '%') OR :numeroradicado IS NULL) \n" +
            "   AND (TRANSLATE(UPPER(ent.SIGLA), 'ÁÉÍÓÚ', 'AEIOU') LIKE '%' || TRANSLATE(UPPER(:entidad), 'ÁÉÍÓÚ', 'AEIOU') || '%' OR :entidad IS NULL)\n\n" +
            "   AND (TRANSLATE(UPPER(p.PRIMERNOMBRE || p.SEGUNDONOMBRE || p.PRIMERAPELLIDO || p.SEGUNDOAPELLIDO || (p.PRIMERNOMBRE || p.PRIMERAPELLIDO)), 'ÁÉÍÓÚ', 'AEIOU') \n" +
            "    LIKE '%' || TRANSLATE(UPPER(:candidato), 'ÁÉÍÓÚ', 'AEIOU') || '%' OR :candidato IS NULL) \n" +
            "   AND (TRANSLATE(UPPER(tpet.NOMBRE), 'ÁÉÍÓÚ', 'AEIOU') LIKE UPPER('%' || TRANSLATE(:estadoTramite, 'ÁÉÍÓÚ', 'AEIOU') || '%') OR :estadoTramite IS NULL) \n" +
            "   AND (TRANSLATE(UPPER(t.NOMBRE), 'ÁÉÍÓÚ', 'AEIOU') LIKE UPPER('%' || TRANSLATE(:cargo, 'ÁÉÍÓÚ', 'AEIOU') || '%') OR :cargo IS NULL) \n" +
            "   AND tp.idTipoCargo BETWEEN 1 AND 5\n" +
            "   AND tpet.idTipoEstadoTramitePosesion BETWEEN 1 AND 5\n" +
            "       order by vp.entidadpublica desc nulls last, vencimiento ASC" , nativeQuery = true)
    List<EscritorioTramitesResponseDTO> filtroAvanzado(
            @Param("login") String login,
            @Param("identificacion") String identificacion,
            @Param("numeroradicado") String numeroradicado,
            @Param("entidad") String entidad,
            @Param("candidato") String candidato,
            @Param("estadoTramite") String estadoTramite,
            @Param("cargo") String cargo
    );

    @Query(value = " WITH AnalistasCarga AS (\n" +
            "    SELECT \n" +
            "        atp.IDENTIFICACION, \n" +
            "        atp.nombre, \n" +
            "        atp.IDTIPOANALISTA, \n" +
            "        COUNT(tp.IDTRAMITEPOSESION) AS contador\n" +
            "    FROM \n" +
            "        Tramiteposesion tp\n" +
            "    JOIN \n" +
            "        asignaciontramiteanalista ata ON tp.idtramiteposesion = ata.idtramiteposesion\n" +
            "    JOIN \n" +
            "        analistatramiteposesion atp ON ata.idanalistatramiteposesion = atp.idanalistatramiteposesion\n" +
            "    LEFT JOIN \n" +
            "        v_prioridadtramiteposesion vptp ON tp.idtramiteposesion = vptp.IDTRAMITEPOSESION\n" +
            "    WHERE \n" +
            "        tp.numeroradicacion IS NOT NULL \n" +
            "        AND atp.nombre IS NOT NULL\n" +
            "        AND tp.idtipoestadotramiteposesion <= 5\n" +
            "    GROUP BY \n" +
            "        atp.IDENTIFICACION, atp.nombre, atp.IDTIPOANALISTA\n" +
            "),\n" +
            "CargaMaxima AS (\n" +
            "    SELECT valor \n" +
            "    FROM parametros \n" +
            "    WHERE nombre = :tipoAnalista \n" +
            ")\n" +
            "SELECT \n" +
            "    atp.*, \n" +
            "    ac.contador\n" +
            "FROM \n" +
            "    analistatramiteposesion atp\n" +
            "JOIN \n" +
            "    AnalistasCarga ac ON atp.IDENTIFICACION = ac.IDENTIFICACION\n" +
            "JOIN \n" +
            "    CargaMaxima cm ON ac.contador > cm.valor\n" +
            "WHERE \n" +
            "    atp.ACTIVO = 1;", nativeQuery = true)
    List<AnalistaTramitePosesion> findByOrderByCargaActualAsc(@Param("tipoAnalista") String tipoAnalista);

@Query(value = "SELECT atp.* \n" +
        "\n" +
        "  FROM prioridadtramiteposesion ptp\n" +
        "\n" +
        "       JOIN prioridadporanalista ppa on ptp.idprioridadtramiteposesion = ppa.idprioridadtramiteposesion  \n" +
        "       JOIN analistatramiteposesion atp on ppa.idanalistatramiteposesion = atp.idanalistatramiteposesion\n" +
        "       JOIN v_prioridadtramiteposesion vppa on ppa.idprioridadtramiteposesion = vppa.IDPRIORIDADTRAMITEPOSESION\n" +
        "       WHERE IDPRIORIDADPORAnalista IS NOT NULL\n" +
        "       AND ptp.IDTRAMITEPOSESION = :tramitePosesion ", nativeQuery = true)
    AnalistaTramitePosesion findAnalistaPrioridadTramitePosesion(@Param("tramitePosesion") Long tramitePosesion);

@Query(value = " WITH TramitesPorCandidato AS (\n" +
        "    SELECT \n" +
        "        atp.IDANALISTATRAMITEPOSESION,\n" +
        "        atp.IDENTIFICACION, \n" +
        "        tp.IDPERSONA, \n" +
        "        COUNT(tp.IDTRAMITEPOSESION) AS num_tramites,\n" +
        "        CASE \n" +
        "            WHEN COUNT(tp.IDTRAMITEPOSESION) > 4 THEN 2.8 -- Máximo ponderación para 4 trámites (0.7 * 4)\n" +
        "            ELSE COUNT(tp.IDTRAMITEPOSESION) * 0.7\n" +
        "        END AS carga\n" +
        "    FROM \n" +
        "        Tramiteposesion tp\n" +
        "    LEFT JOIN \n" +
        "        asignaciontramiteanalista ata ON tp.idtramiteposesion = ata.idtramiteposesion\n" +
        "    LEFT JOIN \n" +
        "        analistatramiteposesion atp ON ata.idanalistatramiteposesion = atp.idanalistatramiteposesion\n" +
        "    WHERE \n" +
        "        tp.numeroradicacion IS NOT NULL\n" +
        "        AND atp.ACTIVO = 1\n" +
        "    GROUP BY \n" +
        "        atp.IDANALISTATRAMITEPOSESION, atp.IDENTIFICACION, tp.IDPERSONA\n" +
        "),\n" +
        "TramitesPorTipo AS (\n" +
        "    SELECT \n" +
        "        atp.IDANALISTATRAMITEPOSESION,\n" +
        "        atp.IDENTIFICACION, \n" +
        "        tp.IDTIPOCARGO, \n" +
        "        COUNT(tp.IDTRAMITEPOSESION) AS num_tramites,\n" +
        "        CASE \n" +
        "            WHEN COUNT(tp.IDTRAMITEPOSESION) > 5 THEN 5 -- Máximo ponderación para 5 trámites\n" +
        "            ELSE COUNT(tp.IDTRAMITEPOSESION)\n" +
        "        END AS carga\n" +
        "    FROM \n" +
        "        Tramiteposesion tp\n" +
        "    LEFT JOIN \n" +
        "        asignaciontramiteanalista ata ON tp.idtramiteposesion = ata.idtramiteposesion\n" +
        "    JOIN \n" +
        "        analistatramiteposesion atp ON ata.idanalistatramiteposesion = atp.idanalistatramiteposesion\n" +
        "    WHERE \n" +
        "        tp.numeroradicacion IS NOT NULL\n" +
        "        AND atp.ACTIVO = 1\n" +
        "    GROUP BY \n" +
        "        atp.IDANALISTATRAMITEPOSESION, atp.IDENTIFICACION, tp.IDTIPOCARGO\n" +
        "),\n" +
        "CargaTotal AS (\n" +
        "    SELECT \n" +
        "        IDENTIFICACION,\n" +
        "        SUM(carga) AS carga_total\n" +
        "    FROM (\n" +
        "        SELECT IDENTIFICACION, carga FROM TramitesPorCandidato\n" +
        "        UNION ALL\n" +
        "        SELECT IDENTIFICACION, carga FROM TramitesPorTipo\n" +
        "    ) cargas\n" +
        "    GROUP BY IDENTIFICACION\n" +
        "),\n" +
        "AnalistasActivosConCarga AS (\n" +
        "    SELECT \n" +
        "        atp.IDANALISTATRAMITEPOSESION,\n" +
        "        atp.IDENTIFICACION,\n" +
        "        COALESCE(ct.carga_total, 0) AS carga_total\n" +
        "    FROM \n" +
        "        analistatramiteposesion atp\n" +
        "    LEFT JOIN \n" +
        "        CargaTotal ct ON atp.IDENTIFICACION = ct.IDENTIFICACION\n" +
        "    WHERE \n" +
        "        atp.ACTIVO = 1 \n" +
        "        AND atp.IDTIPOANALISTA = :tipoAnalista \n" +
        ")\n" +
        "SELECT \n" +
        "    aa.IDANALISTATRAMITEPOSESION,\n" +
        "    aa.IDENTIFICACION, \n" +
        "    aa.carga_total,\n" +
        "    (SELECT valor FROM parametros WHERE nombre = :parametro) - aa.carga_total AS disponibilidad\n" +
        "FROM \n" +
        "    AnalistasActivosConCarga aa\n" +
        "WHERE \n" +
        "    aa.carga_total < (SELECT valor FROM parametros WHERE nombre = :parametro) ", nativeQuery = true)
    List<Object[]> findCargaAnalistas(@Param("parametro") String parametro, @Param("tipoAnalista") Integer tipoAnalista);

    @Query(value = " WITH TramiteSeleccionado AS ( \n" +
            "    SELECT\n" +
            "        tp.*,\n" +
            "        atp.IDENTIFICACION, \n" +
            "        atp.NOMBRE, \n" +
            "        ROW_NUMBER() OVER (PARTITION BY ata.idanalistatramiteposesion \n" +
            "                           ORDER BY tp.fechaprimeratransmision ASC, \n" +
            "                                    CASE WHEN vptp.ENTIDADPUBLICA IS NULL THEN 9999 ELSE vptp.ENTIDADPUBLICA  END ASC) AS rn \n" +
            "    FROM \n" +
            "        Tramiteposesion tp\n" +
            "    JOIN \n" +
            "        asignaciontramiteanalista ata ON tp.idtramiteposesion = ata.idtramiteposesion\n" +
            "    LEFT JOIN \n" +
            "        analistatramiteposesion atp ON ata.idanalistatramiteposesion = atp.idanalistatramiteposesion\n" +
            "    LEFT JOIN \n" +
            "        v_prioridadtramiteposesion vptp ON tp.idtramiteposesion = vptp.IDTRAMITEPOSESION\n" +
            "), \n" +
            "TramiteConCarga AS ( \n" +
            "    SELECT \n" +
            "        ts.idtramiteposesion, \n" +
            "        ts.identificacion, \n" +
            "        ts.nombre, \n" +
            "        ts.idpersona, \n" +
            "         COUNT(tp.IDTRAMITEPOSESION) AS num_tramites, \n" +
            "        CASE \n" +
            "            WHEN ts.IDPERSONA IS NOT NULL THEN \n" +
            "                CASE \n" +
            "                    WHEN COUNT(tp.IDTRAMITEPOSESION) > 4 THEN 2.8 \n" +
            "                    ELSE COUNT(tp.IDTRAMITEPOSESION) * 0.7 \n" +
            "                END \n" +
            "            ELSE \n" +
            "                CASE \n" +
            "                    WHEN COUNT(tp.IDTRAMITEPOSESION) > 5 THEN 5 \n" +
            "                    ELSE COUNT(tp.IDTRAMITEPOSESION) \n" +
            "                END \n" +
            "        END AS carga \n" +
            "    FROM \n" +
            "        TramiteSeleccionado ts \n" +
            "    JOIN \n" +
            "        Tramiteposesion tp ON ts.idtramiteposesion = tp.idtramiteposesion \n" +
            "    LEFT JOIN \n" +
            "        asignaciontramiteanalista ata ON tp.idtramiteposesion = ata.idtramiteposesion \n" +
            "    LEFT JOIN \n" +
            "        analistatramiteposesion atp ON ata.idanalistatramiteposesion = atp.idanalistatramiteposesion \n" +
            "    WHERE \n" +
            "        atp.ACTIVO = 1 \n" +
            "    GROUP BY ts.idtramiteposesion, ts.identificacion, ts.nombre, ts.idpersona \n" +
            ") \n" +
            "SELECT \n" +
            "    tc.identificacion, \n" +
            "    tc.nombre, \n" +
            "    tcc.num_tramites, \n" +
            "    tcc.carga \n" +
            "FROM \n" +
            "    TramiteSeleccionado tc \n" +
            "JOIN \n" +
            "    TramiteConCarga tcc ON tc.idtramiteposesion = tcc.idtramiteposesion \n" +
            "WHERE \n" +
            "    tc.rn = 1 \n" +
            "   ORDER BY \n" +
            "    tc.fechaprimeratransmision ASC", nativeQuery = true)
    List<Object[]> findAnalistasTramitesCarga();


    @Query(value= "SELECT IDANALISTATRAMITEPOSESION, NOMBRE  FROM ANALISTATRAMITEPOSESION WHERE IDENTIFICACION  = :identificacion ", nativeQuery = true)
    List<Object[]> findAnalistaByidentificacion(@Param("identificacion") String identificacion);
}

