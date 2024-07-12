package co.siri.posesiones.services;

import co.siri.posesiones.dtos.AccionAporteDTO;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;

import java.util.List;

/**
 * Interfaz para el servicio de reporte de antecedentes.
 * Proporciona métodos para obtener información sobre las acciones de aporte.
 * <p>
 * Autor: John Macias
 */
public interface IReporteAntecedentesService {

    /**
     * Obtiene una lista de acciones de aporte para una persona específica.
     *
     * @param idPersona el ID de la persona para la que se desean obtener las acciones de aporte
     * @return una lista de objetos {@link AccionAporteDTO} que representan las acciones de aporte
     * @throws ExcepcionPersonalizada si ocurre algún error durante la obtención de las acciones de aporte
     */
    List<AccionAporteDTO> obtenerAccionesAporte(Long idPersona) throws ExcepcionPersonalizada;
}