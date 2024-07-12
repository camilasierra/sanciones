package co.siri.posesiones.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.siri.posesiones.entities.Entidad;

public interface EntidadRepository extends JpaRepository<Entidad, Long> {


}
