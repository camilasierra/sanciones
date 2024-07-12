package co.siri.posesiones.services.imp;

import co.siri.posesiones.entities.Parametros;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.ParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import static co.siri.posesiones.utilidades.Constantes.WS_URL_TOKEN_PDF_SERVICE;

/**
 * Clase para manejar la obtencion de tokens para el servicio de Compliance
 */
@Service
public class AuthComplianceService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private  ParametroRepository repository;

    private String token;

    public String getToken() {
        if (token == null) {
            token = fetchToken();
        }
        return token;
    }

    private boolean isTokenExpired() {
        // Implementa la lógica para verificar si el token ha expirado
        return false;
    }

    /**
     * Metodo encargado de generar el token de acesso para los servicios de Compliance
     * @return
     */
    private String fetchToken() {
        HttpHeaders headers = new HttpHeaders();
        //obtenemos las credenciales de la bd parametrizada
        List<String> headerCredenciales = getCredenciales();
        String plainCreds =  headerCredenciales.get(0) + ":" + headerCredenciales.get(1);
        byte[] base64CredsBytes = Base64.getEncoder().encode(plainCreds.getBytes());
        String base64Creds = new String(base64CredsBytes);

        headers.add("Authorization", "Basic " + base64Creds);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(WS_URL_TOKEN_PDF_SERVICE, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new ExcepcionPersonalizada("Failed to fetch token: " + response.getStatusCode() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Metodo que retorna las credenciales de la tabla Parametros
     * @return
     */
    private  List<String> getCredenciales(){
        List<String> credenciales = new ArrayList<>();
        List<Integer> idParams = Arrays.asList(31, 32);
        List<Parametros> response = repository.getParametrosMultiples(idParams);
        response.forEach(creden -> credenciales.add(creden.getValor()));
        return credenciales;
    }
}
