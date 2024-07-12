package co.siri.posesiones.dtos;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ParametroAnalistaDTO {
    private Integer cargaMaximaAnalistaBase;
    private Integer cargaMaximaAnalistaApoyo;
    private Integer diferenciaMaximaCarga;
    private Long idUsuario;
    private String ipCliente;
}
