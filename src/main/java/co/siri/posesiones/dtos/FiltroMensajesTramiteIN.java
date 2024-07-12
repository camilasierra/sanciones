package co.siri.posesiones.dtos;

import lombok.Data;

/**
 * DTO para representar los filtros de entrada para obtener mensajes de trámite de posesión.
 * Contiene información sobre los criterios de búsqueda como el ID del trámite, tipo de mensaje y sección del mensaje.
 *
 * <p>Autor: John Macias</p>
 */
@Data
public class FiltroMensajesTramiteIN {

    /**
     * El ID del trámite de posesión.
     */
    private Long idTramite;

    /**
     * El ID del tipo de mensaje.
     */
    private Long idTipoMensaje;

    /**
     * El ID de la sección del mensaje.
     */
    private Long idSeccionMensaje;
}
