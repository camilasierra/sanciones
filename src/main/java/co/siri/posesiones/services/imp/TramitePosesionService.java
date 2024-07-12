package co.siri.posesiones.services.imp;

import java.util.List;
import java.util.Objects;
import java.sql.SQLException;
import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import co.siri.posesiones.dtos.ArchivoTramiteDto;
import co.siri.posesiones.dtos.EscritorioTramitesResponseDTO;
import co.siri.posesiones.dtos.TramitePosesionDto;
import co.siri.posesiones.entities.AntecedenteTramitePosesion;
import co.siri.posesiones.entities.ArchivoTramitePosesion;
import co.siri.posesiones.entities.TipoConvenioAntecedente;
import co.siri.posesiones.entities.TramitePosesion;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.mappers.ArchivoTramitePosesionMapper;
import co.siri.posesiones.repositories.AntecedenteTramitePosesionRepository;
import co.siri.posesiones.repositories.ArchivoTramitePosesionRepository;
import co.siri.posesiones.repositories.TipoConvenioAntecedenteRepository;
import co.siri.posesiones.repositories.TramitePosesionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.siri.posesiones.dtos.CambiarEstadoTramitePosesionRequestDto;
import co.siri.posesiones.dtos.DatosBasicosDto;
import co.siri.posesiones.dtos.InfoAdicionalDefensorConsumidor;
import co.siri.posesiones.dtos.InfoAnexoDto;
import co.siri.posesiones.dtos.InfoContactoEntidad;
import co.siri.posesiones.dtos.InfoDesignacionDto;
import co.siri.posesiones.dtos.InfoJuntaDirectiva;
import co.siri.posesiones.dtos.InfoNombramientoDto;
import co.siri.posesiones.dtos.InfoOtrosRepresentantes;
import co.siri.posesiones.dtos.InfoServidorPublico;
import co.siri.posesiones.dtos.TramitePosesionDto;
import co.siri.posesiones.entities.ArchivoTramitePosesion;
import co.siri.posesiones.entities.DocumentoPersona;
import co.siri.posesiones.entities.Domicilio;
import co.siri.posesiones.entities.Entidad;
import co.siri.posesiones.entities.Persona;
import co.siri.posesiones.entities.TipoActividadRepresentante;
import co.siri.posesiones.entities.TipoCalidadCargo;
import co.siri.posesiones.entities.TipoCalidadJuntaDirectiva;
import co.siri.posesiones.entities.TipoCargo;
import co.siri.posesiones.entities.TipoClaseRepresentante;
import co.siri.posesiones.entities.TipoDocumentoLegal;
import co.siri.posesiones.entities.TipoEstadoTramitePosesion;
import co.siri.posesiones.entities.TipoMotivoRetiro;
import co.siri.posesiones.entities.TipoOrganoDesignador;
import co.siri.posesiones.entities.TipoPatrocinadorPosesion;
import co.siri.posesiones.entities.TipoTramitePosesion;
import co.siri.posesiones.entities.TipoVinculoContractual;
import co.siri.posesiones.entities.TramitePosesion;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.ArchivoTramitePosesionRepository;
import co.siri.posesiones.repositories.CargoRepository;
import co.siri.posesiones.repositories.DocumentoPersonaRepository;
import co.siri.posesiones.repositories.DomicilioRepository;
import co.siri.posesiones.repositories.EntidadRepository;
import co.siri.posesiones.repositories.PersonaRepository;
import co.siri.posesiones.repositories.ResultadoComiteRepository;
import co.siri.posesiones.repositories.TipoActividadRepresentanteRepository;
import co.siri.posesiones.repositories.TipoCalidadCargoRepository;
import co.siri.posesiones.repositories.TipoCalidadJuntaDirectivaRepository;
import co.siri.posesiones.repositories.TipoClaseRepresentanteRepository;
import co.siri.posesiones.repositories.TipoDocumentoLegalRepository;
import co.siri.posesiones.repositories.TipoEstadoTramitePosesionRepository;
import co.siri.posesiones.repositories.TipoMotivoRetiroRepository;
import co.siri.posesiones.repositories.TipoOrganoDesignadorRepository;
import co.siri.posesiones.repositories.TipoPatrocinadorPosesionRepository;
import co.siri.posesiones.repositories.TipoTramitePosesionRepository;
import co.siri.posesiones.repositories.TipoVinculoContractualRepository;
import co.siri.posesiones.repositories.TramitePosesionRepository;
import co.siri.posesiones.services.IGeneradorDocumentoPlantillaService;
import co.siri.posesiones.services.ITramitePosesionService;
import co.siri.posesiones.utilidades.Constantes;
import co.siri.posesiones.utilidades.Utilidades;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
 


@Slf4j
@Service
public class TramitePosesionService implements ITramitePosesionService {

	public static final String ERRROR_TRAMITE_NO_ENCONTRADO = "No se encontro un tramite correspondiente al id solicitado";

