package co.siri.posesiones.dtos.comiteII;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoActaComiteDTO {

	String nombreApellido;
	String cargo;
	String entidad;
	String numeroRadicacion;
	String anotacion;
	Integer idResultadoComite;
	String decision;
	
}
