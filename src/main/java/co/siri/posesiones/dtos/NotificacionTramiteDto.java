package co.siri.posesiones.dtos;

public interface NotificacionTramiteDto {

    Long getIdAsignacionTramiteAnalista();
    String getLogin();
    String getIdentificacion();
    String getNombre();
    Long getIdTipoAnalista();
    Long getIdTipoInactivacionAnalista();
    String getActivo();
    Long getLimiteCarga();
    Long getIdUsuarioAnalista();
    String getIpClienteAnalista();
    Long getIdTramitePosesion();
    Long getIdAnalistaTramitePosesion();
    Long getCarga();
    Long getIdUsuarioTramite();
    String getIpClienteTramite();

    String getIdTipoDestinoMensaje();
    String getTexto();

}
