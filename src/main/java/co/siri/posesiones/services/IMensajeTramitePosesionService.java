package co.siri.posesiones.services;

import co.siri.posesiones.dtos.FiltroMensajesTramiteIN;
import co.siri.posesiones.dtos.MensajeTramitePosesionDTO;
import co.siri.posesiones.dtos.MensajeTramitePosesionINDTO;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;

import java.util.List;

/**
 * Interfaz para el servicio de MensajeTramitePosesion.
 * Define los métodos para obtener y guardar mensajes relacionados con los trámites de posesión.
 *
 * <p>Autor: John Macias</p>
 */
public interface IMensajeTramitePosesionService {

    /**
     * Obtiene una lista de mensajes de trámite de posesión basados en los filtros proporcionados.
     *
     * @param filtroMensajesTramite el filtro que contiene los criterios de búsqueda para los mensajes
     * @return una lista de mensajes de trámite de posesión como {@link MensajeTramitePosesionDTO}
     * @throws ExcepcionPersonalizada si ocurre un error durante la obtención de los mensajes
     */
    List<MensajeTramitePosesionDTO> obtenerMensajesTramitesPosesion(FiltroMensajesTramiteIN filtroMensajesTramite) throws ExcepcionPersonalizada;

    /**
     * Guarda un mensaje de trámite de posesión.
     *
     * @param mensajeTramitePosesion el mensaje a guardar
     * @return el mensaje guardado como {@link MensajeTramitePosesionDTO}
     * @throws ExcepcionPersonalizada si ocurre un error durante el guardado del mensaje
     */
    MensajeTramitePosesionDTO guardarMensajeTramitePosesion(MensajeTramitePosesionINDTO mensajeTramitePosesion) throws ExcepcionPersonalizada;
}
