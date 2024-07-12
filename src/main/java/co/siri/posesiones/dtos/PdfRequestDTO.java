package co.siri.posesiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * Clase encargada de mapear el request para consumir el servicio ConsultaConsolidada
 */
public class PdfRequestDTO {
    /**
     * Propiedad que representa el documento de la persona o nit a consultar
     */
    private String datoConsultar;
    /**
     * Propiedad que representa el tipo de doc del dato a consultar
     */
    private  String tipoDocumento;
    /**
     * Propiedad adicional de consulta en el caso de que "tipoDocumento"
     * sea igual "pa" o "int"
     */
    private  String nombrePasaporte;
}
