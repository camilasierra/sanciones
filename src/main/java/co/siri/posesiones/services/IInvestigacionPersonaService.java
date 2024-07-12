package co.siri.posesiones.services;

import co.siri.posesiones.dtos.InvestigacionesPersonasDTO;

import java.util.List;

public  interface IInvestigacionPersonaService {
    List<InvestigacionesPersonasDTO> getInvestigacionPersona(Long idPersona);
}
