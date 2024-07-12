package co.siri.posesiones.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import co.siri.posesiones.dtos.ActaComiteDTO;
import co.siri.posesiones.entities.ResultadoComite;
import co.siri.posesiones.entities.SesionComiteEntity;

public interface IActaComiteServices {

	HashMap<String, Object> consultarComites(List<String> estados, Long idsesion, Integer anotacionEsNula);
	
	String actualizarAnotacionComite(ResultadoComite resultadoComite);

	ActaComiteDTO getInfoArmarActa(List<String> estado, Long idsesion, Integer anotacionEsNula,
						   List<Integer> idtipomiembrocomite, String format) throws IOException;
	
	String getVarios(Long idsesion);
	
	String actualizarVarios(SesionComiteEntity sesionEntity);
	
	
}
