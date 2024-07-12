package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "TIPOACTIVIDADREPRESENTANTE")
@Data
public class TipoActividadRepresentante implements Serializable {
    @Id
    @SequenceGenerator(name = "TIPOACTIVIDADREPRESENTANTE_IDTIPOACTIVIDADREPRESENTANTE_GENERATOR", sequenceName = "TIPOACTIVIDADREPRESENTANTE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPOACTIVIDADREPRESENTANTE_IDTIPOACTIVIDADREPRESENTANTE_GENERATOR")
    @Column(name = "IDTIPOACTIVIDADREPRESENTANTE")
    private Long idTipoActividadRepresentante;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ORDEN")
    private int orden;
    @Column(name = "ACTIVO")
    private boolean activo;
}
