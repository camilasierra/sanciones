package co.siri.posesiones.utilidades.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/** dto para retornar la paginacion */
@Data
@NoArgsConstructor
public class PaginacionInDTO {

    private Short paginaActual;
    private Short tamanoPagina;
}
