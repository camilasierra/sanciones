package co.siri.posesiones.entities;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "RESULTADOCOMITE")
@Data
public class ResultadoComite implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDRESULTADOCOMITE")
    private Integer idresultadocomite;

    @Size(max = 4000)
    @Column(name = "JUSTIFICACION")
    private String justificacion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "IDUSUARIO")
    private int idusuario;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 39)
    @Column(name = "IPCLIENTE")
    private String ipcliente;

    @Column(name = "FINALIZADOFOREST")
    private Character finalizadoforest;

    @Lob
    @Column(name = "ANOTACION")
    private String anotacion;

    @Column(name = "IDSESIONCOMITE")
    private Integer idsesioncomite;

    @Column(name = "IDTIPOCAUSALNEGACION")
    private Short idtipocausalnegacion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "IDTIPOESTADOTRAMITEPOSESION")
    private short idtipoestadotramiteposesion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "IDTRAMITEPOSESION")
    private int idtramiteposesion;
}
