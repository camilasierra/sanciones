package co.siri.posesiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatosBasicosDto {

	private String nombreCandidato;
	
	private String numeroIdentificacion;
	
	private String cargoRealizar;
	
	private String servidorPublico;
	
	private String entidad;
}
