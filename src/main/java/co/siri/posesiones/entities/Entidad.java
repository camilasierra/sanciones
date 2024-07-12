package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The persistent class for the ENTIDAD database table.
 * 
 */
@Entity
@Table(name = "ENTIDAD")
@Data
public class Entidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ENTIDAD_IDENTIDAD_GENERATOR", sequenceName = "ENTIDAD_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENTIDAD_IDENTIDAD_GENERATOR")
	@Column(name = "IDENTIDAD")
	private Long idEntidad;
	@Column(name = "IDTIPOENTIDAD")
	private Long idTipoEntidad;
	@Column(name = "CODIGOENTIDAD")
	private Long codigoEntidad;
	@Column(name = "IDESTADOENTIDAD")
	private Long idEstadoEntidad;
	@Column(name = "IDNATURALEZAJURIDICA")
	private Long idNaturalezaJuridica;
	@Column(name = "IDSECTORECONOMICO")
	private Long idSectorEconomico;
	@Column(name = "IDCIIU")
	private Long idCiiu;
	@Column(name = "IDTIPOSOCIEDAD")
	private Long idTipoSociedad;
	@Column(name = "IDCLASEENTIDAD")
	private Long idClaseEntidad;
	@Column(name = "IDSITUACIONLEGAL")
	private Long idSituacionLegal;
	@Column(name = "IDTIPODOCUMENTO")
	private Long idTipoDocumento;
	@Column(name = "IDNIVELVIGILANCIA")
	private Long idNivelVigilancia;
	@Column(name = "IDPERSONACONTACTO")
	private Long idPersonaContacto;
	@Column(name = "IDDOMICILIOPPAL")
	private Long idDomicilioPPal;
	@Column(name = "IDDOMICILIONOT")
	private Long idDomicilioNot;
	@Column(name = "IDTIPOSUBORDINACION")
	private Long idTipoSubordinacion;
	@Column(name = "FECHADESDEVIGILADA")
	private LocalDate fechaDesdeVigilada;
	@Column(name = "FECHAHASTAVIGILADA")
	private LocalDate fechaHastaVigilada;
	@Column(name = "FECHAACTUALIZACION")
	private LocalDate fechaActualizacion;
	@Column(name = "RAZONSOCIAL")
	private String razonSocial;
	@Column(name = "SIGLA")
	private String sigla;
	@Column(name = "NUMEROIDENTIFICACION")
	private String numeroIdentificacion;
	@Column(name = "URIPAGINAWEB")
	private String uriPaginaWeb;
	@Column(name = "INDVIGILADA")
	private Long indVigilada;
	@Column(name = "CODIGOENTIDADSV")
	private Long codigoEntidadSV;
	@Column(name = "IDENTIDADVIGILA")
	private Long idEntidadVigila;
	@Column(name = "IDNEMONICO")
	private Long idNemonico;
	@Column(name = "CODIGODEPENDENCIA")
	private String codigoDependencia;
	@Column(name = "CODIGO_ENTIDAD")
	private Long codigo_Entidad;
	@Column(name = "CODIGO_ENTIDAD_SV")
	private Long codigo_Entidad_SV;
	@Column(name = "FECHA_ACTUALIZACION")
	private LocalDate fecha_Actualizacion;
	@Column(name = "FECHA_DESDE_VIGILADA")
	private LocalDate fecha_Desde_Vigilada;
	@Column(name = "FECHA_HASTA_VIGILADA")
	private LocalDate fecha_Hasta_Vigilada;
	@Column(name = "ID_CIIU")
	private Long id_Ciiu;
	@Column(name = "ID_CLASE_ENTIDAD")
	private Long id_Clase_Entidad;
	@Column(name = "ID_DOMICILIO_NOT")
	private Long id_Domicilio_Not;
	@Column(name = "ID_DOMICILIO_PPAL")
	private Long id_Domicilio_PPal;
	@Column(name = "ID_ENTIDAD_VIGILA")
	private Long id_Entidad_Vigila;
	@Column(name = "ID_ESTADO_ENTIDAD")
	private Long id_Estado_Entidad;
	@Column(name = "ID_NATURALEZA_JURIDICA")
	private Long id_Naturaleza_Juridica;
	@Column(name = "ID_NEMONICO")
	private Long id_Nemonico;
	@Column(name = "ID_NIVEL_VIGILANCIA")
	private Long id_Nivel_Vigilancia;
	@Column(name = "ID_PERSONA_CONTACTO")
	private Long id_Persona_Contacto;
	@Column(name = "ID_SECTOR_ECONOMICO")
	private Long id_Sector_Economico;
	@Column(name = "ID_SITUACION_LEGAL")
	private Long id_Situacion_Legal;
	@Column(name = "ID_TIPO_DOCUMENTO")
	private Long id_Tipo_Documento;
	@Column(name = "ID_TIPO_SOCIEDAD")
	private Long id_Tipo_Sociedad;
	@Column(name = "ID_TIPO_SUBORDINACION")
	private Long id_Tipo_Subordinacion;
	@Column(name = "IND_VIGILADA")
	private Long ind_Vigilada;
	@Column(name = "NUMERO_IDENTIFICACION")
	private String numero_Identificacion;
	@Column(name = "RAZON_SOCIAL")
	private String razon_Social;
	@Column(name = "URI_PAGINA_WEB")
	private String uri_Pagina_Web;
}