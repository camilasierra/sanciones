package co.siri.posesiones.dtos;

import lombok.Data;

/**
 * DTO para representar un mensaje de trámite de posesión.
 * Contiene información sobre el ID del mensaje, ID del trámite, tipo de mensaje, sección del mensaje,
 * texto del mensaje, estado de leído, estado de borrado y el ID del usuario.
 *
 * <p>Autor: John Macias</p>
 */
@Data
public class MensajeTramitePosesionDTO {

    /**
     * El ID del mensaje de trámite de posesión.
     */
    private Long idMensajeTramite;

    /**
     * El ID del trámite de posesión.
     */
    private Long idTramitePosesion;

    /**
     * El ID del tipo de mensaje de trámite de posesión.
     */
    private Long idTipoMensajeTramite;

    /**
     * El ID de la sección del trámite de posesión.
     */
    private Long idTipoSeccionTramite;

    /**
     * El texto del mensaje.
     */
    private String texto;

    /**
     * Indica si el mensaje ha sido leído ('S' para sí, 'N' para no).
     */
    private char leido;

    /**
     * Indica si el mensaje ha sido borrado ('S' para sí, 'N' para no).
     */
    private char borrado;

    /**
     * El ID del usuario que creó el mensaje.
     */
    private Long idUsuario;
}

