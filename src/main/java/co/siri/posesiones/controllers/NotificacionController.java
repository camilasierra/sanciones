package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.NotificacionTramiteDto;
import co.siri.posesiones.services.INotificacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificacion")
@Tag(name = "Notificacion Cotroller")
//@CrossOrigin(origins = "*")
public class NotificacionController {

    @Autowired
    private INotificacionService iNotificacionService;

    @GetMapping("/notificacion/{idTramitePosesion}/{idAanalistaTramitePosesion}")
    public ResponseEntity<List<NotificacionTramiteDto>> getNotificacion(@PathVariable Long idTramitePosesion, @PathVariable Long idAanalistaTramitePosesion) {
        return ResponseEntity.status(HttpStatus.OK).body(iNotificacionService.listAsignacion(idTramitePosesion, idAanalistaTramitePosesion));
    }


    @GetMapping("/notificacion/{idanalistatramiteposesion}")
    public ResponseEntity<List<NotificacionTramiteDto>> getNotificacionRol(@PathVariable Long idanalistatramiteposesion) {
        return ResponseEntity.status(HttpStatus.OK).body(iNotificacionService.listAsignacionRol(idanalistatramiteposesion));
    }
}
