package co.siri.posesiones.services.imp;
import co.siri.posesiones.dtos.InvestigacionesPersonasDTO;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.InvestigacionPersonaRepository;
import co.siri.posesiones.services.IInvestigacionPersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static co.siri.posesiones.utilidades.Constantes.ERRORCONSULTA;

@Service
public class InvestigacionPersonaService implements IInvestigacionPersonaService {

    @Autowired
    private InvestigacionPersonaRepository personaRepository;
    @Override
    public List<InvestigacionesPersonasDTO> getInvestigacionPersona(Long idPersona) {
        try{
            List<InvestigacionesPersonasDTO> response = personaRepository.getInvestigacionPersonaByIdPersona(idPersona);
            if (response.isEmpty()) {
                throw new ExcepcionPersonalizada("No se encontraron investigaciones, con el id: " + idPersona, HttpStatus.NO_CONTENT);
            }
            return response;
        }catch (ExcepcionPersonalizada ex){
            throw  new ExcepcionPersonalizada(ERRORCONSULTA+ " " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
