package co.siri.posesiones.controllers;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.sql.SQLException;
import java.util.List;
import co.siri.posesiones.dtos.*;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.siri.posesiones.dtos.ArchivoTramiteDto;
import co.siri.posesiones.dtos.CambiarEstadoTramitePosesionRequestDto;
import co.siri.posesiones.entities.TipoEstadoTramitePosesion;
import co.siri.posesiones.services.ITramitePosesionService;
import io.swagger.v3.oas.annotations.tags.Tag;


//@CrossOrigin("*")
@RestController
@RequestMapping("/api/tramitePosesiones")
@Tag(name = "Tramite Posesion Controller")
public class TramitesPosesionesController {
	
    private final ITramitePosesionService tramitePosesionService;
    
    public TramitesPosesionesController(ITramitePosesionService tramitePosesionService) {
        this.tramitePosesionService = tramitePosesionService;
    }

    @GetMapping("/getTramites/{tipoBusqueda}/{busqueda}")
    public ResponseEntity<List<TramitePosesionDto>> getTramites(@PathVariable String tipoBusqueda, @PathVariable String busqueda){
        return ResponseEntity.status(HttpStatus.OK).body(tramitePosesionService.getTramites(tipoBusqueda, busqueda));
    }
    
    @GetMapping("/getDatosBasicos/{idTramite}")
    public ResponseEntity<DatosBasicosDto> getDatosBasicos(@PathVariable Long idTramite){
        return ResponseEntity.status(HttpStatus.OK).body(tramitePosesionService.consultarDatosBasicos(idTramite));
    }
    
    @GetMapping("/getInfoNombramiento/{idTramite}")
    public ResponseEntity<InfoNombramientoDto> getInfoNombramiento(@PathVariable Long idTramite){
        return ResponseEntity.status(HttpStatus.OK).body(tramitePosesionService.consultarInformacionNombramiento(idTramite));

    }
    
    

    @GetMapping("/getInfoServidorPublico/{idTramite}")
    public ResponseEntity<InfoServidorPublico> getInfoServidorPublico(@PathVariable Long idTramite){
        return ResponseEntity.status(HttpStatus.OK).body(tramitePosesionService.consultarInformacionServidorPublico(idTramite));
    }

    @GetMapping("/getInfoOtrosRepresentantes/{idTramite}")
    public ResponseEntity<InfoOtrosRepresentantes> getInfoOtrosRepresentantes(@PathVariable Long idTramite){
        return ResponseEntity.status(HttpStatus.OK).body(tramitePosesionService.consultarInformacionOtrosRepresentantes(idTramite));
    }
    
    @GetMapping("/getInfoDesignacion/{idTramite}")
    public ResponseEntity<InfoDesignacionDto> getInfoDesignacion(@PathVariable Long idTramite){
        return ResponseEntity.status(HttpStatus.OK).body(tramitePosesionService.consultarInformacionDesignacion(idTramite));
    }
    
    @GetMapping("/getInfoContactoEntidad/{idTramite}")
    public ResponseEntity<InfoContactoEntidad> getInfoContactoEntidad(@PathVariable Long idTramite){
        return ResponseEntity.status(HttpStatus.OK).body(tramitePosesionService.consultarInformacionContactoEntidad(idTramite));
    }

    @GetMapping("/getInfoJuntaDirectiva/{idTramite}")
    public ResponseEntity<InfoJuntaDirectiva> getInfoJuntaDirectiva(@PathVariable Long idTramite){
        return ResponseEntity.status(HttpStatus.OK).body(tramitePosesionService.consultarInformacionJuntaDirectiva(idTramite));
    }

    @GetMapping("/getInfoAdicionalesDefensorConsumidor/{idTramite}")
    public ResponseEntity<InfoAdicionalDefensorConsumidor> getInfoAdicionalesDefensorConsumidor(@PathVariable Long idTramite){
        return ResponseEntity.status(HttpStatus.OK).body(tramitePosesionService.consultarInformacionAdicionalDefensorConsumidor(idTramite));
    }
    
    @GetMapping("/getInfoAnexos/{idTramite}")
    public ResponseEntity<List<InfoAnexoDto>> getInfoAnexos(@PathVariable Long idTramite){
        return ResponseEntity.status(HttpStatus.OK).body(tramitePosesionService.consultarInformacionAnexos(idTramite));
    }

    @GetMapping("/getEstadosTramitePosesion")
    public ResponseEntity<List<TipoEstadoTramitePosesion>> getAllActiveTipoEstadoTramitePosesion() {
        List<TipoEstadoTramitePosesion> estados = tramitePosesionService.getAllActiveTipoEstadoTramitePosesion();
        return ResponseEntity.status(HttpStatus.OK).body(estados);
    }

    @PostMapping("/cambiarEstadoTramite")
    public ResponseEntity<?> changeEstadoTramite(@RequestBody CambiarEstadoTramitePosesionRequestDto request) {
        tramitePosesionService.changeEstadoTramite(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    /**
     * Controlador para el servicio de obtener un archivo tramite posesion por tipo e id tramite 
     * @param idTramitePosesion
     * @return
     */
    @GetMapping("/getArchivoTramite/{idTramitePosesion}")
    public ResponseEntity<?> getArchivoTramite(@PathVariable Long idTramitePosesion)  {
		return ResponseEntity.status(HttpStatus.OK).body(tramitePosesionService.getArchivoTramite(idTramitePosesion));
    }
    
    /**
     * Controlador para el servicio de guardar un archivo tramite posesion
     * @param archivoTramiteDto
     * @return
     * @throws SQLException
     */
    @PostMapping("/guardarArchivoTramite")
    public ResponseEntity<Object> guardarArchivoTramite(@RequestBody ArchivoTramiteDto archivoTramiteDto) {
    	return ResponseEntity.status(HttpStatus.OK).body(tramitePosesionService.guardarArchivoTramite(archivoTramiteDto));
    }

    @PostMapping("/devolverTramite")
    public ResponseEntity<String> devolverTramite(@RequestBody CambiarEstadoTramitePosesionRequestDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(tramitePosesionService.devolverTramite(request));
    }

    /**
     * Endpoint para actualizar un trámite posesión.
     *
     * @param idTramite Id del trámite
     * @param idEstadoTramite Id del estado de trámite
     * @return String actualización exitosa
     */
    @PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> actualizarEstadoTramitePosesion(
            @RequestParam Long idTramite, @RequestParam Long idEstadoTramite) throws ExcepcionPersonalizada {
            return ResponseEntity.status(HttpStatus.OK).body(tramitePosesionService.actualizarEstadoTramite(idTramite, idEstadoTramite));
    }
}
