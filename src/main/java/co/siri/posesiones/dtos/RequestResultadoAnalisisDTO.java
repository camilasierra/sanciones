package co.siri.posesiones.dtos;

import co.siri.posesiones.utilidades.dto.AuditDTO;
import lombok.Data;

@Data
public class RequestResultadoAnalisisDTO {

    private String texto;

    private int idTramitePosesion;

    private AuditDTO user;
}
