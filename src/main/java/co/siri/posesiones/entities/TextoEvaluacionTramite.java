package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad para representar un texto de evaluación.
 * Contiene información sobre el texto de evaluación, incluyendo el ID del trámite, tipo de texto de evaluación, sección del mensaje,
 * texto del mensaje, título del texto,  ID del usuario y la IP del cliente.
 *
 * <p>Autor: John Macias</p>
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TEXTOEVALUACIONTRAMITE")
public class TextoEvaluacionTramite {

    /**
     * El ID del texto de evaluación.
     */
    @Id
    @SequenceGenerator(name = "TEXTOEVALUACIONTRAMITE_IDTEXTOEVALUACIONTRAMITE_GENERATOR", sequenceName = "TEXTOEVALUACIONTRAMITE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEXTOEVALUACIONTRAMITE_IDTEXTOEVALUACIONTRAMITE_GENERATOR")
    @Column(name = "IDTEXTOEVALUACIONTRAMITE", nullable = false)
    private Long idTextoTramite;

    /**
     * El ID del trámite de posesión.
     */
    @Column(name = "IDTRAMITEPOSESION")
    private Long idTramitePosesion;

    /**
     * El ID del tipo de texto de evaluación.
     */
    @Column(name = "IDTIPOTEXTOEVALUACIONTRAMITE")
    private Long idTipoTextoTramite;

    /**
     * El ID de la sección del trámite de posesión.
     */
    @Column(name = "IDTIPOSECCIONTRAMITEPOSESION")
    private Long idTipoSeccionTramite;

    /**
     * El texto de evaluación, almacenado como un campo LOB.
     */
    @Lob
    @Column(name = "TEXTO")
    private String texto;

    /**
     * El ID del usuario que creó el texto de evaluación.
     */
    @Column(name = "IDUSUARIO")
    private Long idUsuario;

    /**
     * La IP del cliente desde donde se creó el texto de evaluación.
     */
    @Column(name = "IPCLIENTE")
    private String ipCliente;
}

