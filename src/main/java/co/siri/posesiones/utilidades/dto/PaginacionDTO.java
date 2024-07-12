package co.siri.posesiones.utilidades.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/** dto para retornar la paginacion */
@Data
@NoArgsConstructor
public class PaginacionDTO {

    private Long totalElements;
    private int totalPages;
    private Object contenido;
}
