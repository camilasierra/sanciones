package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.ActualizarAnalistaDTO;
import co.siri.posesiones.dtos.AgregarAnalistaDTO;
import co.siri.posesiones.entities.AnalistaTramitePosesion;
import co.siri.posesiones.services.IGestionAnalistasService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analistas")

public class GestionAnalistasController {

    @Autowired
    private IGestionAnalistasService gestionAnalistasService;

    // Funcionalidad de Listar Analistas
    @GetMapping("/listar-analistas")
    public List<?> listarAnalistas() {
        return gestionAnalistasService.listarAnalistas();
    }
// Funcionalidad para agregar un nuevo analista
    @PostMapping("/agregar-analista")
    public AnalistaTramitePosesion agregarAnalista(@RequestBody AgregarAnalistaDTO agregarAnalistaDTO) {
        return gestionAnalistasService.agregarAnalista(agregarAnalistaDTO);
    }
    // Funcionalidad para Actualizar un Analista
    @PutMapping("/actualizar-analista")
    public ResponseEntity<?> actualizarAnalista(@RequestBody ActualizarAnalistaDTO actualizarAnalistaDTO) {
        AnalistaTramitePosesion analistaEditado = gestionAnalistasService.actualizarAnalista(actualizarAnalistaDTO);
        if (analistaEditado != null) {
            return ResponseEntity.ok(analistaEditado);
        } else {
            return ResponseEntity.status(404).body("El analista que intenta editar no existe.");
        }
    }
}