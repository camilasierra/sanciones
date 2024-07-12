package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PRIORIDADTRAMITEPOSESION")
@Data
public class PrioridadTramitePosesion {

    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prioridadtramiteposesion_seq")
    @SequenceGenerator(name = "prioridadtramiteposesion_seq", sequenceName = "prioridadtramiteposesion_seq", allocationSize = 1)

    @Column(name = "IDPRIORIDADTRAMITEPOSESION")
    private Long idPrioridadTramitePosesion;

    @Column(name = "IDTIPOENTIDAD")
    private Long idTipoEntidad ;

    @Column(name = "IDENTIDAD")
    private Long idEntidad;

    @Column(name = "ENTIDADPUBLICA")
    private String entidadPublica;

    @Column(name = "IDPERSONA")
    private Long idPersona;

    @Column(name = "NUMEROIDENTIFICACION")
    private String numeroIdentificacion;

    @Column(name = "IDTRAMITEPOSESION")
    private Long idTramitePosesion;

    @Column(name = "IDUSUARIO")
    private Long idUsuario;

    @Column(name = "IPCLIENTE")
    private String IPCLIENTE;

}