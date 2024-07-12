package co.siri.posesiones.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.siri.posesiones.entities.SesionComiteReunion;

@Repository
public interface SesionComiteReunionRepository extends JpaRepository<SesionComiteReunion, Long> {

}
