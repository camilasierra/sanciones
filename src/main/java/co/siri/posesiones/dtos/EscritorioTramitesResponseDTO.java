package co.siri.posesiones.dtos;



public interface EscritorioTramitesResponseDTO {
    String getEntidadPublica();
    Long getIdTipoEstadoTramitePosesion();
    Long getIdTramitePosesion();
    String getNumeroRadicado();
    String getIdEntidad();
    String getEntidad();
    String getCargo();
    String getPrimerNombre();
    String getSegundoNombre();
    String getPrimerApellido();
    String getSegundoApellido();
    String getIdentificacion();
    String getEstadoDelTramite();
    Long getDiasTermino();
    Long getDiasHabilesSFC();
    Long getVencimiento();
    String getNuevo();
}
