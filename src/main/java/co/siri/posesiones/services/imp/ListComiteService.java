package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.ListaComitesDTO;
import co.siri.posesiones.entities.SesionComite;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.SesionComiteRepository;
import co.siri.posesiones.services.IListComites;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListComiteService implements IListComites {
    private final SesionComiteRepository sesionComiteRepository;

    public ListComiteService(SesionComiteRepository sesionComiteRepository){
        this.sesionComiteRepository = sesionComiteRepository;
    }
    @Override
    public List<ListaComitesDTO> listaComites(){
        try {
            return sesionComiteRepository.listSesionComite();
        } catch (Exception e){
            System.out.println(e);
            throw  new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ListaComitesDTO> comites(){
        try {
            return sesionComiteRepository.listComite();
        } catch (Exception e){
            System.out.println(e);
            throw  new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
