package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The persistent class for the PERSONA database table.
 * 
 */
@Entity
@Table(name = "PERSONA")
@Data
public class Persona implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "PERSONA_IDPERSONA_GENERATOR", sequenceName = "PERSONA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSONA_IDPERSONA_GENERATOR")
	@Column(name = "IDPERSONA")
	private Long idPersona;

	@Column(name = "PRIMERNOMBRE")
	private String primerNombre;

	@Column(name = "SEGUNDONOMBRE")
	private String segundoNombre;

	@Column(name = "PRIMERAPELLIDO")
	private String primerApellido;

	@Column(name = "SEGUNDOAPELLIDO")
	private String segundoApellido;

	@Column(name = "IDTIPOGENERO")
	private Long idTipoGenero;

	@Column(name = "FECHANACIMIENTO")
	private LocalDate fechaNacimiento;

	@Column(name = "IDCIUDADNACIMIENTO")
	private Long idCiudadNacimiento;

	@Column(name = "IDCIUDADNACIMIENTONOCOD")
	private Long idCiudadNacimientoNoCod;

	@Column(name = "EMAILPRINCIPAL")
	private String emailPrincipal;

	@Column(name = "EMAILSECUNDARIO")
	private String emailSecundario;

	@Column(name = "IDDOMICILIOPRINCIPAL")
	private Long idDomicilioPrincipal;

	@Column(name = "IDDOMICILIOSECUNDARIO")
	private Long idDomicilioSecundario;

	@Column(name = "NUMEROCELULAR")
	private String numeroCelular;

	@Column(name = "IDTIPOESTADOCIVIL")
	private Long idTipoEstadoCivil;

	@Column(name = "NUMEROTARJETAPROFESIONAL")
	private String numeroTarjetaProfesional;

	@Column(name = "ENTETARJETAPROFESIONALEXP")
	private String enteTarjetaProfesionalExp;

	@Column(name = "OCUPACIONACTUAL")
	private String ocupacionActual;

	@Column(name = "EMPRESAOCUPACIONACTUAL")
	private String empresaOcupacionActual;

	@Column(name = "IDENTIDADOCUPACIONACTUAL")
	private Long idEntidadOcupacionActual;

	@Column(name = "NUMEROREGISTROCAMARACOMERCIO")
	private Long numeroRegistroCamaraComercio;

	@Column(name = "FECHAREGISTROCAMARACOMERCIO")
	private LocalDate fechaRegistroCamaraComercio;

	@Column(name = "OBSERVACIONES")
	private String observaciones;

	@Column(name = "OBSERVACIONESINTERNAS")
	private String observacionesInternas;

	@Column(name = "FECHACERTIFICADOJUDICIAL")
	private LocalDate fechaCertificadoJudicial;

	@Column(name = "IDTIPOCLASIFICACIONPERSONA")
	private Long idTipoClasificacionPersona;

	@Column(name = "PUBLICO")
	private Long publico;

	@Column(name = "ACTIVO")
	private Long activo;

	@Column(name = "IDPERSONAREAL")
	private Long idPersonaReal;

	@Column(name = "FECHAFOTO")
	private LocalDate fechaFoto;

	@Column(name = "IDUSUARIO")
	private Long idUsuario;

	@Column(name = "IPCLIENTE")
	private String ipCliente;

	@Column(name = "IDPAISNACIONALIDAD")
	private Long idPaisNacionalidad;

	@Column(name = "NOMBREPAISNACIONALIDAD")
	private String nombrePaisNacionalidad;
}