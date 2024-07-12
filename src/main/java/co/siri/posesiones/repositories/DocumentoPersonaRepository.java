package co.siri.posesiones.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.siri.posesiones.entities.DocumentoPersona;

public interface DocumentoPersonaRepository extends JpaRepository<DocumentoPersona, Long> {

	Optional<DocumentoPersona> findByIdPersona(Long idPersona);
}
