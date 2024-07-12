package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * The persistent class for the TIPOENTIDAD database table.
 * 
 */
@Entity
@Table(name = "TIPOENTIDAD")
@Data
public class TipoEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TIPOENTIDAD_IDTIPOENTIDAD_GENERATOR", sequenceName = "TIPOENTIDAD_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPOENTIDAD_IDTIPOENTIDAD_GENERATOR")
	@Column(name = "IDTIPOENTIDAD")
	private Long idTipoEntidad;
	@Column(name = "NOMBRE")
	private String nombre;
	@Column(name = "CODIGO")
	private String codigo;
	@Column(name = "CATEGORIA")
	private Long categoria;
	@Column(name = "GRUPO")
	private Long grupo;
	@Column(name = "IDCLASEENTIDAD")
	private Long idClaseEntidad;
	@Column(name = "IDCODIGOCLASEANNA")
	private Long idCodigoClaseAnna;
	@Column(name = "TIPORNVEI")
	private String tipoRNVEI;
	@Column(name = "CODIGOAREA")
	private Long codigoArea;
	@Column(name = "TIPOENTIDAD")
	private Long tipoEntidad;
	@Column(name = "INDVIGILADO")
	private Long indVigilado;
	@Column(name = "ACTIVO")
	private Long activo;
}