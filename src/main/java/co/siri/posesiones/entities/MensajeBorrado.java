package co.siri.posesiones.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "MENSAJEBORRADO")
@Data
public class MensajeBorrado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDMENSAJEBORRADO")
    private Long idMensajeBorrado;

    @Column(name = "IDMENSAJEREVISIONTRAMITE")
    private Long idMensajeRevisionTramite;

    @Column(name = "IDUSUARIO")
    private Long idUsuario;

    @Column(name = "IPCLIENTE")
    private String ipCliente;
}
