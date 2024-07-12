package co.siri.posesiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para representar un texto de evaluación.
 * Contiene información sobre el ID del texto, ID del trámite, tipo de texto, sección del texto,
 * texto y el ID del usuario.
 *
 * <p>Autor: John Macias</p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextoEvaluacionDTO {

    /**
     * El ID del texto evaluación.
     */
    private Long idTextoTramite;

    /**
     * El ID del trámite de posesión.
     */
    private Long idTramitePosesion;

    /**
     * El título
     */
    private String titulo;

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
     * El ID del usuario que creó el mensaje.
     */
    private Long idUsuario;

    /**
     * La IP del usuario que creó el mensaje.
     */
    private String ipCliente;
}

