package co.siri.posesiones.repositories;

import co.siri.posesiones.entities.TipoModalidadSesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipoModalidadSesionRepository extends JpaRepository<TipoModalidadSesion, Integer> {
    @Query("SELECT m FROM TipoModalidadSesion m WHERE m.idModalidad = :idModalidad")
    TipoModalidadSesion findByIdTipoModalidad(Integer idModalidad);

    @Query("SELECT m FROM TipoModalidadSesion m")
    List<TipoModalidadSesion> obtenerListaModalidades();
}
