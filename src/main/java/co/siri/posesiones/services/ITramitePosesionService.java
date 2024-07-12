package co.siri.posesiones.services;

import java.sql.SQLException;
import java.util.List;
import co.siri.posesiones.dtos.*;
import co.siri.posesiones.dtos.ArchivoTramiteDto;
import co.siri.posesiones.dtos.TramitePosesionDto;
import co.siri.posesiones.entities.TipoEstadoTramitePosesion;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;

public interface ITramitePosesionService {

	List<TramitePosesionDto> getTramites(String tipoBusqueda, String busqueda);

	DatosBasicosDto consultarDatosBasicos(Long idTramitePosesion);

	InfoNombramientoDto consultarInformacionNombramiento(Long idTramitePosesion);

	InfoServidorPublico consultarInformacionServidorPublico(Long idTramitePosesion);

	InfoOtrosRepresentantes consultarInformacionOtrosRepresentantes(Long idTramitePosesion);

	InfoDesignacionDto consultarInformacionDesignacion(Long idTramitePosesion);

	InfoContactoEntidad consultarInformacionContactoEntidad(Long idTramitePosesion);

	InfoJuntaDirectiva consultarInformacionJuntaDirectiva(Long idTramitePosesion);

	InfoAdicionalDefensorConsumidor consultarInformacionAdicionalDefensorConsumidor(Long idTramitePosesion);

	
	
   
	
	List<InfoAnexoDto> consultarInformacionAnexos(Long idTramitePosesion);
	
	List<TipoEstadoTramitePosesion> getAllActiveTipoEstadoTramitePosesion();

    void changeEstadoTramite(CambiarEstadoTramitePosesionRequestDto request);
    
    /**
     * Servicio de obtener un archivo tramite posesion por tipo e id tramite 
     * @param idTramitePosesion
     * @return
     */
    ArchivoTramiteDto getArchivoTramite(Long idTramitePosesion);
    
     /* Servicio de guardar un archivo tramite posesion
     * @param archivoTramiteDto
     * @return
     */
    ArchivoTramiteDto guardarArchivoTramite(ArchivoTramiteDto archivoTramiteDto);
    String devolverTramite(CambiarEstadoTramitePosesionRequestDto request);

	/**
	 * Actualizar estado de un trámite de posesión.
	 *
	 * @param idTramite Id del trámite
	 * @param idEstadoTramite Id del estado del trámite
	 * @return String actualización
	 * @throws ExcepcionPersonalizada si ocurre un error durante el guardado del trámite de posesión
	 */
	String actualizarEstadoTramite(Long idTramite, Long idEstadoTramite) throws ExcepcionPersonalizada;
}
