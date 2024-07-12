package co.siri.posesiones.dtos;

import lombok.Data;

/**
 * DTO para representar la entrada de un mensaje de trámite de posesión.
 * Contiene información sobre el ID del trámite, tipo de mensaje, sección del mensaje,
 * texto del mensaje y el ID del usuario que crea el mensaje.
 *
 * <p>Autor: John Macias</p>
 */
@Data
public class MensajeTramitePosesionINDTO {

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
     * El ID del usuario que crea el mensaje.
     */
    private Long idUsuario;
}
