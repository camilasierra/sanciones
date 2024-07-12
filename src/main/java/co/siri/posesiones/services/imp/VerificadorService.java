package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.EscritorioTramitesResponseDTO;
import co.siri.posesiones.dtos.FiltroAvanzadoEscritorioDto;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.VerificadorRepository;
import co.siri.posesiones.services.IVerificadorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerificadorService implements IVerificadorService {
    private final VerificadorRepository repository;

    public VerificadorService(VerificadorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EscritorioTramitesResponseDTO> filtroAvanzado(FiltroAvanzadoEscritorioDto filtro) {
        String candidato = filtro.getCandidato() != null ? filtro.getCandidato().replaceAll("\\s", "") : null;
        try {
            return repository.filtroAvanzado(
                            filtro.getIdentificacion(),
                            filtro.getRadicado(),
                            filtro.getEntidad(),
                            candidato,
                            filtro.getEstadoTramite(),
                            filtro.getCargo(),
                            filtro.getIdTipoEstadoTramitePosesion()
                    );
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
