package co.siri.posesiones.services;

import co.siri.posesiones.dtos.ComentariosTramiteRequestDTO;
import co.siri.posesiones.dtos.MensajeRevisionResponseDTO;
import java.util.List;

public interface IMensajeRevisionTramiteService {
    List<MensajeRevisionResponseDTO> getMensajesRevisionTramite(int idTramitePosesion);

    void saveComentariosTramite (ComentariosTramiteRequestDTO requestDTO);
}
