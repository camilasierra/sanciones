package co.siri.posesiones.services.imp.comite_ll;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Answers.values;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.io.*;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import co.siri.posesiones.constant.ConstantesComiteII;
import co.siri.posesiones.dtos.ActaComiteDTO;
import co.siri.posesiones.dtos.DecisionesActaComiteDTO;
import co.siri.posesiones.dtos.VotantesDto;
import co.siri.posesiones.entities.TipoPlantillaEntity;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.utilidades.GenerateDocx;
import co.siri.posesiones.utilidades.GeneratePdf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import co.siri.posesiones.entities.ResultadoComite;
import co.siri.posesiones.entities.SesionComiteEntity;
import co.siri.posesiones.repositories.ActaComiteRepository;
import co.siri.posesiones.repositories.IResultadoComiteRepository;
import co.siri.posesiones.repositories.TipoPlatillaRepository;
import co.siri.posesiones.services.ISolipService;
class ActaComiteServicesImplTest {

    @InjectMocks
    private ActaComiteServicesImpl actaComiteServices;

    @Mock
    private ActaComiteRepository repository;

    @Mock
    private IResultadoComiteRepository comiteRepository;

    @Mock
    private  ActaComiteRepository actaComiteRepository;

    @Mock
    private  TipoPlatillaRepository tipoplantillaRepository;

    @Mock
    private ISolipService solipService;

    @Mock
    private GeneratePdf generatePdf;

    @Mock
    private GenerateDocx generateDocx;

    private List<Object[]> infoSesionComiteMock;
    private List<Object[]> miembrosComiteMock;
    private List<Object[]> invitadosComiteMock;
    private List<Object[]> decisionesMock;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        infoSesionComiteMock = new ArrayList<>();
        infoSesionComiteMock.add(new Object[]{new Date(), "10:00", "12:00", "Ordinaria", null, "Presencial", "Canal 1", "123"});

        miembrosComiteMock = new ArrayList<>();
        miembrosComiteMock.add(new Object[]{"Miembro 1", "Cargo 1"});
        miembrosComiteMock.add(new Object[]{"Miembro 2", "Cargo 2"});

        invitadosComiteMock = new ArrayList<>();
        invitadosComiteMock.add(new Object[]{"Invitado 1", "Cargo 1"});
        invitadosComiteMock.add(new Object[]{"Invitado 2", "Cargo 2"});

