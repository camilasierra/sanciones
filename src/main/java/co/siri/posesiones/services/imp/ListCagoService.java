package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.TipoCargoLogoutDto;
import co.siri.posesiones.entities.TipoCargo;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.CargoRepository;
import co.siri.posesiones.services.IListCargoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListCagoService implements IListCargoService {
    private final CargoRepository repository;
    private final ModelMapper modelMapper;

    public ListCagoService(CargoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TipoCargoLogoutDto> cargos() {
        try {
            List<TipoCargo> cargos =
                    repository.listCargo();

            return cargos.stream()
                    .map(cargo -> modelMapper.map(cargo, TipoCargoLogoutDto.class))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
