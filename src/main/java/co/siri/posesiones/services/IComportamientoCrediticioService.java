package co.siri.posesiones.services;


import co.siri.posesiones.dtos.ComportamientoCrediticioDTO;

import java.util.List;

public interface IComportamientoCrediticioService {

    List<ComportamientoCrediticioDTO> getComportamientoCrediticio (Long idIdentidad);
}
