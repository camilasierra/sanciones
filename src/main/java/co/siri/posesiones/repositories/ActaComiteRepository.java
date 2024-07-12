package co.siri.posesiones.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.siri.posesiones.entities.SesionComiteEntity;

@Repository
public interface ActaComiteRepository extends JpaRepository<SesionComiteEntity, Long> {

	@Query(value = "SELECT p.primernombre||' '||p.segundonombre||' '||p.primerapellido||' '||p.segundoapellido nombreapellido,\r\n"
			+ "       tc.nombre cargo, te.tipoentidad ||'-'||e.codigoentidad||' '||e.sigla entidad, SUBSTR( tp.numeroradicacion, 2, 10 ) numeroradicacion, \r\n"
			+ "       rc.anotacion, rc.idresultadocomite , etp.NOMBRE decision\r\n"
			+ "	   FROM sesioncomite sc\r\n"
			+ "       JOIN resultadocomite rc ON sc.idsesioncomite = rc.idsesioncomite\r\n"
			+ "       JOIN tipoestadotramiteposesion etp ON rc.idtipoestadotramiteposesion = etp.idtipoestadotramiteposesion  --Estado del resultado del comité o temporal\r\n"
			+ "       JOIN tramiteposesion tp ON rc.idtramiteposesion = tp.idtramiteposesion\r\n"
			+ "       JOIN tipocargo tc ON tp.idtipocargo = tc.idtipocargo\r\n"
			+ "       JOIN persona p ON tp.idpersona = p.idpersona\r\n"
			+ "       JOIN Documentopersona dp ON (p.idpersona = dp.idpersona AND dp.anulado = 0 AND dp.principal = 1)\r\n"
			+ "       JOIN entidad e ON tp.identidad = e.identidad\r\n"
			+ "       JOIN tipoentidad te ON e.idtipoentidad = te.idtipoentidad\r\n"
			+ "       JOIN dependencia dep ON e.codigodependencia = dep.codigo\r\n"
			+ "       LEFT JOIN dependencia del ON dep.zonaeconomica = del.codigo\r\n"
			+ "    WHERE etp.nombre IN (:estados) AND sc.idsesioncomite = :idsesioncomite\r\n"
			+ "	   AND ((:anotacionEsNula = 1 AND rc.ANOTACION IS NULL) OR (:anotacionEsNula = 0 AND rc.ANOTACION IS NOT NULL))"
			+ "	   ORDER BY del.codigo, tp.numeroradicacion", nativeQuery = true)
	List<Object[]> getInfoComite(@Param("estados") List<String> estados, 
			@Param("idsesioncomite") Long idsesioncomite, 
			@Param("anotacionEsNula") Integer anotacionEsNula);
	
	
	@Query(value = "SELECT TO_DATE(sc.fechacomite, 'DD-MM-YYYY') AS fechacomite,\r\n"
			+ "    TO_CHAR(sc.horainiciocomite, 'HH:MI AM') AS horainiciocomite, \r\n"
			+ "    TO_CHAR(sc.horafincomite, 'HH:MI AM') AS horafincomite,\r\n"
			+ "    tsc.nombre AS tiposesionnombre, tm.nombre AS nombre,\r\n"
			+ "    tm.nombre AS tipomodalidad, tm.canal, sc.numeroacta\r\n"
			+ "FROM sesioncomite sc\r\n"
			+ "       left join tiposesioncomite tsc on sc.idtiposesioncomite = tsc.idtiposesioncomite\r\n"
			+ "       left join tipomodalidadsesioncomite tm on sc.idtipomodalidadsesioncomite = tm.idtipomodalidadsesioncomite\r\n"
			+ "WHERE sc.idsesioncomite = :idsesioncomite", nativeQuery = true)
	List<Object[]> getInfoSesionComite(@Param("idsesioncomite") Long idsesioncomite);
	
	@Query(value = "Select mc.cargo, mc.nombrepersona, mc.idtipomiembrocomite\r\n"
			+ "  from sesioncomite sc\r\n"
			+ "       join asistentecomite ac on sc.idsesioncomite = ac.idsesioncomite\r\n"
			+ "       join miembrocomite mc on ac.idmiembrocomite = mc.idmiembrocomite\r\n"
			+ "       --join tipo\r\n"
			+ " where mc.activo = 1\r\n"
			+ "   and mc.idtipomiembrocomite in :idtipomiembrocomite\r\n"
			+ "   and sc.idsesioncomite = :idsesioncomite\r\n"
			+ " order by mc.orden", nativeQuery = true)
	List<Object[]> getInfoMiembrosComite(@Param("idsesioncomite") Long idsesioncomite, @Param("idtipomiembrocomite") List<Integer> idtipomiembrocomite);
	
