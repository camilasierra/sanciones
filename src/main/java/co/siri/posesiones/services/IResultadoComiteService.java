package co.siri.posesiones.services;

import co.siri.posesiones.entities.ResultadoComite;

import java.util.List;
import java.util.Optional;

public interface IResultadoComiteService {
    List<ResultadoComite> findAll();

    Optional<ResultadoComite> findById(Integer id);

    ResultadoComite save(ResultadoComite resultadoComite);

    void deleteById(Integer id);
}
