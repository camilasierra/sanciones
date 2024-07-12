package co.siri.posesiones.dtos;

import co.siri.posesiones.utilidades.dto.AuditDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que contiene el request de informacion del comentario realizado
 * por los roles permitdios, se guarda en la tabla : MENSAJEREVISIONTRAMITE
 * <p>Autor: Carlos Suarez</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentariosTramiteRequestDTO {

    /**
     * Id del tramite asociado, debe estar en la tabla ASIGNACIONTRAMITEANALISTA
     */
    private Long idTramitePosesion;

    /**
     * contenido del mensaje que se esta creando
     */
    private String texto;

    /**
     * tipo del mensaje, por quien fue realizado
     */
    private Long idTipoMensaje;

    /**
     * Indicador d mensaje borrado del lado del servidor
     */
    private  Character inBorrado;

    /**
     * Corresponde al rol que crea el comentario
     */
    private  String rol;

    /**
     * datos del usuario que esta en sesion
     */
    private AuditDTO user;
}
