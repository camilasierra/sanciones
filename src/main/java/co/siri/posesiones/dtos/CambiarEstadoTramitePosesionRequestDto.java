package co.siri.posesiones.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa el objeto de entrada para cambiar el estado de un trámite de posesión
 */
@Getter
@Setter
public class CambiarEstadoTramitePosesionRequestDto {

    private Long idUsuario; 
    private Long idTramitePosesion;
    private String ip; 
    private Long idTipoEstadoTramitePosesionNew; 
    private boolean entidad;
}
