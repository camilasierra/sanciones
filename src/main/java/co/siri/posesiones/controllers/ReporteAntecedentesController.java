package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.AccionAporteDTO;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.services.IReporteAntecedentesService;
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
 * Controlador REST para gestionar los reportes de antecedentes.
 * Proporciona endpoints para obtener información sobre las acciones de aporte.
 * <p>
 * Autor: John Macias
 */
@RestController
@RequestMapping("/api/reporteAntecedentes")
public class ReporteAntecedentesController {

    @Autowired
    private IReporteAntecedentesService iReporteAntecedentesService;

    /**
     * Endpoint para obtener las acciones de aporte de una persona específica.
     *
     * @param idPersona el ID de la persona para la que se desean obtener las acciones de aporte
     * @return una lista de objetos {@link AccionAporteDTO} que representan las acciones de aporte
     * @throws ExcepcionPersonalizada si ocurre algún error durante la obtención de las acciones de aporte
     */
    @GetMapping(path = "/accionesAporte", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccionAporteDTO>> obtenerAccionesAporte(@RequestParam("idPersona") Long idPersona) throws ExcepcionPersonalizada {
        return ResponseEntity.status(HttpStatus.OK).body(iReporteAntecedentesService.obtenerAccionesAporte(idPersona));
    }
}