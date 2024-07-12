package co.siri.posesiones.services;

import co.siri.posesiones.dtos.MensajeEnTramiteDto;
import co.siri.posesiones.dtos.MensajeRevisionTramiteDto;
import co.siri.posesiones.entities.MensajeRevisionTramite;

import java.util.List;

public interface IMensajeService {

    List<MensajeRevisionTramite> mensajeEnTramite(Long idTipoDestinoMensaje);

    List<MensajeRevisionTramite> mensajeBorrado();

    void guardarMensaje(MensajeRevisionTramiteDto mensajeRevisionTramite);

    void eliminarMensaje(Long idMensajeRevisionTramite);
}
