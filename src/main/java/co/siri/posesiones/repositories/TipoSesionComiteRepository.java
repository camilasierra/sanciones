package co.siri.posesiones.repositories;

import co.siri.posesiones.entities.TipoSesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipoSesionComiteRepository extends JpaRepository<TipoSesion, Integer> {
    @Query("SELECT s FROM TipoSesion s WHERE s.idTipoSesion = :idTipoSesion")
    TipoSesion findByIdTipoSesion(Integer idTipoSesion);

    @Query("SELECT s FROM TipoSesion s")
    List<TipoSesion> obtenerListaSesiones();
}
