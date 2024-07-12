package co.siri.posesiones.controllers;

import co.siri.posesiones.dtos.InhabilidadPosesionProjection;
import co.siri.posesiones.services.IAnalistaService;
import co.siri.posesiones.services.InhabilidadPosesionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AnalistaControllerTest {

    @InjectMocks
    private AnalistaController analistaController;

    @Mock
    private IAnalistaService analistaService;

    @Mock
    private InhabilidadPosesionService inhabilidadPosesionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetInhabilidadPosesion() {
        Long idTramitePosesion = 1L;
        List<Map<String, Object>> expectedResponse = List.of(new HashMap<>());  // Reemplazar con la respuesta esperada
        when(inhabilidadPosesionService.findInhabilidadPosesionByTramiteAndSeccion(idTramitePosesion)).thenReturn(expectedResponse);

        ResponseEntity<?> response = analistaController.getInhabilidadPosesion(idTramitePosesion);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testGetTipoCargoByTramite() {
        Long idTramitePosesion = 1L;
        List<Map<String, Object>> expectedResponse = List.of(new HashMap<>());  // Reemplazar con la respuesta esperada
        when(inhabilidadPosesionService.findTipoCargoByIdTramitePosesion(idTramitePosesion)).thenReturn(expectedResponse);

        ResponseEntity<?> response = analistaController.getTipoCargoByTramite(idTramitePosesion);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    /*@Test
    public void testObtenerInhabilidadesComunesPorSeccionYTramite() {
        List<Integer> idSecciones = Arrays.asList(1, 2, 3);
        int idTramite = 1;
        List<InhabilidadPosesionProjection> expectedResponse = new ArrayList<>();
        expectedResponse.add(new InhabilidadPosesionProjection() {
            @Override
            public String getDescripcion() {
                return "Descripción de prueba";
            }

            @Override
            public String getInhabilitado() {
                return "Sí";
            }

            @Override
            public String getIdtiposeccioninhabilidadcargo() {
                return "1";
            }

            @Override
            public String getNombre() {
                return "Nombre de prueba";
            }
        });

        when(inhabilidadPosesionService.obtenerInhabilidadesComunesPorSeccionYTramite(idSecciones, idTramite)).thenReturn(expectedResponse);

        ResponseEntity<?> response = analistaController.obtenerInhabilidadesComunesPorSeccionYTramite(idSecciones, idTramite);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
     */

    @Test
    public void testGetSancionesEnfime() {
        Long idPersona = 1L;
        List<Map<String, Object>> expectedResponse = List.of(new HashMap<>());  // Reemplazar con la respuesta esperada
        when(inhabilidadPosesionService.getSancionesenfirme(idPersona)).thenReturn(expectedResponse);

        ResponseEntity<?> response = analistaController.getSancionesEnfime(idPersona);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

   /* @Test
    public void testGetOtrasSituaciones() {
        int idTramitePosesion = 1;
        List<InhabilidadPosesionProjection> expectedResponse = new ArrayList<>();
        expectedResponse.add(new InhabilidadPosesionProjection() {
            @Override
            public String getDescripcion() {
                return "Descripción de prueba";
            }

            @Override
            public String getInhabilitado() {
                return "Sí";
            }

            @Override
            public String getIdtiposeccioninhabilidadcargo() {
                return "1";
            }

            @Override
            public String getNombre() {
                return "Nombre de prueba";
            }
        });

        when(inhabilidadPosesionService.getOtrasSituaciones(idTramitePosesion)).thenReturn(expectedResponse);

        ResponseEntity<?> response = analistaController.getOtrasSituaciones(idTramitePosesion);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    */

}
