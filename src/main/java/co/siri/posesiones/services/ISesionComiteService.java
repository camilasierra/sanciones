package co.siri.posesiones.services;

import co.siri.posesiones.dtos.MiembrosComiteDTO;

import java.util.List;

public interface ISesionComiteService {
    List<MiembrosComiteDTO> miembrosComite(Integer idSesionComite);

}
