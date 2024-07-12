package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.OtroAsistenteComiteRequestDTO;
import co.siri.posesiones.services.IOtroAsistenteComiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/otroAsistenteComite")
public class OtroAsistenteComiteController {
    private final IOtroAsistenteComiteService service;

    @Autowired
    public OtroAsistenteComiteController(IOtroAsistenteComiteService service) {this.service = service;}

    @GetMapping("/getOtroAsistenteComite/{idSesionComite}")
    public ResponseEntity<?> getOtroAsistenteComite(@PathVariable Integer idSesionComite){
        return ResponseEntity.status(HttpStatus.OK).body(service.otroAsistenteComite(idSesionComite));
    }

    @PostMapping("/guardarOtroAsistente")
    public ResponseEntity<Map<String,String>> guardarAsistente(@RequestBody OtroAsistenteComiteRequestDTO request){
        Map<String,String> response = new HashMap<>();
        try{
            service.guardarOtroAsistente(request);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
