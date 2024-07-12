package co.siri.posesiones.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PrioridadRequestDTO {
    private Integer Idtramite;
    private Long idtipoentidad;
    private Integer identidad;
    private String entidadpublica;
    private Integer idpersona;
    private String numeroident;
    private Integer idusuario;
    private String ipcliente;
    private Long Idtramitess;
    private Long Idtramites;
    private Integer idprioridadtramiteposesion;
}
