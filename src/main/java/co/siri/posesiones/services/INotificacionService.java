package co.siri.posesiones.services;

import co.siri.posesiones.dtos.NotificacionTramiteDto;
import co.siri.posesiones.entities.AsignacionTramiteAnalista;
import co.siri.posesiones.entities.MensajeRevisionTramite;

import java.util.List;

public interface INotificacionService {

    List<NotificacionTramiteDto> listAsignacion(Long idTramitePosesion, Long idAanalistaTramitePosesion);

    List<NotificacionTramiteDto> listAsignacionRol(Long idanalistatramiteposesion);
}
