package co.siri.posesiones.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SESIONCOMITE")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SesionComite implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SESIONCOMITE_SEQ")
    @SequenceGenerator(
            sequenceName = "SESIONCOMITE_SEQ",
            name = "SESIONCOMITE_SEQ",
            allocationSize = 1)
    @Column(name = "IDSESIONCOMITE")
    private Long idSesionComite;

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "numeroActa_seq")
    @SequenceGenerator(name = "numeroActa_seq", sequenceName = "NUMEROACTA_SEQ", allocationSize = 1)
    @Column(name = "NUMEROACTA", unique = true)
    private Integer numeroActa;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "FECHACOMITE")
    private Date fechaComite;

    @ManyToOne(
            cascade = {})
    @JoinColumn(name = "IDTIPOSESIONCOMITE")
    private TipoSesion tipoSesion;

    @Column(name = "IDUSUARIO")
    private Integer idUsuario;

    @Column(name = "IPCLIENTE")
    private String ipCliente;

    @Column(name = "HORAINICIOCOMITE")
    private Date horaInicioComite;

    @Column(name = "HORAFINCOMITE")
    private Date horaFinComite;

    @Column(name = "OFICIAL")
    private Boolean oficial;

    @Column(name = "VARIOS")
    private String varios;

    @ManyToOne(
            cascade = {})
    @JoinColumn(name = "IDTIPOMODALIDADSESIONCOMITE")
    private TipoModalidadSesion tipoModalidad;
}
