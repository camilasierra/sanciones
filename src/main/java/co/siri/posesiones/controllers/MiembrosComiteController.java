package co.siri.posesiones.controllers;

import co.siri.posesiones.services.ISesionComiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/miembrosComite")
public class MiembrosComiteController {
    private final ISesionComiteService service;

    @Autowired
    public MiembrosComiteController(ISesionComiteService service) {
        this.service = service;
    }

    @GetMapping("/miembros/{idSesionComite}")
    public ResponseEntity<?> getMiembrosComite(@PathVariable Integer idSesionComite){
        return ResponseEntity.status(HttpStatus.OK).body(service.miembrosComite(idSesionComite));
    }
}
