package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.PrioridadRequestDTO;
import co.siri.posesiones.services.IPrioridadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/prioridad")
@Tag(name = "Prioridad Controller")
public class PrioridadController {
    private final IPrioridadService service;

    @Autowired
    public PrioridadController(IPrioridadService service) {
        this.service = service;
    }

    @GetMapping("/porPrioridades")
    public ResponseEntity<?> porPrioridades(){
        return ResponseEntity.status(HttpStatus.OK).body(service.porPrioridades());
    }

    @GetMapping("/porTipoEntidad")
    public ResponseEntity<?> porTipoEntidad(){
        return ResponseEntity.status(HttpStatus.OK).body(service.porTipoEntidad());
    }

    @GetMapping("/filtroTipoEntidad/{tipoentidad}")
    public ResponseEntity<?> filtrarPorTipoEntidad(@PathVariable String tipoentidad){
        return ResponseEntity.status(HttpStatus.OK).body(service.filtroporTipoEntidad(tipoentidad));
    }

    @GetMapping("/porEntidad")
    public ResponseEntity<?> porEntidad(){
        return ResponseEntity.status(HttpStatus.OK).body(service.porEntidad());
    }

    @GetMapping("/filtroEntidad/{entidad}")
    public ResponseEntity<?> filtrarPorEntidad(@PathVariable String entidad){
        return ResponseEntity.status(HttpStatus.OK).body(service.filtroporEntidad(entidad));
    }

    @GetMapping("/porPersona")
    public ResponseEntity<?> porPersona(){
        return ResponseEntity.status(HttpStatus.OK).body(service.porPersona());
    }

    @GetMapping("/filtroPersona/{persona}")
    public ResponseEntity<?> filtrarPorPersona(@PathVariable String persona){
        return ResponseEntity.status(HttpStatus.OK).body(service.filtroporPersona(persona));
    }

    @GetMapping("/porPersonayEntidad")
    public ResponseEntity<?> porPersonayEntidad(){
        return ResponseEntity.status(HttpStatus.OK).body(service.porPersonayEntidad());
    }

    @GetMapping("/filtroPersonayEntidad/{personayentidad}")
    public ResponseEntity<?> filtrarPorPersonayEntidad(@PathVariable String personayentidad){
        return ResponseEntity.status(HttpStatus.OK).body(service.filtroporPersonayEntidad(personayentidad));
    }

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, String>> guardaPrioridad(@RequestBody PrioridadRequestDTO request) {
        Map<String, String> response = new HashMap<>();
        try {
            service.guardarPrioridad(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/guardarEntidad")
    public ResponseEntity<Map<String, String>> guardaEntidadPublica(@RequestBody PrioridadRequestDTO request) {
        Map<String, String> response = new HashMap<>();
        try {
            service.guardarEntidadPublica(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @DeleteMapping("/eliminar/{Idtramite}")
    public ResponseEntity<Map<String, String>> eliminarPrioridad(@PathVariable Integer Idtramite) {
        Map<String, String> response = new HashMap<>();
        try {
            service.eliminarPrioridad(Idtramite);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/prioridad")
    public ResponseEntity consultarPrioridades(){
        return ResponseEntity.status(HttpStatus.OK).body(service.consultaPrioridades());
    }
}
