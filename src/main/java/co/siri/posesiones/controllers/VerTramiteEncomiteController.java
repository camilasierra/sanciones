package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.ComiteDetallesResponseDto;
import co.siri.posesiones.dtos.ResultadoComiteRequestDTO;
import co.siri.posesiones.entities.TipoEstadoTramitePosesion;
import co.siri.posesiones.services.IVerTramiteEncomite;
import co.siri.posesiones.services.imp.VerTramiteEncomiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/tramite")
@Tag(name = "Tramite Controller")
public class VerTramiteEncomiteController {

    @Autowired
    private IVerTramiteEncomite iVerTramiteEncomite;
    @Autowired
    private VerTramiteEncomiteService service;

    @GetMapping
    @Operation(summary = "obtiene los estados de desicion del tramite")
    public List<TipoEstadoTramitePosesion> getEstadosTramite() {
        return iVerTramiteEncomite.ListarrVerTramiteEncomite();
    }


    @PutMapping("/actualizar")
    @Operation(summary = "actualiza la anotacion y la decision")
    public void actualizarResultadoComite(@RequestBody ResultadoComiteRequestDTO dto) {
        iVerTramiteEncomite.actualizarResultadoComite(dto);
    }

    @GetMapping("/details/{idSesioncomite}")
    @Operation(summary = "Listar los detalles del resultado comite")
    public List<ComiteDetallesResponseDto> getComiteDetails(@PathVariable Long idSesioncomite) {
        return iVerTramiteEncomite.getComiteDetalles(idSesioncomite);
    }

}
