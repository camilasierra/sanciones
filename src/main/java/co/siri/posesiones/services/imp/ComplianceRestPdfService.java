package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.PdfRequestDTO;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.services.IComplianceRestPdfService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

import static co.siri.posesiones.utilidades.Constantes.WS_URL_PDF_SERVICE_PDF_GET;
import static co.siri.posesiones.utilidades.Constantes.WS_URL_PDF_SERVICE_POST;

@Service
public class ComplianceRestPdfService implements IComplianceRestPdfService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthComplianceService authService;

    /**
     * Metodo que devuelce un arreglo de bytes el PDF del servicio Compliance
     *
     * @param request - params de busqueda
     * @return bytes[]
     * @throws IOException
     */
    public byte[] getPdfCompliance(PdfRequestDTO request) {
        String tokenJson = authService.getToken();
        String token = extractToken(tokenJson);

        // Recibir el idDatoConsultado para la búsqueda
        String idDataConsultant = getIdDatoConsultado(request);
        if (idDataConsultant.isEmpty()) {
            throw new ExcepcionPersonalizada("No se encontraron resultados para el id " + request.getDatoConsultar(),
                    HttpStatus.NO_CONTENT);
        }

        // Añadir parámetro de consulta a la URL
        URI uri = UriComponentsBuilder.fromUriString(WS_URL_PDF_SERVICE_PDF_GET)
                .path(idDataConsultant)
                .build()
                .toUri();

        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", "Bearer " + token);
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(header);

        // Realizar la solicitud GET para obtener el PDF como un array de bytes
        ResponseEntity<byte[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, byte[].class);

        // Verificar si la respuesta fue exitosa
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new ExcepcionPersonalizada("Error al obtener el PDF del servicio" + " " + response.getStatusCode()
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Metodo para consumir consultaConsolidada del broker Compliance y extraer el idDatoConsultado
     * para poder generar el PD
     * @param request - datos del cliente a consultar
     * @return
     * @throws ExcepcionPersonalizada
     */
    public String getIdDatoConsultado(PdfRequestDTO request) {
        String tokenJson = authService.getToken();
        String token = extractToken(tokenJson);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PdfRequestDTO> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    WS_URL_PDF_SERVICE_POST, HttpMethod.POST, entity, String.class);

            if (response.getBody() == null) {
                throw new ExcepcionPersonalizada("No se recibió cuerpo de la respuesta", HttpStatus.NO_CONTENT);
            }

            // Convertimos la respuesta JSON en un objeto JsonNode
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.getBody());

            // Extraemos el campo idDatoConsultado
            JsonNode idDatoNode = rootNode.get("idDatoConsultado");
            if (idDatoNode == null || idDatoNode.isNull()) {
                throw new ExcepcionPersonalizada("No se encontró el campo idDatoConsultado en la respuesta JSON", HttpStatus.NO_CONTENT);
            }

            return idDatoNode.asText();
        } catch (IOException e) {
            // Manejo de la excepción de lectura de JSON
            throw new ExcepcionPersonalizada("Error al procesar la respuesta JSON: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ExcepcionPersonalizada e) {
            // Otros errores no específicos
            throw new ExcepcionPersonalizada("Error al procesar la solicitud: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Meotodo encargado de extraer el token en un formato correcto
     * @param tokenJson
     * @return
     */
    private String extractToken(String tokenJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode tokenNode = mapper.readTree(tokenJson);
            JsonNode tokenValueNode = tokenNode.get("token");
            if (tokenValueNode == null || tokenValueNode.isNull()) {
                throw new ExcepcionPersonalizada("No se encontró el campo token en la respuesta JSON del token", HttpStatus.UNAUTHORIZED);
            }
            return tokenValueNode.asText();
        } catch (IOException e) {
            throw new ExcepcionPersonalizada("Error al extraer el token del JSON: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