	private final TramitePosesionRepository tramitePosesionRepository;

	private final ArchivoTramitePosesionRepository archivoTramitePosesionRepository;

	private final PersonaRepository personaRepository;

	private final DocumentoPersonaRepository documentoPersonaRepository;

	private final CargoRepository cargoRepository;

	private final EntidadRepository entidadRepository;

	private final TipoTramitePosesionRepository tipoTramitePosesionRepository;

	private final TipoCalidadCargoRepository tipoCalidadCargoRepository;

	private final TipoMotivoRetiroRepository tipoMotivoRetiroRepository;

	private final TipoOrganoDesignadorRepository tipoOrganoDesignadorRepository;

	private final TipoActividadRepresentanteRepository tipoActividadRepresentanteRepository;

	private final TipoClaseRepresentanteRepository tipoClaseRepresentanteRepository;

	private final TipoDocumentoLegalRepository tipoDocumentoLegalRepository;

	private final TipoPatrocinadorPosesionRepository tipoPatrocinadorPosesionRepository;

	private final TipoVinculoContractualRepository tipoVinculoContractualRepository;

	private final DomicilioRepository domicilioRepository;

	private final TipoCalidadJuntaDirectivaRepository tipoCalidadJuntaDirectivaRepository;

	private final TipoEstadoTramitePosesionRepository tipoEstadoTramitePosesionRepository;
	
	private final AntecedenteTramitePosesionRepository antecedenteTramitePosesionRepository;
    private final ArchivoTramitePosesionMapper archivoTramitePosesionMapper;
    private final TipoConvenioAntecedenteRepository tipoConvenioAntecedenteRepository;

	private final ResultadoComiteRepository resultadoComiteRepository;
	
	private final IGeneradorDocumentoPlantillaService generadorDocumentoPlantillaService;

	public TramitePosesionService(TramitePosesionRepository tramitePosesionRepository,PersonaRepository personaRepository,DocumentoPersonaRepository documentoPersonaRepository,
			CargoRepository cargoRepository,EntidadRepository entidadRepository,TipoTramitePosesionRepository tipoTramitePosesionRepository,
			TipoCalidadCargoRepository tipoCalidadCargoRepository,TipoMotivoRetiroRepository tipoMotivoRetiroRepository,
			TipoOrganoDesignadorRepository tipoOrganoDesignadorRepository, TipoActividadRepresentanteRepository tipoActividadRepresentanteRepository,
			TipoClaseRepresentanteRepository tipoClaseRepresentanteRepository,TipoDocumentoLegalRepository tipoDocumentoLegalRepository,
			  TipoPatrocinadorPosesionRepository tipoPatrocinadorPosesionRepository,TipoVinculoContractualRepository tipoVinculoContractualRepository,
			  DomicilioRepository domicilioRepository, TipoCalidadJuntaDirectivaRepository tipoCalidadJuntaDirectivaRepository, TipoEstadoTramitePosesionRepository tipoEstadoTramitePosesionRepository,
								  ArchivoTramitePosesionRepository archivoTramitePosesionRepository,
								  AntecedenteTramitePosesionRepository antecedenteTramitePosesionRepository,
    						ArchivoTramitePosesionMapper archivoTramitePosesionMapper,
    						TipoConvenioAntecedenteRepository tipoConvenioAntecedenteRepository,
							 ResultadoComiteRepository resultadoComiteRepository, IGeneradorDocumentoPlantillaService generadorDocumentoPlantillaService) {
		this.tramitePosesionRepository = tramitePosesionRepository;
		this.personaRepository = personaRepository;
		this.documentoPersonaRepository = documentoPersonaRepository;
		this.cargoRepository = cargoRepository;
		this.entidadRepository = entidadRepository;
		this.tipoTramitePosesionRepository = tipoTramitePosesionRepository;
		this.tipoCalidadCargoRepository = tipoCalidadCargoRepository;
		this.tipoMotivoRetiroRepository = tipoMotivoRetiroRepository;
		this.tipoOrganoDesignadorRepository = tipoOrganoDesignadorRepository;
		this.tipoActividadRepresentanteRepository = tipoActividadRepresentanteRepository;
		this.tipoClaseRepresentanteRepository = tipoClaseRepresentanteRepository;
		this.tipoDocumentoLegalRepository = tipoDocumentoLegalRepository;
		this.tipoPatrocinadorPosesionRepository = tipoPatrocinadorPosesionRepository;
		this.tipoVinculoContractualRepository = tipoVinculoContractualRepository;
		this.domicilioRepository = domicilioRepository;
		this.tipoCalidadJuntaDirectivaRepository = tipoCalidadJuntaDirectivaRepository;
		this.tipoEstadoTramitePosesionRepository = tipoEstadoTramitePosesionRepository;
		this.archivoTramitePosesionRepository = archivoTramitePosesionRepository;
		this.antecedenteTramitePosesionRepository = antecedenteTramitePosesionRepository;
		this.archivoTramitePosesionMapper = archivoTramitePosesionMapper;
		this.tipoConvenioAntecedenteRepository = tipoConvenioAntecedenteRepository;
		this.resultadoComiteRepository = resultadoComiteRepository;
		this.generadorDocumentoPlantillaService = generadorDocumentoPlantillaService;
	}

