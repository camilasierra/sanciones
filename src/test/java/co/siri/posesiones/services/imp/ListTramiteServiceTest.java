package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.EstaTramiteDto;
import co.siri.posesiones.entities.TipoEstadoTramitePosesion;
import co.siri.posesiones.repositories.EstadoTramiteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ListTramiteServiceTest {
    @Mock
    private EstadoTramiteRepository repository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private ListTramiteService service;

    @Test
    void estaTramites() {
        List<TipoEstadoTramitePosesion> mockedList = new ArrayList<>();
        TipoEstadoTramitePosesion tipoEstadoTramitePosesion = new TipoEstadoTramitePosesion();
        tipoEstadoTramitePosesion.setNombre("Estado de prueba");
        mockedList.add(tipoEstadoTramitePosesion);

        Mockito.when(repository.listTramite()).thenReturn(mockedList);

        EstaTramiteDto expectedDto = new EstaTramiteDto();
        expectedDto.setNombreEstadoTramite("Estado de prueba");
        Mockito.when(modelMapper.map(tipoEstadoTramitePosesion, EstaTramiteDto.class)).thenReturn(expectedDto);

        List<EstaTramiteDto> result = service.estaTramites();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedDto.getNombreEstadoTramite(), result.get(0).getNombreEstadoTramite());

        /*var result = service.estaTramites();
        Assertions.assertNotNull(result);*/
    }
}