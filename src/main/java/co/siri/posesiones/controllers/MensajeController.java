package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.MensajeEnTramiteDto;
import co.siri.posesiones.dtos.MensajeRevisionTramiteDto;
import co.siri.posesiones.entities.MensajeRevisionTramite;
import co.siri.posesiones.services.IMensajeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensaje")
@Tag(name = "Mensaje Cotroller")
//@CrossOrigin(origins = "*")
public class MensajeController {

    @Autowired
    private IMensajeService iMensajeService;

    @GetMapping("/mensaje-tramite/{idTipoDestinoMensaje}")
    public ResponseEntity<List<MensajeRevisionTramite>> getMensajesEnTramite(@PathVariable Long idTipoDestinoMensaje) {
        return ResponseEntity.status(HttpStatus.OK).body(iMensajeService.mensajeEnTramite(idTipoDestinoMensaje));
    }

    @GetMapping("/mensajes-borrados")
    public ResponseEntity<List<MensajeRevisionTramite>> getMensajesBorrados() {
        return ResponseEntity.status(HttpStatus.OK).body(iMensajeService.mensajeBorrado());
    }

    @PostMapping(path = "/enviar-mensaje")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> save(@RequestBody MensajeRevisionTramiteDto mensaje) {
        iMensajeService.guardarMensaje(mensaje);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path = "/eliminar-mensaje/{id}")
    public ResponseEntity<Void> deleteMensaje(@PathVariable Long id) {
        iMensajeService.eliminarMensaje(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
