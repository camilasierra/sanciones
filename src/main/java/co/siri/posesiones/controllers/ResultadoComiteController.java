package co.siri.posesiones.controllers;

import co.siri.posesiones.entities.ResultadoComite;
import co.siri.posesiones.services.IActaComiteServices;
import co.siri.posesiones.services.IResultadoComiteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/resultadocomite")
public class ResultadoComiteController {

    private final IResultadoComiteService resultadoComiteService;

    public ResultadoComiteController(IResultadoComiteService resultadoComiteService) {
        this.resultadoComiteService = resultadoComiteService;
    }

    @GetMapping
    public ResponseEntity<List<ResultadoComite>> getAll() {
        List<ResultadoComite> result = resultadoComiteService.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoComite> getById(@PathVariable Integer id) {
        Optional<ResultadoComite> resultadoComite = resultadoComiteService.findById(id);
        return resultadoComite.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ResultadoComite> create(@RequestBody ResultadoComite resultadoComite) {
        ResultadoComite createdResultadoComite = resultadoComiteService.save(resultadoComite);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResultadoComite);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultadoComite> update(@PathVariable Integer id, @RequestBody ResultadoComite resultadoComite) {
        Optional<ResultadoComite> existingResultadoComite = resultadoComiteService.findById(id);
        if (existingResultadoComite.isPresent()) {
            resultadoComite.setIdresultadocomite(id);
            ResultadoComite updatedResultadoComite = resultadoComiteService.save(resultadoComite);
            return ResponseEntity.ok(updatedResultadoComite);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<ResultadoComite> existingResultadoComite = resultadoComiteService.findById(id);
        if (existingResultadoComite.isPresent()) {
            resultadoComiteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
