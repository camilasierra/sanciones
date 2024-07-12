package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.NotificacionTramiteDto;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.NotificacionRepository;
import co.siri.posesiones.services.INotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService implements INotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    /**
     * Servicio para obtener las notificaciones por el id del tramite y el rol
     * @param idTramitePosesion, idAanalistaTramitePosesion
     * @return
     * @throws ExcepcionPersonalizada
     */
    @Override
    public List<NotificacionTramiteDto> listAsignacion(Long idTramitePosesion, Long idAanalistaTramitePosesion) {
        try {
            return notificacionRepository.listAsignacionTramite(idTramitePosesion, idAanalistaTramitePosesion);
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Servicio para obtener las notifaciones del rol
     * @param idanalistatramiteposesion
     * @return
     * @throws ExcepcionPersonalizada
     */

    @Override
    public List<NotificacionTramiteDto> listAsignacionRol(Long idanalistatramiteposesion) {
        try {
            return notificacionRepository.listNotificacionTramite(idanalistatramiteposesion);
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
