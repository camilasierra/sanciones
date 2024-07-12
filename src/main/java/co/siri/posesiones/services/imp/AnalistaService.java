package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.EscritorioTramitesResponseDTO;
import co.siri.posesiones.dtos.FiltroAvanzadoEscritorioDto;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.AnalistaRepository;
import co.siri.posesiones.services.IAnalistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalistaService implements IAnalistaService {
    private final AnalistaRepository repository;

    @Autowired
    public AnalistaService(AnalistaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EscritorioTramitesResponseDTO> listTramiteAnalista(FiltroAvanzadoEscritorioDto filtro) {
        String candidato = filtro.getCandidato() != null ? filtro.getCandidato().replaceAll("\\s", "") : null;
        try {
            return repository.filtroAvanzado(
                    filtro.getUser(),
                    filtro.getIdentificacion(),
                    filtro.getRadicado(),
                    filtro.getEntidad(),
                    candidato,
                    removeAccents(filtro.getEstadoTramite()),
                    filtro.getCargo()
            );
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    public static String removeAccents(String input) {
        if (input == null) {
            return null;
        }
        
        // Create arrays for accented and unaccented characters
        char[] accented = {'Á', 'É', 'Í', 'Ó', 'Ú', 'á', 'é', 'í', 'ó', 'ú'};
        char[] unaccented = {'A', 'E', 'I', 'O', 'U', 'a', 'e', 'i', 'o', 'u'};

        // Convert input string to char array
        char[] result = input.toCharArray();
        
        // Replace accented characters with unaccented equivalents
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < accented.length; j++) {
                if (result[i] == accented[j]) {
                    result[i] = unaccented[j];
                    break;
                }
            }
        }
        
        // Return the new string
        return new String(result);
    }
}
