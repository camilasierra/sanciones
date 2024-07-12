package co.siri.posesiones.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AsignacionManualDTO {
    private String ipCliente;
    private Long idUsuario;
    private Long idTramitePosesion;
    private Long idAnalistaPosesion;
}
