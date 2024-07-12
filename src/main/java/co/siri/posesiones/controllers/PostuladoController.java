package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.*;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.services.IPostuladoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Controlador REST para gestionar las solicitudes relacionadas con los postulantes.
 * Proporciona endpoints para obtener datos, estudios, cargos con posesión, cargos sin posesión y experiencia de los postulantes.
 *
 * <p>Autor: John Macias</p>
 */
@RestController
@RequestMapping("/api/postulado")
public class PostuladoController {

    @Autowired
    private IPostuladoService iPostuladoService;

    /**
     * Endpoint para obtener los datos de un postulante basado en el ID del trámite.
     *
     * @param idTramite el ID del trámite
     * @return los datos del postulante como un {@link PostuladoDatosDTO}
     * @throws ExcepcionPersonalizada si no se encuentran datos para el ID de trámite dado
     */
    @GetMapping(path = "/datos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostuladoDatosDTO> obtenerDatos(@RequestParam("idTramite") Long idTramite) throws ExcepcionPersonalizada {
        return ResponseEntity.status(HttpStatus.OK).body(iPostuladoService.obtenerDatosPostulado(idTramite));
    }

    /**
     * Endpoint para obtener los estudios de un postulante basado en el ID de la persona.
     *
     * @param idPersona el ID de la persona
     * @return una lista de estudios del postulante como {@link PostuladoEstudioDTO}
     * @throws ExcepcionPersonalizada si no se encuentran estudios para el ID de persona dado
     */
    @GetMapping(path = "/estudios", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostuladoEstudioDTO>> obtenerEstudios(@RequestParam("idPersona") Long idPersona) throws ExcepcionPersonalizada {
        return ResponseEntity.status(HttpStatus.OK).body(iPostuladoService.obtenerEstudiosPostulado(idPersona));
    }

    /**
     * Endpoint para obtener los cargos sin posesión de un postulante basado en el ID de la persona.
     *
     * @param idPersona el ID de la persona
     * @return una lista de cargos sin posesión del postulante como {@link PostuladoCargosSinPosesionDTO}
     * @throws ExcepcionPersonalizada si no se encuentran cargos sin posesión para el ID de persona dado
     */
    @GetMapping(path = "/cargosSinPosesion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostuladoCargosSinPosesionDTO>> obtenerCargosSinPosesion(@RequestParam("idPersona") Long idPersona) throws ExcepcionPersonalizada {
        return ResponseEntity.status(HttpStatus.OK).body(iPostuladoService.obtenerCargosSinPosesionPostulado(idPersona));
    }

    /**
     * Endpoint para obtener los cargos con posesión de un postulante basado en el ID de la persona.
     *
     * @param idPersona el ID de la persona
     * @return una lista de cargos con posesión del postulante como {@link PostuladoCargosPosesionDTO}
     * @throws ExcepcionPersonalizada si no se encuentran cargos con posesión para el ID de persona dado
     */
    @GetMapping(path = "/cargosPosesion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostuladoCargosPosesionDTO>> obtenerCargosPosesion(@RequestParam("idPersona") Long idPersona) throws ExcepcionPersonalizada {
        return ResponseEntity.status(HttpStatus.OK).body(iPostuladoService.obtenerCargosPosesionesPostulado(idPersona));
    }

    /**
     * Endpoint para obtener la experiencia de un postulante basado en el ID de la persona.
     *
     * @param idPersona el ID de la persona
     * @return una lista de experiencias del postulante como {@link ExperienciaPostuladoDTO}
     * @throws ExcepcionPersonalizada si no se encuentran experiencias para el ID de persona dado
     */
    @GetMapping(path = "/experiencia", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExperienciaPostuladoDTO>> obtenerExperiencia(@RequestParam("idPersona") Long idPersona) throws ExcepcionPersonalizada {
        return ResponseEntity.status(HttpStatus.OK).body(iPostuladoService.obtenerExperienciaPostulado(idPersona));
    }
}
