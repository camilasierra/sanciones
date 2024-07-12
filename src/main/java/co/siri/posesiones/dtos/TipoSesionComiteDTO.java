package co.siri.posesiones.dtos;

import lombok.Data;

@Data
public class TipoSesionComiteDTO {

    private Integer idTipoSesion;

    private String nombre;

    private String orden;

    private String activo;
}