	@Override
	public List<TramitePosesionDto> getTramites(String tipoBusqueda, String busqueda) {
		switch (tipoBusqueda) {
		case "1":
			return tramitePosesionRepository.findTramitesByNumeroIdentificacion(busqueda);
		case "2":
			return tramitePosesionRepository.findTramitesByRadicado(busqueda);
		case "3":
			return tramitePosesionRepository.findTramitesByCandidato(busqueda.toLowerCase());
		case "4":
			return tramitePosesionRepository.findTramitesByEntidad(busqueda.toLowerCase());
		default:
			return null;
		}
	}

	@Override
	public DatosBasicosDto consultarDatosBasicos(Long idTramitePosesion) {
		try {
			TramitePosesion tramitePosesion = tramitePosesionRepository.findById(idTramitePosesion).orElse(null);
			if(null == tramitePosesion) {
				throw new ExcepcionPersonalizada(ERRROR_TRAMITE_NO_ENCONTRADO, HttpStatus.NOT_FOUND);
			}
			Persona candidato = personaRepository.findById(tramitePosesion.getIdPersona()).orElse(null);
			if(null == candidato) {
				throw new ExcepcionPersonalizada("No se encontro un candidato correspondiente al tramite", HttpStatus.NOT_FOUND);
			}
			DocumentoPersona documentoPersona = documentoPersonaRepository.findByIdPersona(candidato.getIdPersona()).orElse(null);
			if(null == documentoPersona) {
				throw new ExcepcionPersonalizada("No se encontro un documento correspondiente al candidato", HttpStatus.NOT_FOUND);
			}
			TipoCargo tipoCargo = cargoRepository.findById(tramitePosesion.getIdTipoCargo()).orElse(null);
			Entidad entidad = entidadRepository.findById(tramitePosesion.getIdEntidad()).orElse(null);
			return DatosBasicosDto.builder()
					.nombreCandidato(Utilidades.obtenerNombrePersona(candidato))
					.numeroIdentificacion(documentoPersona.getNumero())
					.cargoRealizar(null == tipoCargo ? "" : tipoCargo.getNombre())
					.servidorPublico(tramitePosesion.getServidorpublico() == Boolean.TRUE ? "Si" : "No")
					.entidad(null == entidad ? "" : entidad.getSigla())
					.build();
		}catch(ExcepcionPersonalizada e) {
			throw new ExcepcionPersonalizada(e.getMessage(), e.getHttpStatus());
		}
	}

	@Override
	public InfoNombramientoDto consultarInformacionNombramiento(Long idTramitePosesion) {
		try {
			TramitePosesion tramitePosesion = tramitePosesionRepository.findById(idTramitePosesion).orElse(null);
			if(null == tramitePosesion) {
				throw new ExcepcionPersonalizada(ERRROR_TRAMITE_NO_ENCONTRADO, HttpStatus.NOT_FOUND);
			}
			TipoTramitePosesion tipoTramitePosesion = tipoTramitePosesionRepository.findById(tramitePosesion.getIdTipoTramitePosesion()).orElse(null);
			TipoCargo tipoCargo = cargoRepository.findById(tramitePosesion.getIdTipoCargo()).orElse(null);
			TipoCalidadCargo tipoCalidadCargo = tipoCalidadCargoRepository.findById(tramitePosesion.getIdTipoCalidadCargo()).orElse(null);
			String[] datosPersonaReemplazada = obtenerDatosPersonaReemplazar(tramitePosesion);
			String motivoRetiro = "";
			if(tramitePosesion.getIdTipoMotivoRetiro() != null) {
				TipoMotivoRetiro tipoMotivoRetiro = tipoMotivoRetiroRepository.findById(tramitePosesion.getIdTipoMotivoRetiro()).orElse(null);
				motivoRetiro = null == tipoMotivoRetiro ? "" : tipoMotivoRetiro.getNombre();
			}
			TipoOrganoDesignador tipoOrganoDesignador = tramitePosesion.getIdTipoOrganoDesignador() != null
					? tipoOrganoDesignadorRepository.findById(tramitePosesion.getIdTipoOrganoDesignador()).orElse(null)
					: null;
			return InfoNombramientoDto.builder()
					.claseNombramiento(null == tipoTramitePosesion ? "" : tipoTramitePosesion.getNombre())
					.cargoPostulado(null == tipoCargo ? "" : tipoCargo.getNombre())
					.cargoDesempenar(String.format("%s %s",null == tipoCargo ? "" : tipoCargo.getNombre(), null == tipoCalidadCargo ? "" : tipoCalidadCargo.getNombre()))
					.calidadCargo(null == tipoCalidadCargo ? "" : tipoCalidadCargo.getNombre())
					.personaReemplazar(datosPersonaReemplazada[0])
					.numeroIdentificacion(datosPersonaReemplazada[1])
					.motivoReemplazo(motivoRetiro)
					.organoDesigno(null == tipoOrganoDesignador ? "" : tipoOrganoDesignador.getNombre())
					.build();
		}catch(ExcepcionPersonalizada e) {
			throw new ExcepcionPersonalizada(e.getMessage(), e.getHttpStatus());
		}
	}

