package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "DEPARTAMENTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Departamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "DEPARTAMENTO_IDDEPARTAMENTO_GENERATOR", sequenceName = "DEPARTAMENTO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPARTAMENTO_IDDEPARTAMENTO_GENERATOR")
    @Column(name = "IDDEPARTAMENTO")
    private Long idDepartamento;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "IDPAIS")
    private Long pais;
}