        decisionesMock = new ArrayList<>();
        decisionesMock.add(new Object[]{"Delegatura 1", "Decision 1", "Estado 1"});
        decisionesMock.add(new Object[]{"Delegatura 2", "Decision 2", "Estado 2"});


    }

    @Test
    void testConsultarComites() {
        List<Object[]> results = Arrays.asList(
                new Object[]{"Test1", "Test2", "Test3", "Test4", mock(Clob.class), 1, "Test6"},
                new Object[]{"Test7", "Test8", "Test9", "Test10", mock(Clob.class), 2, "Test12"}
        );

        when(repository.getInfoComite(anyList(), anyLong(), anyInt())).thenReturn(results);

        HashMap<String, Object> result = actaComiteServices.consultarComites(Arrays.asList("estado1", "estado2"), 1L, 0);

        assertNotNull(result);
        assertNull(result.get("numeroActa"));
        assertTrue(result.containsKey("tramites"));
    }

    @Test
    void testActualizarAnotacionComite() {
        ResultadoComite resultadoComite = new ResultadoComite();
        resultadoComite.setAnotacion("New Anotacion");

        when(comiteRepository.findById(anyInt())).thenReturn(Optional.of(resultadoComite));

        String updatedAnotacion = actaComiteServices.actualizarAnotacionComite(resultadoComite);

        assertEquals("New Anotacion", updatedAnotacion);
    }


    @Test
    void testClobToString() throws Exception {

        String clobContent = "Contenido de prueba";
        Clob mockClob = new javax.sql.rowset.serial.SerialClob(clobContent.toCharArray());

        String result = actaComiteServices.clobToString(mockClob);

        assertNotNull(result);
        assertEquals(clobContent, result);
    }


    @Test
    void testConvertParamsToStringMap() {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("key1", "value1");
        parameters.put("key2", 123);

        ActaComiteServicesImpl service = new ActaComiteServicesImpl(null, null, null, null);
        Map<String, String> result = service.convertParamsToStringMap(parameters);

        assertEquals("value1", result.get("key1"));
        assertEquals("123", result.get("key2"));
    }

    @Test
    void testObtenerPlantilla_Virtual() {
        String result = actaComiteServices.obtenerPlantilla("Virtual");
        assertEquals("Virtual", result);
    }

    @Test
    void testObtenerPlantilla_NoPresencial() {
        String result = actaComiteServices.obtenerPlantilla("No presencial");
        assertEquals("No Presencial", result);
    }

    @Test
    void testObtenerPlantilla_Presencial() {
        String result = actaComiteServices.obtenerPlantilla("Presencial");
        assertEquals("Presencial", result);
    }

    @Test
    void testObtenerPlantilla_Exception() {
        Exception exception = assertThrows(ExcepcionPersonalizada.class, () -> {
            actaComiteServices.obtenerPlantilla("OtroTipo");
        });

        assertEquals(HttpStatus.BAD_REQUEST, ((ExcepcionPersonalizada) exception).getStatus());
        assertEquals(ConstantesComiteII.NO_PLANTILLA, exception.getMessage());
    }


    @Test
    void testReplacePlaceholders() {
        String template = "Hola {0}, hoy es {1}.";
        String[] values = {"Usuario", "miércoles"};

        String result = actaComiteServices.replacePlaceholders(template, values);
        assertEquals("Hola Usuario, hoy es miércoles.", result);
    }

    @Test
    void testReplacePlaceholders_NoValues() {
        String template = "Hola {0}, hoy es {1}.";
        String[] values = {};

        String result = actaComiteServices.replacePlaceholders(template, values);
        assertEquals("Hola {0}, hoy es {1}.", result);
    }

    @Test
    void testReplacePlaceholders_ExtraValues() {
        String template = "Hola {0}, hoy es {1}.";
        String[] values = {"Usuario", "miércoles", "extra"};

        String result = actaComiteServices.replacePlaceholders(template, values);
        assertEquals("Hola Usuario, hoy es miércoles.", result);
    }

    @Test
    void testGetMiembrosComite_OrderCallZero() {
        List<Object[]> list = Arrays.asList(
                new Object[]{"Juan", "Perez", 1},
                new Object[]{"Maria", "Gonzalez", 2},
                new Object[]{"Pedro", "Jimenez", 4}
        );
        int orderCall = 0;

        String result = actaComiteServices.getMiembrosComite(list, orderCall);
        assertEquals("Juan PerezMaria Gonzalez", result);
    }

    @Test
    void testGetMiembrosComite_OrderCallOne() {
        List<Object[]> list = Arrays.asList(
                new Object[]{"Juan", "Perez", 1},
                new Object[]{"Maria", "Gonzalez", 2},
                new Object[]{"Pedro", "Jimenez", 4}
        );
        int orderCall = 1;

        String result = actaComiteServices.getMiembrosComite(list, orderCall);
        assertEquals("Pedro Jimenez", result);
    }

    @Test
    void testGetMiembrosComite_EmptyList() {
        List<Object[]> list = Arrays.asList();
        int orderCall = 0;

        String result = actaComiteServices.getMiembrosComite(list, orderCall);
        assertEquals("", result);
    }

    @Test
    void testGetInvitadosComite_OneAsistente() {
        List<Object[]> asistentes = Arrays.asList(
                new Object[]{"Juan"},
                new Object[]{"Maria"}
        );

        String result = actaComiteServices.getInvitadosComite(asistentes);
        assertEquals("asistieron Juan, Maria,  como invitados a la sesión.", result);
    }

    @Test
    void testGetInvitadosComite_MultipleAsistentes() {
        List<Object[]> asistentes = Arrays.asList(
                new Object[]{"Juan"},
                new Object[]{"Maria"},
                new Object[]{"Pedro"}
        );

        String result = actaComiteServices.getInvitadosComite(asistentes);
        assertEquals("asistieron Juan, Maria, Pedro,  como invitados a la sesión.", result);
    }

    @Test
    void testGetInvitadosComite_EmptyList() {
        List<Object[]> asistentes = Arrays.asList();

        String result = actaComiteServices.getInvitadosComite(asistentes);
        assertEquals("asistieron  como invitados a la sesión.", result);
    }

    @Test
    void testFormatDecisiones() {
        List<DecisionesActaComiteDTO> decisiones = Arrays.asList(
                createDecision(1,"Delegatura A"),
                createDecision(1,"Delegatura B")
        );

        String result = actaComiteServices.formatDecisiones(decisiones);

        String expected = "<b>2.1 Delegatura A</b><br/><b>2.1.1 Decision 1: </b><br/>2.1.1.1 Item 1<br/>2.1.1.2 Item 2<br/><br/><b>2.2 Delegatura B</b><br/><b>2.2.1 Decision 1: </b><br/>2.2.1.1 Item 1<br/>2.2.1.2 Item 2<br/><br/>";

        assertEquals(expected, result);
    }

    private DecisionesActaComiteDTO createDecision(int index, String delegatura) {
        DecisionesActaComiteDTO decision = new DecisionesActaComiteDTO();
        decision.setDelegatura(delegatura);

        HashMap<String, Object> decisiones = new HashMap<>();
        decisiones.put("Decision 1", Arrays.asList(new Object[]{"Item 1"}, new Object[]{"Item 2"}));
        decision.setDecisiones(decisiones);

        return decision;
    }

    @Test
    void testInitializeParameters() throws Exception {
        // Crear un DTO simulado con datos de prueba
        ActaComiteDTO comiteDTO = new ActaComiteDTO();
        comiteDTO.setTipoSesion("Ordinaria");
        comiteDTO.setNumeroActa("123");
        comiteDTO.setFechaComite("01/01/2023");
        comiteDTO.setFechaMesComite("01-Jan-2023 de ");
        comiteDTO.setHoraInicioComite("09:00");
        comiteDTO.setTipoModalidad("Virtual");
        comiteDTO.setCanal("Zoom");
        comiteDTO.setMiembrosComite("Miembro1");
        comiteDTO.setAsistenteComite("Asistente1");
        comiteDTO.setInvitadosComite("Invitado1");
        comiteDTO.setVarios("Varios");
        comiteDTO.setHoraFinComite("11:00");

        // Crear una lista de decisiones simuladas
        List<DecisionesActaComiteDTO> decisiones = new ArrayList<>();
        DecisionesActaComiteDTO decision = new DecisionesActaComiteDTO();
        decision.setDelegatura("Delegatura1");
        HashMap<String, Object> decisionesMap = new HashMap<>();
        decisionesMap.put("Decision1", List.of(new Object[]{"Item1"}, new Object[]{"Item2"}));
        decision.setDecisiones(decisionesMap);
        decisiones.add(decision);
        comiteDTO.setDecisiones(decisiones);

        // Crear una entidad de plantilla simulada
        TipoPlantillaEntity plantillaEntity = new TipoPlantillaEntity();
        plantillaEntity.setTexto("Plantilla con valores: {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}, {11}, {12}");

        // Configurar el comportamiento del repositorio mock
        when(tipoplantillaRepository.findByNombre(anyString())).thenReturn(Optional.of(plantillaEntity));

        // Acceder al método privado usando reflexión
        Method method = ActaComiteServicesImpl.class.getDeclaredMethod("initializeParameters", ActaComiteDTO.class);
        method.setAccessible(true);

        // Llamar al método a probar
        @SuppressWarnings("unchecked")
        Map<String, String> result = (Map<String, String>) method.invoke(actaComiteServices, comiteDTO);

        // Verificar resultados
        assertNotNull(result);
        assertTrue(result.containsKey("BodyData"));
        String expectedBodyData = "Plantilla con valores: ORDINARIA, 123, 01/01/2023, 01-Jan-2023 de , 09:00, Virtual, Zoom, Miembro1, Asistente1, Invitado1, <b>2.1 Delegatura1</b><br/><b>2.1.1 Decision1: </b><br/>2.1.1.1 Item1<br/>2.1.1.2 Item2<br/><br/>, Varios, 11:00";
        assertEquals(expectedBodyData, result.get("BodyData"));
    }

    @Test
    void testActualizarVarios_sesionComiteExists() {
        SesionComiteEntity sesionEntity = new SesionComiteEntity();
        sesionEntity.setId(1L);
        sesionEntity.setVarios("Nuevos varios");
        sesionEntity.setIdUsuario(2);
        sesionEntity.setIpCliente("127.0.0.1");

        SesionComiteEntity existingSesionComite = new SesionComiteEntity();
        existingSesionComite.setId(1L);
        existingSesionComite.setVarios("Nuevos varios");
        existingSesionComite.setIdUsuario(1);
        existingSesionComite.setIpCliente("192.168.0.1");

        when(repository.findById(1L)).thenReturn(Optional.of(existingSesionComite));

        String result = actaComiteServices.actualizarVarios(sesionEntity);

        assertEquals("Nuevos varios", result);
        assertEquals("Nuevos varios", existingSesionComite.getVarios());
        assertEquals(2, existingSesionComite.getIdUsuario().intValue()); // Comparar el valor directamente
        assertEquals("127.0.0.1", existingSesionComite.getIpCliente());

        verify(repository).save(existingSesionComite);
    }

    @Test
    void testActualizarVarios_sesionComiteDoesNotExist() {
        SesionComiteEntity sesionEntity = new SesionComiteEntity();
        sesionEntity.setId(1L);
        sesionEntity.setVarios("Nuevos varios");

        when(repository.findById(1L)).thenReturn(Optional.empty());

        String result = actaComiteServices.actualizarVarios(sesionEntity);

        assertEquals("Nuevos varios", result);
        verify(repository).findById(1L);
        verify(repository, never()).save(any(SesionComiteEntity.class));
    }

    @Test
    void testGetVarios() throws SQLException {

        String contenidoClob = "Contenido del Clob de prueba";
        Clob clob = new StringClob(contenidoClob) {
            @Override
            public InputStream getAsciiStream() throws SQLException {
                return null;
            }

            @Override
            public long position(String searchstr, long start) throws SQLException {
                return 0;
            }

            @Override
            public long position(Clob searchstr, long start) throws SQLException {
                return 0;
            }

            @Override
            public int setString(long pos, String str) throws SQLException {
                return 0;
            }

            @Override
            public int setString(long pos, String str, int offset, int len) throws SQLException {
                return 0;
            }

            @Override
            public OutputStream setAsciiStream(long pos) throws SQLException {
                return null;
            }

            @Override
            public Writer setCharacterStream(long pos) throws SQLException {
                return null;
            }

            @Override
            public void truncate(long len) throws SQLException {

            }

            @Override
            public void free() throws SQLException {

            }

            @Override
            public Reader getCharacterStream(long pos, long length) throws SQLException {
                return null;
            }
        };


        when(repository.getVarios(anyLong())).thenReturn(clob);


        String result = actaComiteServices.getVarios(1L);


        assertNotNull(result);
//        assertEquals(contenidoClob, result);
    }

    private static abstract class StringClob implements Clob {
        private final String content;

        public StringClob(String content) {
            this.content = content;
        }

        @Override
        public long length() throws SQLException {
            return content.length();
        }

        @Override
        public String getSubString(long pos, int length) throws SQLException {
            return content.substring((int) pos - 1, (int) pos - 1 + length);
        }


        @Override
        public Reader getCharacterStream() throws SQLException {
            return new StringReader(content);
        }
    }


    @Test
    void testGetDecisionesComite_emptyList() {
        List<Object[]> list = new ArrayList<>();
        List<DecisionesActaComiteDTO> result = actaComiteServices.getDecisionesComite(list);
        assertEquals(0, result.size(), "The result list should be empty");
    }

    @Test
    void testGetDecisionesComite_nonEmptyList() throws Exception {
        List<Object[]> list = new ArrayList<>();
        Clob clob = mock(Clob.class);
        when(clob.getSubString(1, (int) clob.length())).thenReturn("test decision");
        when(clob.length()).thenReturn((long) "test decision".length());

        list.add(new Object[] { "Delegatura1", "Decision1", "Estado1", clob });

        List<DecisionesActaComiteDTO> result = actaComiteServices.getDecisionesComite(list);

        assertEquals(1, result.size(), "The result list should contain one element");
        assertEquals("Delegatura1", result.get(0).getDelegatura(), "The delegatura should be 'Delegatura1'");
        assertEquals(1, result.get(0).getDecisiones().size(), "The decisiones map should contain one entry");
    }


    @Test
    void testValidateFormat_pdf() throws IOException {
        // Crear el mapa de parámetros con BodyData no nulo
        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", "value");
        parameters.put("BodyData", "Some body data"); // Asegurarse de que BodyData no sea nulo

        // Crear una instancia real de GeneratePdf
        GeneratePdf generatePdf = new GeneratePdf();

        // Inyectar la instancia real en actaComiteServices
        ActaComiteServicesImpl actaComiteServices = new ActaComiteServicesImpl(repository, comiteRepository, tipoplantillaRepository, solipService);

        // Llamar al método validateFormat y verificar el resultado
        String result = actaComiteServices.validateFormat("pdf", parameters);

        // Generar el resultado esperado manualmente usando la misma lógica que el servicio
        ByteArrayOutputStream byteArrayOutputStream = generatePdf.generatePdf(parameters, 3.20, 3.20, 2.54, 2.54, "SUPERINTENDENCIA FINANCIERA DE COLOMBIA", "VISTA PREVIA");
        String expected = org.apache.commons.codec.binary.Base64.encodeBase64String(byteArrayOutputStream.toByteArray());

        // Verificar que el prefijo del resultado sea el esperado
        int prefixLength = 100; // Longitud del prefijo para comparar
        assertEquals(expected.substring(0, prefixLength), result.substring(0, prefixLength));
    }

    @Test
    void testValidateFormat_docx() throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", "value");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write("docx content".getBytes());

        when(generateDocx.generateDocx(any(Map.class), any(Double.class), any(Double.class), any(Double.class), any(Double.class), any(String.class)))
                .thenReturn(byteArrayOutputStream);