	@Query(value = "Select oac.nombreotroasistente, oac.calidadasistente\r\n"
			+ "  from otroasistentecomite oac\r\n"
			+ " where oac.idsesioncomite = :idsesioncomite", nativeQuery = true)
	List<Object[]> getInfoAsistenteComite(@Param("idsesioncomite") Long idsesioncomite);
	
	@Query(value = "Select del.nombre delegatura, etp.nombre estado, p.primernombre||' '||p.segundonombre||' '||p.primerapellido||' '||\r\n"
			+ "			p.segundoapellido ||' con '||tdp.nombre|| ' ' ||dp.numero || ', como '|| tc.nombre ||\r\n"
			+ "			' '||e.sigla || ' Radicación No ' || SUBSTR( tp.numeroradicacion, 2, 10 ) AS decisiones, rc.anotacion  \r\n"
			+ "  from sesioncomite sc\r\n"
			+ "       join resultadocomite rc on sc.idsesioncomite = rc.idsesioncomite\r\n"
			+ "       join tipoestadotramiteposesion etp on rc.idtipoestadotramiteposesion = etp.idtipoestadotramiteposesion  --Estado del resultado del comité o temporal\r\n"
			+ "       join tramiteposesion tp on rc.idtramiteposesion = tp.idtramiteposesion\r\n"
			+ "       join tipocargo tc on tp.idtipocargo = tc.idtipocargo\r\n"
			+ "       join persona p on tp.idpersona = p.idpersona\r\n"
			+ "       join Documentopersona dp on (p.idpersona = dp.idpersona and\r\n"
			+ "                                    dp.anulado = 0 and\r\n"
			+ "                                    dp.principal = 1)\r\n"
			+ "       join tipodocumento tdp on dp.idtipodocumento = tdp.idtipodocumento\r\n"
			+ "       join entidad e on tp.identidad = e.identidad\r\n"
			+ "       join tipoentidad te on e.idtipoentidad = te.idtipoentidad\r\n"
			+ "       left join dependencia dep on e.codigodependencia = dep.codigo\r\n"
			+ "       left join dependencia del on dep.zonaeconomica = del.codigo\r\n"
			+ " WHERE etp.nombre IN (:estado) AND sc.idsesioncomite = :idsesioncomite \r\n"
			+ " AND ((:anotacionEsNula = 1 AND rc.anotacion IS NULL) OR (:anotacionEsNula = 0 AND rc.anotacion IS NOT NULL))\r\n"
			+ " order by del.codigo, etp.orden, tp.numeroradicacion", nativeQuery = true)
	List<Object[]> getInfoDecisionesComite(@Param("estado") List<String> estado, 
			@Param("idsesioncomite") Long idsesioncomite, 
			@Param("anotacionEsNula") Integer anotacionEsNula);
	
	
	@Query(value = "Select sc.varios\r\n"
			+ "  from sesioncomite sc\r\n"
			+ " where sc.idsesioncomite = :idsesioncomite", nativeQuery = true)
	Object getVarios(@Param("idsesioncomite") Long idsesioncomite);
	
	@Query(value = "SELECT t.TEXTO FROM TIPOPLANTILLA t WHERE t.NOMBRE =:nombrePlantilla", nativeQuery = true)
	Object getPlantilla(@Param("nombrePlantilla") String nombrePlantilla);
	
	
	@Query(value = "Select (SELECT d.NOMBRE FROM DEPENDENCIA d WHERE d.CODIGO IN (mc.DELEGATURA)) AS DELEGATURA, AC.HORAVOTACION, sc.IDSESIONCOMITE "
			+ "FROM sesioncomite sc "
			+ "LEFT JOIN asistentecomite ac on sc.idsesioncomite = ac.idsesioncomite "
			+ "LEFT OUTER JOIN miembrocomite mc on ac.idmiembrocomite = mc.idmiembrocomite "
			+ "WHERE mc.activo = 1 "
			+ "AND mc.idtipomiembrocomite in (1,2) "
			+ "AND sc.idsesioncomite = :idsesioncomite "
			+ "ORDER BY mc.orden", nativeQuery = true)
	List<Object[]> consultarInfoVotacion(@Param("idsesioncomite") Long idsesioncomite);
	
	
}
