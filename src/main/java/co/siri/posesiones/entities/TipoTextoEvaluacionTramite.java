package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TIPOTEXTOEVALUACIONTRAMITE")
public class TipoTextoEvaluacionTramite {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDTIPOTEXTOEVALUACIONTRAMITE")
    private int idTipoTextoEvaluacionTramite;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ORDEN")
    private int orden;

    @Column(name = "ACTIVO")
    private Character activo;
}
