package co.siri.posesiones.services;

import co.siri.posesiones.exceptions.ExcepcionPersonalizada;

public interface IPdfService {

    String generarPDFHojaVidaPostulado(Long idTramite, Long idPersona) throws ExcepcionPersonalizada;
}
