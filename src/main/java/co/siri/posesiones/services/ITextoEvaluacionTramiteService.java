package co.siri.posesiones.services;

import co.siri.posesiones.dtos.FiltroTextoEvaluacionIN;
import co.siri.posesiones.dtos.TextoEvaluacionDTO;
import co.siri.posesiones.dtos.TextoEvaluacionINDTO;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;

import java.util.List;

/**
 * Interfaz para el servicio de TextoEvaluacionTramite.
 * Define los métodos para obtener y guardar los textos de evaluación.
 *
 * <p>Autor: John Macias</p>
 */
public interface ITextoEvaluacionTramiteService {

    /**
     * Obtiene una lista de los textos de evaluación basados en los filtros proporcionados.
     *
     * @param filtroTextoEvaluacion el filtro que contiene los criterios de búsqueda para los textos de evaluación
     * @return una lista de los textos de evaluación como {@link TextoEvaluacionDTO}
     * @throws ExcepcionPersonalizada si ocurre un error durante la obtención de los los textos de evaluación
     */
    List<TextoEvaluacionDTO> obtenerTextosEvaluacion(FiltroTextoEvaluacionIN filtroTextoEvaluacion) throws ExcepcionPersonalizada;

    /**
     * Guarda un texto de evaluación.
     *
     * @param textoEvaluacion el texto de evaluación
     * @return el texto de evaluación guardado como {@link TextoEvaluacionDTO}
     * @throws ExcepcionPersonalizada si ocurre un error durante el guardado del texto de evaluación
     */
    TextoEvaluacionDTO guardarTextoEvaluacion(TextoEvaluacionINDTO textoEvaluacion) throws ExcepcionPersonalizada;

}
