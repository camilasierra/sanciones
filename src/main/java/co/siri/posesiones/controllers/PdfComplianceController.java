package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.PdfRequestDTO;
import co.siri.posesiones.services.IComplianceRestPdfService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/pdfCompliance")
@Tag(name = "Controller para la generacion del PDF de la API compliance")
public class PdfComplianceController {

    @Autowired
    private IComplianceRestPdfService complianceRestPdfService;

    @PostMapping("/generatePdfCompliance")
    public ResponseEntity<byte[]> generatePdf(@RequestBody PdfRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(complianceRestPdfService.getPdfCompliance(request));
    }
}
