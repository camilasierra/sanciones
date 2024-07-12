package co.siri.posesiones.services;

import co.siri.posesiones.dtos.*;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.utilidades.dto.PaginacionDTO;
import co.siri.posesiones.utilidades.dto.PaginacionInDTO;

import java.util.List;

/**
 * Interfaz para el servicio de GestionarSesionesComite.
 * Define los métodos para obtener, editar y crear sesiones de comité.
 *
 * <p>Autor: Mateo Sanchez</p>
 */
public interface IGestionarSesionesComiteService {

    /**
     * Obtiene una lista paginada con las sesiones de comite.
     *
     * @param paginado pagina actual y sesiones por pagina a consultar
     * @return una lista de sesiones de comite
     * @throws ExcepcionPersonalizada si ocurre un error durante la obtención de las sesiones
     */
    PaginacionDTO obtenerListaSesionesComite(PaginacionInDTO paginado) throws ExcepcionPersonalizada;

    /**
     * Crea una sesión de comité.
     *
     * @param sesionComite sesión a crear
     * @return el mensaje guardado como {@link SesionComiteOutDTO}
     * @throws ExcepcionPersonalizada si ocurre un error durante la creacion de la sesión de comite
     */
    MensajeSolicitudOut crearSesionComite(SesionComiteInDTO sesionComite) throws ExcepcionPersonalizada;

    /**
     * Crea una sesión de comité.
     *
     * @param sesionComite sesión a editar o modificar
     * @return el mensaje guardado como {@link SesionComiteOutDTO}
     * @throws ExcepcionPersonalizada si ocurre un error durante la creacion de la sesión de comite
     */
    MensajeSolicitudOut editarSesionComite(SesionComiteInDTO sesionComite) throws ExcepcionPersonalizada;

    /**
     * Obtiene una lista con los tipos de modalidades.
     *
     * @return lista tipos de modalidades sesión comité
     * @throws ExcepcionPersonalizada si ocurre un error durante la obtencion de la lista de modalidades.
     */
    List<TipoModalidadComiteDTO> obtenerModalidades() throws ExcepcionPersonalizada;

    /**
     * Obtiene una lista con los tipos de sesiones.
     *
     * @return lista tipos de sesiones de comité
     * @throws ExcepcionPersonalizada si ocurre un error durante la obtencion de la lista de sesiones.
     */
    List<TipoSesionComiteDTO> obtenerSesiones() throws ExcepcionPersonalizada;
}
