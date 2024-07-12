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
@Table(name = "TIPOGRUPOCARGO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoGrupoCargo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDTIPOGRUPOCARGO")
    private Long idTipoGrupoCargo;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ORDEN")
    private Long orden;

    @Column(name = "ACTIVO")
    private Boolean activo;
}
