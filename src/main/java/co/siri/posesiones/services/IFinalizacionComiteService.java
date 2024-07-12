package co.siri.posesiones.services;

import co.siri.posesiones.dtos.MensajeSolicitudOut;
import co.siri.posesiones.dtos.SubirArchivoActaComiteDTO;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;

public interface IFinalizacionComiteService {
	
	MensajeSolicitudOut subirActaComite (SubirArchivoActaComiteDTO actaComite) throws ExcepcionPersonalizada;
	MensajeSolicitudOut oficializarActaComite(Long idsesioncomite) throws ExcepcionPersonalizada;
	
}
