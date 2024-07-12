package co.siri.posesiones.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.siri.posesiones.dtos.MensajeSolicitudOut;
import co.siri.posesiones.dtos.SesionComiteInDTO;
import co.siri.posesiones.dtos.SubirArchivoActaComiteDTO;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.services.IFinalizacionComiteService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/finalizacionComite")
public class FinalizacionComiteController {
	
	@Autowired
	private final IFinalizacionComiteService finalizacionComiteService;
	
    /**
     * Endpoint para subir el acta de comité 
     *
     * @param idsesionComite fecha, archivo en base64
     * @return mensaje con el exito o fracaso de la operación
     * @throws ExcepcionPersonalizada si no se puede subir el acta de comité
     */
    @PostMapping(path = "/subir-acta-comite", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MensajeSolicitudOut> crearSesionComite(@RequestBody SubirArchivoActaComiteDTO actaComiteJSON) throws ExcepcionPersonalizada {
        return ResponseEntity.status(HttpStatus.OK).body(finalizacionComiteService.subirActaComite(actaComiteJSON));
    }
    
    /**
     * Endpoint para oficializar el acta de comité 
     *
     * @param idsesionComite 
     * @return mensaje con el exito o fracaso de la operación
     * @throws ExcepcionPersonalizada si no se puede oficializar el acta de comité
     */
    @PutMapping(path = "/oficializar-acta-comite/{idsesioncomite}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MensajeSolicitudOut> crearSesionComite(@PathVariable Long idsesioncomite) throws ExcepcionPersonalizada {
        return ResponseEntity.status(HttpStatus.OK).body(finalizacionComiteService.oficializarActaComite(idsesioncomite));
    }
	
}
