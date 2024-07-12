package co.siri.posesiones.services;

import co.siri.posesiones.dtos.ComiteDetallesResponseDto;
import co.siri.posesiones.dtos.ResultadoComiteRequestDTO;
import co.siri.posesiones.entities.TipoEstadoTramitePosesion;

import java.util.List;

public interface IVerTramiteEncomite  {
   List<TipoEstadoTramitePosesion> ListarrVerTramiteEncomite();
   public void actualizarResultadoComite(ResultadoComiteRequestDTO dto);
   public List<ComiteDetallesResponseDto> getComiteDetalles(Long idSesioncomite);
}
