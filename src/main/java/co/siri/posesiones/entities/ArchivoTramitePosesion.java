package co.siri.posesiones.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ARCHIVOTRAMITEPOSESION")
public class ArchivoTramitePosesion implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARCHIVOTRAMITEPOSESION_SEQ")
    @SequenceGenerator(name = "ARCHIVOTRAMITEPOSESION_SEQ", sequenceName = "ARCHIVOTRAMITEPOSESION_SEQ", allocationSize = 1)
    @Column(name ="IDARCHIVOTRAMITEPOSESION", nullable = false)
    private Long idArchivoTramitePosesion;

    @Column(name = "ARCHIVO")
    private byte[] archivo;

    @Column(name = "CONTENTTYPE")
    private String contenttype;

    @Column(name = "LONGITUD")
    private Long longitud;

    @Column(name = "NOMBRE")
    private String nombre;

}
