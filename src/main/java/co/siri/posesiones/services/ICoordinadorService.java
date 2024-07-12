package co.siri.posesiones.services;

import co.siri.posesiones.dtos.*;

import java.util.List;

public interface ICoordinadorService {
    List<EscritorioTramitesResponseDTO> asignados(FiltroAvanzadoEscritorioDto filtro);
    List<EscritorioTramitesResponseDTO> noAsignados(FiltroAvanzadoEscritorioDto filtro);

    String asignacionAutomatica(AsignacionAutomaticaDTO asignacionAutomaticaDTO);
    InfoTramiteListAnalistasDTO infoTramiteAnalitas(Long idTramitePosesion);

    void asignacionManual(AsignacionManualDTO asignacionManualDTO);
}