	@Override
	public InfoServidorPublico consultarInformacionServidorPublico(Long idTramitePosesion) {
		try {
			TramitePosesion tramitePosesion = tramitePosesionRepository.findById(idTramitePosesion).orElse(null);
			if(null == tramitePosesion) {
				throw new ExcepcionPersonalizada(ERRROR_TRAMITE_NO_ENCONTRADO, HttpStatus.NOT_FOUND);
			}else {
				return InfoServidorPublico.builder()
						.cargoServidorPublico(null == tramitePosesion.getCargoservidorpublico() ? Strings.EMPTY : tramitePosesion.getCargoservidorpublico())
						.entidadServidorPublico(null == tramitePosesion.getEntidadservidorpublico() ? Strings.EMPTY : tramitePosesion.getEntidadservidorpublico())
						.build();
			}
		}catch (ExcepcionPersonalizada e) {
			throw new ExcepcionPersonalizada(e.getMessage(), e.getHttpStatus());
		}
	}

	@Override
	public InfoOtrosRepresentantes consultarInformacionOtrosRepresentantes(Long idTramitePosesion) {
		try {
			TipoClaseRepresentante tipoClaseRepresentante = new TipoClaseRepresentante();
			TipoActividadRepresentante tipoActividadRepresentante = new TipoActividadRepresentante();
			TramitePosesion tramitePosesion = tramitePosesionRepository.
					findByIdTramitePosesionAndIdTipoCargoAndIdTipoCalidadCargo(idTramitePosesion, 1L, 3L).orElse(null);
			if(null == tramitePosesion) {
				return new InfoOtrosRepresentantes(Strings.EMPTY, Strings.EMPTY);
			}
			if(null != tramitePosesion.getIdTipoActividadRepresentante() && null != tramitePosesion.getIdTipoClaseRepresentante()) {
				tipoClaseRepresentante = tipoClaseRepresentanteRepository.
						findById(tramitePosesion.getIdTipoClaseRepresentante()).orElse(null);
				tipoActividadRepresentante = tipoActividadRepresentanteRepository.
						findById(tramitePosesion.getIdTipoActividadRepresentante()).orElse(null);
			}
			return InfoOtrosRepresentantes.builder()
					.claseRepresentante(null == tipoClaseRepresentante || null == tipoClaseRepresentante.getNombre() ?
							Strings.EMPTY : tipoClaseRepresentante.getNombre())
					.actividadRepresentante(null == tipoActividadRepresentante || null == tipoActividadRepresentante.getNombre() ?
							Strings.EMPTY : tipoActividadRepresentante.getNombre())
					.build();
		}catch (ExcepcionPersonalizada e) {
			throw new ExcepcionPersonalizada(e.getMessage(), e.getHttpStatus());
		}
	}

	@Override
	public InfoDesignacionDto consultarInformacionDesignacion(Long idTramitePosesion) {
		try {
			TramitePosesion tramitePosesion = tramitePosesionRepository.findById(idTramitePosesion).orElse(null);
			if(null == tramitePosesion) {
				throw new ExcepcionPersonalizada(ERRROR_TRAMITE_NO_ENCONTRADO, HttpStatus.NOT_FOUND);
			}else {
				TipoDocumentoLegal tipoDocumentoLegal = tipoDocumentoLegalRepository.findById(tramitePosesion.getIdTipoDocumentoDesignacion()).orElse(null);
				TipoPatrocinadorPosesion tipoPatrocinadorPosesion = tramitePosesion.getIdTipoPatrocinadorPosesion() != null
						? tipoPatrocinadorPosesionRepository.findById(tramitePosesion.getIdTipoPatrocinadorPosesion()).orElse(null)
						: null;

				TipoVinculoContractual tipoVinculoContractual = tramitePosesion.getIdTipoVinculoContractual() != null
						? tipoVinculoContractualRepository.findById(tramitePosesion.getIdTipoVinculoContractual()).orElse(null)
						: null;

				String candidatizadoPor = "";
				if(null == tramitePosesion.getIdCandidatizadoPor()) {
					candidatizadoPor = tramitePosesion.getCandidatizadoPor();
				}else {
					Persona personaPostulo = personaRepository.findById(tramitePosesion.getIdCandidatizadoPor()).orElse(null);
					if(null != personaPostulo) {
						candidatizadoPor = Utilidades.obtenerNombrePersona(personaPostulo);
					}
				}
				String certificacion = "";
				if(null != tramitePosesion.getCertificadoVigenteAmv()) {
					certificacion = tramitePosesion.getCertificadoVigenteAmv().booleanValue() ? Constantes.SI : Constantes.NO;
				}
				return InfoDesignacionDto.builder()
						.documentoDesignacion(null == tipoDocumentoLegal ? "" : tipoDocumentoLegal.getNombre())
						.numeroDocumentoDesignacion(tramitePosesion.getNumeroDocumentoDesignacion())
						.fechaDocumentoDesignacion(Utilidades.formatearFecha(tramitePosesion.getFechaDocumentoDesignacion(), Constantes.FORMATO_YYYY_MM_DD))
						.nombreCandidatizo(candidatizadoPor)
						.enRepresentacion(null == tipoPatrocinadorPosesion ? "" : tipoPatrocinadorPosesion.getNombre())
						.fechaAceptacion(Utilidades.formatearFecha(tramitePosesion.getFechaAceptacion(), Constantes.FORMATO_YYYY_MM_DD))
						.vinculoEntidad(null == tipoVinculoContractual ? "" : tipoVinculoContractual.getNombre())
						.certificacionAmv(certificacion)
						.conflictoInteres(tramitePosesion.getConflictoInteres().booleanValue() ? Constantes.SI : Constantes.NO)
						.horasMensualesDedicacion(String.format("%s %s", tramitePosesion.getHorasDedicacionMensual().toString(),Constantes.HORAS))
						.build();
			}
		}catch(ExcepcionPersonalizada e) {
			throw new ExcepcionPersonalizada(e.getMessage(), e.getHttpStatus());
		}
	}

