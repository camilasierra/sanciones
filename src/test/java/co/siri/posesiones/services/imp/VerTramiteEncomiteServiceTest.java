package co.siri.posesiones.services.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import co.siri.posesiones.dtos.ResultadoComiteRequestDTO;
import co.siri.posesiones.entities.TipoEstadoTramitePosesion;
import co.siri.posesiones.repositories.VerTramiteEnComiteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.ResultadoComiteRepository;
import co.siri.posesiones.services.imp.VerTramiteEncomiteService;

import java.util.Arrays;
import java.util.List;

public class VerTramiteEncomiteServiceTest {

    @Mock
    private ResultadoComiteRepository resultadoComiteRepository;
    @Mock
    private VerTramiteEnComiteRepository repository;

    @InjectMocks
    private VerTramiteEncomiteService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testActualizarResultadoComite() {
        ResultadoComiteRequestDTO dto = new ResultadoComiteRequestDTO();
        dto.setAnotacion("Nueva anotación");
        dto.setIdTipoEstadoTramitePosesion(1);
        dto.setIdSesionComite(1);
        dto.setIdTramitePosesion(1);

        service.actualizarResultadoComite(dto);

        verify(resultadoComiteRepository, times(1)).updateResultadoComite(
                dto.getAnotacion(),
                dto.getIdTipoEstadoTramitePosesion(),
                dto.getIdSesionComite(),
                dto.getIdTramitePosesion()
        );
    }

    @Test
    public void testActualizarResultadoComite_Error() {
        ResultadoComiteRequestDTO dto = new ResultadoComiteRequestDTO();
        doThrow(new RuntimeException("Error al actualizar")).when(resultadoComiteRepository).updateResultadoComite(
                anyString(), anyInt(), anyInt(), anyInt()
        );

    }

    @Test
    public void testListarrVerTramiteEncomite() {
        // Datos de prueba
        TipoEstadoTramitePosesion estado1 = new TipoEstadoTramitePosesion();
        estado1.getNombre();
        estado1.setNombre("Estado 1");

        TipoEstadoTramitePosesion estado2 = new TipoEstadoTramitePosesion();
        estado2.getIdTipoEstadoTramitePosesion();
        estado2.setNombre("Estado 2");

        List<TipoEstadoTramitePosesion> estados = Arrays.asList(estado1, estado2);

        // Simular el comportamiento del repositorio
        when(repository.obtenerEstadosTramite()).thenReturn(estados);

        // Llamar al método del servicio
        List<TipoEstadoTramitePosesion> result = service.ListarrVerTramiteEncomite();

        // Verificar que se devuelva la lista esperada
        assertEquals(2, result.size());
        assertEquals("Estado 1", result.get(0).getNombre());
        assertEquals("Estado 2", result.get(1).getNombre());
    }

    @Test
    public void testListarrVerTramiteEncomite_Error() {
        // Simular excepción en el repositorio
        when(repository.obtenerEstadosTramite()).thenThrow(new RuntimeException("Error en la consulta"));

        // Verificar que se lance la excepción personalizada
        assertThrows(ExcepcionPersonalizada.class, () -> {
            service.ListarrVerTramiteEncomite();
        });
    }
}
