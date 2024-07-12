package co.siri.posesiones.services;

import co.siri.posesiones.dtos.OtroAsistenteComiteDTO;
import co.siri.posesiones.dtos.OtroAsistenteComiteRequestDTO;

import java.util.List;

public interface IOtroAsistenteComiteService {
    List<OtroAsistenteComiteDTO> otroAsistenteComite(Integer idSesionComite);
    void guardarOtroAsistente(OtroAsistenteComiteRequestDTO requestDTO);
}
