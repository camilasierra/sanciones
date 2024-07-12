package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "TIPOCARGO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoCargo implements Serializable {
    private static final long serialVersionUID = 20161129091200L;

    @Id
    @Column(name = "IDTIPOCARGO")
    private Long idTipoCargo;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "IDTIPOGRUPOCARGO")
    private Long idTipoGrupoCargo;

    @Column(name = "ORDEN")
    private Long Order;

    @Column(name = "ACTIVO")
    private Long activo;

    @Column(name = "IDTIPOCARGOSV")
    private Long idTipoCargosv;
}
