package co.siri.posesiones.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "TIPODOCUMENTOLEGAL")
@Data
public class TipoDocumentoLegal implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDTIPODOCUMENTOLEGAL")
    private Long idTipoDocumentoLegal;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ORDEN")
    private Long orden;

    @Column(name = "IDSV")
    private String idSV;

    @Column(name = "TRAMITEPOSESION")
    private Boolean tramitePosesion;

    @Column(name = "ACTIVO")
    private Boolean activo;
}
