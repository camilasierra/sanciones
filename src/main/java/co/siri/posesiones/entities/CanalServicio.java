package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CANALSERVICIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CanalServicio {
    @Id
    @Column(name = "IDCANALSERVICIO")
    private Long idCanalServicio;

    @Column(name = "IDENTIDAD")
    private Long idEntidad;
}
