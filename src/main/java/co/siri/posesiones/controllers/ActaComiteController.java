package co.siri.posesiones.controllers;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import co.siri.posesiones.entities.ResultadoComite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.siri.posesiones.entities.SesionComiteEntity;
import co.siri.posesiones.services.IActaComiteServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/comite/acta")
@Tag(name = "Acta Comite Controller")
public class ActaComiteController {

	@Autowired
	private IActaComiteServices services;

	@GetMapping("/{estado}/{idsesioncomite}/{anotacionEsNula}")
	@Operation(summary = "Listar los tramites por estado y session de comite")
	public ResponseEntity<?> filtroActaComite(@PathVariable String estado,
			@PathVariable Long idsesioncomite, @PathVariable Integer anotacionEsNula){
		List<String> estados = Arrays.asList(estado.split(","));
		return ResponseEntity.status(HttpStatus.OK).body(services.consultarComites(estados, idsesioncomite, anotacionEsNula));
	}

	@PutMapping("/actualizar/varios")
	@Operation(summary = "Actualizar Varios")
    public ResponseEntity<?> updateVarios(@RequestBody SesionComiteEntity sesionEntity){
        return ResponseEntity.status(HttpStatus.OK).body(services.actualizarVarios(sesionEntity));
    }

	@PutMapping("/actualizar/anotacion")
	@Operation(summary = "Actualizar Anotacion")
	public ResponseEntity<?> updateAnotacion(@RequestBody ResultadoComite resultadoComite){
		return ResponseEntity.status(HttpStatus.OK).body(services.actualizarAnotacionComite(resultadoComite));
	}

	@GetMapping("generar-acta/{estado}/{idsesioncomite}/{anotacionEsNula}/{idtipomiembrocomite}/{format}")
	@Operation(summary = "Generacion Acta Preliminar")
	public ResponseEntity<?> generarActaPreliminar(@PathVariable String estado,
			@PathVariable Long idsesioncomite, @PathVariable Integer anotacionEsNula,
			@PathVariable String idtipomiembrocomite,
			@PathVariable String format
        ) throws IOException {
		List<String> estados = Arrays.asList(estado.split(","));
		List<Integer> miembros = Arrays.asList(idtipomiembrocomite.split(",")).stream()
                .map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(services.getInfoArmarActa(estados, idsesioncomite, anotacionEsNula, miembros, format));
	}

	@GetMapping("varios/{idsesioncomite}")
	@Operation(summary = "Obtener Varios")
	public ResponseEntity<?> getVarios(@PathVariable Long idsesioncomite){
		return ResponseEntity.status(HttpStatus.OK).body(services.getVarios(idsesioncomite));
	}


}