	@Override
	public InfoContactoEntidad consultarInformacionContactoEntidad(Long idTramitePosesion) {
		try {
			TramitePosesion tramitePosesion = tramitePosesionRepository.findById(idTramitePosesion).orElse(null);
			if(null == tramitePosesion) {
				throw new ExcepcionPersonalizada(ERRROR_TRAMITE_NO_ENCONTRADO, HttpStatus.NOT_FOUND);
			}else {
				Domicilio domicilioEntidad = domicilioRepository.findById(tramitePosesion.getIdDomicilioNotificacionEntidad()).orElse(null);
				return InfoContactoEntidad.builder()
						.personaResponsable(tramitePosesion.getNombreResponsableTramite())
						.cargo(tramitePosesion.getCargoResponsableTramite())
						.emailResponsableEntidad(null == domicilioEntidad ? "" : domicilioEntidad.getEmail())
						.telNotificacion(null == domicilioEntidad ? "" : domicilioEntidad.getTelefono())
						.extension(Constantes.EXTENSION_NA)
						.direccionNotificacion(null == domicilioEntidad ? "" : domicilioEntidad.getDireccion())
						.notificacionElectronica(tramitePosesion.getNotificacionElectronica().booleanValue() ? Constantes.SI : Constantes.NO)
						.emailNotificacion(tramitePosesion.getEmailNotificacionPostulado())
						.build();
			}
		}catch(ExcepcionPersonalizada e) {
			throw new ExcepcionPersonalizada(e.getMessage(), e.getHttpStatus());
		}
	}

	@Override
	public InfoJuntaDirectiva consultarInformacionJuntaDirectiva(Long idTramitePosesion) {
		try {
			TipoCalidadJuntaDirectiva tipoCalidadJuntaDirectiva = new TipoCalidadJuntaDirectiva();
			TramitePosesion tramitePosesion = tramitePosesionRepository.
					findByIdTramitePosesionAndIdTipoCargo(idTramitePosesion, 2L).orElse(null);
			if(null == tramitePosesion) {
				return new InfoJuntaDirectiva(Strings.EMPTY, Strings.EMPTY);
			}
			if(null != tramitePosesion.getIdTipoCalidadJuntaDirectiva()) {
				tipoCalidadJuntaDirectiva = tipoCalidadJuntaDirectivaRepository
						.findById(tramitePosesion.getIdTipoCalidadJuntaDirectiva()).orElse(null);
			}
			return InfoJuntaDirectiva.builder()
					.calidadJuntaDirectiva(null == tipoCalidadJuntaDirectiva || null == tipoCalidadJuntaDirectiva.getNombre() ?
							Strings.EMPTY : tipoCalidadJuntaDirectiva.getNombre())
					.renglonJuntaDirectiva(null == tramitePosesion.getRenglonJunta() ?
							Strings.EMPTY : tramitePosesion.getRenglonJunta().toString())
					.build();

		}catch (ExcepcionPersonalizada e) {
			throw new ExcepcionPersonalizada(e.getMessage(), e.getHttpStatus());
		}
	}

