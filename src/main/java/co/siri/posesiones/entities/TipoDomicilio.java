package co.siri.posesiones.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "TIPODOMICILIO")
@Data
public class TipoDomicilio implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDTIPODOMICILIO")
    private Long idTipoDomicilio;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ORDEN")
    private Long orden;

    @Column(name = "ACTIVO")
    private String activo;
}
