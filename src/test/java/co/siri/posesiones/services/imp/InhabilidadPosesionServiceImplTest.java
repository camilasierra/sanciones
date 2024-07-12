package co.siri.posesiones.services.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import co.siri.posesiones.dtos.InhabilidadPosesionProjection;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.ActoAdministrativoRepository;
import co.siri.posesiones.repositories.InhabilidadPosesionRepository;
import co.siri.posesiones.services.imp.InhabilidadPosesionServiceImpl;

public class InhabilidadPosesionServiceImplTest {

    @InjectMocks
    private InhabilidadPosesionServiceImpl inhabilidadPosesionService;

    @Mock
    private InhabilidadPosesionRepository inhabilidadPosesionRepository;

    @Mock
    private ActoAdministrativoRepository actoAdministrativoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindInhabilidadPosesionByTramiteAndSeccion() {
        Long idTramitePosesion = 1L;
        List<Map<String, Object>> expectedResponse = Arrays.asList(
                createInhabilidadPosesionMap("Descripción 1", "Inhabilitado 1"),
                createInhabilidadPosesionMap("Descripción 2", "Inhabilitado 2")
        );
        when(inhabilidadPosesionRepository.findInhabilidadPosesionByTramiteAndSeccion(idTramitePosesion)).thenReturn(expectedResponse);

        List<Map<String, Object>> result = inhabilidadPosesionService.findInhabilidadPosesionByTramiteAndSeccion(idTramitePosesion);

        assertEquals(expectedResponse.size(), result.size());
    }

    @Test
    public void testFindTipoCargoByIdTramitePosesion() {
        Long idTramitePosesion = 1L;
        List<Map<String, Object>> expectedResponse = Arrays.asList(
                createTipoCargoMap("Cargo 1"),
                createTipoCargoMap("Cargo 2")
        );
        when(inhabilidadPosesionRepository.findTipoCargosByTramitePosesionId(idTramitePosesion)).thenReturn(expectedResponse);

        List<Map<String, Object>> result = inhabilidadPosesionService.findTipoCargoByIdTramitePosesion(idTramitePosesion);

        assertEquals(expectedResponse.size(), result.size());
    }

    @Test
    public void testObtenerInhabilidadesComunesPorSeccionYTramite() {
        List<Integer> idSecciones = Arrays.asList(1, 2, 3);
        int idTramite = 1;
        List<InhabilidadPosesionProjection> expectedResponse = Arrays.asList(
                createInhabilidadPosesionProjection("Descripción 1", "Inhabilitado 1", "Tipo 1", "Nombre 1"),
                createInhabilidadPosesionProjection("Descripción 2", "Inhabilitado 2", "Tipo 2", "Nombre 2")
        );
        when(inhabilidadPosesionRepository.obtenerInhabilidadesComunesPorSeccionYTramite(idSecciones, idTramite)).thenReturn(expectedResponse);

        List<InhabilidadPosesionProjection> result = inhabilidadPosesionService.obtenerInhabilidadesComunesPorSeccionYTramite(idSecciones, idTramite);

        assertEquals(expectedResponse.size(), result.size());
    }

    @Test
    public void testGetSancionesenfirme() {
        Long idPersona = 1L;
        List<Map<String, Object>> expectedResponse = Arrays.asList(
                createSancionMap("Sanción 1"),
                createSancionMap("Sanción 2")
        );
        when(actoAdministrativoRepository.findActosAdministrativosByPersona(idPersona)).thenReturn(expectedResponse);

        List<Map<String, Object>> result = inhabilidadPosesionService.getSancionesenfirme(idPersona);

        assertEquals(expectedResponse.size(), result.size());
    }

    @Test
    public void testGetOtrasSituaciones() {
        int idTramitePosesion = 1;
        List<InhabilidadPosesionProjection> expectedResponse = Arrays.asList(
                createInhabilidadPosesionProjection("Descripción 1", "Inhabilitado 1", "Tipo 1", "Nombre 1"),
                createInhabilidadPosesionProjection("Descripción 2", "Inhabilitado 2", "Tipo 2", "Nombre 2")
        );
        when(inhabilidadPosesionRepository.GetOtrasSituaciones(idTramitePosesion)).thenReturn(expectedResponse);

        List<InhabilidadPosesionProjection> result = inhabilidadPosesionService.getOtrasSituaciones(idTramitePosesion);

        assertEquals(expectedResponse.size(), result.size());
    }

    // Métodos de apoyo para crear datos simulados
    private Map<String, Object> createInhabilidadPosesionMap(String descripcion, String inhabilitado) {
        // Implementar según la estructura esperada
        return null; // Implementar
    }

    private Map<String, Object> createTipoCargoMap(String cargo) {
        // Implementar según la estructura esperada
        return null; // Implementar
    }

    private InhabilidadPosesionProjection createInhabilidadPosesionProjection(String descripcion, String inhabilitado, String tipoSeccion, String nombre) {
        // Implementar según la estructura esperada
        return null; // Implementar
    }

    private Map<String, Object> createSancionMap(String sancion) {
        // Implementar según la estructura esperada
        return null; // Implementar
    }
}
