package co.siri.posesiones.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FiltroAvanzadoEscritorioDto {
    private String user; //Login
    private String identificacion;
    private String radicado;
    private String entidad;
    private String candidato;
    private String estadoTramite;
    private String cargo;
    private Integer idTipoEstadoTramitePosesion;
}
