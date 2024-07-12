package co.siri.posesiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensajeRevisionTramiteDto {

    private Long idTramitePosesion;

    private String texto;

    private List<Long> idTipoDestinoMensaje;

    private Long idUsuario;

    private String ipCliente;
}
