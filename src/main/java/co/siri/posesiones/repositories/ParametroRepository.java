package co.siri.posesiones.repositories;

import co.siri.posesiones.entities.Parametros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParametroRepository extends JpaRepository<Parametros, Long> {

    Parametros findByIdParametro(Integer parametroId);

    /**
     * Metodo encargado de consultar parametros por ID en la tabla "Parametros"
     * @param idParams valor que puede contener uno o mas params
     * @return
     */
    @Query(value = "SELECT * FROM PARAMETROS " +
            "WHERE IDPARAMETRO IN (:idParams)", nativeQuery = true)
    List<Parametros> getParametrosMultiples(@Param("idParams") List<Integer> idParams);

    @Query("SELECT p FROM Parametros p WHERE p.nombre IN :nombres ORDER BY p.nombre")
    List<Parametros> findParametrosByNames(List<String> nombres);
    
    Optional<Parametros> findByNombre(String nombre);
}
