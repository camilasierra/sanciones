package co.siri.posesiones.repositories;

import co.siri.posesiones.dtos.*;
import org.springframework.data.jpa.repository.JpaRepository;

import co.siri.posesiones.entities.TramitePosesion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface TramitePosesionRepository extends JpaRepository<TramitePosesion, Long>{

    @Query(value = "SELECT new co.siri.posesiones.dtos.TramitePosesionDto(t.idTramitePosesion, p.primerNombre, p.segundoNombre, p.primerApellido, p.segundoApellido, \n" +
            "en.razonSocial, tEnt.tipoEntidad,  en.codigoEntidad, en.sigla, tDoc.sigla, \n" +
            "doc.numero, t.numeroRadicacion, t.fechaPrimeraTransmision, t.fechaUltimaTransmision, analista.nombre ) \n" +
            "FROM TramitePosesion t \n" +
            "INNER JOIN Persona p ON t.idPersona = p.idPersona \n" +
            "INNER JOIN Entidad en ON t.idEntidad = en.idEntidad \n" +
            "INNER JOIN TipoEntidad tEnt ON en.idTipoEntidad = tEnt.idTipoEntidad \n" +
            "INNER JOIN DocumentoPersona doc ON p.idPersona = doc.idPersona \n" +
            "INNER JOIN TipoDocumento tDoc ON doc.idTipoDocumento = tDoc.idTipoDocumento \n" +
            "LEFT JOIN AsignacionTramiteAnalistas asig ON t.idTramitePosesion = asig.idTramitePosesion \n " +
            "LEFT JOIN AnalistaTramitePosesion analista ON asig.idAnalistaTramitePosesion = analista.idAnalistaTramitePosesion \n " +
            "WHERE doc.anulado = false AND doc.principal = true \n" +
            "AND doc.numero = ?1 AND t.numeroRadicacion IS NOT NULL \n " +
            "AND t.idTipoCargo BETWEEN 1 AND 5 \n " +
            "ORDER BY t.fechaPrimeraTransmision DESC NULLS LAST")
    List<TramitePosesionDto> findTramitesByNumeroIdentificacion(String busqueda);

    @Query(value = "SELECT new co.siri.posesiones.dtos.TramitePosesionDto(t.idTramitePosesion, p.primerNombre, p.segundoNombre, p.primerApellido, p.segundoApellido, \n" +
            "en.razonSocial, tEnt.tipoEntidad,  en.codigoEntidad, en.sigla, tDoc.sigla, \n" +
            "doc.numero, t.numeroRadicacion, t.fechaPrimeraTransmision, t.fechaUltimaTransmision, analista.nombre) \n" +
            "FROM TramitePosesion t \n" +
            "INNER JOIN Persona p ON t.idPersona = p.idPersona \n" +
            "INNER JOIN Entidad en ON t.idEntidad = en.idEntidad \n" +
            "INNER JOIN TipoEntidad tEnt ON en.idTipoEntidad = tEnt.idTipoEntidad \n" +
            "INNER JOIN DocumentoPersona doc ON p.idPersona = doc.idPersona \n" +
            "INNER JOIN TipoDocumento tDoc ON doc.idTipoDocumento = tDoc.idTipoDocumento \n" +
            "LEFT JOIN AsignacionTramiteAnalistas asig ON t.idTramitePosesion = asig.idTramitePosesion \n " +
            "LEFT JOIN AnalistaTramitePosesion analista ON asig.idAnalistaTramitePosesion = analista.idAnalistaTramitePosesion \n " +
            "WHERE doc.anulado = false AND doc.principal = true \n" +
            "AND LOWER(CONCAT(p.primerNombre, ' ', p.segundoNombre, ' ', p.primerApellido, ' ', p.segundoApellido)) LIKE %?1%" +
            "AND t.numeroRadicacion IS NOT NULL \n " +
            "AND t.idTipoCargo BETWEEN 1 AND 5 \n " +
            "ORDER BY t.fechaPrimeraTransmision DESC NULLS LAST")
    List<TramitePosesionDto> findTramitesByCandidato(String busqueda);

    @Query(value = "SELECT new co.siri.posesiones.dtos.TramitePosesionDto(t.idTramitePosesion, p.primerNombre, p.segundoNombre, p.primerApellido, p.segundoApellido, \n" +
            "en.razonSocial, tEnt.tipoEntidad,  en.codigoEntidad, en.sigla, tDoc.sigla, \n" +
            "doc.numero, t.numeroRadicacion, t.fechaPrimeraTransmision, t.fechaUltimaTransmision, analista.nombre ) \n" +
            "FROM TramitePosesion t \n" +
            "INNER JOIN Persona p ON t.idPersona = p.idPersona \n" +
            "INNER JOIN Entidad en ON t.idEntidad = en.idEntidad \n" +
            "INNER JOIN TipoEntidad tEnt ON en.idTipoEntidad = tEnt.idTipoEntidad \n" +
            "INNER JOIN DocumentoPersona doc ON p.idPersona = doc.idPersona \n" +
            "INNER JOIN TipoDocumento tDoc ON doc.idTipoDocumento = tDoc.idTipoDocumento \n" +
            "LEFT JOIN AsignacionTramiteAnalistas asig ON t.idTramitePosesion = asig.idTramitePosesion \n " +
            "LEFT JOIN AnalistaTramitePosesion analista ON asig.idAnalistaTramitePosesion = analista.idAnalistaTramitePosesion \n " +
            "WHERE doc.anulado = false AND doc.principal = true \n" +
            "AND t.numeroRadicacion = ?1 AND t.numeroRadicacion IS NOT NULL \n " +
            "AND t.idTipoCargo BETWEEN 1 AND 5 \n " +
            "ORDER BY t.fechaPrimeraTransmision DESC NULLS LAST")
    List<TramitePosesionDto> findTramitesByRadicado(String busqueda);

    @Query(value = "SELECT new co.siri.posesiones.dtos.TramitePosesionDto(t.idTramitePosesion, p.primerNombre, p.segundoNombre, p.primerApellido, p.segundoApellido, \n" +
            "en.razonSocial, tEnt.tipoEntidad,  en.codigoEntidad, en.sigla, tDoc.sigla, \n" +
            "doc.numero, t.numeroRadicacion, t.fechaPrimeraTransmision, t.fechaUltimaTransmision, analista.nombre ) \n" +
            "FROM TramitePosesion t \n" +
            "INNER JOIN Persona p ON t.idPersona = p.idPersona \n" +
            "INNER JOIN Entidad en ON t.idEntidad = en.idEntidad \n" +
            "INNER JOIN TipoEntidad tEnt ON en.idTipoEntidad = tEnt.idTipoEntidad \n" +
            "INNER JOIN DocumentoPersona doc ON p.idPersona = doc.idPersona \n" +
            "INNER JOIN TipoDocumento tDoc ON doc.idTipoDocumento = tDoc.idTipoDocumento \n" +
            "LEFT JOIN AsignacionTramiteAnalistas asig ON t.idTramitePosesion = asig.idTramitePosesion \n " +
            "LEFT JOIN AnalistaTramitePosesion analista ON asig.idAnalistaTramitePosesion = analista.idAnalistaTramitePosesion \n " +
            "WHERE doc.anulado = false AND doc.principal = true \n" +
            "AND LOWER(en.sigla) LIKE %?1% AND t.numeroRadicacion IS NOT NULL \n " +
            "AND t.idTipoCargo BETWEEN 1 AND 5 \n " +
            "ORDER BY t.fechaPrimeraTransmision DESC NULLS LAST")
    List<TramitePosesionDto> findTramitesByEntidad(String busqueda);

    Optional<TramitePosesion> findByIdTramitePosesionAndIdTipoCargoAndIdTipoCalidadCargo(Long idTramitePosesion, Long idTipoCargo, Long idTipoCalidadCargo);

    Optional<TramitePosesion> findByIdTramitePosesionAndIdTipoCargo(Long idTramitePosesion, Long idTipoCargo);

    @Query(value = "SELECT new co.siri.posesiones.dtos.InfoAdicionalDefensorConsumidor(" +
            "top.nombre, " +
            "t.centroConciliacion, " +
            "p.nombre, " +
            "c.nombreCentroPoblado, " +
            "d.direccion, " +
            "d.telefono, " +
            "d.fax, " +
            "d.celular, " +
            "d.email, " +
            "d.emailOtros) " +
            "FROM TramitePosesion t " +
            "INNER JOIN TipoOpcionSiNo top " +
            "ON (t.facultadConciliar = TRUE AND top.idTipoOpcionSiNo = 1) " +
            "OR (t.facultadConciliar = FALSE AND top.idTipoOpcionSiNo = 0) " +
            "INNER JOIN Domicilio d " +
            "ON t.idDomicilioAtencion = d.idDomicilio " +
            "INNER JOIN Ciudad c " +
            "ON d.ciudad = c.idCiudad " +
            "INNER JOIN Pais p " +
            "ON c.pais = p.idPais " +
            "WHERE t.idTramitePosesion = ?1 " +
            "AND t.idTipoCargo = 5")
    InfoAdicionalDefensorConsumidor findDefensorConsumidor(Long idTramitePosesion);



    @Query(value = "SELECT tp.idtramiteposesion, pfat.idpersonafirmaauditoratramite, pfat.idtramiteposesion, " +
            "CASE WHEN tp.espartedefirmaauditora = 1 THEN 'SI' ELSE 'NO' END AS espartedefirmaauditora, " +
            "CASE WHEN tp.espartedefirmaauditora = 1 THEN pfat.numeromercantil ELSE NULL END AS numeromercantil, " +
            "CASE WHEN tp.espartedefirmaauditora = 1 THEN pfat.camaracomercio ELSE NULL END AS camaracomercio, " +
            "CASE WHEN tp.espartedefirmaauditora = 1 THEN pfat.tarjetaprofesional ELSE NULL END AS tarjetaprofesional, " +
            "CASE WHEN tp.espartedefirmaauditora = 1 THEN td.nombre ELSE NULL END AS tipo_doc, " +
            "CASE WHEN tp.espartedefirmaauditora = 1 THEN dp.numero ELSE NULL END AS identificacion, " +
            "CASE WHEN tp.espartedefirmaauditora = 1 THEN TRIM(p.primernombre || ' ' || p.segundonombre || ' ' || p.primerapellido || ' ' || p.segundoapellido) ELSE NULL END AS nombre, " +
            "CASE WHEN tp.espartedefirmaauditora = 1 THEN te.tipoentidad || '-' || e.codigoentidad || ' ' || e.sigla ELSE NULL END AS entidad, " +
            "CASE WHEN tp.revisorfiscalotraentidad = 1 THEN 'SI' ELSE 'NO' END AS revisorFiscalOtraEntidad " +
            "FROM tramiteposesion tp " +
            "LEFT JOIN personafirmaauditoratramite pfat ON tp.idtramiteposesion = pfat.idtramiteposesion " +
            "LEFT JOIN personafirmaauditora pfa ON pfat.idpersonafirmaauditora = pfa.idpersonafirmaauditora " +
            "LEFT JOIN persona p ON pfa.idpersona = p.idpersona " +
            "LEFT JOIN documentopersona dp ON p.idpersona = dp.idpersona AND dp.anulado = 0 AND dp.principal = 1 " +
            "LEFT JOIN tipodocumento td ON dp.idtipodocumento = td.idtipodocumento " +
            "LEFT JOIN entidad e ON pfa.identidad = e.identidad " +
            "LEFT JOIN tipoentidad te ON e.idtipoentidad = te.idtipoentidad " +
            "WHERE tp.idtramiteposesion = :idTramite",
            nativeQuery = true)
    List<FirmaAuditoraProjection> obtenerInformacionFirmaAuditoraPorTramite(@Param("idTramite") Long idTramite);

    @Query(nativeQuery = true, value = "select tp.numeroradicacion, tp.idtipocargo, e.idtipoentidad, tp.* " +
            "from tramiteposesion tp " +
            "join entidad e on tp.identidad = e.identidad " +
            "where 1=1 " +
            "and tp.numeroradicacion is not null " +
            "and (tp.idtipocargo, tp.idtipocalidadcargo, e.idtipoentidad) in ((1,1,1))")
    List<Map<String, Object>> findTramitesByCriteria();

    @Query(value = "SELECT tp.numeroradicacion, " +
            "       p.primernombre || ' ' || p.segundonombre || ' ' || p.primerapellido || ' ' || p.segundoapellido AS \"Nombre Persona\", " +
            "       td.sigla || ' ' || dp.numero AS \"Identificación\", " +
            "       trpc.nombre AS \"Vínculo\", " +
            "       trpn.nombre AS \"Detalle Vinculo\", " +
            "       ra.nombreentidad AS \"Entidad\", " +
            "       ra.nombrecargo AS \"Cargo\", " +
            "       tc.nombre AS \"Tipo Cargo\" " +
            "FROM tramiteposesion tp " +
            "LEFT JOIN relacionadicional ra ON tp.idtramiteposesion = ra.idtramiteposesion " +
            "LEFT JOIN tipocargo tc ON ra.idtipocargo = tc.idtipocargo " +
            "LEFT JOIN entidad e ON ra.identidad = e.identidad " +
            "LEFT JOIN relacionpersona rp ON ra.idrelacionpersona = rp.idrelacionpersona " +
            "LEFT JOIN tiporelacionpersonanombre trpn ON rp.idtiporelacionpersonanombre = trpn.idtiporelacionpersonanombre " +
            "LEFT JOIN tiporelacionpersonaclase trpc ON trpn.idtiporelacionpersonaclase = trpc.idtiporelacionpersonaclase " +
            "LEFT JOIN persona p ON rp.idpersonadestino = p.idpersona " +
            "LEFT JOIN documentopersona dp ON (p.idpersona = dp.idpersona AND dp.anulado = 0 AND dp.principal = 1) " +
            "LEFT JOIN tipodocumento td ON dp.idtipodocumento = td.idtipodocumento " +
            "WHERE tp.numeroradicacion LIKE %:numeroradicacion% " +
            "ORDER BY tp.numeroradicacion DESC NULLS LAST", nativeQuery = true)
    List<Map<String, Object>> findByNumeroRadicacionContaining(String numeroradicacion);


    @Query(value = "SELECT tp.* " +
            " FROM Tramiteposesion tp " +
            " LEFT join asignaciontramiteanalista ata on tp.idtramiteposesion = ata.idtramiteposesion " +
            " LEFT join analistatramiteposesion atp on ata.idanalistatramiteposesion = atp.idanalistatramiteposesion " +
            " LEFT join v_prioridadtramiteposesion vptp on tp.idtramiteposesion = vptp.IDTRAMITEPOSESION " +
            " WHERE tp.numeroradicacion is not null " +
            " AND tp.idtipoestadotramiteposesion <=5 " +
            " AND atp.nombre IS NULL" +
            " ORDER BY fechaprimeratransmision, vptp.entidadpublica", nativeQuery = true)
    List<TramitePosesion> findByAsignadaFalseOrderByPrioridadDescFechaAsc();
    @Query(value = "  WITH TramiteSeleccionado AS (\n" +
            "    SELECT\n" +
            "        tp.*,\n" +
            "        ROW_NUMBER() OVER (PARTITION BY ata.idanalistatramiteposesion \n" +
            "                           ORDER BY tp.fechaprimeratransmision ASC, \n" +
            "                                    CASE WHEN vptp.ENTIDADPUBLICA IS NULL THEN 9999 ELSE vptp.ENTIDADPUBLICA  END ASC) AS rn\n" +
            "    FROM\n" +
            "        Tramiteposesion tp\n" +
            "    JOIN\n" +
            "        asignaciontramiteanalista ata ON tp.idtramiteposesion = ata.idtramiteposesion\n" +
            "    LEFT JOIN\n" +
            "        v_prioridadtramiteposesion vptp ON tp.idtramiteposesion = vptp.IDTRAMITEPOSESION\n" +
            "    WHERE\n" +
            "        ata.idanalistatramiteposesion = :identificacionAnalista \n" +
            "        \n" +
            "),\n" +
            "TramiteConCarga AS (\n" +
            "    SELECT\n" +
            "        ts.IDTRAMITEPOSESION,\n" +
            "        CASE \n" +
            "            WHEN ts.IDPERSONA IS NOT NULL THEN \n" +
            "                CASE \n" +
            "                    WHEN COUNT(tp.IDTRAMITEPOSESION) > 4 THEN 2.8\n" +
            "                    ELSE COUNT(tp.IDTRAMITEPOSESION) * 0.7\n" +
            "                END\n" +
            "            ELSE\n" +
            "                CASE \n" +
            "                    WHEN COUNT(tp.IDTRAMITEPOSESION) > 5 THEN 5\n" +
            "                    ELSE COUNT(tp.IDTRAMITEPOSESION)\n" +
            "                END\n" +
            "        END AS carga\n" +
            "    FROM\n" +
            "        TramiteSeleccionado ts\n" +
            "    JOIN\n" +
            "        Tramiteposesion tp ON ts.idtramiteposesion = tp.idtramiteposesion\n" +
            "    LEFT JOIN\n" +
            "        asignaciontramiteanalista ata ON tp.idtramiteposesion = ata.idtramiteposesion\n" +
            "    LEFT JOIN\n" +
            "        analistatramiteposesion atp ON ata.idanalistatramiteposesion = atp.idanalistatramiteposesion\n" +
            "    WHERE\n" +
            "        atp.ACTIVO = 1\n" +
            "    GROUP BY\n" +
            "        ts.idtramiteposesion, ts.IDPERSONA\n" +
            ")\n" +
            "SELECT\n" +
            "    tc.idtramiteposesion, \n" +
            "    tcc.carga\n" +
            "FROM\n" +
            "    TramiteSeleccionado tc\n" +
            "JOIN\n" +
            "    TramiteConCarga tcc ON tc.idtramiteposesion = tcc.idtramiteposesion\n" +
            "WHERE\n" +
            "    tc.rn = 1\n" +
            "   ORDER BY\n" +
            "    tc.fechaprimeratransmision ASC" , nativeQuery = true)
    List<Object[]> findTramiteParaTransferir(@Param("identificacionAnalista") Integer identificacionAnalista);
}
