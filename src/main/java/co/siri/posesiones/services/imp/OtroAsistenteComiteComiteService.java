package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.OtroAsistenteComiteDTO;
import co.siri.posesiones.dtos.OtroAsistenteComiteRequestDTO;
import co.siri.posesiones.entities.OtroAsistenteComite;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.OtroAsistenteComiteRepository;
import co.siri.posesiones.services.IOtroAsistenteComiteService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.reactor.ReactorEnvironmentPostProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtroAsistenteComiteComiteService implements IOtroAsistenteComiteService {
    private static final Logger logger = LoggerFactory.getLogger(IOtroAsistenteComiteService.class);
    private final OtroAsistenteComiteRepository repository;

    public OtroAsistenteComiteComiteService(OtroAsistenteComiteRepository repository) {this.repository = repository;}

    @Override
    public List<OtroAsistenteComiteDTO> otroAsistenteComite(Integer idSesionComite){
        try{
            return repository.getOtroAsistenteComite(idSesionComite);
        } catch (Exception e ){
            throw new ExcepcionPersonalizada("Error en la consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void guardarOtroAsistente (OtroAsistenteComiteRequestDTO requestDTO){
        Long idOtroAsistente = repository.ultimoId();
        try{
            repository.insertarOtroAsistente(idOtroAsistente,requestDTO.getIdSesionComite(),requestDTO.getNombreOtroAsistente(),requestDTO.getCalidadAsistente(),requestDTO.getIdUsuario(),requestDTO.getIpCliente());
        } catch (Exception e){
            logger.error("Error en consulta", e);
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
