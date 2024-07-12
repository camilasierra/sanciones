package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.*;
import co.siri.posesiones.services.ICoordinadorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


//@CrossOrigin("*")
@RestController
@RequestMapping("/api/coordinador")
@Tag(name = "Cordinador Cotroller")
public class CoordinadorController {
    private final ICoordinadorService service;

    public CoordinadorController(ICoordinadorService service) {
        this.service = service;
    }

    @PostMapping("/getTramite/asignado")
    public ResponseEntity<?> CoordinadorAsignado(@RequestBody FiltroAvanzadoEscritorioDto filtro) {
        return ResponseEntity.status(HttpStatus.OK).body(service.asignados(filtro));
    }

    @PostMapping("/getTramite/no-asignado")
    public ResponseEntity<?> CoordinadorNoAsignado(@RequestBody FiltroAvanzadoEscritorioDto filtro) {
        return ResponseEntity.status(HttpStatus.OK).body(service.noAsignados(filtro));
    }

    @PostMapping("/asignacionAutomatica")
    public ResponseEntity<String> coordinadorAsingacionAutomatica(@RequestBody AsignacionAutomaticaDTO asignacionAutomaticaDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.asignacionAutomatica(asignacionAutomaticaDTO));
    }

    @GetMapping("/infoTramite/infoTramite")
    public ResponseEntity<InfoTramiteListAnalistasDTO> getTramite(@RequestParam Long idTramitePosesion) {
        return ResponseEntity.status(HttpStatus.OK).body(service.infoTramiteAnalitas(idTramitePosesion));
    }

    @PostMapping("/asignar/AsignarTramite")
    public ResponseEntity asignarTramiteAnalista(@RequestBody AsignacionManualDTO asignacionManualDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            service.asignacionManual(asignacionManualDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
