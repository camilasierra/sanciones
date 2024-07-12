package co.siri.posesiones.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.siri.posesiones.entities.TipoConvenioAntecedente;

@Repository
public interface TipoConvenioAntecedenteRepository extends JpaRepository<TipoConvenioAntecedente, Long> {

}
