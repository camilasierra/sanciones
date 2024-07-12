package co.siri.posesiones.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.siri.posesiones.entities.ResultadoComite;

@Repository
public interface IResultadoComiteRepository extends JpaRepository<ResultadoComite, Integer> {
	
}
