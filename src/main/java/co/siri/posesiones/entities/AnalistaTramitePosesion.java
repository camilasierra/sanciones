package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ANALISTATRAMITEPOSESION")
@Data
public class AnalistaTramitePosesion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "analistatramiteposesion_seq")
    @SequenceGenerator(name = "analistatramiteposesion_seq", sequenceName = "analistatramiteposesion_seq", allocationSize = 1)
    @Column(name = "IDANALISTATRAMITEPOSESION")
    private Long idAnalistaTramitePosesion;
    @Column(name = "LOGIN")
    private String login;
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "IDTIPOANALISTA")
    private Long idTipoAnalista;
    @Column(name = "IDTIPOINACTIVACIONANALISTA")
    private Long idTipoInactivacionAnalista;
    @Column(name = "ACTIVO")
    private String activo;
    @Column(name = "LIMITECARGA")
    private Long limiteCarga;
    @Column(name = "IDUSUARIO")
    private Long idUsuario;
    @Column(name = "IPCLIENTE")
    private String ipCliente;
}
