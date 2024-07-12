package co.siri.posesiones.controller;

import co.siri.posesiones.controllers.PrioridadController;
import co.siri.posesiones.dtos.*;
import co.siri.posesiones.entities.PrioridadTramitePosesion;
import co.siri.posesiones.services.IPrioridadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PrioridadControllerTest {

    @Mock
    private IPrioridadService service;

    @InjectMocks
    private PrioridadController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPorPrioridades() {
        List<PrioridadTramitePosesion> prioridades = Collections.singletonList(new PrioridadTramitePosesion());
        when(service.porPrioridades()).thenReturn(prioridades);

        ResponseEntity<?> response = controller.porPrioridades();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(prioridades, response.getBody());
    }

    @Test
    void testPorTipoEntidad() {
        List<TipoEntidaResponseDTO> tipoEntidad = new ArrayList<>();
        when(service.porTipoEntidad()).thenReturn(tipoEntidad);

        ResponseEntity<?> response = controller.porTipoEntidad();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tipoEntidad, response.getBody());
    }

    @Test
    void testFiltrarPorTipoEntidad() {
        String tipoentidad = "Entidad1";
        List<TipoEntidaResponseDTO> filtrado = new ArrayList<>();
        when(service.filtroporTipoEntidad(tipoentidad)).thenReturn(filtrado);

        ResponseEntity<?> response = controller.filtrarPorTipoEntidad(tipoentidad);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(filtrado, response.getBody());
    }

    @Test
    void testPorEntidad() {
        List<PrioridadEntidadResponseDTO> entidad = new ArrayList<>();
        when(service.porEntidad()).thenReturn(entidad);

        ResponseEntity<?> response = controller.porEntidad();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entidad, response.getBody());
    }

    @Test
    void testFiltrarPorEntidad() {
        String entidad = "Entidad1";
        List<PrioridadEntidadResponseDTO> filtrado = new ArrayList<>();
        when(service.filtroporEntidad(entidad)).thenReturn(filtrado);

        ResponseEntity<?> response = controller.filtrarPorEntidad(entidad);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(filtrado, response.getBody());
    }

    @Test
    void testPorPersona() {
        List<PrioridadPersonaResponseDTO> persona = new ArrayList<>();
        when(service.porPersona()).thenReturn(persona);

        ResponseEntity<?> response = controller.porPersona();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(persona, response.getBody());
    }

    @Test
    void testFiltrarPorPersona() {
        String persona = "Persona1";
        List<PrioridadPersonaResponseDTO> filtrado = new ArrayList<>();
        when(service.filtroporPersona(persona)).thenReturn(filtrado);

        ResponseEntity<?> response = controller.filtrarPorPersona(persona);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(filtrado, response.getBody());
    }

    @Test
    void testPorPersonayEntidad() {
        List<PersonayEntidadResponseDTO> personayEntidad = new ArrayList<>();
        when(service.porPersonayEntidad()).thenReturn(personayEntidad);

        ResponseEntity<?> response = controller.porPersonayEntidad();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personayEntidad, response.getBody());
    }

    @Test
    void testFiltrarPorPersonayEntidad() {
        String personayentidad = "PersonaEntidad1";
        List<PersonayEntidadResponseDTO> filtrado = new ArrayList<>();
        when(service.filtroporPersonayEntidad(personayentidad)).thenReturn(filtrado);

        ResponseEntity<?> response = controller.filtrarPorPersonayEntidad(personayentidad);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(filtrado, response.getBody());
    }

    @Test
    void testGuardaPrioridad() {
        PrioridadRequestDTO request = new PrioridadRequestDTO();
        doNothing().when(service).guardarPrioridad(request);

        ResponseEntity<Map<String, String>> response = controller.guardaPrioridad(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new HashMap<>(), response.getBody());
    }

    @Test
    void testGuardaEntidadPublica() {
        PrioridadRequestDTO request = new PrioridadRequestDTO();
        doNothing().when(service).guardarEntidadPublica(request);

        ResponseEntity<Map<String, String>> response = controller.guardaEntidadPublica(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new HashMap<>(), response.getBody());
    }

    @Test
    void testEliminarPrioridad() {
        Integer Idtramite = 1;
        doNothing().when(service).eliminarPrioridad(Idtramite);

        ResponseEntity<Map<String, String>> response = controller.eliminarPrioridad(Idtramite);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new HashMap<>(), response.getBody());
    }
}
