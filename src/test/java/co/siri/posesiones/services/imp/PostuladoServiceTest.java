package co.siri.posesiones.services.imp;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import co.siri.posesiones.dtos.*;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.PostuladoRepository;
import co.siri.posesiones.utilidades.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.util.*;

class PostuladoServiceTest {

    @InjectMocks
    private PostuladoService postuladoService;

    @Mock
    private PostuladoRepository postuladoRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerEstudiosPostulado() throws ExcepcionPersonalizada {
        Long idPersona = 1L;
        IPostuladoEstudios postuladoEstudiosMock = mock(IPostuladoEstudios.class);
        List<IPostuladoEstudios> postuladoEstudiosList = Collections.singletonList(postuladoEstudiosMock);
        PostuladoEstudioDTO postuladoEstudioDTO = new PostuladoEstudioDTO();

        when(postuladoRepository.obtenerEstudiosPostulado(idPersona)).thenReturn(postuladoEstudiosList);
        when(modelMapper.map(postuladoEstudiosMock, PostuladoEstudioDTO.class)).thenReturn(postuladoEstudioDTO);

        List<PostuladoEstudioDTO> result = postuladoService.obtenerEstudiosPostulado(idPersona);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(postuladoRepository).obtenerEstudiosPostulado(idPersona);
        verify(modelMapper).map(postuladoEstudiosMock, PostuladoEstudioDTO.class);
    }

    @Test
    void testObtenerCargosSinPosesionPostulado() throws ExcepcionPersonalizada {
        Long idPersona = 1L;
        IPostuladoCargosSinPosesion cargosSinPosesionMock = mock(IPostuladoCargosSinPosesion.class);
        List<IPostuladoCargosSinPosesion> cargosSinPosesionList = Collections.singletonList(cargosSinPosesionMock);
        PostuladoCargosSinPosesionDTO cargosSinPosesionDTO = new PostuladoCargosSinPosesionDTO();

        when(postuladoRepository.obtenerCargosSinPosesionPostulado(idPersona)).thenReturn(cargosSinPosesionList);
        when(modelMapper.map(cargosSinPosesionMock, PostuladoCargosSinPosesionDTO.class)).thenReturn(cargosSinPosesionDTO);

        List<PostuladoCargosSinPosesionDTO> result = postuladoService.obtenerCargosSinPosesionPostulado(idPersona);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(postuladoRepository).obtenerCargosSinPosesionPostulado(idPersona);
        verify(modelMapper).map(cargosSinPosesionMock, PostuladoCargosSinPosesionDTO.class);
    }

    @Test
    void testObtenerCargosPosesionesPostulado() throws ExcepcionPersonalizada {
        Long idPersona = 1L;
        IPostuladoCargosPosesion cargosPosesionMock = mock(IPostuladoCargosPosesion.class);
        List<IPostuladoCargosPosesion> cargosPosesionList = Collections.singletonList(cargosPosesionMock);
        PostuladoCargosPosesionDTO cargosPosesionDTO = new PostuladoCargosPosesionDTO();

        when(postuladoRepository.obtenerCargosPosesionPostulado(idPersona)).thenReturn(cargosPosesionList);
        when(modelMapper.map(cargosPosesionMock, PostuladoCargosPosesionDTO.class)).thenReturn(cargosPosesionDTO);

        List<PostuladoCargosPosesionDTO> result = postuladoService.obtenerCargosPosesionesPostulado(idPersona);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(postuladoRepository).obtenerCargosPosesionPostulado(idPersona);
        verify(modelMapper).map(cargosPosesionMock, PostuladoCargosPosesionDTO.class);
    }

    @Test
    void testObtenerExperienciaPostulado() throws ExcepcionPersonalizada {
        Long idPersona = 1L;
        IExperienciaPostulado experienciaPostuladoMock = mock(IExperienciaPostulado.class);
        List<IExperienciaPostulado> experienciaPostuladoList = Collections.singletonList(experienciaPostuladoMock);
        ExperienciaPostuladoDTO experienciaPostuladoDTO = new ExperienciaPostuladoDTO();

        when(postuladoRepository.obtenerExperienciaPostulado(idPersona)).thenReturn(experienciaPostuladoList);
        when(modelMapper.map(experienciaPostuladoMock, ExperienciaPostuladoDTO.class)).thenReturn(experienciaPostuladoDTO);

        List<ExperienciaPostuladoDTO> result = postuladoService.obtenerExperienciaPostulado(idPersona);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(postuladoRepository).obtenerExperienciaPostulado(idPersona);
    }

    @Test
    void testObtenerDatosPostuladoThrowsExceptionWhenNotFound() {
        Long idTramite = 1L;

        when(postuladoRepository.obtenerDatosPostulado(idTramite)).thenReturn(Optional.empty());

        ExcepcionPersonalizada exception = assertThrows(ExcepcionPersonalizada.class, () -> {
            postuladoService.obtenerDatosPostulado(idTramite);
        });

        assertEquals(Constantes.NO_DATOS_POSTULADO, exception.getMessage());
        verify(postuladoRepository).obtenerDatosPostulado(idTramite);
    }

    @Test
    void testObtenerEstudiosPostuladoReturnsEmptyListWhenNoData() throws ExcepcionPersonalizada {
        Long idPersona = 1L;

        when(postuladoRepository.obtenerEstudiosPostulado(idPersona)).thenReturn(Collections.emptyList());

        List<PostuladoEstudioDTO> result = postuladoService.obtenerEstudiosPostulado(idPersona);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(postuladoRepository).obtenerEstudiosPostulado(idPersona);
    }


    @Test
    void testObtenerCargosSinPosesionPostuladoReturnsEmptyListWhenNoData() throws ExcepcionPersonalizada {
        Long idPersona = 1L;

        when(postuladoRepository.obtenerCargosSinPosesionPostulado(idPersona)).thenReturn(Collections.emptyList());

        List<PostuladoCargosSinPosesionDTO> result = postuladoService.obtenerCargosSinPosesionPostulado(idPersona);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(postuladoRepository).obtenerCargosSinPosesionPostulado(idPersona);
    }

    @Test
    void testObtenerCargosPosesionesPostuladoReturnsEmptyListWhenNoData() throws ExcepcionPersonalizada {
        Long idPersona = 1L;

        when(postuladoRepository.obtenerCargosPosesionPostulado(idPersona)).thenReturn(Collections.emptyList());

        List<PostuladoCargosPosesionDTO> result = postuladoService.obtenerCargosPosesionesPostulado(idPersona);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(postuladoRepository).obtenerCargosPosesionPostulado(idPersona);
    }

    @Test
    void testObtenerExperienciaPostuladoReturnsEmptyListWhenNoData() throws ExcepcionPersonalizada {
        Long idPersona = 1L;

        when(postuladoRepository.obtenerExperienciaPostulado(idPersona)).thenReturn(Collections.emptyList());

        List<ExperienciaPostuladoDTO> result = postuladoService.obtenerExperienciaPostulado(idPersona);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(postuladoRepository).obtenerExperienciaPostulado(idPersona);
    }
}
