package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "TIPOCALIDADCARGO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoCalidadCargo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDTIPOCALIDADCARGO")
    private Long idTipoCalidadCargo;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "IDTIPOCARGO")
    private Long idTipoCargo;

    @Column(name = "ORDEN")
    private Long Order;

    @Column(name = "ACTIVO")
    private Boolean activo;

    @Column(name = "IDCARGORLSV")
    private Long idTipoCargoSv;
}
