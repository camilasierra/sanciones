package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.AsignacionAutomaticaDTO;
import co.siri.posesiones.dtos.CargaAnalistaDTO;
import co.siri.posesiones.entities.Parametros;
import co.siri.posesiones.entities.TramitePosesion;
import co.siri.posesiones.repositories.AnalistaRepository;
import co.siri.posesiones.repositories.AsinacionTramiteAnalistaRepository;
import co.siri.posesiones.repositories.ParametroRepository;
import co.siri.posesiones.repositories.TramitePosesionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AsignacionAutomaticaTest {
    @Mock
    private TramitePosesionRepository tramiteRepository;

    @Mock
    private AnalistaRepository analistaRepository;

    @Mock
    private AsinacionTramiteAnalistaRepository asignacionRepository;

    @Mock
    private ParametroRepository parametroRepository;

    @InjectMocks
    private CoordinadorService asignacionService;


    @Test
    public void testProcesarAsignacionesNoTramiteException() {

        AsignacionAutomaticaDTO asignacionAutomaticaDTO = new AsignacionAutomaticaDTO();
        asignacionAutomaticaDTO.setIdusuario(1L);
        asignacionAutomaticaDTO.setIpcliente("172.19.30.97");

        Mockito.when(tramiteRepository.findByAsignadaFalseOrderByPrioridadDescFechaAsc()).thenReturn(Collections.emptyList());

        // Ejecutar método bajo prueba

        String result = asignacionService.asignacionAutomatica(asignacionAutomaticaDTO);


        assertEquals("Actualmente no hay trámites pendientes de asignación.", result);

    }

    @Test
    public void testProcesarAsignacionesNoAlistas() {

        Integer analistaBase = 25;
        Integer analistaApoyo = 5;
        Integer diferencia = 5;

        AsignacionAutomaticaDTO asignacionAutomaticaDTO = new AsignacionAutomaticaDTO();
        asignacionAutomaticaDTO.setIdusuario(1L);
        asignacionAutomaticaDTO.setIpcliente("172.19.30.97");

        TramitePosesion tramitePosesion = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(1L);
        tramitePosesion.setIdPersona(1L);

        TramitePosesion tramitePersona2 = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(2L);
        tramitePosesion.setIdPersona(1L);

        TramitePosesion tramitePersona3 = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(3L);
        tramitePosesion.setIdPersona(2L);

        // Preparación de datos de prueba
        List<TramitePosesion> tramites = Arrays.asList(
                tramitePosesion, tramitePersona2, tramitePersona3);

        Mockito.when(tramiteRepository.findByAsignadaFalseOrderByPrioridadDescFechaAsc()).thenReturn(tramites);
        Mockito.when(analistaRepository.findCargaAnalistas(anyString(), anyInt())).thenReturn(Collections.emptyList());

        // Ejecutar método bajo prueba
        String result = asignacionService.asignacionAutomatica(asignacionAutomaticaDTO);

        assertEquals("No hay analistas disponibles en este momento.", result);
    }

    @Test
    public void testProcesarAsignacionesAnalistaBaseApoyo() {

        BigDecimal analistaBase = new BigDecimal(25);
        BigDecimal analistaApoyo = new BigDecimal(5);
        BigDecimal diferencia = new BigDecimal(5);

        AsignacionAutomaticaDTO asignacionAutomaticaDTO = new AsignacionAutomaticaDTO();
        asignacionAutomaticaDTO.setIdusuario(1L);
        asignacionAutomaticaDTO.setIpcliente("172.19.30.97");

        TramitePosesion tramitePosesion = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(1L);
        tramitePosesion.setIdPersona(1L);

        TramitePosesion tramitePersona2 = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(2L);
        tramitePosesion.setIdPersona(1L);

        TramitePosesion tramitePersona3 = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(3L);
        tramitePosesion.setIdPersona(2L);

        //analistas
        CargaAnalistaDTO cargaAnalistaDTO = new CargaAnalistaDTO(
                1,
                1L,
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(2).subtract(analistaBase));

        CargaAnalistaDTO cargaAnalistaDTO2 = new CargaAnalistaDTO(
                2,
                2L,
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(3).subtract(analistaBase));


        List<CargaAnalistaDTO> listaCargaAnalistas = Arrays.asList(
                cargaAnalistaDTO, cargaAnalistaDTO2
        );

        List<Object[]> listaObjetos = new ArrayList<>();

        Object[] objetos = {cargaAnalistaDTO.getId(), cargaAnalistaDTO.getIdentificacion(),
                cargaAnalistaDTO.getCargaTotal(), cargaAnalistaDTO.getDisponibilidad()};

        Object[] objetos1 = {cargaAnalistaDTO2.getId(), cargaAnalistaDTO2.getIdentificacion(),
                cargaAnalistaDTO2.getCargaTotal(), cargaAnalistaDTO2.getDisponibilidad()};

        listaObjetos.add(objetos1);


        Parametros parametros = new Parametros();
        parametros.setValor("5");

        List<Parametros> listaParam = Arrays.asList(
                parametros
        );


        // Preparación de datos de prueba
        List<TramitePosesion> tramites = Arrays.asList(
                tramitePosesion, tramitePersona2, tramitePersona3);

        Mockito.when(tramiteRepository.findByAsignadaFalseOrderByPrioridadDescFechaAsc()).thenReturn(tramites);
        Mockito.when(analistaRepository.findCargaAnalistas(anyString(), anyInt())).thenReturn(listaObjetos);
        Mockito.when(parametroRepository.findParametrosByNames(anyList())).thenReturn(listaParam);

        // Ejecutar método bajo prueba
        String asignacion = asignacionService.asignacionAutomatica(asignacionAutomaticaDTO);

        Assert.notNull(asignacion, "Total de trámites asignados: 0\n" +
                "Total de trámites no asignados: 0\n" +
                "Resultados de Asignación");
    }

    @Test
    public void testProcesarAsignacionesNoAnalistas() {

        BigDecimal analistaBase = new BigDecimal(25);
        BigDecimal analistaApoyo = new BigDecimal(5);
        BigDecimal diferencia = new BigDecimal(5);

        Parametros parametros = new Parametros();
        parametros.setValor(String.valueOf(diferencia));

        AsignacionAutomaticaDTO asignacionAutomaticaDTO = new AsignacionAutomaticaDTO();
        asignacionAutomaticaDTO.setIdusuario(1L);
        asignacionAutomaticaDTO.setIpcliente("172.19.30.97");

        TramitePosesion tramitePosesion = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(1L);
        tramitePosesion.setIdPersona(1L);

        TramitePosesion tramitePersona2 = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(2L);
        tramitePosesion.setIdPersona(1L);

        TramitePosesion tramitePersona3 = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(3L);
        tramitePosesion.setIdPersona(2L);

        TramitePosesion tramitePersona4 = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(3L);
        tramitePosesion.setIdPersona(3L);

        //Analistas de Base
        CargaAnalistaDTO cargaAnalistaDTO = new CargaAnalistaDTO(
                1,
                1L,
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(2).subtract(analistaBase));

        CargaAnalistaDTO cargaAnalistaDTO2 = new CargaAnalistaDTO(
                2,
                2L,
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(3).subtract(analistaBase));

        CargaAnalistaDTO cargaAnalistaDTO3 = new CargaAnalistaDTO(
                3,
                4L,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(5).subtract(analistaBase));

        //Analistas de apoyo
        CargaAnalistaDTO cargaAnalistaApoyoDTO = new CargaAnalistaDTO(
                10,
                10L,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(5).subtract(analistaApoyo));

        CargaAnalistaDTO cargaAnalistaApoyoDTO2 = new CargaAnalistaDTO(
                11,
                12L,
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(3).subtract(analistaApoyo));

        CargaAnalistaDTO cargaAnalistaApoyoDTO3 = new CargaAnalistaDTO(
                12,
                14L,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(5).subtract(analistaApoyo));

        // Preparación de datos de prueba
        List<TramitePosesion> tramites = Arrays.asList(
                tramitePosesion, tramitePersona2, tramitePersona3, tramitePersona4);

        List<CargaAnalistaDTO> listaCargaAnalistas = Arrays.asList(
                cargaAnalistaDTO, cargaAnalistaDTO2, cargaAnalistaDTO3
        );

        List<CargaAnalistaDTO> listaCargaAnalistasApoyo = Arrays.asList(
                cargaAnalistaApoyoDTO, cargaAnalistaApoyoDTO2, cargaAnalistaApoyoDTO3
        );

        //Parametros
        List<Parametros> listParametros = Arrays.asList(
                parametros);


        List<Object[]> listaObjetosBase = new ArrayList<>();

        Object[] objetos = {cargaAnalistaDTO.getId(), cargaAnalistaDTO.getIdentificacion(),
                cargaAnalistaDTO.getCargaTotal(), cargaAnalistaDTO.getDisponibilidad()};

        Object[] objetos1 = {cargaAnalistaDTO2.getId(), cargaAnalistaDTO2.getIdentificacion(),
                cargaAnalistaDTO2.getCargaTotal(), cargaAnalistaDTO2.getDisponibilidad()};

        Object[] objetos2 = {cargaAnalistaDTO3.getId(), cargaAnalistaDTO3.getIdentificacion(),
                cargaAnalistaDTO3.getCargaTotal(), cargaAnalistaDTO3.getDisponibilidad()};

        listaObjetosBase.add(objetos);
        listaObjetosBase.add(objetos1);
        listaObjetosBase.add(objetos2);

        List<Object[]> listaObjetosApoyo = new ArrayList<>();

        Object[] objetoApoyo = {cargaAnalistaApoyoDTO.getId(), cargaAnalistaApoyoDTO.getIdentificacion(),
                cargaAnalistaApoyoDTO.getCargaTotal(), cargaAnalistaApoyoDTO.getDisponibilidad()};

        Object[] objetosApoyo1 = {cargaAnalistaApoyoDTO2.getId(), cargaAnalistaApoyoDTO2.getIdentificacion(),
                cargaAnalistaApoyoDTO2.getCargaTotal(), cargaAnalistaApoyoDTO2.getDisponibilidad()};

        Object[] objetosApoyo2 = {cargaAnalistaApoyoDTO3.getId(), cargaAnalistaApoyoDTO3.getIdentificacion(),
                cargaAnalistaApoyoDTO3.getCargaTotal(), cargaAnalistaApoyoDTO3.getDisponibilidad()};


        listaObjetosApoyo.add(objetoApoyo);
        listaObjetosApoyo.add(objetosApoyo1);
        listaObjetosApoyo.add(objetosApoyo2);

        Mockito.when(tramiteRepository.findByAsignadaFalseOrderByPrioridadDescFechaAsc()).thenReturn(tramites);
        Mockito.when(analistaRepository.findCargaAnalistas(anyString(), anyInt())).thenReturn(listaObjetosBase);
        Mockito.when(analistaRepository.findCargaAnalistas(anyString(), anyInt())).thenReturn(listaObjetosApoyo);
        Mockito.when(tramiteRepository.findTramiteParaTransferir(anyInt())).thenReturn(Collections.emptyList());
        Mockito.when(parametroRepository.findParametrosByNames(anyList())).thenReturn(listParametros);

        // Ejecutar método bajo prueba
        String asignacion = asignacionService.asignacionAutomatica(asignacionAutomaticaDTO);

        Assert.notNull(asignacion, "Total de trámites asignados: 0\n" +
                "Total de trámites no asignados: 0\n" +
                "Resultados de Asignación");

    }

    @Test
    public void testProcesarAsignacionesNoAsignado() {

        BigDecimal analistaBase = new BigDecimal(25);
        BigDecimal analistaApoyo = new BigDecimal(5);
        BigDecimal diferencia = new BigDecimal(5);

        Parametros parametros = new Parametros();
        parametros.setValor(String.valueOf(diferencia));

        AsignacionAutomaticaDTO asignacionAutomaticaDTO = new AsignacionAutomaticaDTO();
        asignacionAutomaticaDTO.setIdusuario(1L);
        asignacionAutomaticaDTO.setIpcliente("172.19.30.97");

        TramitePosesion tramitePosesion = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(1L);
        tramitePosesion.setIdPersona(1L);

        TramitePosesion tramitePersona2 = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(2L);
        tramitePosesion.setIdPersona(1L);

        TramitePosesion tramitePersona3 = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(3L);
        tramitePosesion.setIdPersona(2L);

        TramitePosesion tramitePersona4 = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(3L);
        tramitePosesion.setIdPersona(3L);

        //Analistas de Base
        CargaAnalistaDTO cargaAnalistaDTO = new CargaAnalistaDTO(
                1,
                1L,
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(2).subtract(analistaBase));

        CargaAnalistaDTO cargaAnalistaDTO2 = new CargaAnalistaDTO(
                2,
                2L,
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(3).subtract(analistaBase));

        CargaAnalistaDTO cargaAnalistaDTO3 = new CargaAnalistaDTO(
                3,
                4L,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(5).subtract(analistaBase));

        //Analistas de apoyo
        CargaAnalistaDTO cargaAnalistaApoyoDTO = new CargaAnalistaDTO(
                10,
                10L,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(5).subtract(analistaApoyo));

        CargaAnalistaDTO cargaAnalistaApoyoDTO2 = new CargaAnalistaDTO(
                11,
                12L,
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(3).subtract(analistaApoyo));

        CargaAnalistaDTO cargaAnalistaApoyoDTO3 = new CargaAnalistaDTO(
                12,
                14L,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(5).subtract(analistaApoyo));

        // Preparación de datos de prueba
        List<TramitePosesion> tramites = Arrays.asList(
                tramitePosesion, tramitePersona2, tramitePersona3, tramitePersona4);

        List<CargaAnalistaDTO> listaCargaAnalistas = Arrays.asList(
                cargaAnalistaDTO, cargaAnalistaDTO2, cargaAnalistaDTO3
        );

        List<CargaAnalistaDTO> listaCargaAnalistasApoyo = Arrays.asList(
                cargaAnalistaApoyoDTO, cargaAnalistaApoyoDTO2, cargaAnalistaApoyoDTO3
        );

        //Parametros
        List<Parametros> listParametros = Arrays.asList(
                parametros);

        List<Object[]> listaObjetosBase = new ArrayList<>();

        Object[] objetos = {cargaAnalistaDTO.getId(), cargaAnalistaDTO.getIdentificacion(),
                cargaAnalistaDTO.getCargaTotal(), cargaAnalistaDTO.getDisponibilidad()};

        Object[] objetos1 = {cargaAnalistaDTO2.getId(), cargaAnalistaDTO2.getIdentificacion(),
                cargaAnalistaDTO2.getCargaTotal(), cargaAnalistaDTO2.getDisponibilidad()};

        Object[] objetos2 = {cargaAnalistaDTO3.getId(), cargaAnalistaDTO3.getIdentificacion(),
                cargaAnalistaDTO3.getCargaTotal(), cargaAnalistaDTO3.getDisponibilidad()};

        listaObjetosBase.add(objetos);
        listaObjetosBase.add(objetos1);
        listaObjetosBase.add(objetos2);

        List<Object[]> listaObjetosApoyo = new ArrayList<>();

        Object[] objetoApoyo = {cargaAnalistaApoyoDTO.getId(), cargaAnalistaApoyoDTO.getIdentificacion(),
                cargaAnalistaApoyoDTO.getCargaTotal(), cargaAnalistaApoyoDTO.getDisponibilidad()};

        Object[] objetosApoyo1 = {cargaAnalistaApoyoDTO2.getId(), cargaAnalistaApoyoDTO2.getIdentificacion(),
                cargaAnalistaApoyoDTO2.getCargaTotal(), cargaAnalistaApoyoDTO2.getDisponibilidad()};

        Object[] objetosApoyo2 = {cargaAnalistaApoyoDTO3.getId(), cargaAnalistaApoyoDTO3.getIdentificacion(),
                cargaAnalistaApoyoDTO3.getCargaTotal(), cargaAnalistaApoyoDTO3.getDisponibilidad()};

        listaObjetosApoyo.add(objetoApoyo);
        listaObjetosApoyo.add(objetosApoyo1);
        listaObjetosApoyo.add(objetosApoyo2);

        Mockito.when(tramiteRepository.findByAsignadaFalseOrderByPrioridadDescFechaAsc()).thenReturn(tramites);
        Mockito.when(analistaRepository.findCargaAnalistas(anyString(), anyInt())).thenReturn(listaObjetosBase);
        Mockito.when(analistaRepository.findCargaAnalistas(anyString(), anyInt())).thenReturn(listaObjetosApoyo);
        Mockito.when(tramiteRepository.findTramiteParaTransferir(anyInt())).thenReturn(Collections.emptyList());
        Mockito.when(parametroRepository.findParametrosByNames(anyList())).thenReturn(listParametros);

        // Ejecutar método bajo prueba
        String asignacion = asignacionService.asignacionAutomatica(asignacionAutomaticaDTO);

        Assert.notNull(asignacion, "Total de trámites asignados: 0 \n" +
                "Total de trámites no asignados: 0 \n" +
                "Resultados de Asignación");

    }

    @Test
    public void testProcesarAsignaciones() {

        BigDecimal analistaBase = new BigDecimal(25);
        BigDecimal analistaApoyo = new BigDecimal(5);
        BigDecimal diferencia = new BigDecimal(5);

        Parametros parametros = new Parametros();
        parametros.setValor(String.valueOf(diferencia));

        AsignacionAutomaticaDTO asignacionAutomaticaDTO = new AsignacionAutomaticaDTO();
        asignacionAutomaticaDTO.setIdusuario(1L);
        asignacionAutomaticaDTO.setIpcliente("172.19.30.97");

        TramitePosesion tramitePosesion = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(1L);
        tramitePosesion.setIdPersona(1L);

        TramitePosesion tramitePersona2 = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(2L);
        tramitePosesion.setIdPersona(1L);

        TramitePosesion tramitePersona3 = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(3L);
        tramitePosesion.setIdPersona(2L);

        TramitePosesion tramitePersona4 = new TramitePosesion();
        tramitePosesion.setIdTipoEstadoTramitePosesion(1L);
        tramitePosesion.setIdTramitePosesion(3L);
        tramitePosesion.setIdPersona(3L);

        //Analistas de Base
        CargaAnalistaDTO cargaAnalistaDTO = new CargaAnalistaDTO(
                1,
                1L,
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(2).subtract(analistaBase));

        CargaAnalistaDTO cargaAnalistaDTO2 = new CargaAnalistaDTO(
                2,
                2L,
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(3).subtract(analistaBase));

        CargaAnalistaDTO cargaAnalistaDTO3 = new CargaAnalistaDTO(
                3,
                4L,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(5).subtract(analistaBase));

        //Analistas de apoyo
        CargaAnalistaDTO cargaAnalistaApoyoDTO = new CargaAnalistaDTO(
                10,
                10L,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(5).subtract(analistaApoyo));

        CargaAnalistaDTO cargaAnalistaApoyoDTO2 = new CargaAnalistaDTO(
                11,
                12L,
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(3).subtract(analistaApoyo));

        CargaAnalistaDTO cargaAnalistaApoyoDTO3 = new CargaAnalistaDTO(
                12,
                14L,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(5).subtract(analistaApoyo));

        // Preparación de datos de prueba
        List<TramitePosesion> tramites = Arrays.asList(
                tramitePosesion, tramitePersona2, tramitePersona3, tramitePersona4);

        List<CargaAnalistaDTO> listaCargaAnalistas = Arrays.asList(
                cargaAnalistaDTO, cargaAnalistaDTO2, cargaAnalistaDTO3
        );

        List<CargaAnalistaDTO> listaCargaAnalistasApoyo = Arrays.asList(
                cargaAnalistaApoyoDTO, cargaAnalistaApoyoDTO2, cargaAnalistaApoyoDTO3
        );

        //Parametros
        List<Parametros> listParametros = Arrays.asList(
                parametros);


        List<Object[]> listaObjetosBase = new ArrayList<>();

        Object[] objetos = {cargaAnalistaDTO.getId(), cargaAnalistaDTO.getIdentificacion(),
                cargaAnalistaDTO.getCargaTotal(), cargaAnalistaDTO.getDisponibilidad()};

        Object[] objetos1 = {cargaAnalistaDTO2.getId(), cargaAnalistaDTO2.getIdentificacion(),
                cargaAnalistaDTO2.getCargaTotal(), cargaAnalistaDTO2.getDisponibilidad()};

        Object[] objetos2 = {cargaAnalistaDTO3.getId(), cargaAnalistaDTO3.getIdentificacion(),
                cargaAnalistaDTO3.getCargaTotal(), cargaAnalistaDTO3.getDisponibilidad()};

        listaObjetosBase.add(objetos);
        listaObjetosBase.add(objetos1);
        listaObjetosBase.add(objetos2);

        List<Object[]> listaObjetosApoyo = new ArrayList<>();

        Object[] objetoApoyo = {cargaAnalistaApoyoDTO.getId(), cargaAnalistaApoyoDTO.getIdentificacion(),
                cargaAnalistaApoyoDTO.getCargaTotal(), cargaAnalistaApoyoDTO.getDisponibilidad()};

        Object[] objetosApoyo1 = {cargaAnalistaApoyoDTO2.getId(), cargaAnalistaApoyoDTO2.getIdentificacion(),
                cargaAnalistaApoyoDTO2.getCargaTotal(), cargaAnalistaApoyoDTO2.getDisponibilidad()};

        Object[] objetosApoyo2 = {cargaAnalistaApoyoDTO3.getId(), cargaAnalistaApoyoDTO3.getIdentificacion(),
                cargaAnalistaApoyoDTO3.getCargaTotal(), cargaAnalistaApoyoDTO3.getDisponibilidad()};


        listaObjetosApoyo.add(objetoApoyo);
        listaObjetosApoyo.add(objetosApoyo1);
        listaObjetosApoyo.add(objetosApoyo2);

        Mockito.when(tramiteRepository.findByAsignadaFalseOrderByPrioridadDescFechaAsc()).thenReturn(tramites);
        Mockito.when(analistaRepository.findCargaAnalistas(anyString(), anyInt())).thenReturn(listaObjetosBase);
        Mockito.when(analistaRepository.findCargaAnalistas(anyString(), anyInt())).thenReturn(listaObjetosApoyo);
        Mockito.when(tramiteRepository.findTramiteParaTransferir(anyInt())).thenReturn(Collections.emptyList());
        Mockito.when(parametroRepository.findParametrosByNames(anyList())).thenReturn(listParametros);

        // Ejecutar método bajo prueba
        String asignacion = asignacionService.asignacionAutomatica(asignacionAutomaticaDTO);

        Assert.notNull(asignacion, "Total de trámites asignados: 0 \n" +
                "Total de trámites no asignados: 0 \n" +
                "Resultados de Asignación");

    }

}
