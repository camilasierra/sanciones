package co.siri.posesiones.repositories;

import co.siri.posesiones.dtos.*;
import co.siri.posesiones.entities.PrioridadTramitePosesion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriorizacionRepository extends JpaRepository<PrioridadTramitePosesion, Long> {

        @Query(value = "SELECT ptp.*, ptp.rowid FROM prioridadtramiteposesion ptp ORDER BY 1 DESC", nativeQuery = true)
        List<PrioridadTramitePosesion> porPrioridades();

        @Query(value = "" +
                "SELECT te.idtipoentidad," +
                "             te.tipoentidad || ' - ' || te.nombre AS tipoentidad," +
                "             DECODE(ptp.idprioridadtramiteposesion, NULL, NULL, 1) AS prioridad," +
                "             ptp.entidadpublica," +
                "             ptp.idprioridadtramiteposesion" +
                "        FROM tipoentidad te " +
                "             LEFT JOIN prioridadtramiteposesion ptp ON te.idtipoentidad = ptp.idtipoentidad " +
                "       ORDER BY " +
                "             DECODE(ptp.entidadpublica, 1, 0, NULL, 2, 3) ASC, " +
                "             DECODE(ptp.idprioridadtramiteposesion, NULL, 2, 1) ASC, " +
                "             te.tipoentidad ASC " +
                "             FETCH FIRST 100 ROWS ONLY   ", nativeQuery = true)
        List<TipoEntidaResponseDTO> porTipoEntidad();

        @Query(value = "" +
                "SELECT te.idtipoentidad, " +
                "       te.tipoentidad || ' - ' || te.nombre AS tipoentidad, " +
                "       DECODE(ptp.idprioridadtramiteposesion, NULL, NULL, 1) prioridad, " +
                "       ptp.entidadpublica " +
                "  FROM tipoentidad te " +
                "       LEFT JOIN prioridadtramiteposesion ptp " +
                "              ON te.idtipoentidad = ptp.idtipoentidad " +
                " WHERE LOWER(te.tipoentidad || ' ' || te.nombre) LIKE LOWER('%' || :tipoEntidad || '%') " +
                " ORDER BY DECODE(ptp.idprioridadtramiteposesion, NULL, NULL, 1) NULLS LAST, te.tipoentidad ", nativeQuery = true)
        List<TipoEntidaResponseDTO> filtrarPorTipoEntidad(@Param("tipoEntidad") String tipoentidad);

        @Query(value = "" +
                "SELECT e.identidad,  " +
                "               te.tipoentidad || ' - ' || te.nombre AS tipoentidad,  " +
                "               te.tipoentidad || '-' || e.codigoentidad || ' ' || e.sigla AS entidad,  " +
                "               DECODE(ptp.idprioridadtramiteposesion, NULL, NULL, 1) AS prioridad,  " +
                "               ptp.entidadpublica,  " +
                "               ptp.idprioridadtramiteposesion  " +
                "          FROM entidad e  " +
                "               JOIN tipoentidad te ON e.idtipoentidad = te.idtipoentidad  " +
                "               LEFT JOIN prioridadtramiteposesion ptp ON e.identidad = ptp.identidad  " +
                "         WHERE e.indvigilada = 1  " +
                "           AND e.idestadoentidad = 1  " +
                "         ORDER BY  " +
                "               DECODE(ptp.entidadpublica, 1, 0, NULL, 2, 3) ASC,  " +
                "               DECODE(ptp.idprioridadtramiteposesion, NULL, 2, 1) ASC,  " +
                "               te.tipoentidad ASC,  " +
                "               e.codigoentidad ASC " +
                "               FETCH FIRST 100 ROWS ONLY", nativeQuery = true)
        List<PrioridadEntidadResponseDTO> porEntidad();

        @Query(value = "" +
                "SELECT e.identidad, " +
                "       te.tipoentidad || ' - ' || te.nombre AS tipoentidad, " +
                "       te.tipoentidad || '-' || e.codigoentidad || ' ' || e.sigla AS entidad, " +
                "       DECODE(ptp.idprioridadtramiteposesion, NULL, NULL, 1) prioridad, " +
                "       ptp.entidadpublica, " +
                "       ptp.idprioridadtramiteposesion " +
                "  FROM entidad e " +
                "       JOIN tipoentidad te ON e.idtipoentidad = te.idtipoentidad " +
                "       LEFT JOIN prioridadtramiteposesion ptp ON e.identidad = ptp.identidad " +
                " WHERE e.indvigilada = 1 " +
                "   AND e.idestadoentidad = 1 " +
                "   AND LOWER(te.tipoentidad || ' ' || e.codigoentidad || ' ' || e.sigla) LIKE LOWER('%' || :entidad || '%') " +
                " ORDER BY DECODE(ptp.idprioridadtramiteposesion, NULL, NULL, 1) NULLS LAST, te.tipoentidad, e.codigoentidad " +
                " FETCH FIRST 100 ROWS ONLY  ", nativeQuery = true)
        List<PrioridadEntidadResponseDTO> filtrarPorEntidad(@Param("entidad") String entidad);

        @Query(value = "" +
                "Select * from ( " +
                "Select p.idpersona, " +
                "       p.primernombre || ' ' || p.segundonombre || ' ' || p.primerapellido || ' ' || p.segundoapellido persona, dp.numero, " +
                "       decode(ptp.idprioridadtramiteposesion,null,null,1) prioridad, ptp.idprioridadtramiteposesion " +
                "  from persona p " +
                "       join documentopersona dp on (p.idpersona = dp.idpersona and " +
                "                                    dp.principal = 1 and " +
                "                                    dp.anulado = 0) " +
                "       join tipodocumento td on dp.idtipodocumento = td.idtipodocumento " +
                "       left join prioridadtramiteposesion ptp on p.idpersona = ptp.idpersona " +
                "union " +
                "Select 0 idpersona, '' nombre, ptp.numeroidentificacion, decode(ptp.idprioridadtramiteposesion,null,null,1) prioridad, ptp.idprioridadtramiteposesion " +
                "  from prioridadtramiteposesion ptp " +
                " where ptp.numeroidentificacion is not null) " +
                "order by prioridad nulls last, persona nulls last " +
                "   FETCH FIRST 100 ROWS ONLY ", nativeQuery = true)
        List<PrioridadPersonaResponseDTO> porPersona();

        @Query(value = "" +
                "SELECT * \n" +
                "FROM (\n" +
                "    SELECT p.idpersona, " +
                "           p.primernombre || ' ' || p.segundonombre || ' ' || p.primerapellido || ' ' || p.segundoapellido AS persona, " +
                "           dp.numero, " +
                "           DECODE(ptp.idprioridadtramiteposesion, NULL, NULL, 1) prioridad, " +
                "           ptp.idprioridadtramiteposesion " +
                "      FROM persona p " +
                "           JOIN documentopersona dp ON (p.idpersona = dp.idpersona " +
                "                                         AND dp.principal = 1 " +
                "                                         AND dp.anulado = 0) " +
                "           JOIN tipodocumento td ON dp.idtipodocumento = td.idtipodocumento " +
                "           LEFT JOIN prioridadtramiteposesion ptp ON p.idpersona = ptp.idpersona " +
                "     UNION " +
                "    SELECT 0 AS idpersona, " +
                "           '' AS persona, " +
                "           ptp.numeroidentificacion AS numero, " +
                "           DECODE(ptp.idprioridadtramiteposesion, NULL, NULL, 1) prioridad, " +
                "           ptp.idprioridadtramiteposesion " +
                "      FROM prioridadtramiteposesion ptp " +
                "     WHERE ptp.numeroidentificacion IS NOT NULL " +
                ") " +
                "WHERE LOWER(numero) LIKE LOWER('%' || :persona || '%') " +
                "order by prioridad nulls last, persona nulls last " +
                "FETCH FIRST 100 ROWS ONLY ", nativeQuery = true)
        List<PrioridadPersonaResponseDTO> filtroPorPersona(@Param("persona") String persona);

        @Query(value = "" +
                "SELECT tp.numeroradicacion, dp.numero, " +
                "              p.primernombre || ' ' || p.segundonombre || ' ' || p.primerapellido || ' ' || p.segundoapellido AS persona, " +
                "              te.tipoentidad || '-' || e.codigoentidad || ' ' || e.sigla AS entidad, " +
                "              tc.nombre AS cargo, ptp.idprioridadtramiteposesion, tp.IDTRAMITEPOSESION, " +
                "              DECODE(ptp.idprioridadtramiteposesion, NULL, NULL, 1) AS prioridad " +
                "         FROM tramiteposesion tp " +
                "              JOIN tipocargo tc ON tp.idtipocargo = tc.idtipocargo " +
                "              JOIN persona p ON tp.idpersona = p.idpersona " +
                "              JOIN documentopersona dp ON (p.idpersona = dp.idpersona AND " +
                "                                           dp.principal = 1 AND " +
                "                                           dp.anulado = 0) " +
                "              JOIN entidad e ON tp.identidad = e.identidad " +
                "              JOIN tipoentidad te ON e.idtipoentidad = te.idtipoentidad " +
                "              LEFT JOIN prioridadtramiteposesion ptp ON tp.IDTRAMITEPOSESION = ptp.IDTRAMITEPOSESION " +
                "        WHERE e.indvigilada = 1 " +
                "          AND e.idestadoentidad = 1 " +
                "        ORDER BY DECODE(ptp.idprioridadtramiteposesion, NULL, 2, 1) NULLS LAST, tp.numeroradicacion ASC " +
                "       FETCH FIRST 100 ROWS ONLY ", nativeQuery = true)
        List<PersonayEntidadResponseDTO> porPersonayEntidad();

        @Query(value = "" +
                "SELECT tp.numeroradicacion, " +
                "       dp.numero, " +
                "       p.primernombre || ' ' || p.segundonombre || ' ' || p.primerapellido || ' ' || p.segundoapellido AS persona, " +
                "       te.tipoentidad || '-' || e.codigoentidad || ' ' || e.sigla AS entidad," +
                "       tc.nombre AS cargo, " +
                "       ptp.idprioridadtramiteposesion, " +
                "       tp.IDTRAMITEPOSESION, " +
                "       DECODE(ptp.idprioridadtramiteposesion, NULL, NULL, 1) AS prioridad " +
                "FROM tramiteposesion tp " +
                "       JOIN tipocargo tc ON tp.idtipocargo = tc.idtipocargo " +
                "       JOIN persona p ON tp.idpersona = p.idpersona " +
                "       JOIN documentopersona dp ON (p.idpersona = dp.idpersona AND dp.principal = 1 AND dp.anulado = 0) " +
                "       JOIN entidad e ON tp.identidad = e.identidad " +
                "       JOIN tipoentidad te ON e.idtipoentidad = te.idtipoentidad " +
                "       LEFT JOIN prioridadtramiteposesion ptp ON tp.IDTRAMITEPOSESION = ptp.IDTRAMITEPOSESION " +
                "WHERE e.indvigilada = 1 " +
                "      AND e.idestadoentidad = 1 " +
                "      AND LOWER(REPLACE(REPLACE(p.primernombre || ' ' || COALESCE(p.segundonombre, '') || ' ' || p.primerapellido || ' ' || COALESCE(p.segundoapellido, '') || ' ' || " +
                "             tp.numeroradicacion || ' ' || " +
                "             dp.numero || ' ' || " +
                "             te.tipoentidad || '-' || " +
                "             e.codigoentidad || ' ' || " +
                "             e.sigla || ' ' || " +
                "             tc.nombre || ' ' || " +
                "             tp.numeroradicacion, ' ', ''), '%', ' ')) LIKE LOWER('%' || TRANSLATE(:personayentidad, 'ÁÉÍÓÚ', 'AEIOU') || '%') " +
                "ORDER BY DECODE(ptp.idprioridadtramiteposesion, NULL, 2, 1) NULLS LAST, tp.numeroradicacion ASC " +
                "FETCH FIRST 100 ROWS ONLY ", nativeQuery = true)
        List<PersonayEntidadResponseDTO> filtroPorPersonayEntidad(@Param("personayentidad") String personayentidad);

        @Modifying
        @Transactional
        @Query(value = "INSERT INTO PRIORIDADTRAMITEPOSESION (IDPRIORIDADTRAMITEPOSESION, IDTIPOENTIDAD, IDENTIDAD, ENTIDADPUBLICA, IDPERSONA, NUMEROIDENTIFICACION, IDTRAMITEPOSESION, IDUSUARIO, IPCLIENTE ) " +
                " VALUES (:Idtramite, :idtipoentidad, :identidad, :entidadpublica, :idpersona, :numeroident, :idtramite, :idusuario, :ipcliente)",
                nativeQuery = true)
        void insertarprioridad(
                @Param("Idtramite") Integer Idtramite,
                @Param("idtipoentidad") Long idtipoentidad,
                @Param("identidad") Integer identidad,
                @Param("entidadpublica") String entidadpublica,
                @Param("idpersona") Integer idpersona,
                @Param("numeroident") String numeroident,
                @Param("idtramite") Integer idtramite,
                @Param("idusuario") Integer idusuario,
                @Param("ipcliente") String ipcliente);

        @Modifying
        @Transactional
        @Query(value = "UPDATE PRIORIDADTRAMITEPOSESION SET " +
                "IDTIPOENTIDAD = :idtipoentidad, " +
                "IDENTIDAD = :identidad, " +
                "ENTIDADPUBLICA = :entidadpublica, " +
                "IDPERSONA = :idpersona, " +
                "NUMEROIDENTIFICACION = :numeroident, " +
                "IDTRAMITEPOSESION = :idtramite, " +
                "IDUSUARIO = :idusuario, " +
                "IPCLIENTE = :ipcliente " +
                "WHERE IDPRIORIDADTRAMITEPOSESION = :Idtramite",
                nativeQuery = true)
        void actualizarprioridad(
                @Param("Idtramite") Integer Idtramite,
                @Param("idtipoentidad") Long idtipoentidad,
                @Param("identidad") Integer identidad,
                @Param("entidadpublica") String entidadpublica,
                @Param("idpersona") Integer idpersona,
                @Param("numeroident") String numeroident,
                @Param("idtramite") Integer idtramite,
                @Param("idusuario") Integer idusuario,
                @Param("ipcliente") String ipcliente);


        @Query(value = "SELECT COALESCE(MAX(P.IDPRIORIDADTRAMITEPOSESION), 0) + 1 AS next_id\n" +
                "FROM PRIORIDADTRAMITEPOSESION P", nativeQuery = true)
        Integer ultimoId();

        @Query(value = "SELECT COUNT(P.IDPRIORIDADTRAMITEPOSESION) AS id\n" +
                "FROM PRIORIDADTRAMITEPOSESION P WHERE P.IDPRIORIDADTRAMITEPOSESION = :id", nativeQuery = true)
        Integer EncontrarId(@Param("id") Integer id);

        @Query(value = "SELECT P.IDPRIORIDADTRAMITEPOSESION AS id\n" +
                "FROM PRIORIDADTRAMITEPOSESION P WHERE P.IDENTIDAD = :id", nativeQuery = true)
        Integer EncontrarIdEntidad(@Param("id") Integer id);

        @Modifying
        @Transactional
        @Query(value = "DELETE FROM PRIORIDADTRAMITEPOSESION P WHERE P.IDPRIORIDADTRAMITEPOSESION = :Idtramite", nativeQuery = true)
        void eliminarPrioridad(@Param("Idtramite") Integer Idtramite);

        @Query(value="  SELECT \n" +
                "   pt.idprioridadtramiteposesion,\n" +
                "   CASE WHEN pt.idtipoentidad IS NOT NULL AND pt.idtipoentidad > 0  THEN te.tipoentidad || ' - ' || te.nombre\n" +
                "   WHEN pt.identidad IS NOT NULL  AND pt.identidad > 0 THEN e.IDENTIDAD || ' - ' || e.codigoEntidad || ' ' || e.sigla  \n" +
                "   WHEN pt.idpersona IS NOT NULL AND pt.idpersona > 0 THEN p.idpersona || ' - ' || p.primernombre || ' ' || p.segundonombre || ' ' || p.primerapellido || ' ' || p.segundoapellido \n" +
                "  WHEN pt.IDTRAMITEPOSESION IS NOT NULL THEN \n" +
                "   (   SELECT personaT.idpersona || ' - ' ||  personaT.primernombre || ' ' || personaT.segundonombre || ' ' || personaT.primerapellido || ' - ' || entidadT.codigoentidad || ' - ' || entidadT.sigla\n" +
                "  FROM tramiteposesion tp \n" +
                "     JOIN persona personaT ON personaT.IDPERSONA = tp.IDPERSONA \n" +
                "         JOIN entidad entidadT ON entidadT.IDENTIDAD  = tp.IDENTIDAD \n" +
                "     JOIN tipoentidad tipoEntidadT ON tipoEntidadT.idtipoentidad = entidadT.idtipoentidad\n" +
                "     WHERE  tp.IDTRAMITEPOSESION = pt.IDTRAMITEPOSESION)\n" +
                "     ELSE NULL END AS nombrePrioridad \n" +
                "     FROM PRIORIDADTRAMITEPOSESION pt\n" +
                "   LEFT JOIN tipoentidad te ON te.idtipoentidad = pt.idtipoentidad\n" +
                "    LEFT JOIN entidad e ON e.identidad = pt.identidad\n" +
                "    LEFT JOIN persona p on p.idpersona = pt.idpersona\n" +
                "    LEFT JOIN tramiteposesion tp ON tp.IDTRAMITEPOSESION = pt.IDTRAMITEPOSESION\n" +
                "    LEFT JOIN persona personaT ON personaT.IDPERSONA = tp.IDPERSONA \n" +
                "    LEFT JOIN entidad entidadT ON entidadT.IDENTIDAD  = tp.IDENTIDAD \n" +
                "    LEFT JOIN tipoentidad tipoEntidadT ON tipoEntidadT.idtipoentidad = entidadT.idtipoentidad  \n" +
                "    \n" +
                "   WHERE\n" +
                "    pt.idtipoentidad IS NOT NULL\n" +
                "    OR pt.identidad IS NOT NULL\n" +
                "    OR pt.idpersona IS NOT NULL\n" +
                "   OR pt.IDTRAMITEPOSESION IS NOT NULL ", nativeQuery = true)
        List<Object[]> consultaPrioridades();
}
