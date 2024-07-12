package co.siri.posesiones.services;

import co.siri.posesiones.dtos.TipoCargoLogoutDto;

import java.util.List;

public interface IListCargoService {
    List<TipoCargoLogoutDto> cargos();
}
