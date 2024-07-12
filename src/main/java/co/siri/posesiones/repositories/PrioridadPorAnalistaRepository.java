package co.siri.posesiones.repositories;

import co.siri.posesiones.entities.PrioridadPorAnalista;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrioridadPorAnalistaRepository extends JpaRepository<PrioridadPorAnalista, Long> {


    Optional<PrioridadPorAnalista> findByIdAnalistaTramitePosesion(Long idAnalistaTramitePosesion);
}
