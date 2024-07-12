package co.siri.posesiones.services.imp;

import static org.junit.jupiter.api.Assertions.*;

import co.siri.posesiones.dtos.SesionComiteOutDTO;
import co.siri.posesiones.entities.SesionComite;
import co.siri.posesiones.repositories.SesionComiteRepository;
import co.siri.posesiones.utilidades.dto.PaginacionDTO;
import co.siri.posesiones.utilidades.dto.PaginacionInDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

@Slf4j
@ExtendWith(MockitoExtension.class)
class GestionarSesionesServiceTest {
    private static final short PAGINA_ACTUAL = 0;
    private static final short POR_PAGINA = 20;

    @Mock
    private SesionComiteRepository sesionComiteRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks private GestionarSesionesComiteService gestionarSesionesComiteService;

    @Test
    @DisplayName("Obtener listado paginado de sesiones comite - EXITOSO")
    void obtenerSesionesComite(){
        PaginacionInDTO paginado = new PaginacionInDTO();
        paginado.setPaginaActual(PAGINA_ACTUAL);
        paginado.setTamanoPagina(POR_PAGINA);

        List<SesionComite> sesionesList = Collections.singletonList(new SesionComite());
        Page<SesionComite> sesionesPaginadas = new PageImpl<>(sesionesList, PageRequest.of(paginado.getPaginaActual(), paginado.getTamanoPagina()), 1);

        Mockito.when(
                sesionComiteRepository.obtenerSesionesComite(
                        Mockito.any(Pageable.class)))
                .thenReturn(sesionesPaginadas);
        Mockito.when(
                modelMapper.map(Mockito.any(SesionComite.class),Mockito.any(Class.class)))
                .thenReturn(new SesionComiteOutDTO());

        PaginacionDTO resultado = gestionarSesionesComiteService.obtenerListaSesionesComite(paginado);
        assertEquals(1L, resultado.getTotalElements());
        assertEquals(1, resultado.getTotalPages());
    }
}
