package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "PAR_INHABILIDADAPLICACION")
public class InhabilidadPosesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPARINHABILIDADAPLICACION", nullable = false)
    private Long id;

    @Column(name = "IDTIPOINHABILIDADPOSESION", nullable = false)
    private Long idTipoInhabilidadPosesion;

    @Column(name = "IDTIPOCARGO")
    private Long idTipoCargo;

    @Column(name = "IDTIPOCALIDADCARGO")
    private Long idTipoCalidadCargo;

    @Column(name = "IDTIPOENTIDAD")
    private Long idTipoEntidad;

    @Column(name = "IDTIPOSECCIONINHABILIDADCARGO")
    private Long idTipoSeccionInhabilidadCargo;

    @Column(name = "ACTIVO", nullable = false)
    private String activo;

    @Column(name = "ORDEN", nullable = false)
    private Long orden;
}
