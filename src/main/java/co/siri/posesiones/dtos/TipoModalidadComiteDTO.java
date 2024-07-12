package co.siri.posesiones.dtos;

import lombok.Data;

@Data
public class TipoModalidadComiteDTO {

    private Integer idModalidad;

    private String nombre;

    private String canal;

    private Integer orden;

    private String indicadorCorreo;

    private String activo;
}
