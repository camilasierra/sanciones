package co.siri.posesiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensajeBorradoDto {

    private Long idMensajeBorrado;

    private Long idMensajeRevisionTramite;

    private Long idUsuario;

    private String ipCliente;
}
