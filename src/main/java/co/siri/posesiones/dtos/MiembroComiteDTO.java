package co.siri.posesiones.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MiembroComiteDTO {
    private Integer idmiembrocomite;
    private String nombrePersona;
    private Integer orden;
    private String activo;
    private String textoActa;
    private String idusuario;
    private String ipCliente;
    private Integer idTipoMiembroComite;
    private String numeroIdentificacion;
    private String login;
    private String autorizadoComiteVirtual;
    private String delegatura;
    private String cargo;
}