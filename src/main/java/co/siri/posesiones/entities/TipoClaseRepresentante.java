package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "TIPOCLASEREPRESENTANTE")
@Data
public class TipoClaseRepresentante implements Serializable {
    @Id
    @SequenceGenerator(name = "TIPOCLASEREPRESENTANTE_IDTIPOCLASEREPRESENTANTE_GENERATOR", sequenceName = "TIPOCLASEREPRESENTANTE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPOCLASEREPRESENTANTE_IDTIPOCLASEREPRESENTANTE_GENERATOR")
    @Column(name = "IDTIPOCLASEREPRESENTANTE")
    private Long idTipoClaseRepresentante;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ORDEN")
    private int orden;
    @Column(name = "ACTIVO")
    private boolean activo;
}
