package co.siri.posesiones.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.siri.posesiones.entities.MiembroComite;

public interface MiembroComiteRepository extends JpaRepository<MiembroComite, Integer> {

	
    @Query("SELECT m FROM MiembroComite m WHERE " +
           "LOWER(m.numeroIdentificacion) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(m.idusuario) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(m.nombrePersona) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(m.textoActa) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(m.cargo) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
           "ORDER BY m.activo DESC, m.orden")
    List<MiembroComite> encontrarTodosOrdenadosYFiltrados(@Param("searchText") String searchText);

    @Query(value = "select * from miembrocomite\n" +
            "where activo = 1 " +
            "order by idtipomiembrocomite asc", nativeQuery = true)
    List<MiembroComite> miembrosActivos();

}
