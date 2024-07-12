package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "TIPOAUTORIZACIONTRAMITE")
@Data

public class TipoAutorizacionTramite implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TIPOAUTORIZACIONTRAMITE_IDTIPOAUTORIZACIONTRAMITE_GENERATOR", sequenceName = "TIPOAUTORIZACIONTRAMITE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPOAUTORIZACIONTRAMITE_IDTIPOAUTORIZACIONTRAMITE_GENERATOR")
    @Column(name = "IDTIPOAUTORIZACIONTRAMITE")
    private Long idTipoAutorizacionTramite;
    @Column(name = "NOMBRE")
    private String Nombre;
    @Column(name = "ORDEN")
    private int orden;
    @Column(name = "ACTIVO")
    private boolean activo;

}


