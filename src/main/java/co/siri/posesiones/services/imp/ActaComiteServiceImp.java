package co.siri.posesiones.services.imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.siri.posesiones.entities.ArchivoTramitePosesion;
import co.siri.posesiones.repositories.ArchivoTramitePosesionRepository;
import co.siri.posesiones.services.IActaComiteService;

@Service
public class ActaComiteServiceImp implements IActaComiteService{
	
	@Autowired
	private ArchivoTramitePosesionRepository actaComiteRepo;

	@Override
	public ArchivoTramitePosesion getArchivo_ActaComite(Long idsesioncomite) throws Exception {
		Optional<ArchivoTramitePosesion> actaComite = Optional.of(actaComiteRepo.getActaSesionComite(idsesioncomite));	
		if(actaComite.isPresent()) {
			return actaComite.get();
		}else {
			throw new Exception("No existe acta de comité subida dentro de esta sesión");
		}
		 
	}

}
