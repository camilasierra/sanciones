package co.siri.posesiones.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;


/**
 * The persistent class for the TIPODOCUMENTO database table.
 * 
 */
@Entity
@Table(name = "TIPODOCUMENTO")
@Data
public class TipoDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDTIPODOCUMENTO")
	private Long idTipoDocumento;
	@Column(name = "NOMBRE")
	private String nombre;
	@Column(name = "DISC")
	private String disc;
	@Column(name = "SIGLA")
	private String sigla;
	@Column(name = "CODIGORNVE")
	private Long codigoRnve;
	@Column(name = "CODIGOSB")
	private Long codigoSb;
	@Column(name = "SIGLARNVE")
	private String siglaRnve;
	@Column(name = "ACTIVO")
	private Boolean activo;
}