	@Override
	public InfoAdicionalDefensorConsumidor consultarInformacionAdicionalDefensorConsumidor(Long idTramitePosesion) {
		try {
			InfoAdicionalDefensorConsumidor infoAdicionalDefensorConsumidor = tramitePosesionRepository.findDefensorConsumidor(idTramitePosesion);
            return Objects.requireNonNullElseGet(infoAdicionalDefensorConsumidor, () -> new InfoAdicionalDefensorConsumidor(
					Strings.EMPTY, Strings.EMPTY, Strings.EMPTY,
                    Strings.EMPTY, Strings.EMPTY, Strings.EMPTY,
                    Strings.EMPTY, Strings.EMPTY, Strings.EMPTY, Strings.EMPTY));

		}catch (ExcepcionPersonalizada e) {
			throw new ExcepcionPersonalizada(e.getMessage(), e.getHttpStatus());
		}
	}

	private String[] obtenerDatosPersonaReemplazar(TramitePosesion tramitePosesion) {
		String nombrePersonaReemplazada = "";
		String documentoPersonaReemplazada = "";
		if(tramitePosesion.getIdPersonaReemplazada() != null) {
			Persona personaReemplazada = personaRepository.findById(tramitePosesion.getIdPersonaReemplazada()).orElse(null);
			if(null == personaReemplazada) {
				throw new ExcepcionPersonalizada("No se encontro una persona a reemplazar correspondiente al tramite", HttpStatus.NOT_FOUND);
			}
			DocumentoPersona documentoPersona = documentoPersonaRepository.findByIdPersona(personaReemplazada.getIdPersona()).orElse(null);
			nombrePersonaReemplazada = Utilidades.obtenerNombrePersona(personaReemplazada);
			documentoPersonaReemplazada = null == documentoPersona ? "" : documentoPersona.getNumero();
		}
		return new String[] {nombrePersonaReemplazada,documentoPersonaReemplazada};
	}

	    /**
     * Obtiene todos los tipos de estado de trámite de posesión activos
     * 
     * @return Lista de tipos de estado de trámite de posesión activos
     */
    @Override
    public List<TipoEstadoTramitePosesion> getAllActiveTipoEstadoTramitePosesion() {
        return tipoEstadoTramitePosesionRepository.findAllActive();
    }

    /**
     * Cambia el estado de un trámite de posesión
     * 
     * @param request Datos de la solicitud de cambio de estado
     */
    @Override
    public void changeEstadoTramite(CambiarEstadoTramitePosesionRequestDto request) {
        Optional<TramitePosesion> optionalTramite = tramitePosesionRepository.findById(request.getIdTramitePosesion());
        if (optionalTramite.isPresent()) {
            if(optionalTramite.get().getIdTipoEstadoTramitePosesion()==5) {
            	try {
            		resultadoComiteRepository.deleteByidtramiteposesion(optionalTramite.get().getIdTramitePosesion().intValue());
                    	
            	}catch(Exception e){
            		log.info("no fue posible borrar el registro con idTramitePosesion ",optionalTramite.get().getIdTramitePosesion().intValue());
            		
            	}
            }
            TramitePosesion tramite = optionalTramite.get();
            tramite.setIdTipoEstadoTramitePosesion(request.getIdTipoEstadoTramitePosesionNew());
            tramite.setIdUsuario(request.getIdUsuario());
            tramite.setIpCliente(request.getIp());
            if(tramite.getIdTipoEstadoTramitePosesion() == 1) {
            	tramite.setFechaUltimaDevolucion(new Date());
            }
            tramitePosesionRepository.save(tramite);

        }
    }
    
