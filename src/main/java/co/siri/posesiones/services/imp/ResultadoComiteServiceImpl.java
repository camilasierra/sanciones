package co.siri.posesiones.services.imp;

import co.siri.posesiones.entities.ResultadoComite;
import co.siri.posesiones.repositories.ResultadoComiteRepository;
import co.siri.posesiones.services.IResultadoComiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultadoComiteServiceImpl implements IResultadoComiteService {

    @Autowired
    private ResultadoComiteRepository resultadoComiteRepository;

    @Override
    public List<ResultadoComite> findAll() {
        return resultadoComiteRepository.findAll();
    }

    @Override
    public Optional<ResultadoComite> findById(Integer id) {
        return resultadoComiteRepository.findById(id);
    }

    @Override
    public ResultadoComite save(ResultadoComite resultadoComite) {
        return resultadoComiteRepository.save(resultadoComite);
    }

    @Override
    public void deleteById(Integer id) {
        resultadoComiteRepository.deleteById(id);
    }
}
