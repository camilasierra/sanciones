package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.comiteII.GenerarOficioDto;
import co.siri.posesiones.dtos.comiteII.TramitesEstadoDTO;
import co.siri.posesiones.entities.ArchivoTramitePosesion;
import co.siri.posesiones.services.ComiteOficioService;
import co.siri.posesiones.services.ComiteTramiteEstadoService;
import co.siri.posesiones.services.IActaComiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "ComiteII")
@RequestMapping("/api/comite")
//@CrossOrigin("*")
public class ComiteIIController {
    private final ComiteTramiteEstadoService tramiteEstado;
    private final IActaComiteService actaComiteService;
    private final ComiteOficioService oficioService;

    @GetMapping("/tramites-estado/{estado}/{idsesioncomite}/{anotacionEsNula}")
    @Operation(summary = "Listar tramites por estado y sesion comite")
    public List<TramitesEstadoDTO> tramitesEstado(
            @PathVariable String estado,
            @PathVariable Long idsesioncomite,
            @PathVariable Integer anotacionEsNula
            ) {
        return tramiteEstado.tramitesEstado(estado, idsesioncomite, anotacionEsNula);
    }
    
    @GetMapping("/acta-comite/{idsesioncomite}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long idsesioncomite) throws Exception {
        ArchivoTramitePosesion actaComite = actaComiteService.getArchivo_ActaComite(idsesioncomite);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + actaComite.getNombre() + "\"")
                .body(actaComite.getArchivo());

    }
    
    @PostMapping("/generate-document")
    public ResponseEntity<?> generateDocument(@RequestBody GenerarOficioDto oficioDto) {
        List<String> base64Document = oficioService.generateDocument(oficioDto);

        if (base64Document == null) {
            return ResponseEntity.badRequest().body("Error al generar el documento");
        }

        return ResponseEntity.ok(base64Document);
    }

    @PostMapping("/radicar-solip")
    public ResponseEntity<String> radicacionSolip(@RequestBody List<String> base64Documents) {
        String radicacion = oficioService.radicacionSolip(base64Documents);
        return ResponseEntity.ok(radicacion);
    }


    @PostMapping("/generate-zip")
    public ResponseEntity<?> generateZip(@RequestBody GenerarOficioDto oficioDto) {
        try {
            byte[] zipContent = oficioService.generateDocumentAndZip(oficioDto);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "documentos.zip");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(zipContent);
        } catch (IOException e) {
            String errorMessage = "Error generating ZIP file: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }
}
