package co.siri.posesiones.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SESIONCOMITEREUNION")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SesionComiteReunion {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SESIONCOMITEREUNION_SEQ")
    @SequenceGenerator(
            sequenceName = "SESIONCOMITEREUNION_SEQ",
            name = "SESIONCOMITEREUNION_SEQ",
            allocationSize = 1)
	@Column(name = "IDSESIONCOMITEREUNION", nullable = false)
	private Long idSesionComiteReunion;

	@Column(name = "IDSESIONCOMITE", nullable = false)
	private Long idSesionComite;

	@Column(name = "FECHAINICIOCOMITE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicioComite;

	@Column(name = "FECHAHORAFINCOMITE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaHoraFinComite;

	@Column(name = "IDARCHIVOCORREOSVOTO")
	private Long idArchivoCorreosVoto;

	@Column(name = "ITERACION", nullable = false)
	private Long iteracion;

	@Column(name = "IDUSUARIO", nullable = false)
	private Long idUsuario;

	@Column(name = "IPCLIENTE", nullable = false, length = 39)
	private String ipCliente;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "IDARCHIVOCORREOSVOTO", referencedColumnName =
	 * "IDARCHIVOTRAMITEPOSESION", insertable = false, updatable = false) private
	 * ArchivoTramitePosesion archivoTramitePosesion;
	 * 
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "IDSESIONCOMITE", referencedColumnName = "IDSESIONCOMITE",
	 * insertable = false, updatable = false) private SesionComite sesionComite;
	 */
}
