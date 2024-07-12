package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.*;

import co.siri.posesiones.entities.AsignacionTramiteAnalistas;
import co.siri.posesiones.entities.Persona;
import co.siri.posesiones.entities.TramitePosesion;
import co.siri.posesiones.repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.BigDecimalConversion;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CoordinadorServiceTest {
    @Mock
    private CoordinadorRepository repository;

    @Mock
    private TramitePosesionRepository tramitePosesionRepository;
    @Mock
    private AnalistaRepository analistaRepository;

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private AsinacionTramiteAnalistaRepository asignacionAnalistaRepository;
    @InjectMocks
    private CoordinadorService service;

    @Test
    void tramiteAsignados() {
        FiltroAvanzadoEscritorioDto coordinador = new FiltroAvanzadoEscritorioDto();
        coordinador.setRadicado("p123");
        coordinador.setEntidad("Bancolombia");
        coordinador.setCandidato("Andres");
        coordinador.setEstadoTramite("Aprovado");
        coordinador.setCargo("CargoPrueba");

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

        Mockito.when(repository.asignados(
                coordinador.getIdentificacion(),
                coordinador.getRadicado(),
                coordinador.getEntidad(),
                coordinador.getCandidato(),
                coordinador.getEstadoTramite(),
                coordinador.getCargo()
        )).thenReturn(resultadosEsperados);

        List<EscritorioTramitesResponseDTO> resultados = service.asignados(coordinador);
        assertEquals(resultadosEsperados, resultados);
    }

    @Test
    void tramiteNoAsignados() {
        FiltroAvanzadoEscritorioDto coordinador = new FiltroAvanzadoEscritorioDto();
        coordinador.setRadicado("p123");
        coordinador.setEntidad("Bancolombia");
        coordinador.setCandidato("Andres");
        coordinador.setEstadoTramite("Aprovado");
        coordinador.setCargo("CargoPrueba");

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

        Mockito.when(repository.noAsignados(
                coordinador.getIdentificacion(),
                coordinador.getRadicado(),
                coordinador.getEntidad(),
                coordinador.getCandidato(),
                coordinador.getEstadoTramite(),
                coordinador.getCargo()
        )).thenReturn(resultadosEsperados);

        List<EscritorioTramitesResponseDTO> resultados = service.noAsignados(coordinador);
        assertEquals(resultadosEsperados, resultados);
    }

    @Test
    void testInfoTramiteAnalistasWithNullTramitePosesion() {
        Mockito.when(tramitePosesionRepository.getReferenceById(1L)).thenReturn(null);
        InfoTramiteListAnalistasDTO infoTramiteListAnalistasDTO = service.infoTramiteAnalitas(1L);
        assertNull(infoTramiteListAnalistasDTO);
    }

    @Test
    void testInfoTramiteAnalistasTramitePosesion() {
        TramitePosesion tramitePosesion = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(1L);
        tramitePosesion.setIdPersona(1L);

        List<Object[]> listaAnalistasTramiteCarga = new ArrayList<>();
        listaAnalistasTramiteCarga.add(new Object[]{(String) "1", (String) "Analista 1", (Number) 5, BigDecimal.valueOf(3.5)});
        listaAnalistasTramiteCarga.add(new Object[]{(String) "2", (String) "Analista 2", (Number) 10, BigDecimal.valueOf(7.2)});
        listaAnalistasTramiteCarga.add(new Object[]{(String) "3", (String) "Analista 3", (Number) 3, BigDecimal.valueOf(1.8)});
        listaAnalistasTramiteCarga.add(new Object[]{(String) "4", (String) "Analista 4", (Number) 8, BigDecimal.valueOf(6.0)});

        Persona persona = new Persona();
        persona.setIdPersona(1L);
        persona.setPrimerNombre("Ana");
        persona.setSegundoNombre("Lisa");
        persona.setPrimerApellido("La");


        Mockito.when(tramitePosesionRepository.getReferenceById(1L)).thenReturn(tramitePosesion);
        Mockito.when(analistaRepository.findAnalistasTramitesCarga()).thenReturn(listaAnalistasTramiteCarga);
        Mockito.when(personaRepository.getReferenceById(1L)).thenReturn(persona);


        InfoTramiteListAnalistasDTO infoTramiteListAnalistasDTO = service.infoTramiteAnalitas(1L);

        assertNotNull(infoTramiteListAnalistasDTO);
        assertEquals(infoTramiteListAnalistasDTO.getListaAnalistasCarga().size(), listaAnalistasTramiteCarga.size());
    }

    @Test
    void testAsignacionManual() {
        AsignacionManualDTO asignacionManualDTO = new AsignacionManualDTO();
        asignacionManualDTO.setIdAnalistaPosesion(1L);
        asignacionManualDTO.setIdTramitePosesion(2L);
        asignacionManualDTO.setIpCliente("192.168.1.1");
        asignacionManualDTO.setIdUsuario(3L);

        service.asignacionManual(asignacionManualDTO);


        verify(asignacionAnalistaRepository, times(1)).save(any(AsignacionTramiteAnalistas.class));
    }

    @Test
    void testObtenerNombrePersona() {
        Persona persona = new Persona();
        persona.setPrimerNombre("Juan");
        persona.setSegundoNombre("Carlos");
        persona.setPrimerApellido("Pérez");
        persona.setSegundoApellido("González");
        when(personaRepository.getReferenceById(1L)).thenReturn(persona);


        String nombreCompleto = service.obtenerNombrePersona(1L);


        assertEquals("Juan Carlos Pérez González", nombreCompleto);
    }
}