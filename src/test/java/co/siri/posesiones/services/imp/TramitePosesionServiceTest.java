package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.*;
import co.siri.posesiones.entities.*;
import co.siri.posesiones.repositories.*;
import co.siri.posesiones.dtos.TramitePosesionDto;
import co.siri.posesiones.entities.TramitePosesion;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.mappers.ArchivoTramitePosesionMapper;
import co.siri.posesiones.utilidades.Constantes;

import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@SpringBootTest
public class TramitePosesionServiceTest {
	
    @InjectMocks
    private TramitePosesionService tramitePosesionService;
    
    @Mock
    private TipoEstadoTramitePosesionRepository tipoEstadoTramitePosesionRepository;

    @Mock
    private TramitePosesionRepository tramitePosesionRepository;

    @Mock
    private AntecedenteTramitePosesionRepository antecedenteTramitePosesionRepository;
    
    @Mock
    private ArchivoTramitePosesionRepository archivoTramitePosesionRepository;

    @Mock
    private ArchivoTramitePosesionMapper archivoTramitePosesionMapper;
    
    @Mock
    private  TipoConvenioAntecedenteRepository tipoConvenioAntecedenteRepository;

    @Mock
    private TipoActividadRepresentanteRepository tipoActividadRepresentanteRepository;

    @Mock
    private TipoClaseRepresentanteRepository tipoClaseRepresentanteRepository;

    @Mock
    private TipoCalidadJuntaDirectivaRepository tipoCalidadJuntaDirectivaRepository;

    private TramitePosesion tramitePosesion;
    private TipoClaseRepresentante tipoClaseRepresentante;
    private TipoActividadRepresentante tipoActividadRepresentante;
    private TipoCalidadJuntaDirectiva tipoCalidadJuntaDirectiva;
    private InfoAdicionalDefensorConsumidor infoAdicionalDefensorConsumidor;

    @BeforeEach
    public void setup() {
        tramitePosesion = new TramitePosesion();
        tramitePosesion.setIdTipoClaseRepresentante(1L);
        tramitePosesion.setIdTipoActividadRepresentante(1L);
        tramitePosesion.setIdTipoCalidadJuntaDirectiva(1L);
        tramitePosesion.setRenglonJunta(5L);

        tipoClaseRepresentante = new TipoClaseRepresentante();
        tipoClaseRepresentante.setNombre("Clase Representante");

        tipoActividadRepresentante = new TipoActividadRepresentante();
        tipoActividadRepresentante.setNombre("Actividad Representante");

        tipoCalidadJuntaDirectiva = new TipoCalidadJuntaDirectiva();
        tipoCalidadJuntaDirectiva.setNombre("Calidad Junta Directiva");

        infoAdicionalDefensorConsumidor = new InfoAdicionalDefensorConsumidor(
                "Facultad Conciliador",
                "Centro de Conciliación",
                "Pais",
                "Ciudad",
                "Direccion",
                "Telefono",
                "Numero de Fax",
                "Numero de Celular",
                "Correo Electronico Principal",
                "Correo Electronico Secundario"
        );
    }

    @Test
    public void getTramitesTestByNumeroIdentificacion() {
        //given
        Mockito.when(tramitePosesionRepository.findTramitesByNumeroIdentificacion(any())).thenReturn(getTramitesMock());
        //when
        List<TramitePosesionDto> tramitePosesionDtos = tramitePosesionService.getTramites("1", "Busqueda");
        //expect
        Assertions.assertThat(tramitePosesionDtos.size()).isEqualTo(1);
    }

    @Test
    public void getTramitesTestByRadicado() {
        //given
        Mockito.when(tramitePosesionRepository.findTramitesByRadicado(any())).thenReturn(getTramitesMock());
        //when
        List<TramitePosesionDto> tramitePosesionDtos = tramitePosesionService.getTramites("2", "Busqueda");
        //expect
        Assertions.assertThat(tramitePosesionDtos.size()).isEqualTo(1);
    }

    @Test
    public void getTramitesTestByCandidato() {
        //given
        Mockito.when(tramitePosesionRepository.findTramitesByCandidato(any())).thenReturn(getTramitesMock());
        //when
        List<TramitePosesionDto> tramitePosesionDtos = tramitePosesionService.getTramites("3", "Busqueda");
        //expect
        Assertions.assertThat(tramitePosesionDtos.size()).isEqualTo(1);
    }

    @Test
    public void getTramitesTestByEntidad() {
        //given
        Mockito.when(tramitePosesionRepository.findTramitesByEntidad(any())).thenReturn(getTramitesMock());
        //when
        List<TramitePosesionDto> tramitePosesionDtos = tramitePosesionService.getTramites("4", "Busqueda");
        //expect
        Assertions.assertThat(tramitePosesionDtos.size()).isEqualTo(1);
    }

