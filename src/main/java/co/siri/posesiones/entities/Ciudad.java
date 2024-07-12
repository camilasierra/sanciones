package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CIUDAD")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ciudad implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "CIUDAD_IDCIUDAD_GENERATOR", sequenceName = "CIUDAD_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CIUDAD_IDCIUDAD_GENERATOR")
    @Column(name = "IDCIUDAD")
    private Long idCiudad;

    @Column(name = "ACTIVO")
    private Boolean activo;

    @Column(name = "CODIGOCENTROPOBLADO")
    private String codigoCentroPoblado;

    @Column(name = "FECHADESDE")
    private Date fechaDesde;

    @Column(name = "FECHAHASTA")
    private Date fechaHasta;

    @Column(name = "LATITUD")
    private Float latitud;

    @Column(name = "LONGITUD")
    private Float longitud;

    @Column(name = "NOMBRECENTROPOBLADO")
    private String nombreCentroPoblado;

    @Column(name = "OTROS")
    private Boolean otros;

    @Column(name = "RGLFTO322")
    private Long rglfTo322;

    @Column(name = "IDDEPARTAMENTO")
    private Long departamento;

    @Column(name = "IDMUNICIPIO")
    private Long municipio;

    @Column(name = "IDPAIS")
    private Long pais;
}
