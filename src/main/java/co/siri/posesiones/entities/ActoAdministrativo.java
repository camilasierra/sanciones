package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "ACTOADMINISTRATIVO")
public class ActoAdministrativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDACTOADMINISTRATIVO")
    private Long idActoAdministrativo;

    @Column(name = "IDTIPOACTOADMIN")
    private Long idTipoActoAdmin;

    @Column(name = "NUMEROACTOADMIN")
    private String numeroActoAdmin;

    @Column(name = "FECHAACTOADMIN")
    @Temporal(TemporalType.DATE)
    private Date fechaActoAdmin;

    @Column(name = "IDCLASIFICACIONACTOADMIN")
    private Long idClasificacionActoAdmin;

    @Column(name = "CODIGODEPENDENCIA")
    private String codigoDependencia;

    @Column(name = "FECHAPUBLICACION")
    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;

    @Column(name = "IDTIPOESTADOACTOADMIN")
    private Long idTipoEstadoActoAdmin;

    @Column(name = "NUMERORADICADOANTECEDENTE")
    private String numeroRadicadoAntecedente;

    @Column(name = "ESPUBLICACIONWEBAUTORIZADA")
    private Boolean esPublicacionWebAutorizada;

    @Column(name = "MOTIVOFIRMEZA")
    private String motivoFirmeza;

    @Column(name = "IDAPPUSER")
    private Long idAppUser;

    @Column(name = "IPCLIENTE")
    private String ipCliente;

    @Column(name = "IDARCHIVORESOLUCION")
    private Long idArchivoResolucion;

    @Column(name = "FECHACAMBIO")
    @Temporal(TemporalType.DATE)
    private Date fechaCambio;

    @Column(name = "FECHAFIRMEZA")
    @Temporal(TemporalType.DATE)
    private Date fechaFirmeza;
}
