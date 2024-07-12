package co.siri.posesiones.services.imp.comite_ll;

import co.siri.posesiones.dtos.comiteII.TramitesEstadoDTO;
import co.siri.posesiones.repositories.ComiteRepository;
import co.siri.posesiones.services.ComiteTramiteEstadoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ComiteTramiteEstadoServiceImp implements ComiteTramiteEstadoService {
    private final ComiteRepository comiteRepository;
    
    @Override
    public List<TramitesEstadoDTO> tramitesEstado(String estado, Long idsesioncomite, Integer anotacionEsNula) {
        return comiteRepository.tramitesEstado(estado, idsesioncomite, anotacionEsNula);
    }
}
