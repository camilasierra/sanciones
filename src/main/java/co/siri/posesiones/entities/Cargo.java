package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CARGO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cargo implements Serializable {
    private static final long serialVersionUID = 20170531155001L;

    @Id
    @SequenceGenerator(name = "CARGO_SEQ", sequenceName = "CARGO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARGO_SEQ")
    @Column(name = "IDCARGO")
    private Long idCargo;

    @Column(name = "IDTIPOCARGO")
    private Long idTipoCargo;

    @Column(name = "IDTIPOCALIDADCARGO")
    private Long idTipoCalidadCargo;

    @Column(name = "RENGLON")
    private Long renglon;

    @Column(name = "IDENTIDAD")
    private Long idEntidad;

    @Column(name = "IDPERSONA")
    private Long idPersona;

    @Column(name = "FECHADESDE")
    private Date fechaDesde;

    @Column(name = "FECHAHASTA")
    private Date fechaHasta;

    @Column(name = "IDTIPOCOMPLEMENTOESTADOCARGO")
    private Long idTipoComplementoEstadoCargo;

    @Column(name = "RESPONSABLECORRESPONDENCIA")
    private Boolean responsablecorrespondencia;

    @Column(name = "NOMBREPUBLICOCARGO")
    private String nombrePublicoCargo;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "NUMEROREGISTROCAMARACOMERCIO")
    private Long numeroRegistroCamaraComercio;

    @Column(name = "FECHAREGISTROCAMARACOMERCIO")
    private Date FechaRegistroCamaraComercio;

    @Column(name = "CIUDADCAMARACOMERCIO")
    private Long ciudadCamaraComercio;

    @Column(name = "CODIGOOFICINA")
    private Long codigoOficina;

    @Column(name = "IDCANALSERVICIO")
    private Long idCanalServicio;

    @Column(name = "IDFIRMAAUDITORA")
    private Long idFirmaAuditora;

    @Column(name = "IDDOMICILIOATENCION")
    private Long idDomicilioatencion;

  /*  @Column(name = "IDTIPOIDENTIPERSONAREEMPLAZADA")
    private Long idTipoIdentiPersonaReemplazada;*/

    @Column(name = "IDENTIFPERSONAREEMPLAZADA")
    private String iDentifPersonaReemplazada;

    @Column(name = "CARGOCONPOSESION")
    private Boolean cargoConPosesion;

    @Column(name = "IDPERSONAREEMPLAZADA")
    private Long idPersonaReemplazada;

    @Column(name = "IDCARGOREEMPLAZADO")
    private Long idCargoReemplazado;

    @Column(name = "IDTIPOMIEMBROSALA")
    private Long idTipoMiembroSala;

    @Column(name = "IDTIPOCLASEMIEMBRO")
    private Long idTipoClaseMiembro;

    @Column(name = "IDTRAMITEPOSESIONINICIA")
    private Long idTramitePosesionInicia;

    @Column(name = "IDTRAMITEPOSESIONFINALIZA")
    private Long IdTramitePosesionFinaliza;

    @Column(name = "IDTIPODOCAUTORIZAPOSESION")
    private Long idTipoDocAutorizaPosesion;

    @Column(name = "IDTIPOMIEMBRO")
    private Long idTipoMiembro;

    /*@Column(name = "SOCIOOACCIONISTA")
    private String sociooAccionista;*/

    @Column(name = "LABORAENOTRAENTIDAD")
    private Boolean laboraEnOtraEntidad;

    @Column(name = "VINCULOLABORAL")
    private Boolean vinculoLaboral;

    @Column(name = "INDEPENDIENTE")
    private Boolean independiente;

    @Column(name = "NITENTIDAD")
    private String nitEntidad;

    @Column(name = "NOMBREENTIDAD")
    private String nombreEntidad;

    @Column(name = "TARJETAPROFESIONAL")
    private String tarjeteProfesional;

    @Column(name = "PROFESION")
    private String profesion;

    @Column(name = "NUMERORADICACIONINICIO")
    private String numeroRadicacionInicio;

    @Column(name = "FECHARADICACIONINICIO")
    private Date fechaRadicacionInicio;

    @Column(name = "NUMERORADICACIONFIN")
    private String numeroRadicacionFin;

    @Column(name = "FECHARADICACIONFIN")
    private Date fechaRadicacionFin;

    @Column(name = "FECHAREVISION")
    private Date fechaRevision;

    @Column(name = "INFORMACIONPUBLICA")
    private Boolean informacionPublica;

    @Column(name = "NUMERODOCAUTORIZAPOSESIONV")
    private String numeroDocAutorizaPosesionV;

    @Column(name = "NUMERODOCAUTORIZAPOSESION")
    private String numeroDocAutorizaPosesion;

    @Column(name = "FECHAAUTORIZAPOSESION")
    private Date fechaAutorizaPosesion;

    @Column(name = "IDTIPODOCDESIGNACION")
    private Long idTipoDocDesignacion;

    @Column(name = "NUMERODOCDESIGNACIONV")
    private String numeroDocDesignacionV;

    @Column(name = "NUMERODOCDESIGNACION")
    private Long numeroDocDesignacion;

    @Column(name = "FECHADOCDESIGNACION")
    private Date fechaDocDesigNacion;

    @Column(name = "NUMERODOCRETIROV")
    private String numeroDocRetiroV;

    @Column(name = "NUMERODOCRETIRO")
    private String numeroDocRetiro;

    @Column(name = "IDTIPODOCRETIRO")
    private Long idTipoDocRetiro;

    @Column(name = "FECHADOCRETIRO")
    private Date fechaDocRetiro;

    @Column(name = "IDTIPOMOTIVORETIRO")
    private Long idTipoMotivoRetiro;

    @Column(name = "DESCRIPCIONRETIRO")
    private String descripcionRetiro;

    @Column(name = "NOTASINTERNAS")
    private String notasInternas;

    @Column(name = "IPCLIENTE")
    private String ipCliente;

    @Column(name = "IDUSUARIO")
    private Long idUsuario;

    @Column(name = "ACTIVO")
    private String activo;

}
