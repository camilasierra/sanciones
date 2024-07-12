package co.siri.posesiones.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MIEMBROCOMITE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiembroComite implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "miembro_comite_seq")
    @SequenceGenerator(name = "miembro_comite_seq", sequenceName = "MIEMBROCOMITE_SEQ", allocationSize = 1)
    @Column(name = "IDMIEMBROCOMITE")
    private Integer idmiembrocomite;

    @Column(name = "NOMBREPERSONA")
    private String nombrePersona;

    @Column(name = "ORDEN")
    private Integer orden;

    @Column(name = "ACTIVO")
    private String activo; 

    @Column(name = "TEXTOACTA")
    private String textoActa;

    @Column(name = "IDUSUARIO")
    private String idusuario;

    @Column(name = "IPCLIENTE")
    private String ipCliente;

    @Column(name = "IDTIPOMIEMBROCOMITE")
    private Integer idTipoMiembroComite;

    @Column(name = "NUMEROIDENTIFICACION")
    private String numeroIdentificacion;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "AUTORIZADOCOMITEVIRTUAL")
    private String autorizadoComiteVirtual; 

    @Column(name = "DELEGATURA")
    private String delegatura; 

    @Column(name = "CARGO")
    private String cargo; 
}
