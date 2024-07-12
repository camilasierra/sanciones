package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Entidad de Tipo Sesion Comite
 *
 * @since 1.0.0
 */
@Entity
@Table(name = "TIPOSESIONCOMITE")
@Setter
@Getter
@NoArgsConstructor
public class TipoSesion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDTIPOSESIONCOMITE")
    private Integer idTipoSesion;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ORDEN")
    private String orden;

    @Column(name = "ACTIVO")
    private String activo;

}
