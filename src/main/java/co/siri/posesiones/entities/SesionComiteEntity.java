package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "sesioncomite")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SesionComiteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDSESIONCOMITE")
    private Long id;

    @Column(name = "NUMEROACTA")
    private Integer numeroActa;

    @Column(name = "FECHACOMITE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaComite;

    @Column(name = "IDTIPOSESIONCOMITE")
    private Integer idTipoSesionComite;

    @Column(name = "IDUSUARIO")
    private Integer idUsuario;

    @Column(name = "IPCLIENTE")
    private String ipCliente;

    @Column(name = "HORAINICIOCOMITE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaInicioComite;

    @Column(name = "HORAFINCOMITE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaFinComite;

    @Column(name = "OFICIAL")
    private String oficial;

    @Column(name = "VARIOS")
    private String varios;

    @Column(name = "IDTIPOMODALIDADSESIONCOMITE")
    private Integer idTipoModalidadSesionComite;
}
