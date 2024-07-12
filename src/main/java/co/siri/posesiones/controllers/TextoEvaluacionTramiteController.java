package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.*;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.services.ITextoEvaluacionTramiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las solicitudes relacionadas con los textos de evaluación de trámites.
 * Proporciona endpoints para obtener y guardar textos de evaluación de trámites.
 *
 * <p>Autor: John Macias</p>
 */
@RestController
@RequestMapping("/api/textoEvaluacionTramite")
public class TextoEvaluacionTramiteController {

    @Autowired
    private ITextoEvaluacionTramiteService iTextoEvaluacionTramiteService;

    /**
     * Endpoint para obtener una lista de los textos de evaluación basados en los filtros proporcionados.
     *
     * @param request el filtro que contiene los criterios de búsqueda para los textos de evaluación
     * @return una lista de los textos de evaluación como {@link TextoEvaluacionDTO}
     * @throws ExcepcionPersonalizada si ocurre un error durante la obtención de los textos de evaluación
     */
    @PostMapping(path = "/listaTextoEvaluacion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TextoEvaluacionDTO>> obtenerTextosEvaluacionTramite(@RequestBody FiltroTextoEvaluacionIN request) throws ExcepcionPersonalizada {
        return ResponseEntity.status(HttpStatus.OK).body(iTextoEvaluacionTramiteService.obtenerTextosEvaluacion(request));
    }

    /**
     * Endpoint para guardar un texto de evaluación.
     *
     * @param texto el texto de evaluación
     * @return el texto de evaluación guardado como {@link TextoEvaluacionDTO}
     */
    @PostMapping(path = "/guardarTextoMensaje", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TextoEvaluacionDTO> guardarTextosEvaluacionTramite(@RequestBody TextoEvaluacionINDTO texto) {
        return ResponseEntity.status(HttpStatus.OK).body(iTextoEvaluacionTramiteService.guardarTextoEvaluacion(texto));
    }

}