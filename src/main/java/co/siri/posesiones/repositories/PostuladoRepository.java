package co.siri.posesiones.repositories;

import co.siri.posesiones.dtos.*;
import co.siri.posesiones.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar las consultas relacionadas con los postulantes.
 * Proporciona métodos para obtener datos personales, estudios, cargos con posesión, cargos sin posesión y experiencia.
 *
 * <p>Autor: John Macias</p>
 */
@Repository
public interface PostuladoRepository extends JpaRepository<Persona, Long> {

    /**
     * Obtiene los datos personales de un postulante basado en el ID del trámite de posesión.
     *
     * @param idTramitePosesion el ID del trámite de posesión
     * @return una entidad {@link IPostuladoDatos} con los datos del postulante
     */
    @Query(value=
            """
            Select 
                   p.idpersona,
                   p.primernombre, p.segundonombre, p.primerapellido, p.segundoapellido, dp.numero AS identificacion, lower(tdc.SIGLA) tipoIdentifiacion, tg.nombre genero,
                   p.fechanacimiento, pn.nombre nacionalidad, nvl(cn.nombrecentropoblado, cnc.nombre) ciudadNacimiento,
                   nvl(cres.nombrecentropoblado, cncres.nombre) ciudadResidencia,
                   dres.direccion direccionResidencia, dres.celular||', '||dres.telefono numeroContacto,
                   tec.nombre estadoCivil, p.primernombre||' '||pc.segundonombre||' '||pc.primerapellido||' '||pc.segundoapellido nombreConyuge,
                   tdc.sigla||'-'||dpc.numero identificacionConyuge, pc.ocupacionactual ocupacionConyuge, 
                   pc.empresaocupacionactual empresaOcupacionConyuge,
                   nvl(cnot.nombrecentropoblado, cncnot.nombre) ciudadNotificacion,
                   dnot.direccion direccionNotificacion, dnot.email
              from persona p
                   join tramiteposesion tp on p.idpersona = tp.idpersona
                   join documentopersona dp on (p.idpersona = dp.idpersona and dp.principal = 1 and dp.anulado = 0)
                   join tipodocumento td on dp.idtipodocumento = td.idtipodocumento
                   left join domicilio dnot on p.iddomiciliosecundario = dnot.iddomicilio
                   left join ciudad cnot on dnot.idciudad = cnot.idciudad
                   left join ciudadnocodificada cncnot on dnot.idciudadnocod = cncnot.idciudadnocodificada
                   left join domicilio dres on p.iddomicilioprincipal = dres.iddomicilio
                   left join ciudad cres on dres.idciudad = cres.idciudad
                   left join ciudadnocodificada cncres on dres.idciudadnocod = cncres.idciudadnocodificada
                   left join tipogenero tg on p.idtipogenero = tg.idtipogenero
                   left join pais pn on p.idpaisnacionalidad = pn.idpais
                   left join ciudad cn on p.idciudadnacimiento = cn.idciudad
                   left join ciudadnocodificada cnc on p.idciudadnacimientonocod = cnc.idciudadnocodificada
                   left join tipoestadocivil tec on p.idtipoestadocivil = tec.idtipoestadocivil
                   left join relacionpersona rp on (p.idpersona = rp.idpersonaorigen and rp.idtiporelacionpersonanombre in (1,33))
                   join persona pc on (rp.idpersonadestino = pc.idpersona)
                   join documentopersona dpc on (pc.idpersona = dpc.idpersona and dpc.principal = 1 and dpc.anulado = 0)
                   join tipodocumento tdc on dpc.idtipodocumento = tdc.idtipodocumento
             where tp.idtramiteposesion = :idTramitePosesion
            """, nativeQuery = true)
    Optional<IPostuladoDatos> obtenerDatosPostulado(@Param("idTramitePosesion") Long idTramitePosesion);

    /**
     * Obtiene los estudios de un postulante basado en el ID de la persona.
     *
     * @param idPersona el ID de la persona
     * @return una lista de entidades {@link IPostuladoEstudios} con los estudios del postulante
     */
    @Query(value=
            """
            Select 
            e.idpersona AS idPersona,
            tne.nombre AS nivelEducativo, e.institucioneducativa AS institucionEducativa, 
            e.nombrecarrera AS nombreCarrera, tae.nombre AS areaConocimiento,
            tee.nombre AS estado, e.fechaterminacion AS fechaTerminacion, e.tarjetaprofesional||'/'||e.fechatarjetaprofesional AS tarjetaProfesional
              from estudio e
                   left join tiponiveleducativo tne on e.idtiponiveleducativo = tne.idtiponiveleducativo
                   left join tipoafinidadestudio tae on e.idtipoafinidadestudio = tae.idtipoafinidadestudio
                   left join tipoestadoestudio tee on e.idtipoestadoestudio = tee.idtipoestadoestudio
             where e.idpersona = :idPersona
            order by tee.orden, tne.orden, e.fechaterminacion desc
            """, nativeQuery = true)
    List<IPostuladoEstudios> obtenerEstudiosPostulado(@Param("idPersona") Long idPersona);

