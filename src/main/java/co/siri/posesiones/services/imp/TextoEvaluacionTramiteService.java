package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.FiltroTextoEvaluacionIN;
import co.siri.posesiones.dtos.TextoEvaluacionDTO;
import co.siri.posesiones.dtos.TextoEvaluacionINDTO;
import co.siri.posesiones.dtos.TextoEvaluacionProjection;
import co.siri.posesiones.entities.TextoEvaluacionTramite;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.TextoEvaluacionTramiteRepository;
import co.siri.posesiones.services.ITextoEvaluacionTramiteService;
import co.siri.posesiones.utilidades.Constantes;
import co.siri.posesiones.utilidades.Utilidades;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar los textos de evaluación.
 * Esta clase maneja la lógica de negocio para obtener y guardar los textos de evaluación.
 *
 * <p>Autor: John Macias</p>
 */
@Slf4j
@Service
public class TextoEvaluacionTramiteService implements ITextoEvaluacionTramiteService {

    @Autowired
    private TextoEvaluacionTramiteRepository mensajeTramitePosesionRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene una lista de los textos de evaluación basados en los filtros proporcionados.
     *
     * @param filtro el filtro que contiene los criterios de búsqueda para los textos de evaluación
     * @return una lista de los textos de evaluación como {@link TextoEvaluacionDTO}
     * @throws ExcepcionPersonalizada si ocurre un error durante la obtención de los textos de evaluación
     */
    @Override
    public List<TextoEvaluacionDTO> obtenerTextosEvaluacion(FiltroTextoEvaluacionIN filtro) throws ExcepcionPersonalizada {
        log.info(Constantes.INICIO_SERVICIO_OBTENER_TEXTO);
        List<TextoEvaluacionProjection> mensajeTramiteList =
                mensajeTramitePosesionRepository.obtenerMensajesTramite(filtro.getIdTramite(), filtro.getIdTipoTexto(), filtro.getIdSeccionTexto());
        if (mensajeTramiteList.isEmpty()) {
            log.info(Constantes.NO_TEXTO_TRAMITE);
            return new ArrayList<>();
        }
        log.info(Constantes.CONSULTA_EXITOSA);
        return mensajeTramiteList.stream().map(this::convertirProjectionToDTO).toList();
    }

    /**
     * Guarda un texto de evaluación.
     *
     * @param texto el texto de evaluación a guardar
     * @return el texto de evaluación guardado como {@link TextoEvaluacionDTO}
     * @throws ExcepcionPersonalizada si ocurre un error durante el guardado del texto de evaluación
     */
    @Override
    public TextoEvaluacionDTO guardarTextoEvaluacion(TextoEvaluacionINDTO texto) throws ExcepcionPersonalizada {
        log.info(Constantes.INICIO_SERVICIO_GUARDAR_TEXTO_TRAMITE);
        TextoEvaluacionTramite mensajeTramite = TextoEvaluacionTramite.builder()
                .idTramitePosesion(texto.getIdTramitePosesion())
                .idTipoTextoTramite(texto.getIdTipoTextoTramite())
                .idTipoSeccionTramite(texto.getIdTipoSeccionTramite())
                .texto(texto.getTexto())
                .idUsuario(texto.getUsuario().getIdusuario())
                .ipCliente(texto.getUsuario().getIpUsuario())
                .build();

        TextoEvaluacionTramite mensajeTramitePersistido = mensajeTramitePosesionRepository.save(mensajeTramite);
        log.info(Constantes.GUARDADO_EXITOSO_TEXTO);
        return modelMapper.map(mensajeTramitePersistido, TextoEvaluacionDTO.class);
    }

    /***
     * Método encargado de mapear repuesta del servicio getResultadoAnalista y convertir
     * Clob a String
     * @param projection
     * @return
     */
    private TextoEvaluacionDTO convertirProjectionToDTO(TextoEvaluacionProjection projection) {
        String textoClob = Utilidades.convertClobToString(projection.getTexto());
        return  TextoEvaluacionDTO.builder()
                .titulo(projection.getTitulo())
                .texto(textoClob)
                .idTextoTramite(projection.getIdTextoTramite())
                .idTramitePosesion(projection.getIdTramitePosesion())
                .idTipoSeccionTramite(projection.getIdTipoSeccionTramite())
                .idTipoTextoTramite(projection.getIdTipoTextoTramite())
                .idUsuario(projection.getIdUsuario())
                .ipCliente(projection.getIpCliente())
                .build();
    }

}
