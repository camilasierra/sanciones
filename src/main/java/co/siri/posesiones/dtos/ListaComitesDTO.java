package co.siri.posesiones.dtos;

import lombok.Data;

import java.util.Date;

public interface ListaComitesDTO {
    Long getIdsesioncomite();
    Date getFechacomite();
    String getNumeroacta();
    String getNombre();
}
