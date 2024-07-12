package co.siri.posesiones.dtos;


public interface GestionAnalistasDTO {
    Long getIdAnalistaTramitePosesion();
    String getLogin();
    String getIdentificacion();
    String getNombre();
    String getIdTipoAnalista();
    String getnombreTipoAnalista();
    String getIdTipoInactivacionAnalista();
    String getnombreTipoInactivacionAnalista();
    String getActivo();
    String getLimiteCarga();
    Long getIdUsuario();
    Long getIdPrioridadTramitePosesion();
    String getIpCliente();
}