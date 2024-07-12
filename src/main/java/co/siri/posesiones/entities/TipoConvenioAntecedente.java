package co.siri.posesiones.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "TIPOCONVENIOANTECEDENTE")
@Data
public class TipoConvenioAntecedente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDTIPOCONVENIOANTECEDENTE", nullable = false)
    private Long idTipoConvenioAntecedente;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ACTIVO")
    private char activo;
    @Column(name = "ORDEN")
    private Integer orden;
    @Column(name = "ARCHIVOOBLIGATORIO")
    private char archivoObligatorio;

}
