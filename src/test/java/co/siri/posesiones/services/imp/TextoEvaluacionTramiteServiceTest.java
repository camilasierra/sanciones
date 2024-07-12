package co.siri.posesiones.services.imp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;

import co.siri.posesiones.dtos.FiltroTextoEvaluacionIN;
import co.siri.posesiones.dtos.TextoEvaluacionDTO;
import co.siri.posesiones.dtos.TextoEvaluacionINDTO;
import co.siri.posesiones.dtos.TextoEvaluacionProjection;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.TextoEvaluacionTramiteRepository;
import co.siri.posesiones.utilidades.dto.AuditDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class TextoEvaluacionTramiteServiceTest {

    @Mock
    private TextoEvaluacionTramiteRepository mensajeTramitePosesionRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TextoEvaluacionTramiteService mensajeTramitePosesionService;

    private TextoEvaluacionProjection mensajeTramiteProjection;
    private TextoEvaluacionDTO mensajeTramiteDTO;
    private TextoEvaluacionINDTO mensajeTramiteINDTO;
    private List<TextoEvaluacionProjection> mensajeTramiteList;
    private List<TextoEvaluacionDTO> mensajeTramiteDTOList;

    @BeforeEach
    void setUp() {
        mensajeTramiteProjection = new TextoEvaluacionProjection() {
            @Override
            public Long getIdTextoTramite() {
                return 1L;
            }

            @Override
            public Long getIdTramitePosesion() {
                return 1L;
            }

            @Override
            public String getTitulo() {
                return "Titulo";
            }

            @Override
            public Long getIdTipoTextoTramite() {
                return 1L;
            }

            @Override
            public Long getIdTipoSeccionTramite() {
                return 1L;
            }

            @Override
            public Clob getTexto() {
                return null;
            }

            @Override
            public Long getIdUsuario() {
                return 1L;
            }

            @Override
            public String getIpCliente() {
                return "127.0.0.1";
            }
        };

        mensajeTramiteDTO = new TextoEvaluacionDTO();
        mensajeTramiteDTO.setIdTextoTramite(1L);
        mensajeTramiteDTO.setIdTramitePosesion(1L);
        mensajeTramiteDTO.setIdTipoTextoTramite(1L);
        mensajeTramiteDTO.setIdTipoSeccionTramite(1L);
        mensajeTramiteDTO.setTexto("Texto de prueba");
        mensajeTramiteDTO.setIdUsuario(1L);

        AuditDTO audit = new AuditDTO();
        audit.setIdusuario(1L);
        audit.setIpUsuario("127.0.0.1");

        mensajeTramiteINDTO = new TextoEvaluacionINDTO();
        mensajeTramiteINDTO.setIdTramitePosesion(1L);
        mensajeTramiteINDTO.setIdTipoTextoTramite(1L);
        mensajeTramiteINDTO.setIdTipoSeccionTramite(1L);
        mensajeTramiteINDTO.setTexto("Texto de prueba");
        mensajeTramiteINDTO.setUsuario(audit);

        mensajeTramiteList = new ArrayList<>();
        mensajeTramiteList.add(mensajeTramiteProjection);

        mensajeTramiteDTOList = new ArrayList<>();
        mensajeTramiteDTOList.add(mensajeTramiteDTO);
    }

    @Test
    void obtenerMensajesTramitesPosesionMensajesExistentesDevuelveMensajes() throws ExcepcionPersonalizada {
        FiltroTextoEvaluacionIN filtro = new FiltroTextoEvaluacionIN();
        filtro.setIdTramite(1L);
        filtro.setIdTipoTexto(1L);
        filtro.setIdSeccionTexto(1L);

        when(mensajeTramitePosesionRepository.obtenerMensajesTramite(any(), any(), any())).thenReturn(mensajeTramiteList);
        List<TextoEvaluacionDTO> result = mensajeTramitePosesionService.obtenerTextosEvaluacion(filtro);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(mensajeTramitePosesionRepository).obtenerMensajesTramite(any(), any(), any());
    }

    @Test
    void obtenerMensajesTramitesPosesionSinMensajesDevuelveListaVacia() throws ExcepcionPersonalizada {
        FiltroTextoEvaluacionIN filtro = new FiltroTextoEvaluacionIN();
        filtro.setIdTramite(1L);
        filtro.setIdTipoTexto(1L);
        filtro.setIdSeccionTexto(1L);

        when(mensajeTramitePosesionRepository.obtenerMensajesTramite(any(), any(), any())).thenReturn(new ArrayList<>());

        List<TextoEvaluacionDTO> result = mensajeTramitePosesionService.obtenerTextosEvaluacion(filtro);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mensajeTramitePosesionRepository).obtenerMensajesTramite(any(), any(), any());
        verify(modelMapper, never()).map(any(), any());
    }

}