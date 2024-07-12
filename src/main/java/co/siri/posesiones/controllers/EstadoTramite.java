package co.siri.posesiones.controllers;

import co.siri.posesiones.services.IListTramiteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@CrossOrigin("*")
@RestController
@RequestMapping("/api/estadoTramite")
public class EstadoTramite {
    private final IListTramiteService estadoTramite;

    public EstadoTramite(IListTramiteService estadoTramite) {
        this.estadoTramite = estadoTramite;
    }


    @GetMapping("/listaEstadoTramite")
    @Operation(summary = "Listar estados de tramites")
    public ResponseEntity<?> listEstadoTramite() {
        return ResponseEntity.status(HttpStatus.OK).body(estadoTramite.estaTramites());
    }
}
