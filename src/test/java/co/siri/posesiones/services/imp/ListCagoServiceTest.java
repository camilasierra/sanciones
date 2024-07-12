package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.TipoCargoLogoutDto;
import co.siri.posesiones.entities.TipoCargo;
import co.siri.posesiones.repositories.CargoRepository;
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
class ListCagoServiceTest {

    @Mock
    private CargoRepository repository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private ListCagoService service;

    @Test
    void cargos() {
        List<TipoCargo> mockedList = new ArrayList<>();
        TipoCargo tipoCargo = new TipoCargo();
        tipoCargo.setNombre("Cargo de prueba");
        mockedList.add(tipoCargo);

        Mockito.when(repository.listCargo()).thenReturn(mockedList);

        TipoCargoLogoutDto expectedDto = new TipoCargoLogoutDto();
        expectedDto.setNombreCargo("Cargo de prueba");
        Mockito.when(modelMapper.map(tipoCargo, TipoCargoLogoutDto.class)).thenReturn(expectedDto);

        List<TipoCargoLogoutDto> result = service.cargos();

        assertNotNull(result);
        assertEquals(expectedDto.getNombreCargo(), result.get(0).getNombreCargo());
    }
}