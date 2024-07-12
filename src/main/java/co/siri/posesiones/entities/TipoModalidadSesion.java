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
@Table(name = "TIPOMODALIDADSESIONCOMITE")
@Setter
@Getter
@NoArgsConstructor
public class TipoModalidadSesion implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDTIPOMODALIDADSESIONCOMITE")
    private Integer idModalidad;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "CANAL")
    private String canal;

    @Column(name = "ORDEN")
    private Integer orden;

    @Column(name = "INDICADORCORREO")
    private String indicadorCorreo;

    @Column(name = "ACTIVO")
    private String activo;
}
