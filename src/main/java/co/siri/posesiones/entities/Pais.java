package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "PAIS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pais implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PAIS_IDPAIS_GENERATOR", sequenceName = "PAIS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAIS_IDPAIS_GENERATOR")
    @Column(name = "IDPAIS")
    private Long idPais;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "CODIGOPAISISO")
    private String codigoPaisIso;

    @Column(name = "NROPAISINTERNACIONAL")
    private Long nroPaisInternacional;

    @Column(name = "CODIGOPAISISO3")
    private String codigoPaisIso3;

    @Column(name = "PAI_CODAREA")
    private Long Pai_Codarea;

    @Column(name = "PAI_DESCRIPCION")
    private String Pai_Descripcion;
}
