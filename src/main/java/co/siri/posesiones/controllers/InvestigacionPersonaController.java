package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.InvestigacionesPersonasDTO;
import co.siri.posesiones.services.IInvestigacionPersonaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/investigacionPersona")
@Tag(name = "servicio para  traer la informacion sobre investigaciones de personas  de poseciones de cada entidad")
public class InvestigacionPersonaController {

    @Autowired
    private IInvestigacionPersonaService investigacionPersonaService;

    @GetMapping("/getInvestigacionPersona/{idPersona}")
    public ResponseEntity<List<InvestigacionesPersonasDTO>> getInvestigacionPersona(@PathVariable("idPersona") Long idPersona) {
        return ResponseEntity.status(HttpStatus.OK).body(investigacionPersonaService.getInvestigacionPersona(idPersona));
    }
}
