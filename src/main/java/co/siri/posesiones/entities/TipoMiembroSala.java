package co.siri.posesiones.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TIPOMIEMBROSALA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoMiembroSala {
    @Id
    @Column(name = "IDTIPOMIEMBROSALA")
    private Long idTipoMiembroSala;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ORDEN")
    private Long orden;

    @Column(name = "ACTIVO")
    private Boolean activo;

}
