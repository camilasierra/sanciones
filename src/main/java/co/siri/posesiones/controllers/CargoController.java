package co.siri.posesiones.controllers;

import co.siri.posesiones.services.IListCargoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/cargo")
public class CargoController {
    private final IListCargoService cargoService;

    public CargoController(IListCargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping("/listaCargo")
    @Operation(summary = "Listar cargos")
    public ResponseEntity<?> listCargo() {
        return ResponseEntity.status(HttpStatus.OK).body(cargoService.cargos());
    }
}
