package co.siri.posesiones.repositories;

import co.siri.posesiones.dtos.IAccionAporte;
import co.siri.posesiones.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar las consultas relacionadas con los reportes de antecedentes.
 * Extiende JpaRepository para proporcionar operaciones CRUD.
 * <p>
 * Autor: John Macias
 */
@Repository
public interface ReporteAntecedentesRepository extends JpaRepository<Persona, Long> {

    /**
     * Obtiene una lista de acciones de aporte para una persona específica.
     *
     * @param idPersona el ID de la persona para la que se desean obtener las acciones de aporte
     * @return una lista de objetos {@link IAccionAporte} que representan las acciones de aporte
     */
    @Query(value=
            """
            Select 
            e.razonsocial AS entidadVigilada, 
            aa.nombreentidad AS razonSocial, 
            aa.nit AS nit,
            aa.porcentajeparticipacion AS participacion, 
            decode(aa.inscritaenbolsa,1,'SI','NO') AS inscritaEnBolsa
            from accionaporte aa
            left join entidad e on aa.identidad = e.identidad
            left join tipoentidad te on e.idtipoentidad = te.idtipoentidad
            where aa.idpersona = :idPersona 
            order by aa.idaccionaporte desc
            """, nativeQuery = true)
    List<IAccionAporte> obtenerAccionesAporte(@Param("idPersona") Long idPersona);

}
