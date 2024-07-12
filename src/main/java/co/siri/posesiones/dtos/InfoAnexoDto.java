package co.siri.posesiones.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InfoAnexoDto {

	private String nombreArchivo;
	
	private String archivo;
}
