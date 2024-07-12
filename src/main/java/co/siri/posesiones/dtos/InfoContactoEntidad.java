package co.siri.posesiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoContactoEntidad {

	private String personaResponsable;
	
	private String cargo;
	
	private String emailResponsableEntidad;
	
	private String telNotificacion;
	
	private String extension;
	
	private String direccionNotificacion;
	
	private String notificacionElectronica;
	
	private String emailNotificacion;
}
