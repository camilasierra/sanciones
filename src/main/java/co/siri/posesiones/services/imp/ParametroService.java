package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.ParametroAnalistaDTO;
import co.siri.posesiones.dtos.TopeAnalista;
import co.siri.posesiones.entities.Parametros;
import co.siri.posesiones.repositories.ParametroRepository;
import co.siri.posesiones.services.IParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParametroService implements IParametroService {
    @Autowired
    private ParametroRepository parametroRepository;

    @Override
    public Parametros getParametro(Integer parametroId) {
        return parametroRepository.findByIdParametro(parametroId);
    }

    @Override
    public List<Parametros> getParametrosAnalista() {
        TopeAnalista topeBase = TopeAnalista.CARGA_MAXIMA_ANALISTA_BASE;
        TopeAnalista topeApoyo = TopeAnalista.CARGA_MAXIMA_ANALISTA_APOYO;
        TopeAnalista diferenciaCarga = TopeAnalista.DIFERENCIA_CARGA;

        List<String> listParameters = new ArrayList<>();
        listParameters.add(topeBase.getCargaMaxima());
        listParameters.add(topeApoyo.getCargaMaxima());
        listParameters.add(diferenciaCarga.getCargaMaxima());

        return parametroRepository.findParametrosByNames(listParameters);
    }

    @Override
    public void actualizarParametros(ParametroAnalistaDTO parametro) {

        List<Parametros> parametros = getParametrosAnalista();

        for(Parametros param: parametros) {
            param.setIdUsuario(parametro.getIdUsuario());
            param.setIpCliente(parametro.getIpCliente());

            switch (param.getNombre()){
                case "cargaMaximaAnalistaBase":
                    param.setValor(parametro.getCargaMaximaAnalistaBase().toString());
                    break;
                case "cargaMaximaAnalistaApoyo":
                    param.setValor(parametro.getCargaMaximaAnalistaApoyo().toString());
                    break;
                case "diferenciaMaximaCarga":
                    param.setValor(parametro.getDiferenciaMaximaCarga().toString());
                default:
                    break;
            }

            parametroRepository.save(param);
        }
    }
}