    @Override
    public ArchivoTramiteDto getArchivoTramite(Long idTramitePosesion){
    	System.out.println("llego");
    	
    	List<AntecedenteTramitePosesion> antecedenteTramitePosesion = antecedenteTramitePosesionRepository.obtenerAntecedentePorTramite(idTramitePosesion, Constantes.ID_TIPO_CONVENIO_PARA_ANTECEDENTE_TRAMITE_POSESION);
    	System.out.println("llego al sql " + antecedenteTramitePosesion);
    	if(null == antecedenteTramitePosesion || antecedenteTramitePosesion.isEmpty()) {
			throw new ExcepcionPersonalizada("Error, no existe un antecedente con ese id tramite ", HttpStatus.INTERNAL_SERVER_ERROR);
		}else if(1 < antecedenteTramitePosesion.size()){
			throw new ExcepcionPersonalizada("Error, hay mas de un antecedente por tramite ", HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			ArchivoTramitePosesion archivoTramitePosesion = antecedenteTramitePosesion.get(0).getIdArchivoTramitePosesion();
			String base64String = Base64.getEncoder().encodeToString(archivoTramitePosesion.getArchivo());
			ArchivoTramiteDto archivoTramiteDto = archivoTramitePosesionMapper.toDto(archivoTramitePosesion);
			archivoTramiteDto.setArchivoBase(base64String);
	    	return archivoTramiteDto;
		}
    	
    }
    
    @Override
    public ArchivoTramiteDto guardarArchivoTramite(ArchivoTramiteDto archivoTramiteDto) {
    	try {
    		List<AntecedenteTramitePosesion> antecedenteTramitePosesionVali = antecedenteTramitePosesionRepository.obtenerAntecedentePorTramite(archivoTramiteDto.getIdTramite(), Constantes.ID_TIPO_CONVENIO_PARA_ANTECEDENTE_TRAMITE_POSESION);
        	if(!antecedenteTramitePosesionVali.isEmpty()) {
        		//entra a actualizacion de antecedente 
        		if(1 < antecedenteTramitePosesionVali.size()) {
        			throw new ExcepcionPersonalizada("Error, hay mas de un antecedente por tramite ", HttpStatus.INTERNAL_SERVER_ERROR);
        		}else {
        			AntecedenteTramitePosesion antecedenteUpdate = antecedenteTramitePosesionVali.get(0);
        			ArchivoTramitePosesion archivoTramitePosesion = antecedenteUpdate.getIdArchivoTramitePosesion();
            		byte[] decodedBytes = Base64.getDecoder().decode(archivoTramiteDto.getArchivoBase());
            		
            		archivoTramitePosesion.setArchivo(decodedBytes);
                	archivoTramitePosesion = updateArchivo(archivoTramitePosesion);
                	if(archivoTramitePosesion.getIdArchivoTramitePosesion() != null) {
                		antecedenteUpdate.setReportado(Constantes.REPORTADO_PARA_ANTECEDENTE_TRAMITE_POSESION);
                		antecedenteUpdate.setIdArchivoTramitePosesion(archivoTramitePosesion);
                		antecedenteUpdate.setIdUsuario(archivoTramiteDto.getIdUsuario());
                		antecedenteUpdate.setIpCliente(archivoTramiteDto.getIpCliente());
                		antecedenteUpdate = updateAntecedente(antecedenteUpdate);
                		ArchivoTramiteDto archivoTramiteDto2 = archivoTramitePosesionMapper.toDto(archivoTramitePosesion);
                		archivoTramiteDto2.setIdTramite(archivoTramiteDto.getIdTramite());
                		archivoTramiteDto2.setIdUsuario(archivoTramiteDto.getIdUsuario());
                		archivoTramiteDto2.setIpCliente(archivoTramiteDto.getIpCliente());
                    	return archivoTramiteDto2;
                	}else {
                		throw new ExcepcionPersonalizada("Error al guardar el archivo", HttpStatus.INTERNAL_SERVER_ERROR);
                	}
        		}
        	}else {
        		//entra a creacion de nuevo antecdente 
        		ArchivoTramitePosesion archivoTramitePosesion = new ArchivoTramitePosesion();
        		archivoTramitePosesion = archivoTramitePosesionMapper.toDomain(archivoTramiteDto);
        		byte[] decodedBytes = Base64.getDecoder().decode(archivoTramiteDto.getArchivoBase());
        	   
        		archivoTramitePosesion.setArchivo(decodedBytes);
            	archivoTramitePosesion = archivoTramitePosesionRepository.save(archivoTramitePosesion);
            	if(archivoTramitePosesion.getIdArchivoTramitePosesion() != null) {
            		TipoConvenioAntecedente tipoConvenioAntecedente = tipoConvenioAntecedenteRepository.findById(Constantes.ID_TIPO_CONVENIO_PARA_ANTECEDENTE_TRAMITE_POSESION).get();
                	TramitePosesion tramitePosesion = tramitePosesionRepository.findById(archivoTramiteDto.getIdTramite()).get();
                	AntecedenteTramitePosesion antecedenteTramitePosesion = new AntecedenteTramitePosesion();
                	antecedenteTramitePosesion.setIdTipoConvenioAntecedente(tipoConvenioAntecedente);
                	antecedenteTramitePosesion.setReportado(Constantes.REPORTADO_PARA_ANTECEDENTE_TRAMITE_POSESION);
                	antecedenteTramitePosesion.setIdArchivoTramitePosesion(archivoTramitePosesion);
                	antecedenteTramitePosesion.setIdTramitePosesion(tramitePosesion);
                	antecedenteTramitePosesion.setIdUsuario(archivoTramiteDto.getIdUsuario());
                	antecedenteTramitePosesion.setIpCliente(archivoTramiteDto.getIpCliente());
                	antecedenteTramitePosesion = antecedenteTramitePosesionRepository.save(antecedenteTramitePosesion);
                	ArchivoTramiteDto archivoTramiteDto2 = archivoTramitePosesionMapper.toDto(archivoTramitePosesion);
            		archivoTramiteDto2.setIdTramite(archivoTramiteDto.getIdTramite());
            		archivoTramiteDto2.setIdUsuario(archivoTramiteDto.getIdUsuario());
            		archivoTramiteDto2.setIpCliente(archivoTramiteDto.getIpCliente());
                	return archivoTramiteDto2;
            	}else {
            		throw new ExcepcionPersonalizada("Error al guardar el archivo", HttpStatus.INTERNAL_SERVER_ERROR);
            	}
    			
        	}
    		
		} catch (Exception e) {
			throw new ExcepcionPersonalizada(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
    }
    
   
	public ArchivoTramitePosesion updateArchivo(ArchivoTramitePosesion entity) {
		if (entity == null) {
			throw new NullPointerException("Archivo tramite");
		}
		return archivoTramitePosesionRepository.save(entity);

	}
	
	public AntecedenteTramitePosesion updateAntecedente(AntecedenteTramitePosesion entity) {
		if (entity == null) {
			throw new NullPointerException("Antecedente tramite posesion");
		}
		return antecedenteTramitePosesionRepository.save(entity);
	}

	/**
	 *
	 * @param idTramite Id del trámite
	 * @param idEstadoTramite Id del estado del trámite
	 * @return String de actualización exitosa
	 * @throws ExcepcionPersonalizada
	 */
	@Override
	public String actualizarEstadoTramite(Long idTramite, Long idEstadoTramite) throws ExcepcionPersonalizada {
		log.info(Constantes.INICIO_SERVICIO_ACTUALIZAR_ESTADO_TRAMITE);
		Optional<TramitePosesion> tramite = tramitePosesionRepository.findById(idTramite);
		if(tramite.isEmpty()){
			log.info(MessageFormat.format(Constantes.TRAMITE_NO_ENCONTRADO, idTramite));
			throw new ExcepcionPersonalizada(MessageFormat.format(Constantes.TRAMITE_NO_ENCONTRADO, idTramite), HttpStatus.NOT_FOUND);
		}
		log.info(Constantes.ACTUALIZACION_EXITOSA);
		tramite.get().setIdTipoEstadoTramitePosesion(idEstadoTramite);
		tramitePosesionRepository.save(tramite.get());
		return Constantes.ACTUALIZACION_EXITOSA;
	}

	@Override
	public List<InfoAnexoDto> consultarInformacionAnexos(Long idTramitePosesion) {
		try {
			TramitePosesion tramitePosesion = tramitePosesionRepository.findById(idTramitePosesion).orElse(null);
			if(null == tramitePosesion) {
				throw new ExcepcionPersonalizada(ERRROR_TRAMITE_NO_ENCONTRADO, HttpStatus.NOT_FOUND);
			}else {
				List<InfoAnexoDto> listado = new ArrayList<>();
				if(tramitePosesion.getIdArchivoConflictoInteres() != null) {
					ArchivoTramitePosesion archivoConflicto = archivoTramitePosesionRepository.findById(tramitePosesion.getIdArchivoConflictoInteres()).orElse(null);
					if(null != archivoConflicto) {
						listado.add(InfoAnexoDto.builder().nombreArchivo(Constantes.NOMBRE_ARCHIVO_CONFLICTO_INTERESES).archivo(Base64.getEncoder().encodeToString(archivoConflicto.getArchivo())).build());
					}
				}
				if(tramitePosesion.getIdArchivoTramitePosesion() != null) {
					ArchivoTramitePosesion archivoTramitePosesion = archivoTramitePosesionRepository.findById(tramitePosesion.getIdArchivoTramitePosesion()).orElse(null);
					if(null != archivoTramitePosesion) {
						listado.add(InfoAnexoDto.builder().nombreArchivo(Constantes.NOMBRE_ARCHIVO_TRAMITE_POSESION).archivo(Base64.getEncoder().encodeToString(archivoTramitePosesion.getArchivo())).build());
					}
				}
				if(tramitePosesion.getIdArchivoAntesDeFirma() != null) {
					ArchivoTramitePosesion archivoSinFirma = archivoTramitePosesionRepository.findById(tramitePosesion.getIdArchivoAntesDeFirma()).orElse(null);
					if(null != archivoSinFirma) {
						listado.add(InfoAnexoDto.builder().nombreArchivo(Constantes.NOMBRE_ARCHIVO_ACTA_SIN_FIRMA).archivo(Base64.getEncoder().encodeToString(archivoSinFirma.getArchivo())).build());
					}
				}
				if(tramitePosesion.getIdArchivoFirmado() != null) {
					ArchivoTramitePosesion archivoConFirma = archivoTramitePosesionRepository.findById(tramitePosesion.getIdArchivoFirmado()).orElse(null);
					if(null != archivoConFirma) {
						listado.add(InfoAnexoDto.builder().nombreArchivo(Constantes.NOMBRE_ARCHIVO_ACTA_CON_FIRMA).archivo(Base64.getEncoder().encodeToString(archivoConFirma.getArchivo())).build());
					}
				}
				return listado;
			}
		}catch(ExcepcionPersonalizada e) {
			throw new ExcepcionPersonalizada(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
    public String devolverTramite(CambiarEstadoTramitePosesionRequestDto request) {
        try {
        	this.changeEstadoTramite(request);
        	return generadorDocumentoPlantillaService.generarOficioDevolucion(request.isEntidad(), request.getIdTramitePosesion());
        }catch(ExcepcionPersonalizada e) {
			throw new ExcepcionPersonalizada(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    }
}
