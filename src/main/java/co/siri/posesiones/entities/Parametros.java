package co.siri.posesiones.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="PARAMETROS")
@Data
public class Parametros {

    @Id
    @Column(name = "IDPARAMETRO")
    private Long idParametro;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "VALOR")
    private String valor;
    @Column(name = "IDUSUARIO")
    private Long idUsuario;
    @Column(name = "IPCLIENTE")
    private String ipCliente;
}
