package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.*;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.PostuladoRepository;
import co.siri.posesiones.services.IPostuladoService;
import co.siri.posesiones.utilidades.Constantes;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

/**
 * Implementación del servicio para gestionar Postulados.
 * Esta clase maneja la lógica de negocio para obtener datos, estudios, cargos sin posesión,
 * cargos con posesión y experiencia relacionados con los postulantes.
 *
 * <p>Autor: John Macias</p>
 */
@Slf4j
@Service
public class PostuladoService implements IPostuladoService {

    @Autowired
    private PostuladoRepository postuladoRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene los datos de un postulante basado en el ID del trámite dado.
     *
     * @param idTramite el ID del trámite
     * @return los datos del postulante como un {@link PostuladoDatosDTO}
     * @throws ExcepcionPersonalizada si no se encuentran datos para el ID de trámite dado
     */
    @Override
    public PostuladoDatosDTO obtenerDatosPostulado(Long idTramite) throws ExcepcionPersonalizada {
        log.info(Constantes.INICIO_SERVICIO_DATOS_POSTULADO);
        Optional<IPostuladoDatos> postuladoDatos = postuladoRepository.obtenerDatosPostulado(idTramite);
        if (postuladoDatos.isEmpty()) {
            log.info(Constantes.NO_DATOS_POSTULADO);
            throw new ExcepcionPersonalizada(Constantes.NO_DATOS_POSTULADO, HttpStatus.NOT_FOUND);
        }
        log.info(Constantes.CONSULTA_EXITOSA);
        return modelMapper.map(postuladoDatos, PostuladoDatosDTO.class);
    }

    /**
     * Obtiene los estudios de un postulante basado en el ID de la persona dada.
     *
     * @param idPersona el ID de la persona
     * @return una lista de estudios del postulante como {@link PostuladoEstudioDTO}
     * @throws ExcepcionPersonalizada si no se encuentran estudios para el ID de persona dado
     */
    @Override
    public List<PostuladoEstudioDTO> obtenerEstudiosPostulado(Long idPersona) throws ExcepcionPersonalizada {
        log.info(Constantes.INICIO_SERVICIO_ESTUDIOS_POSTULADO);
        List<IPostuladoEstudios> postuladoEstudiosList = postuladoRepository.obtenerEstudiosPostulado(idPersona);
        if (postuladoEstudiosList.isEmpty()) {
            log.info(Constantes.NO_ESTUDIOS_POSTULADO);
            return new ArrayList<>();
        }
        log.info(Constantes.CONSULTA_EXITOSA);
        return postuladoEstudiosList.stream().map(estudio -> modelMapper.map(estudio, PostuladoEstudioDTO.class)).toList();
    }

    /**
     * Obtiene los cargos sin posesión de un postulante basado en el ID de la persona dada.
     *
     * @param idPersona el ID de la persona
     * @return una lista de cargos sin posesión del postulante como {@link PostuladoCargosSinPosesionDTO}
     * @throws ExcepcionPersonalizada si no se encuentran cargos sin posesión para el ID de persona dado
     */
    @Override
    public List<PostuladoCargosSinPosesionDTO> obtenerCargosSinPosesionPostulado(Long idPersona) throws ExcepcionPersonalizada {
        log.info(Constantes.INICIO_SERVICIO_SIN_CARGOS);
        List<IPostuladoCargosSinPosesion> postuladoCargosSinPosesionList = postuladoRepository.obtenerCargosSinPosesionPostulado(idPersona);
        if (postuladoCargosSinPosesionList.isEmpty()) {
            log.info(Constantes.NO_DATOS_SIN_CARGO);
            return new ArrayList<>();
        }
        log.info(Constantes.CONSULTA_EXITOSA);
        return postuladoCargosSinPosesionList.stream().map(cargoSinPosesion -> modelMapper.map(cargoSinPosesion, PostuladoCargosSinPosesionDTO.class)).toList();
    }

    /**
     * Obtiene los cargos con posesión de un postulante basado en el ID de la persona dada.
     *
     * @param idPersona el ID de la persona
     * @return una lista de cargos con posesión del postulante como {@link PostuladoCargosPosesionDTO}
     * @throws ExcepcionPersonalizada si no se encuentran cargos con posesión para el ID de persona dado
     */
    @Override
    public List<PostuladoCargosPosesionDTO> obtenerCargosPosesionesPostulado(Long idPersona) throws ExcepcionPersonalizada {
        log.info(Constantes.INICIO_SERVICIO_CARGOS);
        List<IPostuladoCargosPosesion> postuladoCargosPosesionList = postuladoRepository.obtenerCargosPosesionPostulado(idPersona);
        if (postuladoCargosPosesionList.isEmpty()) {
            log.info(Constantes.NO_DATOS_CARGOS);
            return new ArrayList<>();
        }
        log.info(Constantes.CONSULTA_EXITOSA);
        return postuladoCargosPosesionList.stream().map(cargoPosesion -> modelMapper.map(cargoPosesion, PostuladoCargosPosesionDTO.class)).toList();
    }

    /**
     * Obtiene la experiencia de un postulante basado en el ID de la persona dada.
     *
     * @param idPersona el ID de la persona
     * @return una lista de experiencias del postulante como {@link ExperienciaPostuladoDTO}
     * @throws ExcepcionPersonalizada si no se encuentran experiencias para el ID de persona dado
     */
    @Override
    public List<ExperienciaPostuladoDTO> obtenerExperienciaPostulado(Long idPersona) throws ExcepcionPersonalizada{
        log.info(Constantes.INICIO_SERVICIO_EXPERIENCIA_POSTULADO);
        List<IExperienciaPostulado> experienciaPostuladoList = postuladoRepository.obtenerExperienciaPostulado(idPersona);
        if (experienciaPostuladoList.isEmpty()) {
            log.info(Constantes.NO_DATOS_EXPERIENCIA);
            return new ArrayList<>();
        }
        log.info(Constantes.CONSULTA_EXITOSA);
        return experienciaPostuladoList.stream().map(this::convertirAExperienciaDTO).toList();
    }

    /**
     * Método para convertir a experiencia DTO
     * @param experiencia IExperienciaPostulado
     * @return ExperienciaPostuladoDTO
     * @throws ExcepcionPersonalizada
     */
    private ExperienciaPostuladoDTO convertirAExperienciaDTO(IExperienciaPostulado experiencia) throws ExcepcionPersonalizada {
        try {
            return ExperienciaPostuladoDTO.builder()
                    .idPersona(experiencia.getIdPersona())
                    .sector(experiencia.getSector())
                    .claseDeSociedad(experiencia.getClaseDeSociedad())
                    .razonSocial(experiencia.getRazonSocial())
                    .nombreCargo(experiencia.getNombreCargo())
                    .tipoCargo(experiencia.getTipoCargo())
                    .areaDesempenio(experiencia.getAreaDesempenio())
                    .motivoRetiro(experiencia.getMotivoRetiro())
                    .fechaInicio(experiencia.getFechaInicio())
                    .fechaRetiro(experiencia.getFechaRetiro())
                    .nombreArchivo(experiencia.getNombreArchivo())
                    .contentTypeArchivo(experiencia.getContentTypeArchivo())
                    .archivo(experiencia.getArchivo() != null ?
                            Base64.getEncoder().encodeToString(experiencia.getArchivo()
                                    .getBytes(1, (int) experiencia.getArchivo().length())) : null)
                    .longitudArchivo(experiencia.getLongitudArchivo())
                    .build();
        } catch (SQLException ex){
            log.info(ex.getMessage());
            throw new ExcepcionPersonalizada("Se ha producido un error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
