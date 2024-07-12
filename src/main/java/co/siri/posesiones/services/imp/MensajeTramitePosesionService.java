package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.FiltroMensajesTramiteIN;
import co.siri.posesiones.dtos.MensajeTramitePosesionDTO;
import co.siri.posesiones.dtos.MensajeTramitePosesionINDTO;
import co.siri.posesiones.entities.MensajeTramitePosesion;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.MensajeTramitePosesionRepository;
import co.siri.posesiones.services.IMensajeTramitePosesionService;
import co.siri.posesiones.utilidades.Constantes;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar mensajes de trámite de posesión.
 * Esta clase maneja la lógica de negocio para obtener y guardar mensajes relacionados con los trámites de posesión.
 *
 * <p>Autor: John Macias</p>
 */
@Slf4j
@Service
public class MensajeTramitePosesionService implements IMensajeTramitePosesionService {

    @Autowired
    private MensajeTramitePosesionRepository mensajeTramitePosesionRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene una lista de mensajes de trámite de posesión basados en los filtros proporcionados.
     *
     * @param filtro el filtro que contiene los criterios de búsqueda para los mensajes
     * @return una lista de mensajes de trámite de posesión como {@link MensajeTramitePosesionDTO}
     * @throws ExcepcionPersonalizada si ocurre un error durante la obtención de los mensajes
     */
    @Override
    public List<MensajeTramitePosesionDTO> obtenerMensajesTramitesPosesion(FiltroMensajesTramiteIN filtro) throws ExcepcionPersonalizada {
        //log.info(Constantes.INICIO_SERVICIO_OBTENER_MENSAJES);
        List<MensajeTramitePosesion> mensajeTramiteList =
                mensajeTramitePosesionRepository.obtenerMensajesTramite(filtro.getIdTramite(), filtro.getIdTipoMensaje(), filtro.getIdSeccionMensaje());
        if (mensajeTramiteList.isEmpty()) {
           // log.info(Constantes.NO_MENSAJES_TRAMITE);
            return new ArrayList<>();
        }
        log.info(Constantes.CONSULTA_EXITOSA);
        return mensajeTramiteList.stream().map(mensaje -> modelMapper.map(mensaje, MensajeTramitePosesionDTO.class)).toList();
    }

    /**
     * Guarda un mensaje de trámite de posesión.
     *
     * @param mensaje el mensaje a guardar
     * @return el mensaje guardado como {@link MensajeTramitePosesionDTO}
     * @throws ExcepcionPersonalizada si ocurre un error durante el guardado del mensaje
     */
    @Override
    public MensajeTramitePosesionDTO guardarMensajeTramitePosesion(MensajeTramitePosesionINDTO mensaje) throws ExcepcionPersonalizada {
        //log.info(Constantes.INICIO_SERVICIO_GUARDAR_MENSAJE_TRAMITE);
        MensajeTramitePosesion mensajeTramite = MensajeTramitePosesion.builder()
                .idTramitePosesion(mensaje.getIdTramitePosesion())
                .idTipoMensajeTramite(mensaje.getIdTipoMensajeTramite())
                .idTipoSeccionTramite(mensaje.getIdTipoSeccionTramite())
                .texto(mensaje.getTexto())
                //.leido(Constantes.CHAR_N)
                //.borrado(Constantes.CHAR_N)
                .idUsuario(mensaje.getIdUsuario())
               // .ipCliente(Constantes.IP_BASE)
                .build();

        MensajeTramitePosesion mensajeTramitePersistido = mensajeTramitePosesionRepository.save(mensajeTramite);
        //log.info(Constantes.GUARDADO_EXITOSO_MENSAJE);
        return modelMapper.map(mensajeTramitePersistido, MensajeTramitePosesionDTO.class);
    }
}
