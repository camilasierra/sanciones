package co.siri.posesiones.services;

import co.siri.posesiones.dtos.ParametroAnalistaDTO;
import co.siri.posesiones.entities.Parametros;

import java.util.List;

public interface IParametroService {

    Parametros getParametro(Integer parametroId);

    List<Parametros> getParametrosAnalista();

    void actualizarParametros(ParametroAnalistaDTO parametro);
}
