package co.siri.posesiones.services.imp.comite_ll;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import co.siri.posesiones.constant.ConstantesComiteII;
import co.siri.posesiones.dtos.SalidaDto;
import co.siri.posesiones.dtos.comiteII.GenerarOficioDto;
import co.siri.posesiones.dtos.comiteII.OficiosDto;
import co.siri.posesiones.entities.Parametros;
import co.siri.posesiones.entities.TipoPlantillaEntity;
import co.siri.posesiones.repositories.ComiteRepository;
import co.siri.posesiones.repositories.ParametroRepository;
import co.siri.posesiones.repositories.TipoPlatillaRepository;
import co.siri.posesiones.services.ComiteOficioService;
import co.siri.posesiones.services.ISolipService;
import co.siri.posesiones.utilidades.Constantes;
import co.siri.posesiones.utilidades.CamposRadicarTramiteEnum;
import co.siri.posesiones.utilidades.GenerateDocx;
import co.siri.posesiones.utilidades.GeneratePdf;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ComiteOficiosServicesImp implements ComiteOficioService {

    private final TipoPlatillaRepository tipoplantillaRepository;
    private final ComiteRepository comiteRepository;
    private final ParametroRepository parametroRepository;
    private final RestTemplate restTemplate;
    private final ISolipService solipService;
    private static final List<String> PLANTILLAS_DS = ConstantesComiteII.PLANTILLAS_DS;
    private static final List<String> ESTADOS_TS = ConstantesComiteII.ESTADOS_TS;
    private static final List<String> LISTADOS_CARGO = ConstantesComiteII.LISTADOS_CARGO;
    private String CARGO;
    private List<OficiosDto> listadoOficios;

    public ComiteOficiosServicesImp(TipoPlatillaRepository tipoplantillaRepository, ComiteRepository comiteRepository,ParametroRepository parametroRepository,RestTemplate restTemplate,ISolipService solipService) {
        this.tipoplantillaRepository = tipoplantillaRepository;
        this.comiteRepository = comiteRepository;
        this.parametroRepository = parametroRepository;
        this.restTemplate = restTemplate;
        this.solipService = solipService;
    }

    @Override
    public List<String> generateDocument(GenerarOficioDto oficioDto) {
        Long idsesioncomite = oficioDto.getSesionComite();
        Integer anotacionEsNula = oficioDto.getSinAsignacion();

        String dataStado = oficioDto.getEstadoTramite();
        List<String> estados = Arrays.asList(dataStado.split(","));

        List<OficiosDto> listadoOficios = comiteRepository.obtenerOficiosPdforWord(estados, idsesioncomite, anotacionEsNula);
        if (listadoOficios.isEmpty()) {
            return Collections.singletonList(ConstantesComiteII.NO_DATA);
        }

        List<String> documentosGenerados = new ArrayList<>();
        for (OficiosDto oficio : listadoOficios) {
            CARGO = oficio.getNombreCargo();
            try {
                Map<String, Object> parameters = initializeParameters(oficio, oficioDto);
                String documentoBase64 = validateFormat(ConstantesComiteII.FORMAT_PDF, parameters);
                documentosGenerados.add(documentoBase64);
            } catch (Exception e) {
                log.error("Error generating document for oficio: {}", oficio, e);
            }
        }
        return documentosGenerados;
    }

    @Override
    public byte[] generateDocumentAndZip(GenerarOficioDto oficioDto) throws IOException {
        Long idsesioncomite = oficioDto.getSesionComite();
        Integer anotacionEsNula = oficioDto.getSinAsignacion();

        String dataStado = oficioDto.getEstadoTramite();
        List<String> estados = Arrays.asList(dataStado.split(",")); // Convierte la cadena en una lista de cadenas
        List<OficiosDto> listadoOficios = comiteRepository.obtenerOficiosPdforWord(estados, idsesioncomite, anotacionEsNula);

        if (listadoOficios.isEmpty()) {
            throw new IOException(ConstantesComiteII.NO_DATA);
        }

        List<String> documentosGenerados = new ArrayList<>();
        for (OficiosDto oficio : listadoOficios) {
            CARGO = oficio.getNombreCargo();
            try {
                Map<String, Object> parameters = initializeParameters(oficio, oficioDto);
                String documentoBase64 = validateFormat(ConstantesComiteII.FORMAT_DOCX, parameters);
                documentosGenerados.add(documentoBase64);
            } catch (Exception e) {
                log.error("Error generating document for oficio: {}", oficio, e);
            }
        }

        if (documentosGenerados.isEmpty() || ConstantesComiteII.NO_DATA.equals(documentosGenerados.get(0))) {
            throw new IOException("Error al generar los documentos.");
        }

        return generateZipWithDocuments(documentosGenerados);
    }

    public String validateFormat(String format, Map<String, Object> parameters) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream;
        String headerDocument = "";
        switch (format.toLowerCase()) {
            case "pdf":
                GeneratePdf generatePdf = new GeneratePdf();
                String watermarkText = "";
                byteArrayOutputStream = generatePdf.generatePdf(convertParamsToStringMap(parameters), 3.20, 3.20, 2.54, 2.54, headerDocument, watermarkText);
                return Base64.encodeBase64String(byteArrayOutputStream.toByteArray());
            case "docx":
                GenerateDocx generateDocx = new GenerateDocx();
                ByteArrayOutputStream documentStream = generateDocx.generateDocx(convertParamsToStringMap(parameters), 3.20, 3.20, 2.54, 2.54, headerDocument);
                return java.util.Base64.getEncoder().encodeToString(documentStream.toByteArray());
            default:
                throw new IllegalArgumentException("Formato no soportado: " + format);
        }
    }

    private Map<String, String> convertParamsToStringMap(Map<String, Object> parameters) {
        Map<String, String> stringMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            stringMap.put(entry.getKey(), entry.getValue().toString());
        }
        return stringMap;
    }

    private Map<String, Object> initializeParameters(OficiosDto oficio, GenerarOficioDto oficioDto) {
        Map<String, Object> parameters = new HashMap<>();

        String plantillaNombre = obtenerPlantilla(oficio,oficioDto); // Obtener el nombre de la plantilla
        Optional<TipoPlantillaEntity> plantilla = tipoplantillaRepository.findByNombre(plantillaNombre);

        LocalDate fecha = oficio.getFechaComite();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fecha != null ? fecha.format(formatter) : "";
        String firma = "";

        if (plantilla.get().getConfirm() == true) {
            firma = obtenerFirma(oficio.getIdentificacion());//"19233242"
        }

        String[] valores = {
                Optional.ofNullable(oficio.getNombre()).orElse(""), //0
                Optional.ofNullable(oficio.getNombreEntidad()).orElse(""),//1
                Optional.ofNullable(oficio.getCiudad()).orElse(""),//2
                Optional.ofNullable(oficio.getDireccion()).orElse(""),//3
                Optional.ofNullable(oficio.getNumeroRadicacion()).orElse(""),//4
                fechaFormateada,//5
                Optional.ofNullable(oficio.getNombreCargo()).orElse(""),//6
                Optional.ofNullable(oficio.getPresidente()).orElse(""),//7
                Optional.ofNullable(oficio.getIdentificacion()).orElse(""),//8
                Optional.ofNullable(oficio.getAnotacion()).orElse(""),//9
                Optional.of(firma).orElse(""),//10
        };

        String bodyData = plantilla.map(tp -> replacePlaceholders(tp.getTexto(), valores))
                .orElse("Plantilla no encontrada.");

        parameters.put("BodyData", bodyData);

        return parameters;
    }
    
    private String obtenerFirma(String numIdentificacion) {
    	try {
    		return Constantes.BASE_IMAGEN + solipService.consultarFirmaDocumento(numIdentificacion);
    	}catch(Exception e) {
    		return "";
    	}
    }

    private String replacePlaceholders(String template, String[] values) {
        for (int i = 0; i < values.length; i++) {
            template = template.replace("{" + i + "}", values[i]);
        }
        return template;
    }

    public byte[] generateZipWithDocuments(List<String> base64Documents) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

        for (int i = 0; i < base64Documents.size(); i++) {
            try {
                byte[] document = Base64.decodeBase64(base64Documents.get(i));
                ZipEntry zipEntry = new ZipEntry("document_" + (i + 1) + ".docx");
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(document);
                zipOutputStream.closeEntry();
            } catch (Exception e) {
                log.error("Error adding document to zip: ", e);
            }
        }

        zipOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    private String obtenerPlantilla(OficiosDto oficio,GenerarOficioDto oficioDto) {
        if (oficioDto.getSinAsignacion() == 1) {
            return obtenerPlantillaSinAsignacion(oficio, oficioDto);
        } else {
            return obtenerPlantillaConAsignacion(oficio, oficioDto);
        }
    }

    private String obtenerPlantillaSinAsignacion(OficiosDto oficio, GenerarOficioDto oficioDto) {
        if (ESTADOS_TS.get(0).equalsIgnoreCase(oficioDto.getEstadoTramite())) {
            if (LISTADOS_CARGO.get(0).equalsIgnoreCase(oficio.getNombreCargo())) {
                return PLANTILLAS_DS.get(0);
            }
            return PLANTILLAS_DS.get(1);
        }
        if (LISTADOS_CARGO.get(0).equalsIgnoreCase(oficio.getNombreCargo())) {
            return PLANTILLAS_DS.get(0);
        } else {
            return PLANTILLAS_DS.get(1);
        }
    }

    private String obtenerPlantillaConAsignacion(OficiosDto oficio,GenerarOficioDto oficioDto) {
        for (int i = 1; i < LISTADOS_CARGO.size(); i++) {
            if (LISTADOS_CARGO.get(i).equalsIgnoreCase(oficio.getNombreCargo())) {
                return obtenerPlantillaPorEstado(oficioDto.getEstadoTramite());
            }
        }
        throw new IllegalArgumentException(ConstantesComiteII.NO_PLANTILLA);
    }

    private String obtenerPlantillaPorEstado(String estadoTramite) {
        switch (estadoTramite) {
            case "Autorizado":
                return PLANTILLAS_DS.get(4);
            case "Negado":
                return PLANTILLAS_DS.get(2);
            case "Suspendido":
                return PLANTILLAS_DS.get(6);
            case "Desistimiento tácito":
                return PLANTILLAS_DS.get(9);
            case "Desistimiento expreso":
                return PLANTILLAS_DS.get(8);
            default:
                throw new IllegalArgumentException(ConstantesComiteII.NO_PLANTILLA);
        }
    }
    @Override
    public String radicacionSolip(List<String> base64Documents) {
    	try {
    		Parametros parametroRadicar = parametroRepository.findByNombre(Constantes.PARAMETRO_URL_RADICAR).orElse(null);
        	if(null != parametroRadicar) {
        		String urlSolip = parametroRadicar.getValor();

            	HttpHeaders headers = new HttpHeaders();
    			headers.setContentType(MediaType.APPLICATION_XML);
    			String xmlRequestBody = obtenerPeticionRadicar(base64Documents);
    			HttpEntity<String> request = new HttpEntity<>(xmlRequestBody, headers);

    			ResponseEntity<String> response = restTemplate.exchange(urlSolip, HttpMethod.POST, request, String.class);

    			String responseBody = response.getBody();

    			log.debug("Respuesta solip al radicar:  " + responseBody);

    			XmlMapper xmlMapper = new XmlMapper();

    			SalidaDto salida = xmlMapper.readValue(responseBody, SalidaDto.class);

    			if (salida.getMensaje().equals("OK")) {
    				log.info("Se ha realizado la radicacion en solip OK");
                    return "Se ha realizado la radicacion en solip OK" + salida.getMensaje();
    			}else {
    				log.error("No se ha podido realizar la radicacion en solip: " + responseBody);
                    return "No se ha podido realizar la radicacion en solip: " + responseBody;
    			}
        	}
            return "No se ha podido realizar la radicacion: parametroRadicar es null";
        }catch(Exception e) {
    		log.error("Error al radicar la peticion en Solip ", e);
            return "Error al radicar la peticion en Solip " + e;
    	}

    }

    public String obtenerPeticionRadicar(List<String> base64Documents) {
    	try {
    		StringBuilder builder = new StringBuilder();
			builder.append(CamposRadicarTramiteEnum.CAMPO_ENCABEZADO.getValor());
			builder.append(CamposRadicarTramiteEnum.CAMPO_ENTRADA.getValor());
    		byte [] zipDocumentos = generateZipWithDocuments(base64Documents);
    		String documentosBase64 = Base64.encodeBase64String(zipDocumentos);
    		builder.append(CamposRadicarTramiteEnum.CAMPO_ARCHIVO.getMensajeFormat(documentosBase64));
    		builder.append(CamposRadicarTramiteEnum.CAMPO_HASANEXOS.getMensajeFormat("false"));
			builder.append(CamposRadicarTramiteEnum.CAMPO_SOLICITUDREMITENTE.getMensajeFormat("8"));
			builder.append(CamposRadicarTramiteEnum.CAMPO_TIPOENTIDAD.getMensajeFormat("1"));
			builder.append(CamposRadicarTramiteEnum.CAMPO_CODIGOENTIDAD.getMensajeFormat("42"));
			builder.append(CamposRadicarTramiteEnum.CAMPO_NOFOLIOS.getMensajeFormat("0"));
			builder.append(CamposRadicarTramiteEnum.CAMPO_CODIGOTRAMITE.getMensajeFormat("2000"));
			builder.append(CamposRadicarTramiteEnum.CAMPO_FECHASOLICITUD.getMensajeFormat(""));
			builder.append(CamposRadicarTramiteEnum.CAMPO_NOMBREARCHIVO.getMensajeFormat("archivos_radicados.zip"));
			builder.append(CamposRadicarTramiteEnum.CAMPO_CIERRE_ENTRADA.getValor());
			return builder.toString();
    	}catch(Exception e) {
    		log.error("Error al generar la peticion Radicar Solip ",e);
    		return null;
    	}
    }
}
