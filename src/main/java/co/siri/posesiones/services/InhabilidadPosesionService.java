package co.siri.posesiones.services;

import co.siri.posesiones.dtos.FirmaAuditoraProjection;
import co.siri.posesiones.dtos.InhabilidadPosesionDTO;
import co.siri.posesiones.dtos.InhabilidadPosesionProjection;
import co.siri.posesiones.dtos.TipoCargoDTO;
import co.siri.posesiones.entities.InhabilidadPosesion;
import co.siri.posesiones.entities.Tiposeccioninhabilidadcargo;
import co.siri.posesiones.entities.TramitePosesion;

import java.util.List;
import java.util.Map;

public interface InhabilidadPosesionService {

    List<InhabilidadPosesion> findAllByTipoSeccionInhabilidadCargo();

    List<Map<String, Object>> findInhabilidadPosesionByTramiteAndSeccion(Long idTramitePosesion);

    List<Map<String, Object>> findTramitesByCriteria();

    List<Map<String, Object>> findTipoCargoByIdTramitePosesion(Long idTramitePosesion);

    List<InhabilidadPosesionProjection> obtenerInhabilidadesComunesPorSeccionYTramite(List<Integer> idSecciones, int idTramite);

    List<InhabilidadPosesionProjection> obtenerInhabilidadesPorTramite(Long idTramite);

    List<FirmaAuditoraProjection> obtenerInformacionFirmaAuditoraPorTramite(Long idTramite);

    List<Map<String, Object>> getInhabilidadPosesion(int idTramitePosesion);

    List<Tiposeccioninhabilidadcargo> findAllTiposSeccion();

    List<Map<String, Object>> getTramitesPorNumeroRadicacion(String numeroRadicacion);

    List <Map<String, Object>> getSancionesenfirme(Long idPersona);

    List<InhabilidadPosesionProjection> getOtrasSituaciones(int idTramitePosesion);
    }
