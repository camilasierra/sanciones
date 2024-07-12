package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "TIPOOPCIONSINO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoOpcionSiNo implements Serializable {
    @Id
    @SequenceGenerator(name = "TIPOOPCIONSINO_IDTIPOOPCIONSINO_GENERATOR", sequenceName = "TIPOOPCIONSINO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPOOPCIONSINO_IDTIPOOPCIONSINO_GENERATOR")
    @Column(name = "IDTIPOOPCIONSINO")
    private Long idTipoOpcionSiNo;

    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ORDEN")
    private int orden;
    @Column(name = "ACTIVO")
    private boolean activo;
}
