package co.siri.posesiones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
@Entity
@Table(name = "DOMICILIO")
@Data
public class Domicilio implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "DOMICILIO_IDDOMICILIO_GENERATOR", sequenceName = "DOMICILIO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOMICILIO_IDDOMICILIO_GENERATOR")
    @Column(name = "IDDOMICILIO")
    private Long idDomicilio;

    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "TELEFONO")
    private String telefono;

    @Column(name = "IDCIUDAD")
    private Long ciudad;

    @Column(name = "DISCDOMICILIO")
    private String discDomicilio;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "EMAILOTROS")
    private String emailOtros;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "CELULAR")
    private String celular;

    @Column(name = "IDTIPODOMICILIO")
    private Long tipoDomicilio;

    @Column(name = "IDCIUDADNOCOD")
    private Long ciudadNoCodificada;
}