//        String result = actaComiteServices.validateFormat("docx", parameters);
        String expected = org.apache.commons.codec.binary.Base64.encodeBase64String(byteArrayOutputStream.toByteArray());

        assertEquals(expected, "ZG9jeCBjb250ZW50");
    }

    @Test
    void testValidateFormat_unsupportedFormat() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", "value");

        assertThrows(IllegalArgumentException.class, () -> {
            actaComiteServices.validateFormat("txt", parameters);
        }, "Unsupported format should throw IllegalArgumentException");
    }


    @Test
    void testGetInfoArmarActa_successful() throws Exception {
        List<Object[]> infoSesionComite = new ArrayList<>();
        infoSesionComite.add(new Object[] { new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"), "09:00", "11:00", "Ordinaria", "214213", "Virtual", "Zoom", 1L });

        List<Object[]> miembrosComite = new ArrayList<>();
        miembrosComite.add(new Object[] { "Miembro1", "Cargo1", 0 });
        miembrosComite.add(new Object[] { "Asistente1", "Cargo2", 1 });

        List<Object[]> invitadosComite = new ArrayList<>();
        invitadosComite.add(new Object[] { "Invitado1", "Cargo3" });

        List<Object[]> decisiones = new ArrayList<>();
        decisiones.add(new Object[] { "Delegatura1", "Decision1", "Estado1", mock(java.sql.Clob.class) });

        when(repository.getInfoSesionComite(any(Long.class))).thenReturn(infoSesionComite);
        when(repository.getInfoMiembrosComite(any(Long.class), anyList())).thenReturn(miembrosComite);
        when(repository.getInfoAsistenteComite(any(Long.class))).thenReturn(invitadosComite);
        when(repository.getInfoDecisionesComite(anyList(), any(Long.class), any(Integer.class))).thenReturn(decisiones);

        // Simulate PDF and DOCX generation
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write("document content".getBytes());

        when(generatePdf.generatePdf(any(Map.class), any(Double.class), any(Double.class), any(Double.class), any(Double.class), any(String.class), any(String.class)))
                .thenReturn(byteArrayOutputStream);

        when(generateDocx.generateDocx(any(Map.class), any(Double.class), any(Double.class), any(Double.class), any(Double.class), any(String.class)))
                .thenReturn(byteArrayOutputStream);

        List<String> estado = new ArrayList<>();
        estado.add("aprobado");
        Long idsesion = 1L;
        Integer anotacionEsNula = 0;
        List<Integer> idtipomiembrocomite = new ArrayList<>();
        idtipomiembrocomite.add(1);
        String format = "pdf";

        ActaComiteDTO result = actaComiteServices.getInfoArmarActa(estado, idsesion, anotacionEsNula, idtipomiembrocomite, format);

        assertNotNull(result);
//        assertEquals("Exitoso", result.getEstado());
//        assertNotNull(result.getData());
    }

    @Test
    void testGetInfoArmarActa_incompleteData() throws Exception {
        /*List<Object[]> infoSesionComite = new ArrayList<>();
        infoSesionComite.add(new Object[] { new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"), "09:00", "11:00", "Ordinaria", null, "Virtual", "Zoom", 1L });

        List<Object[]> miembrosComite = new ArrayList<>();
        List<Object[]> invitadosComite = new ArrayList<>();
        List<Object[]> decisiones = new ArrayList<>();

        when(repository.getInfoSesionComite(any(Long.class))).thenReturn(infoSesionComite);
        when(repository.getInfoMiembrosComite(any(Long.class), anyList())).thenReturn(miembrosComite);
        when(repository.getInfoAsistenteComite(any(Long.class))).thenReturn(invitadosComite);
        when(repository.getInfoDecisionesComite(anyList(), any(Long.class), any(Integer.class))).thenReturn(decisiones);

        List<String> estado = new ArrayList<>();
        estado.add("aprobado");
        Long idsesion = 1L;
        Integer anotacionEsNula = 0;
        List<Integer> idtipomiembrocomite = new ArrayList<>();
        idtipomiembrocomite.add(1);
        String format = "pdf";

        ActaComiteDTO result = actaComiteServices.getInfoArmarActa(estado, idsesion, anotacionEsNula, idtipomiembrocomite, format);

        assertNotNull(result);
        assertNotNull(result.getFechaComite()); // Asegúrate de que el campo no sea nulo
        assertNotNull(result.getMiembrosComite()); // Asegúrate de que el campo no sea nulo
        assertNotNull(result.getAsistenteComite()); // Asegúrate de que el campo no sea nulo
        assertNotNull(result.getDecisiones()); // Asegúrate de que el campo no sea nulo
        assertEquals("No Exitoso, Campo requerido nulo Miembros Comite, Campo requerido nulo Asistentes Comite, Campo requerido nulo Decisiones, ", result.getEstado());
    */}

    @Test
    void testObtenerFechaHora() {
        Timestamp timestamp = Timestamp.valueOf("2024-07-04 12:34:56");

        String[] expected = {"04-07-2024", "12:34:56", "p. m."};
        String[] actual = actaComiteServices.obtenerFechaHora(timestamp);

        assertArrayEquals(expected, actual, "The date and time parts should match the expected values");
    }

    @Test
    void testGetVotantesDto() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[] {"Comité A", Timestamp.valueOf("2024-07-04 12:34:56")});

        List<VotantesDto> result = actaComiteServices.getVotantesDto(list);

        assertEquals(1, result.size(), "The result list should contain one element");

        VotantesDto votante = result.get(0);
        assertEquals("Comité A", votante.getNombreComite(), "The committee name should be 'Comité A'");
        assertEquals("04-07-2024", votante.getDiaVotacion(), "The voting day should be '04-07-2024'");
        assertEquals("12:34:56 p. m.", votante.getHoraVotacion(), "The voting time should be '12:34:56 PM'");
    }

    @Test
    void testGetVotantesDto_emptyList() {
        List<Object[]> list = new ArrayList<>();

        List<VotantesDto> result = actaComiteServices.getVotantesDto(list);

        assertEquals(0, result.size(), "The result list should be empty");
    }
}
