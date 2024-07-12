package co.siri.posesiones.services.imp;

import co.siri.posesiones.repositories.AsignacionRepository;
import co.siri.posesiones.services.IAsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsignacionService implements IAsignacionService {

    private AsignacionRepository asignacionRepository;

    @Autowired
    public AsignacionService(AsignacionRepository asignacionRepository) {
        this.asignacionRepository = asignacionRepository;
    }

    @Override
    public boolean getAsignacionTramiteAnalista(Long idTramitePosesion, Long idAnalistaTramitePosesion) {

        return asignacionRepository.findAsignacionByIdTramitePosesionAndIdAnalistaTramitePosesion(idTramitePosesion, idAnalistaTramitePosesion);
    }
}
