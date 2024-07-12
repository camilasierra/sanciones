package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "TIPOPATROCINADORPOSESION")
@Data

public class TipoPatrocinadorPosesion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TIPOPATROCINADORPOSESION_IDTIPOPATROCINADORPOSESION_GENERATOR", sequenceName = "TIPOPATROCINADORPOSESION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPOPATROCINADORPOSESION_IDTIPOPATROCINADORPOSESION_GENERATOR")
    @Column(name = "IDTIPOPATROCINADORPOSESION")
    private Long idTipoPatrocinadorPosesion;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ORDEN")
    private int orden;
}


