package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "MENSAJEREVISIONTRAMITE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MensajeRevisionTramite implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * El ID del mensaje de trámite de posesión.
     */
    @Id
    @SequenceGenerator(name = "IDMENSAJEREVISIONTRAMITE_IDENTIDAD_GENERATOR", sequenceName = "MENSAJEREVISIONTRAMITE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IDMENSAJEREVISIONTRAMITE_IDENTIDAD_GENERATOR")
    @Column(name = "IDMENSAJEREVISIONTRAMITE", nullable = false)
    private Long idMensajeRevisionTramite;

    /**
     * El ID del trámite de posesión.
     */
    @Column(name = "IDTRAMITEPOSESION")
    private Long idTramitePosesion;

    /**
     * El texto del mensaje, almacenado como un campo LOB.
     */
    @Lob
    @Column(name = "TEXTO")
    private String texto;

    /**
     * El ID del tipo de mensaje de trámite de posesión.
     */
    @Column(name = "IDTIPODESTINOMENSAJE")
    private Long idTipoDestinoMensaje;


    /**
     * El ID del usuario que creó el mensaje.
     */
    @Column(name = "INDBORRADO")
    private Character inBorrado;

    /**
     * El ID del usuario que creó el mensaje.
     */
    @Column(name = "IDUSUARIO")
    private Long idUsuario;

    /**
     * La IP del cliente desde  se creó el mensaje.
     */
    @Column(name = "IPCLIENTE")
    private String ipCliente;

    /**
     * Corresponde a la fecha en que se crea el mensaje
     */
    @Column(name = "FECHANOTIFICACION")
    private Date fechaNotificaicon;

    /**
     * Corresponde al rol que crea el mensaje
     */
    @Column(name = "ROL")
    private String rol;

}
