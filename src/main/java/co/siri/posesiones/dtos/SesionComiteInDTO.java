package co.siri.posesiones.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * DTO para representar una sesion de comite.
 * Contiene información sobre el ID de la sesion, numero de acta, fecha de comite, ID  del tipo y modalidad de la sesión de comité
 *
 * <p>Autor: Mateo Sanchez</p>
 */
@Getter
@Setter
@Data
public class SesionComiteInDTO {
    /**
     * El ID de la sesion de comite
     */
    private Long idSesion;
    /**
     * Fecha de la sesion de comité.
     */
    private Date fechaComite;
    /**
     * El ID del tipo de sesión de comité.
     */
    private Integer idTipoSesionComite;
    /**
     * El ID de la modalidad de sesión de comité.
     */
    private Integer idModalidadComite;
    /**
     * IP de quien realiza la petición.
     */
    private String ipcliente;
    /**
     * El ID del usuario que realiza la petición.
     */
    private Integer idusuario;
}
