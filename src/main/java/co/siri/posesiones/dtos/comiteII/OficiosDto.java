package co.siri.posesiones.dtos.comiteII;

import java.time.LocalDate;

public interface OficiosDto {
    Long getIdTipoGenero();
    String getGenero();
    String getNombre();
    String getNombreEntidad();
    String getCiudad();
    String getDireccion();
    LocalDate getFechaComite();
    String getNombreCargo();
    String getEstadoResultado();
    String getAnotacion(); // Este método ahora devolverá un String
    String getNumeroRadicacion();
    String getIdentificacion();
    String getPresidente();
}

