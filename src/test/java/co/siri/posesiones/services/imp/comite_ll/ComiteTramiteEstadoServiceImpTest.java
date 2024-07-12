package co.siri.posesiones.services.imp.comite_ll;

import co.siri.posesiones.dtos.comiteII.TramitesEstadoDTO;
import co.siri.posesiones.repositories.ComiteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ComiteTramiteEstadoServiceImpTest {

    @Mock
    ComiteRepository comiteRepository;

    @InjectMocks
    private ComiteTramiteEstadoServiceImp comiteTramiteEstadoServiceImp;

    private List<TramitesEstadoDTO> tramitesEstadoList;

    @BeforeEach
    void setUp() {
        TramitesEstadoDTO tramite1 = Mockito.mock(TramitesEstadoDTO.class);
        TramitesEstadoDTO tramite2 = Mockito.mock(TramitesEstadoDTO.class);
        tramitesEstadoList = Arrays.asList(tramite1, tramite2);
    }

    @Test
    void tramitesEstado() {
        Mockito.when(comiteRepository.tramitesEstado(Mockito.anyString(), Mockito.anyLong(), Mockito.anyInt())).thenReturn(tramitesEstadoList);

        List<TramitesEstadoDTO> result = comiteTramiteEstadoServiceImp.tramitesEstado("estadoPrueba", 1L, 1);
        assertNotNull(result);
        assertEquals(tramitesEstadoList, result);
    }
}
