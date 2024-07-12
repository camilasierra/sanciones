package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ASIGNACIONTRAMITEANALISTA")
@Data
public class AsignacionTramiteAnalistas {

    @Id
    @SequenceGenerator(name = "ASIGNACIONTRAMITEANALISTA_SEQ", sequenceName = "ASIGNACIONTRAMITEANALISTA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ASIGNACIONTRAMITEANALISTA_SEQ")
    @Column(name = "IDASIGNACIONTRAMITEANALISTA", nullable = false)
    private Long idAsignacionTramiteAnalista;
    @Column(name = "IDTRAMITEPOSESION")
    private Long idTramitePosesion;
    @Column(name = "IDANALISTATRAMITEPOSESION")
    private Long idAnalistaTramitePosesion;
    @Column(name = "IDUSUARIO")
    private Long idUsuario;
    @Column(name = "IPCLIENTE")
    private String ipCliente;
}
