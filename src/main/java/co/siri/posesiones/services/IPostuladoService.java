package co.siri.posesiones.services;

import co.siri.posesiones.dtos.*;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;

import java.util.List;

/**
 * Interfaz para el servicio de Postulado.
 * Define los métodos para obtener datos, estudios, cargos sin posesión,
 * cargos con posesión y experiencia relacionados con los postulantes.
 *
 * <p>Autor: John Macias</p>
 */
public interface IPostuladoService {

    /**
     * Obtiene los datos de un postulante basado en el ID del trámite dado.
     *
     * @param idTramite el ID del trámite
     * @return los datos del postulante como un {@link PostuladoDatosDTO}
     * @throws ExcepcionPersonalizada si no se encuentran datos para el ID de trámite dado
     */
    PostuladoDatosDTO obtenerDatosPostulado(Long idTramite) throws ExcepcionPersonalizada;

    /**
     * Obtiene los estudios de un postulante basado en el ID de la persona dada.
     *
     * @param idPersona el ID de la persona
     * @return una lista de estudios del postulante como {@link PostuladoEstudioDTO}
     * @throws ExcepcionPersonalizada si no se encuentran estudios para el ID de persona dado
     */
    List<PostuladoEstudioDTO> obtenerEstudiosPostulado(Long idPersona) throws ExcepcionPersonalizada;

    /**
     * Obtiene los cargos sin posesión de un postulante basado en el ID de la persona dada.
     *
     * @param idPersona el ID de la persona
     * @return una lista de cargos sin posesión del postulante como {@link PostuladoCargosSinPosesionDTO}
     * @throws ExcepcionPersonalizada si no se encuentran cargos sin posesión para el ID de persona dado
     */
    List<PostuladoCargosSinPosesionDTO> obtenerCargosSinPosesionPostulado(Long idPersona) throws ExcepcionPersonalizada;

    /**
     * Obtiene los cargos con posesión de un postulante basado en el ID de la persona dada.
     *
     * @param idPersona el ID de la persona
     * @return una lista de cargos con posesión del postulante como {@link PostuladoCargosPosesionDTO}
     * @throws ExcepcionPersonalizada si no se encuentran cargos con posesión para el ID de persona dado
     */
    List<PostuladoCargosPosesionDTO> obtenerCargosPosesionesPostulado(Long idPersona) throws ExcepcionPersonalizada;

    /**
     * Obtiene la experiencia de un postulante basado en el ID de la persona dada.
     *
     * @param idPersona el ID de la persona
     * @return una lista de experiencias del postulante como {@link ExperienciaPostuladoDTO}
     * @throws ExcepcionPersonalizada si no se encuentran experiencias para el ID de persona dado
     */
    List<ExperienciaPostuladoDTO> obtenerExperienciaPostulado(Long idPersona) throws ExcepcionPersonalizada;

}

