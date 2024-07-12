package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.FiltroAvanzadoEscritorioDto;
import co.siri.posesiones.services.IVerificadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/verificador")
@Tag(name = "Verificador Cotroller")
public class VerificadorController {
    private final IVerificadorService verificadorService;

    public VerificadorController(IVerificadorService verificadorService) {
        this.verificadorService = verificadorService;
    }

    @PostMapping("listado-filtro")
    @Operation(summary = "Buscar - filtros")
    public ResponseEntity<?> filtroAnalista(@RequestBody FiltroAvanzadoEscritorioDto filtro) {
        return ResponseEntity.status(HttpStatus.OK).body(verificadorService.filtroAvanzado(filtro));
    }
}
