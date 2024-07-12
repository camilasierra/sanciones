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
@Table(name = "INVESTIGACIONPERSONA")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvestigacionPersona implements Serializable {

    @Serial
    private static final long serialVersionUID = 1209123L;
    /**
     * El ID pk de la tabla INVESTIGACIONPERSONA
     */
    @Id
    @SequenceGenerator(name = "INVESTIGACIONPERSONA_GENERATOR", sequenceName = "INVESTIGACIONPERSONA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INVESTIGACIONPERSONA_GENERATOR")
    @Column(name = "IDINVESTIGACIONPERSONA", nullable = false)
    private Long IdInvestigacionPersona;

    /**
     * Identificador de la persona a la cual pertenece la investigacion (PERSONA)
     */
    @Column(name = "IDPERSONA")
    private Long idPersona;

    /**
     * Identificador del tipo de investigacion (TIPOINVESTIGACION)
     */
    @Column(name = "IDTIPOINVESTIGACION")
    private Long idTipoInvestigacion;

    /**
     * Identificador de la entidad que realiza la investigacion (TIPOENTIDADINVESTIGACIONSANC)
     */
    @Column(name = "IDTIPOENTIDADINVESTIGACIONSANC")
    private Long idTipoEntidadInvestigacionSanc;

    /**
     * Identificador del tema o materia por la cual serealiza la investigacion (TIPOMATERIAINVESTIGACIONSANC)
     */
    @Column(name = "IDTIPOMATERIAINVESTIGACIONSANC")
    private Long idTipoMateriaInvestigacionSanc;

    /**
     * Identificador del tipo de acto administrativo sancionatorio (TIPOACTOINVESTIGACIONSANCION)
     */
    @Column(name = "IDTIPOACTOINVESTIGACIONSANCION")
    private Long idTipoActoInvestigacionSancion;

    /**
     * Número investigación
     */
    @Column(name = "NUMERO")
    private String numero;

    /**
     * Identificador del estado de la investigación
     */
    @Column(name = "IDTIPOESTADOINVESTIGACION")
    private Long idTipoEstadoInvestigacion;

    /**
     * Campo de auditoría, usuario que creó o modificó el registro. (USUARIO - Sistema de Autorización)
     */
    @Column(name = "IDUSUARIO")
    private Long idUsuario;

    /**
     * Campo de auditor IP del equipo desde donde se conecta el usuario.
     */
    @Column(name = "IPCLIENTE")
    private String ipCliente;

    /**
     * Fecha de inicio de la investigación
     */
    @Column(name = "FECHADESDE")
    private Date fechaDesde;

    /**
     * Fecha de finalización de la investigación
     */
    @Column(name = "FECHAHASTA")
    private Date fechaHasta;
}
