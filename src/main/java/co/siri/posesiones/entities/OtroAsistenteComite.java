package co.siri.posesiones.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "OTROASISTENTECOMITE")
public class OtroAsistenteComite {

    @Id
    @Column(name = "IDOTROASISTENTECOMITE")
    private Long idOtroAsistenteComite;

    @Column(name = "IDSESIONCOMITE")
    private Integer idSesionComite;

    @Column(name = "NOMBREOTROASISTENTE")
    private String nombreOtroAsistente;

    @Column(name = "CALIDADASISTENTE")
    private String calidadAsistente;

    @Column(name = "IDUSUARIO")
    private Integer idUsuario;

    @Column(name = "IPCLIENTE")
    private String ipCliente;
}
