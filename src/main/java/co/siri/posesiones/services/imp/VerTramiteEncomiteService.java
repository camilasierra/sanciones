package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.ComiteDetallesResponseDto;
import co.siri.posesiones.dtos.ResultadoComiteRequestDTO;
import co.siri.posesiones.entities.TipoEstadoTramitePosesion;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.ResultadoComiteRepository;
import co.siri.posesiones.repositories.VerTramiteEnComiteRepository;
import co.siri.posesiones.services.IVerTramiteEncomite;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerTramiteEncomiteService implements IVerTramiteEncomite {

    @Autowired
    private VerTramiteEnComiteRepository repository;
    @Autowired
    private ResultadoComiteRepository resultadoComiteRepository;

    @Override
    public List<TipoEstadoTramitePosesion> ListarrVerTramiteEncomite() {
        try {
            return repository.obtenerEstadosTramite();
        }
        catch (Exception e){
            throw new ExcepcionPersonalizada ("Error en la consulta" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public void actualizarResultadoComite(ResultadoComiteRequestDTO dto) {
        try {
            resultadoComiteRepository.updateResultadoComite(
                    dto.getAnotacion(),
                    dto.getIdTipoEstadoTramitePosesion(),
                    dto.getIdSesionComite(),
                    dto.getIdTramitePosesion()
            );
        }catch (Exception e){
            throw new ExcepcionPersonalizada("Error al actualizar",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public List<ComiteDetallesResponseDto> getComiteDetalles(Long idSesioncomite) {
        try {
            return resultadoComiteRepository.findComiteDetails(idSesioncomite);
        }catch (Exception e) {
            throw new ExcepcionPersonalizada("Error al listar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
