package co.siri.posesiones.services;

import co.siri.posesiones.entities.ArchivoTramitePosesion;

public interface IActaComiteService {
	
	ArchivoTramitePosesion getArchivo_ActaComite(Long idsesioncomite) throws Exception;
	

}
