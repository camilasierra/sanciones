package co.siri.posesiones.services;

import co.siri.posesiones.dtos.comiteII.TramitesEstadoDTO;

import java.util.List;

public interface ComiteTramiteEstadoService {
    List<TramitesEstadoDTO> tramitesEstado(String estado, Long idsesioncomite, Integer anotacionEsNula);
}
