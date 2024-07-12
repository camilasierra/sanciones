package co.siri.posesiones.controllers;

import co.siri.posesiones.services.IListComites;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comite")
public class ComitePosesionesController {
    private final IListComites comites;

    public ComitePosesionesController(IListComites comites) {this.comites = comites;}

    @GetMapping("listaComites")
    @Operation(summary = "Lista de Comites")
    public ResponseEntity<?> listComites(){
        return ResponseEntity.status(HttpStatus.OK).body(comites.listaComites());
    }

    @GetMapping("comitePosesiones")
    @Operation(summary = "Comites")
    public ResponseEntity<?> ComitesPosesiones(){
        return ResponseEntity.status(HttpStatus.OK).body(comites.comites());
    }


}
