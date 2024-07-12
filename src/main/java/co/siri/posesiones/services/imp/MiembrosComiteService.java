package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.MiembrosComiteDTO;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.SesionComiteRepository;
import co.siri.posesiones.services.ISesionComiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiembrosComiteService implements ISesionComiteService {
    private static final Logger logger = LoggerFactory.getLogger(ISesionComiteService.class);
    private final SesionComiteRepository repository;

    public MiembrosComiteService(SesionComiteRepository repository){
        this.repository = repository;
    }

    @Override
    public List<MiembrosComiteDTO> miembrosComite(Integer idSesionComite){
        try{
            return repository.getMiembrosComite(idSesionComite);
        } catch (Exception e){
            logger.error("Error en consulta", e);
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