    @Test
    public void getTramitesTestDefault() {
        //given
        //when
        List<TramitePosesionDto> tramitePosesionDtos = tramitePosesionService.getTramites("5", "Busqueda");
        //expect
        Assertions.assertThat(tramitePosesionDtos).isNull();
    }
    


    private List<TramitePosesionDto> getTramitesMock(){
        List<TramitePosesionDto> list = new ArrayList<>();
        TramitePosesionDto tramitePosesionDto = TramitePosesionDto.builder()
                .primerNombre("nombre")
                .segundoNombre("nombre2")
                .primerApellido("apellido")
                .segundoApellido("apellido2")
                .razonSocial("razonSocial")
                .tipoEntidad(1L)
                .codigoEntidad(1L)
                .siglaEntidad("sigla")
                .tipoDocumento("CC")
                .numeroDocumento("123456789")
                .numeroRadicacion("12345678900000")
                .fechaPrimeraTransmision(new Date())
                .fechaUltimaTransmision(new Date())
                .build();
        list.add(tramitePosesionDto);
        return list;
    }
    
    
    
    @Test
    public void testGetArchivoTramite_PositiveCase() {
        // Mock data
        Long idTramitePosesion = 1L;
        byte[] archivoBytes = "Archivo de prueba".getBytes();
        ArchivoTramitePosesion archivoTramitePosesion = new ArchivoTramitePosesion();
        archivoTramitePosesion.setArchivo(archivoBytes);

        // Mock repository response
        List<AntecedenteTramitePosesion> antecedenteTramitePosesionList = new ArrayList<>();
        antecedenteTramitePosesionList.add(new AntecedenteTramitePosesion());
        antecedenteTramitePosesionList.get(0).setIdArchivoTramitePosesion(archivoTramitePosesion);
        Mockito.when(antecedenteTramitePosesionRepository.obtenerAntecedentePorTramite(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(antecedenteTramitePosesionList);

        // Mock mapper response
        ArchivoTramiteDto archivoTramiteDto = new ArchivoTramiteDto();
        archivoTramiteDto.setArchivoBase(Base64.getEncoder().encodeToString(archivoBytes));
        Mockito.when(archivoTramitePosesionMapper.toDto(archivoTramitePosesion)).thenReturn(archivoTramiteDto);

        // Test service method
        ArchivoTramiteDto result = tramitePosesionService.getArchivoTramite(idTramitePosesion);

        // Assertions
        Assertions.assertThat("Archivo de prueba").isEqualTo(new String(Base64.getDecoder().decode(result.getArchivoBase())));
    
    }
    
    @Test
    public void testGuardarArchivoTramite_CrearNuevoAntecedente() {
        // Mock data
        ArchivoTramiteDto archivoTramiteDto = new ArchivoTramiteDto();
        archivoTramiteDto.setIdTramite(1L);
        archivoTramiteDto.setArchivoBase(Base64.getEncoder().encodeToString("Archivo de prueba".getBytes()));
        archivoTramiteDto.setIdUsuario(1L);
        archivoTramiteDto.setIpCliente("192.168.1.1");

        // Mock repository response
        TipoConvenioAntecedente tipoConvenioAntecedente = new TipoConvenioAntecedente();
        Mockito.when(tipoConvenioAntecedenteRepository.findById(any(Long.class))).thenReturn(Optional.of(tipoConvenioAntecedente));
        
        TramitePosesion tramitePosesion = new TramitePosesion();
        Mockito.when(tramitePosesionRepository.findById(any(Long.class))).thenReturn(Optional.of(tramitePosesion));
        
        Mockito.when(antecedenteTramitePosesionRepository.obtenerAntecedentePorTramite(any(Long.class), any(Long.class))).thenReturn(new ArrayList<>());
        Mockito.when(archivoTramitePosesionMapper.toDomain(archivoTramiteDto)).thenReturn(new ArchivoTramitePosesion());
        
        
        // Mock save response
        ArchivoTramitePosesion archivoTramitePosesion = new ArchivoTramitePosesion();
        archivoTramitePosesion.setIdArchivoTramitePosesion(1L);
        Mockito.when(archivoTramitePosesionRepository.save(any(ArchivoTramitePosesion.class)))
                .thenReturn(archivoTramitePosesion);

        // Test service method
        ArchivoTramiteDto result = tramitePosesionService.guardarArchivoTramite(archivoTramiteDto);

        // Assertions
        Assertions.assertThat("Archivo de prueba").isEqualTo(new String(Base64.getDecoder().decode(result.getArchivoBase())));
    }
    
    
    @Test
    public void testGuardarArchivoTramite_ActualizarAntecedenteExistente() {
        // Mock data
        ArchivoTramiteDto archivoTramiteDto = new ArchivoTramiteDto();
        archivoTramiteDto.setIdTramite(1L);
        archivoTramiteDto.setArchivoBase(Base64.getEncoder().encodeToString("Archivo de prueba".getBytes()));
        archivoTramiteDto.setIdUsuario(1L);
        archivoTramiteDto.setIpCliente("192.168.1.1");

        AntecedenteTramitePosesion antecedenteTramitePosesion = new AntecedenteTramitePosesion();
        antecedenteTramitePosesion.setIdArchivoTramitePosesion(new ArchivoTramitePosesion());

        List<AntecedenteTramitePosesion> antecedenteTramitePosesionList = new ArrayList<>();
        antecedenteTramitePosesionList.add(antecedenteTramitePosesion);

        Mockito.when(antecedenteTramitePosesionRepository.obtenerAntecedentePorTramite(any(Long.class), any(Long.class)))
                .thenReturn(antecedenteTramitePosesionList);

        ArchivoTramitePosesion archivoTramitePosesion = new ArchivoTramitePosesion();
        archivoTramitePosesion.setIdArchivoTramitePosesion(1L);
        Mockito.when(archivoTramitePosesionRepository.save(any(ArchivoTramitePosesion.class)))
                .thenReturn(archivoTramitePosesion);

        // Test service method
        ArchivoTramiteDto result = tramitePosesionService.guardarArchivoTramite(archivoTramiteDto);

        // Assertions
        Assertions.assertThat("Archivo de prueba").isEqualTo(new String(Base64.getDecoder().decode(result.getArchivoBase())));
    }

    @Test
    void consultarInformacionServidorPublico() {
        when(tramitePosesionRepository.findById(anyLong())).thenReturn(Optional.of(new TramitePosesion()));
        InfoServidorPublico infoServidorPublico = tramitePosesionService.consultarInformacionServidorPublico(anyLong());
        Assertions.assertThat(infoServidorPublico).isNotNull();
    }

    @Test
    void consultarInformacionServidorPublico_ErrorTramiteNoEncontrado() {
        when(tramitePosesionRepository.findById(1L)).thenReturn(null);
        assertThrows(ExcepcionPersonalizada.class, () -> tramitePosesionService.consultarInformacionServidorPublico(anyLong()));
    }

    @Test
    void testConsultarInformacionOtrosRepresentantes_Exito() {
        when(tramitePosesionRepository.findByIdTramitePosesionAndIdTipoCargoAndIdTipoCalidadCargo(anyLong(), anyLong(), anyLong()))
                .thenReturn(Optional.of(tramitePosesion));
        when(tipoClaseRepresentanteRepository.findById(anyLong()))
                .thenReturn(Optional.of(tipoClaseRepresentante));
        when(tipoActividadRepresentanteRepository.findById(anyLong()))
                .thenReturn(Optional.of(tipoActividadRepresentante));

        InfoOtrosRepresentantes resultado = tramitePosesionService.consultarInformacionOtrosRepresentantes(1L);

        assertEquals("Clase Representante", resultado.getClaseRepresentante());
        assertEquals("Actividad Representante", resultado.getActividadRepresentante());
    }

    @Test
    void testConsultarInformacionOtrosRepresentantes_TramitePosesionNoEncontrado() {
        when(tramitePosesionRepository.findByIdTramitePosesionAndIdTipoCargoAndIdTipoCalidadCargo(anyLong(), anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        InfoOtrosRepresentantes resultado = tramitePosesionService.consultarInformacionOtrosRepresentantes(1L);

        assertEquals(Strings.EMPTY, resultado.getClaseRepresentante());
        assertEquals(Strings.EMPTY, resultado.getActividadRepresentante());
    }

    @Test
    void testConsultarInformacionOtrosRepresentantes_RepresentanteNoEncontrado() {
        when(tramitePosesionRepository.findByIdTramitePosesionAndIdTipoCargoAndIdTipoCalidadCargo(anyLong(), anyLong(), anyLong()))
                .thenReturn(Optional.of(tramitePosesion));
        when(tipoClaseRepresentanteRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        when(tipoActividadRepresentanteRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        InfoOtrosRepresentantes resultado = tramitePosesionService.consultarInformacionOtrosRepresentantes(1L);

        assertEquals(Strings.EMPTY, resultado.getClaseRepresentante());
        assertEquals(Strings.EMPTY, resultado.getActividadRepresentante());
    }

    @Test
    void testConsultarInformacionJuntaDirectiva_Exito() {
        when(tramitePosesionRepository.findByIdTramitePosesionAndIdTipoCargo(anyLong(), anyLong()))
                .thenReturn(Optional.of(tramitePosesion));
        when(tipoCalidadJuntaDirectivaRepository.findById(anyLong()))
                .thenReturn(Optional.of(tipoCalidadJuntaDirectiva));

        InfoJuntaDirectiva resultado = tramitePosesionService.consultarInformacionJuntaDirectiva(1L);

        assertEquals("Calidad Junta Directiva", resultado.getCalidadJuntaDirectiva());
        assertEquals("5", resultado.getRenglonJuntaDirectiva());
    }

    @Test
    void testConsultarInformacionJuntaDirectiva_TramitePosesionNoEncontrado() {
        when(tramitePosesionRepository.findByIdTramitePosesionAndIdTipoCargo(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        InfoJuntaDirectiva resultado = tramitePosesionService.consultarInformacionJuntaDirectiva(1L);

        assertEquals(Strings.EMPTY, resultado.getCalidadJuntaDirectiva());
        assertEquals(Strings.EMPTY, resultado.getRenglonJuntaDirectiva());
    }

    @Test
    void testConsultarInformacionJuntaDirectiva_TipoCalidadJuntaDirectivaNoEncontrado() {
        when(tramitePosesionRepository.findByIdTramitePosesionAndIdTipoCargo(anyLong(), anyLong()))
                .thenReturn(Optional.of(tramitePosesion));
        when(tipoCalidadJuntaDirectivaRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        InfoJuntaDirectiva resultado = tramitePosesionService.consultarInformacionJuntaDirectiva(1L);

        assertEquals(Strings.EMPTY, resultado.getCalidadJuntaDirectiva());
        assertEquals("5", resultado.getRenglonJuntaDirectiva());
    }

    @Test
    void testConsultarInformacionAdicionalDefensorConsumidor_Exito() {
        when(tramitePosesionRepository.findDefensorConsumidor(anyLong()))
                .thenReturn(infoAdicionalDefensorConsumidor);

        InfoAdicionalDefensorConsumidor resultado = tramitePosesionService.consultarInformacionAdicionalDefensorConsumidor(1L);

        assertEquals("Facultad Conciliador", resultado.getFacultadConciliador());
        assertEquals("Centro de Conciliación", resultado.getCentroConciliaciónInscrito());
        assertEquals("Pais", resultado.getPais());
        assertEquals("Ciudad", resultado.getCiudad());
        assertEquals("Direccion", resultado.getDireccion());
        assertEquals("Telefono", resultado.getNumeroTelefono());
        assertEquals("Numero de Fax", resultado.getNumeroFax());
        assertEquals("Numero de Celular", resultado.getNumeroCelular());
        assertEquals("Correo Electronico Principal", resultado.getCorreoElectronicoPrincipal());
        assertEquals("Correo Electronico Secundario", resultado.getCorreoElectronicoSecunadario());
    }

    @Test
    void testConsultarInformacionAdicionalDefensorConsumidor_DefensorNoEncontrado() {
        when(tramitePosesionRepository.findDefensorConsumidor(anyLong()))
                .thenReturn(null);

        InfoAdicionalDefensorConsumidor resultado = tramitePosesionService.consultarInformacionAdicionalDefensorConsumidor(1L);

        assertEquals(Strings.EMPTY, resultado.getFacultadConciliador());
        assertEquals(Strings.EMPTY, resultado.getCentroConciliaciónInscrito());
        assertEquals(Strings.EMPTY, resultado.getPais());
        assertEquals(Strings.EMPTY, resultado.getCiudad());
        assertEquals(Strings.EMPTY, resultado.getDireccion());
        assertEquals(Strings.EMPTY, resultado.getNumeroTelefono());
        assertEquals(Strings.EMPTY, resultado.getNumeroFax());
        assertEquals(Strings.EMPTY, resultado.getNumeroCelular());
        assertEquals(Strings.EMPTY, resultado.getCorreoElectronicoPrincipal());
        assertEquals(Strings.EMPTY, resultado.getCorreoElectronicoSecunadario());
    }

    @Test
    void testConsultarInformacionAdicionalDefensorConsumidor_ExcepcionPersonalizada() {
        when(tramitePosesionRepository.findDefensorConsumidor(anyLong()))
                .thenThrow(new ExcepcionPersonalizada("Error", HttpStatus.BAD_REQUEST));

        ExcepcionPersonalizada exception = assertThrows(
                ExcepcionPersonalizada.class,
                () -> tramitePosesionService.consultarInformacionAdicionalDefensorConsumidor(1L)
        );

        assertEquals("Error", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

}
