package co.siri.posesiones.repositories;

import co.siri.posesiones.dtos.comiteII.OficiosDto;
import co.siri.posesiones.dtos.comiteII.TramitesEstadoDTO;
import co.siri.posesiones.entities.SesionComiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComiteRepository extends JpaRepository<SesionComiteEntity, Long> {

    @Query(value = "SELECT rc.idresultadocomite AS idresultadocomite, " +
            "del.nombre AS nombre, " +
            "del.codigo AS codigo, " +
            "tp.numeroradicacion AS numeroradicacion, " +
            "e.sigla AS entidad, " +
            "dp.numero AS identificacion, " +
            "p.primernombre || ' ' || p.segundonombre || ' ' || p.primerapellido || ' ' || p.segundoapellido AS nombreapellido, " +
            "tc.nombre AS cargo, " +
            "etp.nombre AS estado, " +
            "tp.idtramiteposesion," +
            "uig.DIASTERMINO - uig.DIASHABILESSFC AS vencimiento " +
            "FROM sesioncomite sc " +
            "JOIN resultadocomite rc ON sc.idsesioncomite = rc.idsesioncomite " +
            "JOIN tipoestadotramiteposesion etp ON rc.idtipoestadotramiteposesion = etp.idtipoestadotramiteposesion " +
            "JOIN tramiteposesion tp ON rc.idtramiteposesion = tp.idtramiteposesion " +
            "JOIN tipocargo tc ON tp.idtipocargo = tc.idtipocargo " +
            "JOIN persona p ON tp.idpersona = p.idpersona " +
            "JOIN documentopersona dp ON (p.idpersona = dp.idpersona AND dp.anulado = 0 AND dp.principal = 1) " +
            "JOIN entidad e ON tp.identidad = e.identidad " +
            "JOIN tipoentidad te ON e.idtipoentidad = te.idtipoentidad " +
            "JOIN dependencia dep ON e.codigodependencia = dep.codigo " +
            "INNER JOIN v_ig_tramiteposesion_ultimo uig ON tp.idtramiteposesion = uig.idtramiteposesion " +
            "LEFT JOIN dependencia del ON dep.zonaeconomica = del.codigo " +
            "WHERE etp.nombre = :estado AND sc.idsesioncomite = :idsesioncomite " +
            "AND ((:anotacionEsNula = 1 AND rc.ANOTACION IS NULL) OR (:anotacionEsNula = 0 AND rc.ANOTACION IS NOT NULL)) " +
            "ORDER BY del.codigo, tp.numeroradicacion", nativeQuery = true)
    List<TramitesEstadoDTO> tramitesEstado(
            @Param("estado") String estado,
            @Param("idsesioncomite") Long idsesioncomite,
            @Param("anotacionEsNula") Integer anotacionEsNula
    );

    @Query(value = """
    SELECT sc.IDSESIONCOMITE,
           TG.IDTIPOGENERO,
           TG.NOMBRE AS GENERO,
           P.PRIMERNOMBRE || ' ' || P.SEGUNDONOMBRE || ' ' || P.PRIMERAPELLIDO || ' ' || P.SEGUNDOAPELLIDO AS NOMBRE,
           E.SIGLA AS NOMBREENTIDAD,
           ESB.ciudad,
           ESB.direccion,
           SC.FECHACOMITE,
           TC.NOMBRE AS NOMBRECARGO,
           TETP.NOMBRE AS ESTADORESULTADO,
           DBMS_LOB.SUBSTR(RC.ANOTACION, 4000, 1) AS ANOTACION,
           TP.NUMERORADICACION,
           m.NUMEROIDENTIFICACION AS identificacion,
           m.NOMBREPERSONA AS presidente
    FROM SESIONCOMITE SC
    JOIN RESULTADOCOMITE RC ON SC.IDSESIONCOMITE = RC.IDSESIONCOMITE
    JOIN TIPOESTADOTRAMITEPOSESION TETP ON RC.IDTIPOESTADOTRAMITEPOSESION = TETP.IDTIPOESTADOTRAMITEPOSESION
    JOIN TRAMITEPOSESION TP ON RC.IDTRAMITEPOSESION = TP.IDTRAMITEPOSESION
    JOIN ENTIDAD E ON TP.IDENTIDAD = E.IDENTIDAD
    LEFT JOIN ENTIDADSB ESB ON E.IDENTIDAD = ESB.identidad
    JOIN TIPOENTIDAD TE ON E.IDTIPOENTIDAD = TE.IDTIPOENTIDAD
    JOIN TIPOCARGO TC ON TP.IDTIPOCARGO = TC.IDTIPOCARGO
    JOIN TIPOCALIDADCARGO TCC ON TP.IDTIPOCALIDADCARGO = TCC.IDTIPOCALIDADCARGO
    JOIN PERSONA P ON TP.IDPERSONA = P.IDPERSONA
    LEFT JOIN TIPOGENERO TG ON P.IDTIPOGENERO = TG.IDTIPOGENERO
    JOIN ASISTENTECOMITE a ON a.IDSESIONCOMITE = sc.IDSESIONCOMITE
    JOIN MIEMBROCOMITE m ON a.IDMIEMBROCOMITE = m.IDMIEMBROCOMITE
    WHERE SC.IDSESIONCOMITE = :sesionComite
    AND TETP.nombre IN (:estadoTramite)
    AND ((:anotacionEsNula = 1 AND rc.anotacion IS NULL) OR (:anotacionEsNula = 0 AND rc.anotacion IS NOT NULL))
    ORDER BY e.codigodependencia, TETP.ORDEN, TP.NUMERORADICACION
    """, nativeQuery = true)
    List<OficiosDto> obtenerOficiosPdforWord(
            @Param("estadoTramite") List<String> estadoTramite,
            @Param("sesionComite") Long sesionComite,
            @Param("anotacionEsNula") Integer anotacionEsNula
    );
}
