package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.EscritorioTramitesResponseDTO;
import co.siri.posesiones.dtos.FiltroAvanzadoEscritorioDto;
import co.siri.posesiones.repositories.VerificadorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class VerificadorServiceTest {

    @Mock
    private VerificadorRepository repository;
    @InjectMocks
    private VerificadorService service;

    @Test
    void filtroAvanzado() {
        FiltroAvanzadoEscritorioDto filtro = new FiltroAvanzadoEscritorioDto();
        filtro.setUser("usuario");
        filtro.setIdentificacion("identificacion");
        filtro.setRadicado("radicado");
        filtro.setEntidad("entidad");
        filtro.setCandidato("candidato");
        filtro.setEstadoTramite("estadoTramite");
        filtro.setCargo("cargo");

        EscritorioTramitesResponseDTO dto = new EscritorioTramitesResponseDTO() {
            @Override
            public String getEntidadPublica() {
                return "Entidad Pública";
            }
            @Override
            public Long getIdTipoEstadoTramitePosesion() {
                return 1L; // Ejemplo de Long
            }
            @Override
            public Long getIdTramitePosesion() {
                return 2L; // Ejemplo de Long
            }
            @Override
            public String getNumeroRadicado() {
                return "Radicado123";
            }
            @Override
            public String getIdEntidad() {
                return "ID123";
            }
            @Override
            public String getEntidad() {
                return "Entidad";
            }
            @Override
            public String getCargo() {
                return "Cargo";
            }
            @Override
            public String getPrimerNombre() {
                return "PrimerNombre";
            }
            @Override
            public String getSegundoNombre() {
                return "SegundoNombre";
            }
            @Override
            public String getPrimerApellido() {
                return "PrimerApellido";
            }
            @Override
            public String getSegundoApellido() {
                return "SegundoApellido";
            }
            @Override
            public String getIdentificacion() {
                return "Identificacion";
            }
            @Override
            public String getEstadoDelTramite() {
                return "Estado";
            }
            @Override
            public Long getDiasTermino() {
                return 10L;
            }
            @Override
            public Long getDiasHabilesSFC() {
                return 5L;
            }
            @Override
            public Long getVencimiento() {
                return 5L;
            }
            @Override
            public String getNuevo() {
                return "1";
            }
        };

        List<EscritorioTramitesResponseDTO> resultadosEsperados = Collections.singletonList(dto);

        Mockito.when(repository.filtroAvanzado(
                filtro.getIdentificacion(),
                filtro.getRadicado(),
                filtro.getEntidad(),
                filtro.getCandidato(),
                filtro.getEstadoTramite(),
                filtro.getCargo(),
                filtro.getIdTipoEstadoTramitePosesion()
        )).thenReturn(resultadosEsperados);

        List<EscritorioTramitesResponseDTO> resultados = service.filtroAvanzado(filtro);
        assertEquals(resultadosEsperados, resultados);
    }
}