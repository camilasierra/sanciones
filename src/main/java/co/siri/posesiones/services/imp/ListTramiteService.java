package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.EstaTramiteDto;
import co.siri.posesiones.entities.TipoEstadoTramitePosesion;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.EstadoTramiteRepository;
import co.siri.posesiones.services.IListTramiteService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListTramiteService implements IListTramiteService {
    private final EstadoTramiteRepository repository;
    private final ModelMapper modelMapper;

    public ListTramiteService(EstadoTramiteRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }
    @Override
    public List<EstaTramiteDto> estaTramites() {
        try {
            List<TipoEstadoTramitePosesion> estaTramites =
                    repository.listTramite();

            return estaTramites.stream()
                    .map(estaTramite -> modelMapper.map(estaTramite, EstaTramiteDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
