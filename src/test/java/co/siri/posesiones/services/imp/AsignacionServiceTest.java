package co.siri.posesiones.services.imp;

import co.siri.posesiones.repositories.AsignacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AsignacionServiceTest {

    @Mock
    private AsignacionRepository asignacionRepository;

    @InjectMocks
    private AsignacionService asignacionService;

    @Test
    void consultaAsignacionTramiteAnalistaTrueTest() {
        Long idTramite = 1L;
        Long idAnalista = 2L;

        Mockito.when(asignacionRepository.findAsignacionByIdTramitePosesionAndIdAnalistaTramitePosesion(anyLong(), anyLong())).thenReturn(true);

        boolean result = asignacionService.getAsignacionTramiteAnalista(idTramite, idAnalista);

        assertTrue(result);

        verify(asignacionRepository, times(1)).findAsignacionByIdTramitePosesionAndIdAnalistaTramitePosesion(anyLong(), anyLong());


    }

    @Test
    void consultaAsignacionTramiteAnalistaFalseTest() {
        Long idTramite = 1L;
        Long idAnalista = 2L;

        Mockito.when(asignacionRepository.findAsignacionByIdTramitePosesionAndIdAnalistaTramitePosesion(anyLong(), anyLong())).thenReturn(false);

        boolean result = asignacionService.getAsignacionTramiteAnalista(idTramite, idAnalista);

        assertFalse(result);

        verify(asignacionRepository, times(1)).findAsignacionByIdTramitePosesionAndIdAnalistaTramitePosesion(anyLong(), anyLong());


    }

}
