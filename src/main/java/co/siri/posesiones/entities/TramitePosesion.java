package co.siri.posesiones.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Clob;

/**
 * The persistent class for the TRAMITEPOSESION database table.
 * 
 */
@Entity
@Table(name = "TRAMITEPOSESION")
@Data
@NoArgsConstructor
public class  TramitePosesion implements Serializable {
	private static final long serialVersionUID = 20102016160307L;

		@Id
		@SequenceGenerator(name = "TRAMITEPOSESION_IDTRAMITEPOSESION_GENERATOR", sequenceName = "TRAMITEPOSESION_SEQ", allocationSize = 1)
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAMITEPOSESION_IDTRAMITEPOSESION_GENERATOR")
		@Column(name = "IDTRAMITEPOSESION")
		private Long idTramitePosesion;

		@Column(name = "IDTIPOCARGO")
		private Long idTipoCargo;

		@Column(name = "IDTIPOCALIDADCARGO")
		private Long idTipoCalidadCargo;
		
		@Column(name = "IDCANDIDATIZADOPOR")
		private Long idCandidatizadoPor;

		@Column(name = "CANDIDATIZADOPOR")
		private String candidatizadoPor;

		@Column(name = "CENTROCONCILIACION")
		private String centroConciliacion;

		@Column(name = "EMAILNOTIFICACIONPOSTULADO")
		private String emailNotificacionPostulado;

		@Column(name = "ENCARGO")
		private Boolean encargo;

		@Column(name = "FECHAACEPTACION")
		private Date fechaAceptacion;

		@Column(name = "FECHADESDEENCARGO")
		private Date fechaDesdeEncargo;

		@Column(name = "FECHADOCUMENTODESIGNACION")
		private Date fechaDocumentoDesignacion;

		@Column(name = "FECHAHASTAENCARGO")
		private Date fechaHastaEncargo;

		@Column(name = "FECHAPRIMERATRANSMISION")
		private Date fechaPrimeraTransmision;

		@Column(name = "FECHARECURSO")
		private Date fechaRecurso;

		@Column(name = "FECHADESDE")
		private Date fechaDesde;

		@Column(name = "FECHAULTIMADEVOLUCION")
		private Date fechaUltimaDevolucion;

		@Column(name = "FECHAULTIMATRANSMISION")
		private Date fechaUltimaTransmision;

		@Column(name = "HORASDEDICACIONMENSUAL")
		private Long horasDedicacionMensual;

		@Column(name = "IDPERSONA")
		private Long idPersona;

		@Column(name = "IDPERSONAFOTO")
		private Long idPersonaFoto;

		@Column(name = "IDTIPOORGANODESIGNADOR")
		private Long idTipoOrganoDesignador;

		@Column(name = "IDTIPODOCUMENTODESIGNACION")
		private Long idTipoDocumentoDesignacion;

		@Column(name = "IDTIPOVINCULOCONTRACTUAL")
		private Long idTipoVinculoContractual;

		@Column(name = "IDCARGOOTRAACTUACION")
		private Long idCargoContraActuacion;

		@Column(name = "IDTIPOPATROCINADORPOSESION")
		private Long idTipoPatrocinadorPosesion;

		@Column(name = "IDARCHIVOTRAMITEPOSESION")
		private Long idArchivoTramitePosesion;

		@Column(name = "IDARCHIVOFIRMADO")
		private Long idArchivoFirmado;

		@Column(name = "IDARCHIVOANTESDEFIRMA")
		private Long idArchivoAntesDeFirma;

		@Column(name = "IDPERSONAREEMPLAZADA")
		private Long idPersonaReemplazada;

		@Column(name = "IDTIPOCALIDADJUNTADIRECTIVA")
		private Long idTipoCalidadJuntaDirectiva;

		@Column(name = "IDTIPOACTIVIDADREPRESENTANTE")
		private Long idTipoActividadRepresentante;

		@Column(name = "IDTIPOCLASEREPRESENTANTE")
		private Long idTipoClaseRepresentante;

		@Column(name = "IDTIPOTRAMITEPOSESION")
		private Long idTipoTramitePosesion;

		@Column(name = "IDTIPOMOTIVORETIRO")
		private Long idTipoMotivoRetiro;

