package co.siri.posesiones.services;


import java.util.List;

import co.siri.posesiones.dtos.ActualizarAnalistaDTO;
import co.siri.posesiones.dtos.AgregarAnalistaDTO;
import co.siri.posesiones.entities.AnalistaTramitePosesion;




public interface IGestionAnalistasService {
    List<?> listarAnalistas();
    AnalistaTramitePosesion agregarAnalista(AgregarAnalistaDTO agregarAnalistaDTO);
    AnalistaTramitePosesion actualizarAnalista(ActualizarAnalistaDTO actualizarAnalistaDTO);

}
