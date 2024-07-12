package co.siri.posesiones.dtos;

import java.util.Date;

public interface MensajeEnTramiteDto {

    Long getIdMensajeRevisionTramite();

    Long getIdTramitePosesion();

    String getTexto();

    Long getIdTopoDestinoMensaje();

    String getIndorrado();

    Long getIdUsuario();

    String getIpCliente();

    Date getNotificacion();

    String getRol();
}
