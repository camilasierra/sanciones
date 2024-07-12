package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The persistent class for the DOCUMENTOPERSONA database table.
 * 
 */
@Entity
@Table(name = "DOCUMENTOPERSONA")
@Data
public class DocumentoPersona implements Serializable {
	private static final long serialVersionUID = 20161231000000L;

	@Id
	@SequenceGenerator(name = "DOCUMENTOPERSONA_IDDOCUMENTOPERSONA_GENERATOR", sequenceName = "DOCUMENTOPERSONA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUMENTOPERSONA_IDDOCUMENTOPERSONA_GENERATOR")
	@Column(name = "IDDOCUMENTOPERSONA")
	private Long idDocumentoPersona;
	@Column(name = "IDPERSONA")
	private Long idPersona;
	@Column(name = "IDTIPODOCUMENTO")
	private Long idTipoDocumento;
	@Column(name = "NUMERO")
	private String numero;
	@Column(name = "FECHAINGRESO")
	private LocalDate fechaIngreso;
	@Column(name = "PRINCIPAL")
	private Boolean principal;
	@Column(name = "FECHAFINPRINCIPAL")
	private LocalDate fechaFinPrincipal;
	@Column(name = "FECHAEXPEDICION")
	private LocalDate fechaExpedicion;
	@Column(name = "IDCIUDADEXPEDICION")
	private Long idCiudadExpedicion;
	@Column(name = "IDCIUDADEXPEDICIONNOCOD")
	private Long idCiudadExpedicionNoCod;
	@Column(name = "IDUSUARIO")
	private Long idUsuario;
	@Column(name = "IPCLIENTE")
	private String ipCliente;
	@Column(name = "ANULADO")
	private boolean anulado;
}