package co.siri.posesiones.services.imp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import co.siri.posesiones.dtos.AccionAporteDTO;
import co.siri.posesiones.dtos.IAccionAporte;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.ReporteAntecedentesRepository;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class ReporteAntecedentesServiceTest {

    @Mock
    private ReporteAntecedentesRepository reporteAntecedentesRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReporteAntecedentesService reporteAntecedentesService;

    @Test
    void testObtenerAccionesAporte_Success() throws ExcepcionPersonalizada {
        Long idPersona = 1L;
        List<IAccionAporte> accionesAporteMock = new ArrayList<>();
        IAccionAporte accionAporteMock = mock(IAccionAporte.class);
        accionesAporteMock.add(accionAporteMock);

        when(reporteAntecedentesRepository.obtenerAccionesAporte(idPersona)).thenReturn(accionesAporteMock);
        AccionAporteDTO accionAporteDTO = new AccionAporteDTO();
        when(modelMapper.map(accionAporteMock, AccionAporteDTO.class)).thenReturn(accionAporteDTO);

        List<AccionAporteDTO> result = reporteAntecedentesService.obtenerAccionesAporte(idPersona);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reporteAntecedentesRepository, times(1)).obtenerAccionesAporte(idPersona);
        verify(modelMapper, times(1)).map(accionAporteMock, AccionAporteDTO.class);
    }

    @Test
    void testObtenerAccionesAporte_Empty() throws ExcepcionPersonalizada {
        Long idPersona = 1L;
        List<IAccionAporte> accionesAporteMock = new ArrayList<>();

        when(reporteAntecedentesRepository.obtenerAccionesAporte(idPersona)).thenReturn(accionesAporteMock);

        List<AccionAporteDTO> result = reporteAntecedentesService.obtenerAccionesAporte(idPersona);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(reporteAntecedentesRepository, times(1)).obtenerAccionesAporte(idPersona);
        verify(modelMapper, times(0)).map(any(), eq(AccionAporteDTO.class));
    }

    @Test
    void testObtenerAccionesAporte_Exception() {
        Long idPersona = 1L;

        when(reporteAntecedentesRepository.obtenerAccionesAporte(idPersona)).thenThrow(new ExcepcionPersonalizada("Excepción", HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(ExcepcionPersonalizada.class, () -> {
            reporteAntecedentesService.obtenerAccionesAporte(idPersona);
        });
    }
}
