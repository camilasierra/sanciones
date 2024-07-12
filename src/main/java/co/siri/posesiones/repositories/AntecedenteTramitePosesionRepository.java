package co.siri.posesiones.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import co.siri.posesiones.entities.AntecedenteTramitePosesion;


@Repository
public interface AntecedenteTramitePosesionRepository extends JpaRepository<AntecedenteTramitePosesion, Long> {
	
	@Query("select c from AntecedenteTramitePosesion c where c.idTramitePosesion.idTramitePosesion = :idTramite and c.idTipoConvenioAntecedente.idTipoConvenioAntecedente = :idTipoConvenio")
	public List<AntecedenteTramitePosesion> obtenerAntecedentePorTramite(@Param("idTramite") Long idTramite, @Param("idTipoConvenio") Long idTipoConvenio);
	
	
}
