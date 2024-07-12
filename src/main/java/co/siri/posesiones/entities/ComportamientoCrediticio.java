package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "COMPORTAMIENTOCREDITICIO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComportamientoCrediticio implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * El ID pk de la tabla COMPORTAMIENTOCREDITICIO
     */
    @Id
    @SequenceGenerator(name = "COMENTARIOSNORMATIVOS_GENERATOR", sequenceName = "COMENTARIOSNORMATIVOS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMENTARIOSNORMATIVOS_GENERATOR")
    @Column(name = "IDCOMPORTAMIENTOCREDITICIO", nullable = false)
    private Long idComportamientoCrediticio;

    /**
     * El ID de la persona a la cula pertenece el historial crediticio
     */
    @Column(name = "IDPERSONA")
    private Long idPersona;

    /**
     * id de la tabla TIPOCLASECENTRAL
     */
    @Column(name = "IDTIPOCLASECENTRAL")
    private String idTipoClaseCentral;

    /**
     * El ID del tipo de mensaje de trámite de posesión.
     */
    @Column(name = "IDTIPODESTINOMENSAJE")
    private Long idTipoDestinoMensaje;


    /**
     * Central donde fue reportada la persona
     */
    @Column(name = "NOMBRECENTRAL")
    private String nombreCentral;

    /**
     * Id del tipo del tipo de obligacion en mora tabla (TIPOOBLIGACION)
     */
    @Column(name = "IDTIPOOBLIGACION")
    private Long idTipoObligacion;

    /**
     * Fecha del reporte de la mora
     */
    @Column(name = "FECHAREPORTE")
    private Date fechaReporte;

    /**
     * Clasificador rango de la mora tabla (TIPOCLASIFICACIONMORA)
     */
    @Column(name = "IDTIPOCLASIFICACIONMORA")
    private Long idTipoClaseMora;

    /**
     * Identificador del estado de la Mora tabla (TIPOESTADODEUDA)
     */
    @Column(name = "IDTIPOESTADODEUDA")
    private String idTipoEstadoDeuda;

    /**
     * Fecha pago de la mora
     */
    @Column(name = "FECHAPAGO")
    private Date fechaCargo;

    /**
     * Indica si el reporte se realizo de manera correcta
     */
    @Column(name = "IDTIPOCALIDADREPORTECREDITICIO")
    private Long idTipoCalReporteCrediticio;

    /**
     * Nombre de la empresa que reporta la mora en las centrales
     */
    @Column(name = "EMPRESAREPORTA")
    private String empresaReporte;

    /**
     * Identificador de la justificacion de la mora
     */
    @Column(name = "IDARCHIVOJUSTIFICACION")
    private Long idArchivoJustificacion;

    /**
     * Identificacion certificacion de finalizacion de mora
     */
    @Column(name = "IDARCHIVOCERTIFICACION")
    private Long idArchivoCertificacion;

    /**
     * La IP del cliente desde  se creó el mensaje.
     */
    @Column(name = "IDUSUARIO")
    private Long idUsuario;

    /**
     * La IP del cliente desde  se creó el mensaje.
     */
    @Column(name = "IPCLIENTE")
    private String ipCliente;
}
