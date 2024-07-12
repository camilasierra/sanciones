package co.siri.posesiones.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.siri.posesiones.entities.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long> {


}