    /**
     * Obtiene los cargos con posesión de un postulante basado en el ID de la persona.
     *
     * @param idPersona el ID de la persona
     * @return una lista de entidades {@link IPostuladoCargosPosesion} con los cargos con posesión del postulante
     */
    @Query(value=
            """
            Select 
            c.idpersona AS idPersona,
            te.tipoentidad||'-'||e.codigoentidad||' '||e.sigla AS entidad, c.nombrepublicocargo AS cargo,
                   c.fechadesde AS fechaDesde, c.fechahasta AS fechaHasta, decode(c.activo,1,'SI','NO') AS activo, c.descripcion AS observacion,
                   cec.nombre AS estado
              from Cargo c
                   left join Entidad e on c.identidad = e.identidad
                   left join Tipoentidad te on e.idtipoentidad = te.idtipoentidad
                   left join tipocomplementoestadocargo cec on c.idtipocomplementoestadocargo = cec.idtipocomplementoestadocargo
             where c.cargoconposesion = 1
             and c.idpersona = :idPersona
               order by c.fechadesde desc
            """, nativeQuery = true)
    List<IPostuladoCargosPosesion> obtenerCargosPosesionPostulado(@Param("idPersona") Long idPersona);

    /**
     * Obtiene los cargos sin posesión de un postulante basado en el ID de la persona.
     *
     * @param idPersona el ID de la persona
     * @return una lista de entidades {@link IPostuladoCargosSinPosesion} con los cargos sin posesión del postulante
     */
    @Query(value=
            """
            Select 
            c.idpersona AS idPersona,
            te.tipoentidad||'-'||e.codigoentidad||' '||e.sigla As entidad, c.nombrepublicocargo AS cargo,
            null AS areaDesempenio,  tmr.nombre AS motivoRetiro,
            c.fechadesde AS fechaInicio, c.fechahasta AS fechaRetiro
            from Cargo c
            left join Entidad e on c.identidad = e.identidad
            left join Tipoentidad te on e.idtipoentidad = te.idtipoentidad
            left join tipocomplementoestadocargo cec on c.idtipocomplementoestadocargo = cec.idtipocomplementoestadocargo
            left join Tipomotivoretiro tmr on c.idtipomotivoretiro = tmr.idtipomotivoretiro
            where c.cargoconposesion = 0 
            and c.idpersona = :idPersona
            union
            Select 
            ef.idpersona AS idPersona,
            te.tipoentidad||'-'||e.codigoentidad||' '||e.sigla AS entidad, ef.nombrecargo AS cargo,
            tae.nombre AS areaDesempenio, tmr.nombre AS motivoRetiro,
            ef.fechadesde AS fechaInicio, ef.fechahasta AS fechaRetiro
            from experienciafinanciera ef
            left join Entidad e on ef.identidad = e.identidad
            left join Tipoentidad te on e.idtipoentidad = te.idtipoentidad
            left join Tipoareaexperiencia tae on ef.idtipoareaexperiencia = tae.idtipoareaexperiencia
            left join Tipomotivoretiro tmr on ef.idtipomotivoretiro = tmr.idtipomotivoretiro
            where ef.idpersona = :idPersona
            """, nativeQuery = true)
    List<IPostuladoCargosSinPosesion> obtenerCargosSinPosesionPostulado(@Param("idPersona") Long idPersona);

    /**
     * Obtiene la experiencia de un postulante basada en el ID de la persona.
     *
     * @param idPersona el ID de la persona
     * @return una lista de entidades {@link IExperienciaPostulado} con la experiencia del postulante
     */
    @Query(value=
            """
            Select 
            enf.idpersona AS idPersona,
            tse.nombre AS sector, tsoe.nombre AS claseDeSociedad, enf.nombreentidad AS razonSocial, 
            enf.nombrecargo AS nombreCargo,
            tc.nombre AS tipoCargo, tae.nombre AS areaDesempenio, tmr.nombre AS motivoRetiro, 
            enf.fechadesde AS fechaInicio,
            enf.fechahasta AS fechaRetiro,
            ap.nombre AS nombreArchivo,
            ap.contenttype AS contentTypeArchivo,
            ap.archivo AS archivo,
            ap.longitud as longitudArchivo
            from experiencianofinanciera enf
            left join tiposectorexperiencia tse on enf.idtiposectorexperiencia = tse.idtiposectorexperiencia
            left join tiposociedadexperiencia tsoe on enf.idtiposociedadexperiencia = tsoe.idtiposociedadexperiencia
            left join tipocargo tc on enf.idtipocargo = tc.idtipocargo
            left join tipoareaexperiencia tae on enf.idtipoareaexperiencia = tae.idtipoareaexperiencia
            left join tipomotivoretiro tmr on enf.idtipomotivoretiro = tmr.idtipomotivoretiro
            left join archivopersona ap on enf.idarchivopersona = ap.idarchivopersona
            where enf.idpersona = :idPersona
            """,nativeQuery = true)
    List<IExperienciaPostulado> obtenerExperienciaPostulado(@Param("idPersona") Long idPersona);

}
