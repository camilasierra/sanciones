package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "TIPOVINCULOCONTRACTUAL")
@Data

public class TipoVinculoContractual implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TIPOVINCULOCONTRACTUAL_IDTIPOVINCULOCONTRACTUAL_GENERATOR", sequenceName = "TIPOVINCULOCONTRACTUAL_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPOVINCULOCONTRACTUAL_IDTIPOVINCULOCONTRACTUAL_GENERATOR")
    @Column(name = "IDTIPOVINCULOCONTRACTUAL")
    private Long idTipoVinculoContractual;

    @Column(name = "NOMBRE")
    private String Nombre;
    @Column(name = "ORDEN")
    private int orden;
    @Column(name = "ACTIVO")
    private boolean activo;

}


