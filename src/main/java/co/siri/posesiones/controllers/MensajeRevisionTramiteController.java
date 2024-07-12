package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.ComentariosTramiteRequestDTO;
import co.siri.posesiones.dtos.MensajeRevisionResponseDTO;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.services.IMensajeRevisionTramiteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static co.siri.posesiones.utilidades.Constantes.*;

@Controller
@RequestMapping("/api/mensajesRevisionTramite")
@Tag(name = "servicio para carga y guardado de comentarios realizados, para el escritorio analista")
public class MensajeRevisionTramiteController {

    @Autowired
    private IMensajeRevisionTramiteService iMensajeRevisionTramiteService;


    @GetMapping("/getMensajeRevisionTramite/{idTramitePosesion}")
    public ResponseEntity<List<MensajeRevisionResponseDTO>> getMensajesRevisionTramite(@PathVariable("idTramitePosesion") int idTramitePosesion) {
        List<MensajeRevisionResponseDTO> mensajes = iMensajeRevisionTramiteService.getMensajesRevisionTramite(idTramitePosesion);
        if (mensajes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(mensajes);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(mensajes);
        }
    }

    @PostMapping("/saveComentariosTramite")
    public ResponseEntity<Map<String, String>> saveComentariosTramite(@RequestBody ComentariosTramiteRequestDTO request) {
        Map<String, String> response = new HashMap<>();
        try {
            iMensajeRevisionTramiteService.saveComentariosTramite(request);
            response.put("message", GUARDADOEXITOSO);
            return ResponseEntity.ok(response);
        } catch (ExcepcionPersonalizada e) {
            response.put("message", ERRORGUARDADO + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
