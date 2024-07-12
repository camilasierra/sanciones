package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.FirmaAuditoraProjection;
import co.siri.posesiones.dtos.InhabilidadPosesionDTO;
import co.siri.posesiones.dtos.InhabilidadPosesionProjection;
import co.siri.posesiones.dtos.TipoCargoDTO;
import co.siri.posesiones.entities.InhabilidadPosesion;
import co.siri.posesiones.entities.Tiposeccioninhabilidadcargo;
import co.siri.posesiones.entities.TramitePosesion;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.ActoAdministrativoRepository;
import co.siri.posesiones.repositories.InhabilidadPosesionRepository;
import co.siri.posesiones.repositories.TiposeccioninhabilidadcargoRepository;
import co.siri.posesiones.repositories.TramitePosesionRepository;
import co.siri.posesiones.services.InhabilidadPosesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InhabilidadPosesionServiceImpl implements InhabilidadPosesionService {

    private final InhabilidadPosesionRepository inhabilidadPosesionRepository;

    private final TramitePosesionRepository tramitePosesionRepository;

    private final TiposeccioninhabilidadcargoRepository tiposeccioninhabilidadcargoRepository;

    private final ActoAdministrativoRepository actoAdministrativoRepository;
    @Autowired
    public InhabilidadPosesionServiceImpl(InhabilidadPosesionRepository inhabilidadPosesionRepository, TramitePosesionRepository tramitePosesionRepository, TiposeccioninhabilidadcargoRepository tiposeccioninhabilidadcargoRepository, ActoAdministrativoRepository actoAdministrativoRepository) {
        this.inhabilidadPosesionRepository = inhabilidadPosesionRepository;
        this.tramitePosesionRepository = tramitePosesionRepository;
        this.tiposeccioninhabilidadcargoRepository = tiposeccioninhabilidadcargoRepository;
        this.actoAdministrativoRepository = actoAdministrativoRepository;
    }

    @Override
    public List<Tiposeccioninhabilidadcargo> findAllTiposSeccion() {
        return tiposeccioninhabilidadcargoRepository.findAll();
    }

    @Override
    public List<Map<String, Object>> findTramitesByCriteria() {
        return tramitePosesionRepository.findTramitesByCriteria();
    }

    @Override
    public List<InhabilidadPosesion> findAllByTipoSeccionInhabilidadCargo() {
        try {
            return inhabilidadPosesionRepository.findAllByTipoSeccionInhabilidadCargo();
        } catch (Exception ex) {
            throw new ExcepcionPersonalizada("Error al buscar todas las inhabilidades por tipo de sección", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Map<String, Object>> findInhabilidadPosesionByTramiteAndSeccion(Long idTramitePosesion) {
        try {
            return inhabilidadPosesionRepository.findInhabilidadPosesionByTramiteAndSeccion(idTramitePosesion);
        } catch (Exception ex) {

            throw new ExcepcionPersonalizada("Error al buscar inhabilidades por tramite y sección", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Map<String, Object>>  findTipoCargoByIdTramitePosesion(Long idTramitePosesion) {
        try {
            return inhabilidadPosesionRepository.findTipoCargosByTramitePosesionId(idTramitePosesion);
        } catch (Exception ex) {
            throw new ExcepcionPersonalizada("Error al buscar el tipo de cargo por ID de trámite de posesión", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<InhabilidadPosesionProjection> obtenerInhabilidadesComunesPorSeccionYTramite(List<Integer> idSecciones, int idTramite) {
        try {
            return inhabilidadPosesionRepository.obtenerInhabilidadesComunesPorSeccionYTramite(idSecciones, idTramite);

        } catch (Exception ex) {
            throw new ExcepcionPersonalizada("Error al buscar el tipo de Tramite y Secciones", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<InhabilidadPosesionProjection>
    obtenerInhabilidadesPorTramite(Long idTramite) {
        try {
            return inhabilidadPosesionRepository.obtenerInhabilidadesPorTramite(idTramite);
        } catch (Exception ex) {
            throw new ExcepcionPersonalizada("Error al obtner inhabilidades por el tramite", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<FirmaAuditoraProjection> obtenerInformacionFirmaAuditoraPorTramite(Long idTramite) {

        try {
            return tramitePosesionRepository.obtenerInformacionFirmaAuditoraPorTramite(idTramite);
        }catch (Exception ex) {
            throw new ExcepcionPersonalizada("Error al obtner firma auditor por tramite", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public List<Map<String, Object>> getInhabilidadPosesion(int idTramitePosesion) {
        try {
            return inhabilidadPosesionRepository.findInhabilidadPosesion(idTramitePosesion);
        } catch (DataAccessException ex) {
            throw new ExcepcionPersonalizada("Error al obtener las inhabilidades de posesión", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Map<String, Object>> getTramitesPorNumeroRadicacion(String numeroRadicacion) {
        return tramitePosesionRepository.findByNumeroRadicacionContaining(numeroRadicacion);
    }

    @Override
    public List <Map<String, Object>> getSancionesenfirme(Long idPersona){
            return actoAdministrativoRepository.findActosAdministrativosByPersona(idPersona);
    }

    @Override
    public List<InhabilidadPosesionProjection> getOtrasSituaciones(int idTramitePosesion){
       return inhabilidadPosesionRepository.GetOtrasSituaciones(idTramitePosesion);
    }
}
