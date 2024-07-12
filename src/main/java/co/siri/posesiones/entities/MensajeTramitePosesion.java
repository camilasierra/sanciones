package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad para representar un mensaje de trámite de posesión.
 * Contiene información sobre el mensaje, incluyendo el ID del trámite, tipo de mensaje, sección del mensaje,
 * texto del mensaje, estado de leído, estado de borrado, ID del usuario y la IP del cliente.
 *
 * <p>Autor: John Macias</p>
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MENSAJETRAMITEPOSESION")
public class MensajeTramitePosesion {

    /**
     * El ID del mensaje de trámite de posesión.
     */
    @Id
    @SequenceGenerator(name = "MENSAJETRAMITEPOSESION_IDENTIDAD_GENERATOR", sequenceName = "MENSAJETRAMITEPOSESION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENSAJETRAMITEPOSESION_IDENTIDAD_GENERATOR")
    @Column(name = "IDMENSAJETRAMITEPOSESION", nullable = false)
    private Long idMensajeTramite;

    /**
     * El ID del trámite de posesión.
     */
    @Column(name = "IDTRAMITEPOSESION")
    private Long idTramitePosesion;

    /**
     * El ID del tipo de mensaje de trámite de posesión.
     */
    @Column(name = "IDTIPOMENSAJETRAMITEPOSESION")
    private Long idTipoMensajeTramite;

    /**
     * El ID de la sección del trámite de posesión.
     */
    @Column(name = "IDTIPOSECCIONTRAMITEPOSESION")
    private Long idTipoSeccionTramite;

    /**
     * El texto del mensaje, almacenado como un campo LOB.
     */
    @Lob
    @Column(name = "TEXTO")
    private String texto;

    /**
     * Indica si el mensaje ha sido leído ('S' para sí, 'N' para no).
     */
    @Column(name = "LEIDO")
    private char leido;

    /**
     * Indica si el mensaje ha sido borrado ('S' para sí, 'N' para no).
     */
    @Column(name = "BORRADO")
    private char borrado;

    /**
     * El ID del usuario que creó el mensaje.
     */
    @Column(name = "IDUSUARIO")
    private Long idUsuario;

    /**
     * La IP del cliente desde donde se creó el mensaje.
     */
    @Column(name = "IPCLIENTE")
    private String ipCliente;
}

