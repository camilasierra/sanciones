package co.siri.posesiones.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO para traer lista de mensajes en revision asociados a un tramite
 * <p>Autor: Carlos Suarez</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MensajeRevisionResponseDTO {
    /**
     * Pripiedad que contienen el id de la tabla MENSAJEREVISIONTRAMITE
     */
    private Integer idMensajeRevisionTramite;

    /**
     * Pripiedad que contienen el id del tramite al cual se asocian los mensajes
     */
    private Integer idTramitePosesion;

    /**
     * Pripiedad que contienen el texto con los mensajes
     */
    private String texto;

    /**
     * Pripiedad que contienen el id de la tabla TIPODESTINOMENSAJE
     * el cual contiene el rol de quien realiza el comentario
     */
    private Integer idTipoDestinoMensaje;

    /**
     * Pripiedad que contiene el indicador si un mensaje asociado
     * a un tramite ha sido borrado
     */
    private Character inBorrado;

    /**
     * Nombre del funcionario que realiza el comentario
     */
    private String nombre;

    /**
     * Rol del funcionario que realiza el comentario
     */
    private String rol;

    /**
     * Fecha en que se realizo el comentario
     */
    private Date fechaNotificacion;
}
