package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.*;
import co.siri.posesiones.entities.PrioridadTramitePosesion;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.PriorizacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PrioridadServiceTest {

    @Mock
    private PriorizacionRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PrioridadService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPorPrioridades() {
        PrioridadTramitePosesion prioridad = new PrioridadTramitePosesion();
        List<PrioridadTramitePosesion> prioridades = Collections.singletonList(prioridad);
        when(repository.porPrioridades()).thenReturn(prioridades);
        when(modelMapper.map(prioridad, PrioridadTramitePosesion.class)).thenReturn(prioridad);

        List<PrioridadTramitePosesion> result = service.porPrioridades();

        assertEquals(prioridades, result);
        verify(repository, times(1)).porPrioridades();
    }

    @Test
    void testPorPrioridades_Exception() {
        when(repository.porPrioridades()).thenThrow(new RuntimeException());

        ExcepcionPersonalizada exception = assertThrows(ExcepcionPersonalizada.class, () -> {
            service.porPrioridades();
        });

        assertEquals("Error en consulta", exception.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    @Test
    void testPorTipoEntidad() {
        TipoEntidaResponseDTO tipoEntidad = new TipoEntidaResponseDTO() {
            @Override
            public Integer getIdtipoentidad() {
                return null;
            }

            @Override
            public String getTipoentidad() {
                return null;
            }

            @Override
            public String getPrioridad() {
                return null;
            }

            @Override
            public String getEntidadpublica() {
                return null;
            }

            @Override
            public Integer getIdprioridadtramiteposesion() {
                return null;
            }
        };
        List<TipoEntidaResponseDTO> tipoEntidades = Collections.singletonList(tipoEntidad);
        when(repository.porTipoEntidad()).thenReturn(tipoEntidades);

        List<TipoEntidaResponseDTO> result = service.porTipoEntidad();

        assertEquals(tipoEntidades, result);
        verify(repository, times(1)).porTipoEntidad();
    }

    @Test
    void testPorTipoEntidad_Exception() {
        when(repository.porTipoEntidad()).thenThrow(new RuntimeException());

        ExcepcionPersonalizada exception = assertThrows(ExcepcionPersonalizada.class, () -> {
            service.porTipoEntidad();
        });

        assertEquals("Error en consulta", exception.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    @Test
    void testFiltroporTipoEntidad() {
        String tipoentidad = "Entidad1";
        TipoEntidaResponseDTO tipoEntidad = new TipoEntidaResponseDTO() {
            @Override
            public Integer getIdtipoentidad() {
                return null;
            }

            @Override
            public String getTipoentidad() {
                return null;
            }

            @Override
            public String getPrioridad() {
                return null;
            }

            @Override
            public String getEntidadpublica() {
                return null;
            }

            @Override
            public Integer getIdprioridadtramiteposesion() {
                return null;
            }
        };
        List<TipoEntidaResponseDTO> tipoEntidades = Collections.singletonList(tipoEntidad);
        when(repository.filtrarPorTipoEntidad(tipoentidad)).thenReturn(tipoEntidades);

        List<TipoEntidaResponseDTO> result = service.filtroporTipoEntidad(tipoentidad);

        assertEquals(tipoEntidades, result);
        verify(repository, times(1)).filtrarPorTipoEntidad(tipoentidad);
    }

    @Test
    void testFiltroporTipoEntidad_Exception() {
        String tipoentidad = "Entidad1";
        when(repository.filtrarPorTipoEntidad(tipoentidad)).thenThrow(new RuntimeException());

        ExcepcionPersonalizada exception = assertThrows(ExcepcionPersonalizada.class, () -> {
            service.filtroporTipoEntidad(tipoentidad);
        });

        assertEquals("Error en consulta", exception.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    @Test
    void testGuardarPrioridad() {
        PrioridadRequestDTO requestDTO = new PrioridadRequestDTO();
        when(repository.ultimoId()).thenReturn(1);

        service.guardarPrioridad(requestDTO);

        verify(repository, times(1)).insertarprioridad(
                anyInt(),
                eq(requestDTO.getIdtipoentidad()),
                eq(requestDTO.getIdentidad()),
                eq(requestDTO.getEntidadpublica()),
                eq(requestDTO.getIdpersona()),
                eq(requestDTO.getNumeroident()),
                eq(requestDTO.getIdtramite()),
                eq(requestDTO.getIdusuario()),
                eq(requestDTO.getIpcliente())
        );
    }


    @Test
    void testEliminarPrioridad() {
        Integer Idtramite = 1;

        service.eliminarPrioridad(Idtramite);

        verify(repository, times(1)).eliminarPrioridad(Idtramite);
    }

    @Test
    void testEliminarPrioridad_Exception() {
        Integer Idtramite = 1;
        doThrow(new RuntimeException()).when(repository).eliminarPrioridad(Idtramite);

        ExcepcionPersonalizada exception = assertThrows(ExcepcionPersonalizada.class, () -> {
            service.eliminarPrioridad(Idtramite);
        });

        assertEquals("Error en consulta", exception.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }
}

