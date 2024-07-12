package co.siri.posesiones.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "ASIGNACIONTRAMITEANALISTA")
@Data
public class AsignacionTramiteAnalista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDASIGNACIONTRAMITEANALISTA")
    private Long idAsignacionTramiteAnalista;

    @Column(name = "IDTRAMITEPOSESION")
    private Long idTramitePosesion;

    @Column(name = "IDANALISTATRAMITEPOSESION")
    private Long idAanalistaTramitePosesion;

    @Column(name = "CARGA")
    private Long carga;

    @Column(name = "IDUSUARIO")
    private Long idUsuario;

    @Column(name = "IPCLIENTE")
    private Long ipCliente;
}
