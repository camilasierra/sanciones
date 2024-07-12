package co.siri.posesiones.repositories;

import co.siri.posesiones.entities.ActoAdministrativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ActoAdministrativoRepository extends JpaRepository<ActoAdministrativo, Long> {

    @Query(value = "SELECT caa.nombre AS tema, " +
            "aa.numeroactoadmin, " +
            "aa.descripcion, " +
            "aa.fechaactoadmin, " +
            "teaa.nombre AS estado, " +
            "daa.idpersona " +
            "FROM actoadministrativo aa " +
            "JOIN clasificacionactoadmin caa ON aa.idclasificacionactoadmin = caa.idclasificacionactoadmin " +
            "JOIN destinoactoadministrativo daa ON aa.idactoadministrativo = daa.idactoadminitrativo " +
            "JOIN tipoestadoactoadmin teaa ON aa.idtipoestadoactoadmin = teaa.idtipoestadoactoadmin " +
            "WHERE aa.idtipoestadoactoadmin = 7 " +
            "AND caa.esmultasancion = 1 " +
            "AND daa.idpersona = :idPersona", nativeQuery = true)
    List<Map<String, Object>> findActosAdministrativosByPersona(@Param("idPersona") Long idPersona);

}