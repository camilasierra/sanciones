package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tiposeccioninhabilidadcargo")
public class Tiposeccioninhabilidadcargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDTIPOSECCIONINHABILIDADCARGO", nullable = false)
    private Long idTipoSeccionInhabilidadCargo;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ORDEN", nullable = false)
    private Long orden;

    @Column(name = "ACTIVO", nullable = false)
    private String activo;


}