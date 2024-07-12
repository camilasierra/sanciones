package co.siri.posesiones.services;

import co.siri.posesiones.dtos.*;
import co.siri.posesiones.entities.PrioridadTramitePosesion;

import java.util.List;

public interface IPrioridadService {
    List<PrioridadTramitePosesion> porPrioridades();
    List<TipoEntidaResponseDTO> porTipoEntidad();
    List<TipoEntidaResponseDTO> filtroporTipoEntidad(String tipoentidad);
    List<PrioridadEntidadResponseDTO> porEntidad();
    List<PrioridadEntidadResponseDTO> filtroporEntidad(String entidad);
    List<PrioridadPersonaResponseDTO> porPersona();
    List<PrioridadPersonaResponseDTO> filtroporPersona(String persona);
    List<PersonayEntidadResponseDTO> porPersonayEntidad();
    List<PersonayEntidadResponseDTO> filtroporPersonayEntidad(String personayentidad);
    void guardarPrioridad(PrioridadRequestDTO requestDTO);
    void guardarEntidadPublica(PrioridadRequestDTO requestDTO);
    void eliminarPrioridad(Integer Idtramite);

    List<PrioridadDTO> consultaPrioridades();
}
