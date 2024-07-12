package co.siri.posesiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfoAdicionalDefensorConsumidor {
    private String facultadConciliador;
    private String centroConciliaciónInscrito;
    private String pais;
    private String ciudad;
    private String direccion;
    private String numeroTelefono;
    private String numeroFax;
    private String numeroCelular;
    private String correoElectronicoPrincipal;
    private String correoElectronicoSecunadario;
}
