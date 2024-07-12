package co.siri.posesiones.controllers;

import co.siri.posesiones.services.IAsignacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/asignacion")
@Tag(name = "Asignacion Cotroller")
public class AsignacionController {

    private final IAsignacionService asignacionService;


    public AsignacionController(IAsignacionService asignacionService) {
        this.asignacionService = asignacionService;
    }

    @GetMapping("/getAsignacion/tramiteAnalista")
    @Operation(summary = "Validar relacion tramite analista")
    public ResponseEntity getAsignacionTramiteAnalista(@Param("idTramitePosesion") Long idTramitePosesion, @Param("idAnalistaTramitePosesion") Long idAnalistaTramitePosesion) {
        return ResponseEntity.status(HttpStatus.OK).body(asignacionService.getAsignacionTramiteAnalista(idTramitePosesion, idAnalistaTramitePosesion));
    }
}
