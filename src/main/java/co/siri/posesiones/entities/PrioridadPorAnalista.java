package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "PRIORIDADPORANALISTA")
@Data
public class PrioridadPorAnalista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "PRIORIDADPORANALISTA_SEQ", sequenceName = "PRIORIDADPORANALISTA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRIORIDADPORANALISTA_SEQ")
    @Column(name = "IDPRIORIDADPORANALISTA")
    private Long idprioridadPorAnalista;

    @Column(name = "IDANALISTATRAMITEPOSESION")
    private Long idAnalistaTramitePosesion;

    @Column(name = "IDPRIORIDADTRAMITEPOSESION")
    private Long idPrioridadTramitePosesion;

    @Column(name = "ACTIVO")
    private Long activo;

    @Column(name = "IDUSUARIO")
    private Long idUsuario;

    @Column(name = "IPCLIENTE")
    private String ipCliente;

}
