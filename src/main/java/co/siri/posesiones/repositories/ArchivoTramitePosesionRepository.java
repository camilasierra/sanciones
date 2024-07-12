package co.siri.posesiones.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.siri.posesiones.entities.ArchivoTramitePosesion;

@Repository
public interface ArchivoTramitePosesionRepository extends JpaRepository<ArchivoTramitePosesion, Long> {
    
    @Query(value = "SELECT a.* FROM archivotramiteposesion a\r\n"
    		+ "INNER JOIN sesioncomitereunion sc\r\n"
    		+ "ON a.idarchivotramiteposesion = sc.idarchivocorreosvoto\r\n"
    		+ "WHERE sc.idsesioncomite = :idsesioncomite", nativeQuery = true)
    ArchivoTramitePosesion getActaSesionComite(@Param("idsesioncomite") Long idSesionComite);
    
}
