package co.siri.posesiones.dtos;

import lombok.Getter;
import lombok.Setter;

import java.sql.Clob;

@Setter
@Getter
public class ResultadoComiteRequestDTO {
    private String anotacion;
    private Integer idSesionComite;
    private Integer idTramitePosesion;
    private Integer idTipoEstadoTramitePosesion;
}
