package co.siri.posesiones.services.imp.comite_ll;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.*;

import co.siri.posesiones.constant.ConstantesComiteII;
import co.siri.posesiones.dtos.SalidaDto;
import co.siri.posesiones.entities.Parametros;
import co.siri.posesiones.repositories.ComiteRepository;
import co.siri.posesiones.repositories.ParametroRepository;
import co.siri.posesiones.utilidades.Constantes;
import co.siri.posesiones.utilidades.GenerateDocx;
import co.siri.posesiones.utilidades.GeneratePdf;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.siri.posesiones.dtos.comiteII.GenerarOficioDto;
import co.siri.posesiones.dtos.comiteII.OficiosDto;
import co.siri.posesiones.entities.TipoPlantillaEntity;
import co.siri.posesiones.repositories.TipoPlatillaRepository;
import co.siri.posesiones.services.ISolipService;
import org.mockito.Spy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class ComiteOficiosServicesImpTest {

    @Mock
    private TipoPlatillaRepository tipoplantillaRepository;

    @Mock
    private ISolipService solipService;

    @Mock
    private GeneratePdf generatePdf;

    @Mock
    private ComiteRepository comiteRepository;

    @Mock
    private GenerateDocx generateDocx;

    @Mock
    private ParametroRepository parametroRepository;

    @Mock
    private RestTemplate restTemplate;
    @Spy
    @InjectMocks
    private ComiteOficiosServicesImp comiteOficiosServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ConstantesComiteII.PLANTILLAS_DS = new ArrayList<>(Arrays.asList("Plantilla1", "Plantilla2", "Plantilla3", "Plantilla4", "PlantillaAutorizado", "Plantilla5", "PlantillaSuspendido", "Plantilla7", "PlantillaDesistimientoExpreso", "PlantillaDesistimientoTacito"));

        ConstantesComiteII.LISTADOS_CARGO = new ArrayList<>(Arrays.asList("Cargo1", "Cargo2", "Cargo3"));

        ConstantesComiteII.ESTADOS_TS = new ArrayList<>(Arrays.asList("Autorizado", "Negado", "Suspendido", "Desistimiento tácito", "Desistimiento expreso"));
    }

    @Test
    void testValidateFormat_docx() throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", "value");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write("docx content".getBytes());

        when(generateDocx.generateDocx(any(Map.class), any(Double.class), any(Double.class), any(Double.class), any(Double.class), any(String.class)))
                .thenReturn(byteArrayOutputStream);

        String expected = org.apache.commons.codec.binary.Base64.encodeBase64String(byteArrayOutputStream.toByteArray());

        assertEquals(expected, "ZG9jeCBjb250ZW50");
    }

    @Test
    void testGenerateZipWithDocuments() throws Exception {
        List<String> base64Documents = new ArrayList<>();
        base64Documents.add(org.apache.commons.codec.binary.Base64.encodeBase64String("doc1 content".getBytes()));
        base64Documents.add(org.apache.commons.codec.binary.Base64.encodeBase64String("doc2 content".getBytes()));

        byte[] zipBytes = comiteOficiosServices.generateZipWithDocuments(base64Documents);

        assertNotNull(zipBytes);
        assertTrue(zipBytes.length > 0);
    }

    @Test
    void testInitializeParameters() throws Exception {
        OficiosDto oficio = mock(OficiosDto.class);
        when(oficio.getNombre()).thenReturn("Nombre");
        when(oficio.getNombreEntidad()).thenReturn("Entidad");
        when(oficio.getCiudad()).thenReturn("Ciudad");
        when(oficio.getDireccion()).thenReturn("Dirección");
        when(oficio.getNumeroRadicacion()).thenReturn("123");
        when(oficio.getFechaComite()).thenReturn(LocalDate.of(2023, 1, 1));
        when(oficio.getNombreCargo()).thenReturn("Cargo"); // Ajusta según LISTADOS_CARGO
        when(oficio.getPresidente()).thenReturn("Presidente");
        when(oficio.getIdentificacion()).thenReturn("456");
        when(oficio.getAnotacion()).thenReturn("Anotación");

        // Configuración del DTO GenerarOficioDto
        GenerarOficioDto oficioDto = new GenerarOficioDto();
        oficioDto.setSinAsignacion(1);
        oficioDto.setEstadoTramite("Autorizado"); // Ajusta según los estados esperados

        // Configuración de la entidad TipoPlantillaEntity
        TipoPlantillaEntity plantillaEntity = new TipoPlantillaEntity();
        plantillaEntity.setTexto("Plantilla con valores: {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}");
        plantillaEntity.setConfirm(true); // Asegúrate de que `confirm` esté configurado según tu lógica

        // Mock del repositorio y servicios
        TipoPlatillaRepository tipoplantillaRepository = mock(TipoPlatillaRepository.class);
        ISolipService solipService = mock(ISolipService.class);
        when(tipoplantillaRepository.findByNombre(anyString())).thenReturn(Optional.of(plantillaEntity));
        when(solipService.consultarFirmaDocumento(anyString())).thenReturn("FirmaBase64");

        // Crear instancia real del servicio con los mocks
        ComiteOficiosServicesImp comiteOficiosServices = new ComiteOficiosServicesImp(
                tipoplantillaRepository,
                mock(ComiteRepository.class),
                mock(ParametroRepository.class),
                mock(RestTemplate.class),
                solipService
        );

        // Verifica la plantilla devuelta por obtenerPlantilla usando reflexión
        Method obtenerPlantillaMethod = ComiteOficiosServicesImp.class.getDeclaredMethod("obtenerPlantilla", OficiosDto.class, GenerarOficioDto.class);
        obtenerPlantillaMethod.setAccessible(true);
        String plantillaNombre = (String) obtenerPlantillaMethod.invoke(comiteOficiosServices, oficio, oficioDto);
        assertNotNull(plantillaNombre, "La plantilla devuelta no debe ser nula");

        // Llamada al método privado usando reflexión
        Method method = ComiteOficiosServicesImp.class.getDeclaredMethod("initializeParameters", OficiosDto.class, GenerarOficioDto.class);
        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) method.invoke(comiteOficiosServices, oficio, oficioDto);

        // Verificaciones
        assertNotNull(result);
        assertTrue(result.containsKey("BodyData"));

        // Construir el texto esperado basado en la plantilla
        String expectedBodyData = "Plantilla con valores: Nombre, Entidad, Ciudad, Dirección, 123, 01/01/2023, Cargo, Presidente, 456, Anotación, data:image/png;base64,FirmaBase64";
        assertEquals(expectedBodyData, result.get("BodyData"));

        // Verificación de interacción con solipService
        verify(solipService, times(1)).consultarFirmaDocumento(anyString());
    }

    @Test
    void testGenerateDocument() throws IOException {
        // Preparar datos de prueba
        GenerarOficioDto oficioDto = new GenerarOficioDto();
        oficioDto.setSesionComite(1L);
        oficioDto.setSinAsignacion(1);
        oficioDto.setEstadoTramite("Autorizado");

        OficiosDto oficio = mock(OficiosDto.class);
        when(oficio.getNombre()).thenReturn("Nombre");
        when(oficio.getNombreEntidad()).thenReturn("Entidad");
        when(oficio.getCiudad()).thenReturn("Ciudad");
        when(oficio.getDireccion()).thenReturn("Dirección");
        when(oficio.getNumeroRadicacion()).thenReturn("123");
        when(oficio.getFechaComite()).thenReturn(LocalDate.of(2023, 1, 1));
        when(oficio.getNombreCargo()).thenReturn("Cargo");
        when(oficio.getPresidente()).thenReturn("Presidente");
        when(oficio.getIdentificacion()).thenReturn("456");
        when(oficio.getAnotacion()).thenReturn("Anotación");

        List<OficiosDto> listadoOficios = Collections.singletonList(oficio);

        // Configurar mocks
        ComiteRepository comiteRepository = mock(ComiteRepository.class);
        TipoPlatillaRepository tipoplantillaRepository = mock(TipoPlatillaRepository.class);
        ParametroRepository parametroRepository = mock(ParametroRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        ISolipService solipService = mock(ISolipService.class);
        GeneratePdf generatePdf = mock(GeneratePdf.class);

        when(comiteRepository.obtenerOficiosPdforWord(anyList(), anyLong(), anyInt())).thenReturn(listadoOficios);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write("pdf content".getBytes());

        when(generatePdf.generatePdf(any(Map.class), any(Double.class), any(Double.class), any(Double.class), any(Double.class), any(String.class), any(String.class)))
                .thenReturn(byteArrayOutputStream);

        TipoPlantillaEntity tipoPlantillaEntity = new TipoPlantillaEntity();
        tipoPlantillaEntity.setConfirm(true);
        tipoPlantillaEntity.setTexto("Texto de la plantilla con {0} y {1}");

        when(tipoplantillaRepository.findByNombre(anyString())).thenReturn(Optional.of(tipoPlantillaEntity));
        when(solipService.consultarFirmaDocumento(anyString())).thenReturn("firma_base64");

        // Crear una instancia real del servicio con mocks inyectados
        ComiteOficiosServicesImp comiteOficiosServices = new ComiteOficiosServicesImp(
                tipoplantillaRepository,
                comiteRepository,
                parametroRepository,
                restTemplate,
                solipService
        );

        // Mocking internal methods
        ComiteOficiosServicesImp spyComiteOficiosServices = spy(comiteOficiosServices);
        doReturn("cGRmIGNvbnRlbnQ=").when(spyComiteOficiosServices).validateFormat(anyString(), any(Map.class));

        // Llamar al método a probar
        List<String> result = spyComiteOficiosServices.generateDocument(oficioDto);

        // Verificar resultados
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("cGRmIGNvbnRlbnQ=", result.get(0));

        verify(comiteRepository, times(1)).obtenerOficiosPdforWord(anyList(), anyLong(), anyInt());
        verify(spyComiteOficiosServices, times(1)).validateFormat(anyString(), any(Map.class));
    }



    @Test
    void testGenerateDocumentAndZip() throws IOException {
        GenerarOficioDto oficioDto = new GenerarOficioDto();
        oficioDto.setSesionComite(1L);
        oficioDto.setSinAsignacion(1);
        oficioDto.setEstadoTramite("Autorizado");

        OficiosDto oficio = mock(OficiosDto.class);
        when(oficio.getNombre()).thenReturn("Nombre");
        when(oficio.getNombreEntidad()).thenReturn("Entidad");
        when(oficio.getCiudad()).thenReturn("Ciudad");
        when(oficio.getDireccion()).thenReturn("Dirección");
        when(oficio.getNumeroRadicacion()).thenReturn("123");
        when(oficio.getFechaComite()).thenReturn(LocalDate.of(2023, 1, 1));
        when(oficio.getNombreCargo()).thenReturn("Cargo"); // Ajusta según LISTADOS_CARGO
        when(oficio.getPresidente()).thenReturn("Presidente");
        when(oficio.getIdentificacion()).thenReturn("456");
        when(oficio.getAnotacion()).thenReturn("Anotación");

        List<OficiosDto> listadoOficios = Collections.singletonList(oficio);

        // Configurar mocks
        ComiteRepository comiteRepository = mock(ComiteRepository.class);
        TipoPlatillaRepository tipoplantillaRepository = mock(TipoPlatillaRepository.class);
        ParametroRepository parametroRepository = mock(ParametroRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        ISolipService solipService = mock(ISolipService.class);

        when(comiteRepository.obtenerOficiosPdforWord(anyList(), anyLong(), anyInt())).thenReturn(listadoOficios);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write("docx content".getBytes());

        GenerateDocx generateDocx = mock(GenerateDocx.class);
        when(generateDocx.generateDocx(any(Map.class), any(Double.class), any(Double.class), any(Double.class), any(Double.class), any(String.class)))
                .thenReturn(byteArrayOutputStream);

        TipoPlantillaEntity tipoPlantillaEntity = new TipoPlantillaEntity();
        tipoPlantillaEntity.setConfirm(true);
        tipoPlantillaEntity.setTexto("Texto de la plantilla con {0} y {1}");

        when(tipoplantillaRepository.findByNombre(anyString())).thenReturn(Optional.of(tipoPlantillaEntity));

        when(solipService.consultarFirmaDocumento(anyString())).thenReturn("firma_base64");

        // Crear una instancia real del servicio con mocks inyectados
        ComiteOficiosServicesImp comiteOficiosServices = new ComiteOficiosServicesImp(
                tipoplantillaRepository,
                comiteRepository,
                parametroRepository,
                restTemplate,
                solipService
        );

        // Mocking internal methods
        ComiteOficiosServicesImp spyComiteOficiosServices = spy(comiteOficiosServices);
        doReturn("ZG9jeCBjb250ZW50").when(spyComiteOficiosServices).validateFormat(anyString(), any(Map.class));
        doReturn(new byte[0]).when(spyComiteOficiosServices).generateZipWithDocuments(anyList());

        // Llamar al método a probar
        byte[] result = spyComiteOficiosServices.generateDocumentAndZip(oficioDto);

        // Verificar resultados
        assertNotNull(result);
        verify(comiteRepository, times(1)).obtenerOficiosPdforWord(anyList(), anyLong(), anyInt());
        verify(spyComiteOficiosServices, times(1)).generateZipWithDocuments(anyList());
    }



    @Test
    void testValidateFormat_pdf() throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("key", "value");
        parameters.put("BodyData", "Some body data");

        GeneratePdf generatePdf = new GeneratePdf();

        comiteOficiosServices = new ComiteOficiosServicesImp(
                tipoplantillaRepository, comiteRepository, parametroRepository, restTemplate, solipService);

        String result = comiteOficiosServices.validateFormat("pdf", parameters);

        ByteArrayOutputStream byteArrayOutputStream = generatePdf.generatePdf(convertParamsToStringMap(parameters), 3.20, 3.20, 2.54, 2.54, "", "");
        String expected = org.apache.commons.codec.binary.Base64.encodeBase64String(byteArrayOutputStream.toByteArray());
        int prefixLength = 100;
        assertEquals(expected.substring(0, prefixLength), result.substring(0, prefixLength));
    }

    private Map<String, String> convertParamsToStringMap(Map<String, Object> params) {
        Map<String, String> stringParams = new HashMap<>();
        params.forEach((key, value) -> stringParams.put(key, value != null ? value.toString() : null));
        return stringParams;
    }

    @Test
    void testRadicacionSolip() throws Exception {
        // Datos de prueba
        List<String> base64Documents = Collections.singletonList("base64doc1");

        // Mock del parámetro
        Parametros parametroRadicar = new Parametros();
        parametroRadicar.setNombre(Constantes.PARAMETRO_URL_RADICAR);
        parametroRadicar.setValor("http://localhost:8080/solip/radicar");

        // Mock del repositorio para devolver el parámetro
        when(parametroRepository.findByNombre(Constantes.PARAMETRO_URL_RADICAR)).thenReturn(Optional.of(parametroRadicar));

        // Crear una respuesta simulada
        String responseXml = "<SalidaDto><mensaje>OK</mensaje></SalidaDto>";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseXml, HttpStatus.OK);

        // Mock del restTemplate para devolver la respuesta simulada
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class))).thenReturn(responseEntity);

        // Llamar al método a probar
        String result = comiteOficiosServices.radicacionSolip(base64Documents);

        // Verificar resultados
        assertEquals("Se ha realizado la radicacion en solip OKOK", result);

        // Verificar interacciones
        verify(parametroRepository, times(1)).findByNombre(Constantes.PARAMETRO_URL_RADICAR);
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void testRadicacionSolip_parametroRadicarNull() {
        // Datos de prueba
        List<String> base64Documents = Collections.singletonList("base64doc1");

        // Mock del repositorio para devolver null
        when(parametroRepository.findByNombre(Constantes.PARAMETRO_URL_RADICAR)).thenReturn(Optional.empty());

        // Llamar al método a probar
        String result = comiteOficiosServices.radicacionSolip(base64Documents);

        // Verificar resultados
        assertEquals("No se ha podido realizar la radicacion: parametroRadicar es null", result);

        // Verificar interacciones
        verify(parametroRepository, times(1)).findByNombre(Constantes.PARAMETRO_URL_RADICAR);
        verifyNoInteractions(restTemplate);
    }

    @Test
    void testRadicacionSolip_errorEnRadicacion() throws Exception {
        // Datos de prueba
        List<String> base64Documents = Collections.singletonList("base64doc1");

        // Mock del parámetro
        Parametros parametroRadicar = new Parametros();
        parametroRadicar.setNombre(Constantes.PARAMETRO_URL_RADICAR);
        parametroRadicar.setValor("http://localhost:8080/solip/radicar");

        // Mock del repositorio para devolver el parámetro
        when(parametroRepository.findByNombre(Constantes.PARAMETRO_URL_RADICAR)).thenReturn(Optional.of(parametroRadicar));

        // Crear una respuesta simulada con error
        String responseXml = "<SalidaDto><mensaje>Error</mensaje></SalidaDto>";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseXml, HttpStatus.OK);

        // Mock del restTemplate para devolver la respuesta simulada
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class))).thenReturn(responseEntity);

        // Llamar al método a probar
        String result = comiteOficiosServices.radicacionSolip(base64Documents);

        // Verificar resultados
        assertEquals("No se ha podido realizar la radicacion en solip: " + responseXml, result);

        // Verificar interacciones
        verify(parametroRepository, times(1)).findByNombre(Constantes.PARAMETRO_URL_RADICAR);
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void testRadicacionSolip_excepcion()  {
        List<String> base64Documents = Collections.singletonList("base64doc1");

        Parametros parametroRadicar = new Parametros();
        parametroRadicar.setNombre(Constantes.PARAMETRO_URL_RADICAR);
        parametroRadicar.setValor("http://localhost:8080/solip/radicar");

        when(parametroRepository.findByNombre(Constantes.PARAMETRO_URL_RADICAR)).thenReturn(Optional.of(parametroRadicar));
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new RuntimeException("Simulated exception"));

        String result = comiteOficiosServices.radicacionSolip(base64Documents);

        assertTrue(result.contains("Error al radicar la peticion en Solip"));
        verify(parametroRepository, times(1)).findByNombre(Constantes.PARAMETRO_URL_RADICAR);
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void testObtenerPlantillaConAsignacion_noAsignacion() throws Exception {
        OficiosDto oficio = mock(OficiosDto.class);
        when(oficio.getNombreCargo()).thenReturn("CargoNoAsignado");

        GenerarOficioDto oficioDto = new GenerarOficioDto();
        oficioDto.setEstadoTramite("Autorizado");

        // Llamar al método privado usando reflexión y esperar una excepción
        Method method = ComiteOficiosServicesImp.class.getDeclaredMethod("obtenerPlantillaConAsignacion", OficiosDto.class, GenerarOficioDto.class);
        method.setAccessible(true);

        Exception exception = assertThrows(InvocationTargetException.class, () -> {
            method.invoke(comiteOficiosServices, oficio, oficioDto);
        });

        assertTrue(exception.getCause() instanceof IllegalArgumentException);
        assertEquals(ConstantesComiteII.NO_PLANTILLA, exception.getCause().getMessage());
    }


    @Test
    void testObtenerPlantillaPorEstado_default() throws Exception {
        String estadoTramite = "EstadoNoValido";

        // Llamar al método privado usando reflexión y esperar una excepción
        Method method = ComiteOficiosServicesImp.class.getDeclaredMethod("obtenerPlantillaPorEstado", String.class);
        method.setAccessible(true);

        Exception exception = assertThrows(InvocationTargetException.class, () -> {
            method.invoke(comiteOficiosServices, estadoTramite);
        });

        assertTrue(exception.getCause() instanceof IllegalArgumentException);
        assertEquals(ConstantesComiteII.NO_PLANTILLA, exception.getCause().getMessage());
    }


}
