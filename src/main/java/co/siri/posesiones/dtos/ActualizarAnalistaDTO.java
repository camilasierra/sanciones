package co.siri.posesiones.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizarAnalistaDTO {
    private Long idAnalistaTramitePosesion;
    private String nombre;
    private String identificacion;
    private Long idTipoAnalista;
    private Long limiteCarga;
    private Long idPrioridad;
    private Long idUsuario;
    private String login;
    private Long activo;
    private Long idTipoInactivacionAnalista;
    private String ipCliente;
}
