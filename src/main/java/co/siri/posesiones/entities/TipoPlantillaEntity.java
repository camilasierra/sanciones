package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TIPOPLANTILLA")
public class TipoPlantillaEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDTIPOPLANTILLA")
    private Long idTipoPlantilla;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "TEXTO")
    @Lob
    private String texto;

    @Column(name = "CONFIRMA")
    private Boolean confirm;

    @Column(name = "IDARCHIVOTIPOPLANTILLA")
    private Boolean idArchivoTipoPlantilla;

}
