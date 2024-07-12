package co.siri.posesiones.services.imp;
import co.siri.posesiones.dtos.ActualizarAnalistaDTO;
import co.siri.posesiones.dtos.AgregarAnalistaDTO;
import co.siri.posesiones.dtos.GestionAnalistasDTO;
import co.siri.posesiones.entities.AnalistaTramitePosesion;
import co.siri.posesiones.entities.PrioridadPorAnalista;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.GestionAnalistasRepository;
import co.siri.posesiones.repositories.PrioridadPorAnalistaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GestionAnalistasServiceTest {

    @InjectMocks
    private GestionAnalistasService service;

    @Mock
    private GestionAnalistasRepository repository;

    @Mock
    private PrioridadPorAnalistaRepository prioridadPorAnalistaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarAnalistas() {
        GestionAnalistasDTO analista1 = mock(GestionAnalistasDTO.class);
        GestionAnalistasDTO analista2 = mock(GestionAnalistasDTO.class);
        List<GestionAnalistasDTO> analistas = Arrays.asList(analista1, analista2);
        when(repository.listarAnalistas()).thenReturn(analistas);

        List<GestionAnalistasDTO> result = service.listarAnalistas();
        assertEquals(2, result.size());
    }
    @Test
    void testAgregarAnalista() {
        // Configurar el DTO
        AgregarAnalistaDTO dto = new AgregarAnalistaDTO();
        dto.setNombre("Nombre");
        dto.setIdentificacion("123456");
        dto.setIdTipoAnalista(1L);
        dto.setLimiteCarga(5L);
        dto.setIdPrioridad(1L);
        dto.setIdUsuario(1L);
        dto.setLogin("login");
        dto.setActivo(1L);
        dto.setIpCliente("127.0.0.1");
    
        // Crear el objeto de retorno del mock
        AnalistaTramitePosesion analista = new AnalistaTramitePosesion();
        analista.setIdAnalistaTramitePosesion(1L); // Asegúrate de que el ID no es null
        analista.setNombre(dto.getNombre());
        analista.setIdentificacion(dto.getIdentificacion());
        analista.setIdTipoAnalista(dto.getIdTipoAnalista());
        analista.setLimiteCarga(dto.getLimiteCarga());
        analista.setIdUsuario(dto.getIdUsuario());
        analista.setLogin(dto.getLogin());
        analista.setActivo(dto.getActivo().toString());
        analista.setIpCliente(dto.getIpCliente());
    
        // Configurar el mock para asignar el ID al guardar
        when(repository.save(any(AnalistaTramitePosesion.class))).thenAnswer(invocation -> {
            AnalistaTramitePosesion analistaTramitePosesion = invocation.getArgument(0);
            analistaTramitePosesion.setIdAnalistaTramitePosesion(1L);
            return analistaTramitePosesion;
        });
    
        when(repository.findById(1L)).thenReturn(Optional.of(analista));
    
        // Ejecutar el método a probar
        AnalistaTramitePosesion result = service.agregarAnalista(dto);
    
        // Verificar resultados
        assertNotNull(result);
        assertEquals(1L, result.getIdAnalistaTramitePosesion());
    
        // Verificar que los métodos del repositorio fueron llamados
        verify(repository, times(1)).save(any(AnalistaTramitePosesion.class));
        verify(prioridadPorAnalistaRepository, times(1)).save(any(PrioridadPorAnalista.class));
    }
    
    @Test
    void testActualizarAnalista() {
        ActualizarAnalistaDTO dto = new ActualizarAnalistaDTO();
        dto.setIdAnalistaTramitePosesion(1L);
        dto.setNombre("Nombre Actualizado");
        dto.setIdentificacion("123456");
        dto.setIdTipoAnalista(1L);
        dto.setLimiteCarga(10L);
        dto.setIdUsuario(1L);
        dto.setLogin("login");
        dto.setActivo(1L);
        dto.setIpCliente("127.0.0.1");
        dto.setIdPrioridad(2L);

        AnalistaTramitePosesion analista = new AnalistaTramitePosesion();
        analista.setIdAnalistaTramitePosesion(1L);

        PrioridadPorAnalista prioridad = new PrioridadPorAnalista();
        prioridad.setIdAnalistaTramitePosesion(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(analista));
        when(repository.save(any(AnalistaTramitePosesion.class))).thenReturn(analista);
        when(prioridadPorAnalistaRepository.findByIdAnalistaTramitePosesion(1L)).thenReturn(Optional.of(prioridad));

        AnalistaTramitePosesion result = service.actualizarAnalista(dto);
        assertNotNull(result);
        assertEquals("Nombre Actualizado", result.getNombre());

        verify(repository, times(1)).save(any(AnalistaTramitePosesion.class));
        verify(prioridadPorAnalistaRepository, times(1)).save(any(PrioridadPorAnalista.class));
    }

    @Test
    void testAgregarAnalistaSinNombre() {
        AgregarAnalistaDTO dto = new AgregarAnalistaDTO();
        dto.setIdentificacion("123456");

        Exception exception = assertThrows(ExcepcionPersonalizada.class, () -> {
            service.agregarAnalista(dto);
        });

        String expectedMessage = "El nombre del analista es obligatorio.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testActualizarAnalistaNoEncontrado() {
        ActualizarAnalistaDTO dto = new ActualizarAnalistaDTO();
        dto.setIdAnalistaTramitePosesion(1L);

        when(repository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ExcepcionPersonalizada.class, () -> {
            service.actualizarAnalista(dto);
        });

        String expectedMessage = "Analista no encontrado con ID: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}