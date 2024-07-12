package co.siri.posesiones.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TIPOORGANODESIGNADOR")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoOrganoDesignador implements Serializable {
	
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDTIPOORGANODESIGNADOR")
    private Long idTipoOrganoDesignador;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ORDEN")
    private Long orden;

    @Column(name = "ACTIVO")
    private Boolean activo;
}
