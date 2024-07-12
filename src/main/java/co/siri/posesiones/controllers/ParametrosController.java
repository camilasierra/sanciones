package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.ParametroAnalistaDTO;
import co.siri.posesiones.entities.Parametros;
import co.siri.posesiones.services.IParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/parametros")
public class ParametrosController {

    private final IParametroService parametroService;

    @Autowired
    public ParametrosController(IParametroService parametroService) {
        this.parametroService = parametroService;
    }

    @GetMapping("/getParametro/{parametroId}")
    public ResponseEntity<?> getTramites(@PathVariable Integer parametroId) {
        return ResponseEntity.status(HttpStatus.OK).body(parametroService.getParametro(parametroId));
    }

    @GetMapping("/parametrosAnalista")
    public ResponseEntity<?> getParametrosAnalista() {
        return ResponseEntity.status(HttpStatus.OK).body(parametroService.getParametrosAnalista());
    }

    @PatchMapping("/patchParametros/topesAnalista")
    public ResponseEntity<Parametros> actualizarParametros(@RequestBody ParametroAnalistaDTO parametro) {
        parametroService.actualizarParametros(parametro);

        return ResponseEntity.noContent().build();
    }
}
