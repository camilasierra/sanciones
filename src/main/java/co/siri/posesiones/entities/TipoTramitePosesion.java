package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "TIPOTRAMITEPOSESION")
@Data

public class TipoTramitePosesion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TIPOTRAMITEPOSESION_IDTIPOTRAMITEPOSESION_GENERATOR", sequenceName = "TIPOTRAMITEPOSESION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPOTRAMITEPOSESION_IDTIPOTRAMITEPOSESION_GENERATOR")
    @Column(name = "IDTIPOTRAMITEPOSESION")
    private Long idTipoTramitePosesion;

    @Column(name = "IDTIPOCLASETRAMITEPOSESION")
    private Long idTipoClaseTramitePosesion;

    @Column(name = "NOMBRE")
    private String Nombre;
    @Column(name = "ORDEN")
    private int orden;
    @Column(name = "ACTIVO")
    private boolean activo;
}


