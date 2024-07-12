package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "TIPOCALIDADJUNTADIRECTIVA")
@Data

public class TipoCalidadJuntaDirectiva implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TIPOCALIDADJUNTADIRECTIVA_IDTIPOCALIDADJUNTADIRECTIVA_GENERATOR", sequenceName = "TIPOCALIDADJUNTADIRECTIVA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPOCALIDADJUNTADIRECTIVA_IDTIPOCALIDADJUNTADIRECTIVA_GENERATOR")
    @Column(name = "IDTIPOCALIDADJUNTADIRECTIVA")

    private Long idTipoCalidadJuntaDirectiva;

    @Column(name = "NOMBRE")
    private String Nombre;
    @Column(name = "ORDEN")
    private int orden;
    @Column(name = "ACTIVO")
    private boolean activo;
    @Column(name = "PRINCIPAL")
    private Boolean principal;
}


