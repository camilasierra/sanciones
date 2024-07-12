package co.siri.posesiones.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "TIPOCOMPLEMENTOESTADOCARGO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoComplementoEstadoCargo implements Serializable {

    private static final long serialVersionUID = 20180302154901L;

    @Id
    @Column(name = "IDTIPOCOMPLEMENTOESTADOCARGO")
    private Long idTipoComplementoEstadoCargo;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ORDEN")
    private Long orden;

    @Column(name = "ACTIVO")
    private String activo;
}
