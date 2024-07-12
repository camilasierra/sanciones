package co.siri.posesiones.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * DTO para representar una sesion de comite.
 * Contiene información sobre el ID de la sesion, numero de acta, fecha de comite, tipo y modalidad de la sesión de comité
 *
 * <p>Autor: Mateo Sanchez</p>
 */
@Getter
@Setter
@Data
public class SesionComiteOutDTO {
    /**
     * El ID de la sesión de comité.
     */
    private Long idSesionComite;
    /**
     * Número de acta de la sesion de comité.
     */
    private Long numeroActa;
    /**
     * Fecha de la sesion de comité.
     */
    private Date fechaComite;
    /**
     * El ID del tipo de sesión de comité.
     */
    private TipoSesionComiteDTO tipoSesion;
    /**
     * El ID de la modalidad de sesión de comité.
     */
    private TipoModalidadComiteDTO tipoModalidad;

}
