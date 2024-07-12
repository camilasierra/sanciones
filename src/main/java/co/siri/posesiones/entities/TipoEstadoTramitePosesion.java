package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "IPOESTADOTRAMITEPOSESION")
@Data
public class TipoEstadoTramitePosesion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TIPOESTADOTRAMITEPOSESION_IDTIPOESTADOTRAMITEPOSESION_GENERATOR", sequenceName = "TIPOESTADOTRAMITEPOSESION_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPOESTADOTRAMITEPOSESION_IDTIPOESTADOTRAMITEPOSESION_GENERATOR")
    @Column(name = "IDTIPOESTADOTRAMITEPOSESION")
    private Long idTipoEstadoTramitePosesion;

    @Column(name = "ACTIVO")
    private Boolean activo;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ORDEN")
    private Long orden;
}
