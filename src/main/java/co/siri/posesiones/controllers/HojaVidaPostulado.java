package co.siri.posesiones.controllers;

import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.services.IPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hojaVidaPostulado")
public class HojaVidaPostulado {

    @Autowired
    private IPdfService iPdfService;

    @GetMapping(path = "/generarPdf")
    public ResponseEntity<String> generarPDFHojaVida(
            @RequestParam("idTramite") Long idTramite,
            @RequestParam("idPersona") Long idPersona) throws ExcepcionPersonalizada{
        return ResponseEntity.status(HttpStatus.OK).body(iPdfService.generarPDFHojaVidaPostulado(idTramite, idPersona));
    }
}
