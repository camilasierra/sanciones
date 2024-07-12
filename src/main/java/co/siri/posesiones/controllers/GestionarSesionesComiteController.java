package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.*;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.services.IGestionarSesionesComiteService;
import co.siri.posesiones.utilidades.dto.PaginacionDTO;
import co.siri.posesiones.utilidades.dto.PaginacionInDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las sesiones de comités .
 * Proporciona endpoints para obtener, crear y editar sesiones de comités.
 *
 * <p>Autor: Mateo Sanchez</p>
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sesionesComite")
public class GestionarSesionesComiteController {

    private final IGestionarSesionesComiteService iGestionarSesionesComiteService;
    /**
     * Endpoint para obtener el listado de sesiones comite.
     *
     * @param paginado paginado para la consulta de sesiones
     * @return listado de sesiones comite
     * @throws ExcepcionPersonalizada si no se encuentran datos para el ID de trámite dado
     */
    @PostMapping(path = "/listaSesionesComite", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginacionDTO> obtenerListaSesionesComite(@RequestBody PaginacionInDTO paginado) throws ExcepcionPersonalizada {
        return ResponseEntity.status(HttpStatus.OK).body(iGestionarSesionesComiteService.obtenerListaSesionesComite(paginado));
    }

    /**
     * Endpoint para crear una sesion de comité
     *
     * @param sesionComite fecha, tipo sesion y tipo modalidad de la sesion a crear
     * @return mensaje con el exito o fracaso de la operación
     * @throws ExcepcionPersonalizada si no se puede crear la sesión de comité
     */
    @PostMapping(path = "/crearSesionComite", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MensajeSolicitudOut> crearSesionComite(@RequestBody SesionComiteInDTO sesionComite) throws ExcepcionPersonalizada {
        return ResponseEntity.status(HttpStatus.OK).body(iGestionarSesionesComiteService.crearSesionComite(sesionComite));
    }

    /**
     * Endpoint para modificar o editar una sesion de comité.
     *
     * @param sesionComite fecha, tipo sesion y tipo modalidad de la sesion a modificar
     * @return mensaje con el exito o fracaso de la operación
     * @throws ExcepcionPersonalizada si no se puede editar la sesión de comité
     */
    @PutMapping(path = "/editarSesionComite", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MensajeSolicitudOut> editarSesionComite(@RequestBody SesionComiteInDTO sesionComite) throws ExcepcionPersonalizada {
        return ResponseEntity.status(HttpStatus.OK).body(iGestionarSesionesComiteService.editarSesionComite(sesionComite));
    }

    /**
     * Endpoint para obtener el listado de los tipos de modernización.
     *
     * @return lista con los tipos de modalidad de seiones comité
     * @throws ExcepcionPersonalizada si no obtiene los tipos de modalidad
     */
    @GetMapping(path = "/tipoModalidad", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TipoModalidadComiteDTO>> obtenerModalidades() throws ExcepcionPersonalizada {
        return ResponseEntity.status(HttpStatus.OK).body(iGestionarSesionesComiteService.obtenerModalidades());
    }

    /**
     * Endpoint para obtener el listado de los tipos de sesiones de comité.
     *
     * @return lista con los tipos de sesiones comité
     * @throws ExcepcionPersonalizada si no obtiene los tipos de sesiones de comité
     */
    @GetMapping(path = "/tipoSesion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TipoSesionComiteDTO>> obtenerSesiones() throws ExcepcionPersonalizada {
        return ResponseEntity.status(HttpStatus.OK).body(iGestionarSesionesComiteService.obtenerSesiones());
    }
}
