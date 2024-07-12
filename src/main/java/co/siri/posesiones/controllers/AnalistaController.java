package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.FiltroAvanzadoEscritorioDto;
import co.siri.posesiones.entities.InhabilidadPosesion;
import co.siri.posesiones.services.IAnalistaService;
import co.siri.posesiones.services.InhabilidadPosesionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/analista")
@Tag(name = "Analista Cotroller")
public class AnalistaController {
    private final IAnalistaService analistaService;

    private final InhabilidadPosesionService inhabilidadPosesionService;

    public AnalistaController(IAnalistaService analistaService, InhabilidadPosesionService inhabilidadPosesionService) {
        this.analistaService = analistaService;

        this.inhabilidadPosesionService = inhabilidadPosesionService;
    }



    @PostMapping("/getEscritorio/busqueda-filtro")
    @Operation(summary = "Buscar - filtros")
    public ResponseEntity<?> filtroAnalista(@RequestBody FiltroAvanzadoEscritorioDto filtro) {
        return ResponseEntity.status(HttpStatus.OK).body(analistaService.listTramiteAnalista(filtro));
    }

//    @GetMapping("/getTipoPosesion/cargo")
//    @Operation(summary = "Buscar - Obtener")
//    public ResponseEntity<?> getCargo() {
//        return ResponseEntity.status(HttpStatus.OK).body(inhabilidadPosesionService.findAllByTipoSeccionInhabilidadCargo());
//    }

    @GetMapping("/getTipoPosesion/Seccion-Cargo")
    @Operation(summary = "Buscar - Obtener seccion cargo")
    public ResponseEntity<?> getSeccionCargo() {
        return ResponseEntity.status(HttpStatus.OK).body(inhabilidadPosesionService.findAllTiposSeccion());
    }

//    @GetMapping("/getFindTramite")
//    @Operation(summary = "Buscar - Obtener Inhabilidad Posesion")
//    public ResponseEntity<?> findTramite() {
//        return ResponseEntity.status(HttpStatus.OK).body(inhabilidadPosesionService.findTramitesByCriteria());
//    }

    @GetMapping("/getInhabilidadPosesion")
    @Operation(summary = "Buscar - Obtener Inhabilidad Posesion")
    public ResponseEntity<?> getInhabilidadPosesion(@RequestParam Long idTramitePosesion) {
        return ResponseEntity.status(HttpStatus.OK).body(inhabilidadPosesionService.findInhabilidadPosesionByTramiteAndSeccion(idTramitePosesion));
    }

    @GetMapping("/getTipoCargo/tramite")
    @Operation(summary = "Buscar - Obtener Tipo de Cargo por Tramite")
    public ResponseEntity<?> getTipoCargoByTramite(@RequestParam Long idTramitePosesion) {
        return ResponseEntity.status(HttpStatus.OK).body(inhabilidadPosesionService.findTipoCargoByIdTramitePosesion(idTramitePosesion));
    }

    @GetMapping("/inhabilidades-comunes-por-seccion-y-tramite")
    @Operation(summary = "Buscar - Obtener inhabilidades por seccion")
    public ResponseEntity<?> obtenerInhabilidadesComunesPorSeccionYTramite(
            @RequestParam("idSecciones") List<Integer> idSecciones,
            @RequestParam("idTramite") int idTramite) {
        return ResponseEntity.status(HttpStatus.OK).body(inhabilidadPosesionService.obtenerInhabilidadesComunesPorSeccionYTramite(idSecciones, idTramite));
    }

//    @GetMapping("/inhabilidades-por-tramite")
//    @Operation(summary = "Buscar - Obtener inhabilidades por Tramite")
//    public ResponseEntity<?> obtenerInhabilidadesPorTramite(@RequestParam Long idTramite) {
//        return ResponseEntity.status(HttpStatus.OK).body(inhabilidadPosesionService.obtenerInhabilidadesPorTramite(idTramite));
//    }

    @GetMapping("/firma-auditora")
    @Operation(summary = "Buscar - Obtener firma de aditor por Tramite")
    public ResponseEntity<?> obtenerInformacionFirmaAuditoraPorTramite(@RequestParam Long idTramite) {
        return ResponseEntity.status(HttpStatus.OK).body(inhabilidadPosesionService.obtenerInformacionFirmaAuditoraPorTramite(idTramite));
    }

    @GetMapping("/inhabilidad-posesion")
    @Operation(summary = "Buscar - Obtener firma de aditor por Tramite")
    public ResponseEntity<?> getInhabilidadPosesion(@RequestParam int idTramitePosesion){
        return ResponseEntity.status(HttpStatus.OK).body(inhabilidadPosesionService.getInhabilidadPosesion(idTramitePosesion));

    }

    @GetMapping("/getInformacionAdicional")
    @Operation(summary = "Buscar - Obtener firma de aditor por Tramite")
    public ResponseEntity<?> getTramitesPorNumeroRadicacion(@RequestParam String numeroRadicacion) {
        return ResponseEntity.status(HttpStatus.OK).body(inhabilidadPosesionService.getTramitesPorNumeroRadicacion(numeroRadicacion));
    }

    @GetMapping("/get-sanciones-enfime")
    @Operation(summary = "Buscar - Obtener Sanciones en firme")
    public ResponseEntity<?> getSancionesEnfime(@RequestParam Long idPersona) {
        return ResponseEntity.status(HttpStatus.OK).body(inhabilidadPosesionService.getSancionesenfirme(idPersona));
    }


    @GetMapping("/get-otras-situaciones")
    @Operation(summary = "Buscar - Obtener Otras situaciones")
    public ResponseEntity<?> getOtrasSituaciones(@RequestParam int idTramitePosesion) {
        return ResponseEntity.status(HttpStatus.OK).body(inhabilidadPosesionService.getOtrasSituaciones(idTramitePosesion));
    }


}
