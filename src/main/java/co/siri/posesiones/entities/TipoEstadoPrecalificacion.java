package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "TIPOAUTORIZACIONTRAMITE")
@Data

public class TipoEstadoPrecalificacion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TIPOESTADOPRECALIFICACION_IDTIPOESTADOPRECALIFICACION_GENERATOR", sequenceName = "TIPOESTADOPRECALIFICACION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPOAUTORIZACIONTRAMITE_IDTIPOAUTORIZACIONTRAMITE_GENERATOR")
    @Column(name = "IDTIPOESTADOPRECALIFICACION")

    private Long idTipoEstadoPrecalificacion;

    private String nombre;

    private Long orden;

    private Boolean activo;

}


