package co.siri.posesiones.dtos;

import co.siri.posesiones.utilidades.dto.AuditDTO;
import lombok.Data;

/**
 * DTO para representar la entrada de un texto de evaluación.
 * Contiene información sobre el ID del trámite, tipo de mensaje, sección del texto,
 * texto y el usuario que crea el texto.
 *
 * <p>Autor: John Macias</p>
 */
@Data
public class TextoEvaluacionINDTO {

    /**
     * El ID del trámite de posesión.
     */
    private Long idTramitePosesion;

    /**
     * El ID del tipo de texto.
     */
    private Long idTipoTextoTramite;

    /**
     * El ID de la sección del trámite de posesión.
     */
    private Long idTipoSeccionTramite;

    /**
     * El texto del mensaje.
     */
    private String texto;

    /**
     * Usuario
     */
    private AuditDTO usuario;
}
