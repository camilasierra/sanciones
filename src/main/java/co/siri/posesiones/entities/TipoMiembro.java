package co.siri.posesiones.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TIPOMIEMBRO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoMiembro {
    @Id
    @Column(name = "IDTIPOMIEMBRO")
    private Long idTipoMiembro;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ORDEN")
    private Long orden;

    @Column(name = "ACTIVO")
    private Boolean activo;
}
