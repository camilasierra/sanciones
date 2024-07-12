package co.siri.posesiones.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "TIPOSECCIONTRAMITEPOSESION")
public class TipoSeccionTramitePosesion {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDTIPOSECCIONTRAMITEPOSESION")
    private int idTipoSeccionTramitePosesion;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ORDEN")
    private int orden;

    @Column(name = "ACTIVO")
    private Character activo;
}
