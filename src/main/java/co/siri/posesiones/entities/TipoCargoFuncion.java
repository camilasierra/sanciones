package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "TIPOCARGOFUNCION")
@Data

public class TipoCargoFuncion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TIPOCARGOFUNCION_IDTIPOCARGOFUNCION_GENERATOR", sequenceName = "TIPOCARGOFUNCION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPOCARGOFUNCION_IDTIPOCARGOFUNCION_GENERATOR")
    @Column(name = "IDTIPOCARGOFUNCION")
    private Long idTipoCargoFuncion;
    @Column(name = "NOMBRE")
    private String Nombre;
    @Column(name = "ORDEN")
    private int orden;
    @Column(name = "ACTIVO")
    private boolean activo;

}


