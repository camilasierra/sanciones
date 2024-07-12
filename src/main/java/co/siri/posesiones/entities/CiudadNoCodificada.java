package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Entity
@Table(name = "CIUDADNOCODIFICADA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CiudadNoCodificada implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "CIUDADNOCODIFICADA_IDCIUDADNOCODIFICADA_GENERATOR", sequenceName = "CIUDADNOCODIFICADA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CIUDADNOCODIFICADA_IDCIUDADNOCODIFICADA_GENERATOR")
    @Column(name = "IDCIUDADNOCODIFICADA")
    private Long idCiudadNoCodificada;
    @Column(name = "NOMBRE")
    private String nombre;

    // uni-directional many-to-one association to Departamento
    @Column(name = "IDDEPARTAMENTO")
    private Long idDepartamento;

    // uni-directional many-to-one association to Pais
    @Column(name = "IDPAIS")
    private Long idPais;
}
