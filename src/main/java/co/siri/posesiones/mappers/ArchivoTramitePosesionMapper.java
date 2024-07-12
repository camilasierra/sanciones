package co.siri.posesiones.mappers;

import org.mapstruct.Mapper;
import co.siri.posesiones.dtos.ArchivoTramiteDto;
import co.siri.posesiones.entities.ArchivoTramitePosesion;

@Mapper(componentModel = "spring")
public interface ArchivoTramitePosesionMapper {
		
	public ArchivoTramitePosesion  toDomain(ArchivoTramiteDto archivoTramiteDto);
		
	public ArchivoTramiteDto  toDto(ArchivoTramitePosesion archivoTramitePosesion);

}
