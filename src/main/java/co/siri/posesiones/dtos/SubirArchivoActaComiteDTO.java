package co.siri.posesiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubirArchivoActaComiteDTO {
	
	private String ip_cliente;
	private Long idusuario;
	private Long idsesioncomite;
	private String archivo_base64;
	private String nombre_archivo;
	private String contentType;

}
