package co.siri.posesiones.services;

import co.siri.posesiones.dtos.EscritorioTramitesResponseDTO;
import co.siri.posesiones.dtos.FiltroAvanzadoEscritorioDto;

import java.util.List;

public interface IAnalistaService {
    List<EscritorioTramitesResponseDTO> listTramiteAnalista(FiltroAvanzadoEscritorioDto filtro);
}
