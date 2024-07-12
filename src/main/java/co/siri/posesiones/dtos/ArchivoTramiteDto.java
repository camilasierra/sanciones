package co.siri.posesiones.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArchivoTramiteDto {
	
	private Long idArchivoTramitePosesion;
    private byte[] archivo;
    private String contenttype;
    private Long longitud;
    private String nombre;
    private String archivoBase;
    private Long idTramite;
    private String ipCliente;
    private Long idUsuario;
    


}