		/*@Column(name = "IDUSUARIOREGISTRO")
		private Long idUsuarioRegistro;*/

		@Column(name = "IDTIPOESTADOTRAMITEPOSESION")
		private Long idTipoEstadoTramitePosesion;

		@Column(name = "IDENTIDAD")
		private Long idEntidad;

		@Column(name = "IDUSUARIO")
		private Long idUsuario;

		@Column(name = "IDDOMICILIOATENCION")
		private Long idDomicilioAtencion;

		@Column(name = "IDDOMICILIONOTIFICACIONENTIDAD")
		private Long idDomicilioNotificacionEntidad;

		@Column(name = "IDACTOADMINISTRATIVORESULTADO")
		private Long idActoAdministrativoResultado;

		@Column(name = "IDTIPOESTADOPRECALIFICACION")
		private Long idTipoEstadoPrecalificacion;

		@Column(name = "IDTIPOAUTORIZACIONTRAMITE")
		private Long idTipoAutorizacionTramite;

		@Column(name = "IDCARGOANTESDETRAMITE")
		private Long idCargoAntesDeTramite;

		@Column(name = "IDTIPOCARGOFUNCION")
		private Long idTipoCargoFuncion;

		/*@Column(name = "IDACTOADMINISTRATIVORESPUESTA")
		private Long idActoAdministrativoRespuesta;*/

		@Column(name = "IDARCHIVOCONFLICTOINTERES")
		private Long idArchivoConflictoInteres;

		@Column(name = "INTERPUSORECURSO")
		private Boolean interpusoRecurso;

		@Column(name = "IPCLIENTE")
		private String ipCliente;

		/*@Column(name = "TRAMITESISTEMAANTERIOR")
		private String tramitesSistemaAnterior;*/

		@Column(name = "JUSTIFICACIONCANCELACION")
		private String justificacionCancelacion;

		@Column(name = "NOMBRECARGO")
		private String nombreCargo;

		@Column(name = "FACULTADCONCILIAR")
		private Boolean facultadConciliar;

		@Column(name = "NUMERODOCUMENTODESIGNACION")
		private String numeroDocumentoDesignacion;

		@Column(name = "NUMERORADICACION")
		private String numeroRadicacion;

		@Column(name = "PUNTAJEFINAL")
		private Float puntajeFinal;

		@Column(name = "RECOMENDACION")
		private String recomendacion;

		@Column(name = "RENGLONJUNTA")
		private Long renglonJunta;

		@Column(name = "CERTIFICAREQUISITOSABREVIADA")
		private Boolean certificaRequisitosAbreviada;

		@Column(name = "CARGORESPONSABLETRAMITE")
		private String cargoResponsableTramite;

		@Column(name = "REVISORFISCALOTRAENTIDAD")
		private Boolean revisorFiscalOtraEntidad;

		@Column(name = "ESPARTEDEFIRMAAUDITORA")
		private Boolean esParteDeFirmaAuditora;

		@Column(name = "SERVIDORPUBLICO")
		private Boolean servidorpublico;

		@Column(name = "CARGOSERVIDORPUBLICO")
		private String cargoservidorpublico;

		@Column(name = "ENTIDADSERVIDORPUBLICO")
		private String entidadservidorpublico;

		@Column(name = "CONFLICTOINTERES")
		private Boolean conflictoInteres;

		@Column(name = "SELECCIONADOPARACOMITE")
		private Boolean seleccionadoParaComite;

		@Column(name = "FECHACOMITE")
		private Date fechaComite;

		@Column(name = "ESTADOAUTORIZADONEGADO")
		private String estadoAutorizadoNegado;

		@Column(name = "OBSERVACIONES")
		private String observaciones;

		
		@Column(name = "NOMBRERESPONSABLETRAMITE")
		private String nombreResponsableTramite;

		@Column(name = "NOTIFICACIONELECTRONICA")
		private Boolean notificacionElectronica	;

		@Column(name = "CERTIFICADOVIGENTEAMV")
		private Boolean certificadoVigenteAmv;

		@Column(name = "NOMBREDELEGATURA")
		private String nombreDelegatura;
		@Column(name="INDVISTO")
		private Boolean indvisto;
	}
