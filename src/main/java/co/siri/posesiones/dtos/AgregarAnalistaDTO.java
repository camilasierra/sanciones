package co.siri.posesiones.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgregarAnalistaDTO {
    private String nombre;
    private String identificacion;
    private Long idTipoAnalista;
    private Long limiteCarga;
    private Long idPrioridad;
    private Long nombrePrioridad;
    private Long IdUsuario;
    private String login;
    private Long activo;
    private String ipCliente;
}
