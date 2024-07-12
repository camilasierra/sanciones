package co.siri.posesiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoNombramientoDto {

	private String claseNombramiento;
	
	private String cargoPostulado;
	
	private String cargoDesempenar;
	
	private String calidadCargo;
	
	private String personaReemplazar;
	
	private String numeroIdentificacion;
	
	private String motivoReemplazo;
	
	private String organoDesigno;
}
