package co.siri.posesiones.dtos;

import lombok.Data;

/**
 * DTO para representar los filtros de entrada para obtener textos de evaluación.
 * Contiene información sobre los criterios de búsqueda como el ID del trámite, tipo de texto y sección del texto.
 *
 * <p>Autor: John Macias</p>
 */
@Data
public class FiltroTextoEvaluacionIN {

    /**
     * El ID del trámite de posesión.
     */
    private Long idTramite;

    /**
     * El ID del tipo de texto.
     */
    private Long idTipoTexto;

    /**
     * El ID de la sección del texto.
     */
    private Long idSeccionTexto;
}
