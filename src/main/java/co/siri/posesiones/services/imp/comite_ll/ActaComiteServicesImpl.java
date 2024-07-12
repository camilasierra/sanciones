package co.siri.posesiones.services.imp.comite_ll;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import co.siri.posesiones.constant.ConstantesComiteII;
import co.siri.posesiones.entities.TipoPlantillaEntity;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.TipoPlatillaRepository;
import co.siri.posesiones.utilidades.Constantes;
import co.siri.posesiones.utilidades.GenerateDocx;
import co.siri.posesiones.utilidades.GeneratePdf;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.siri.posesiones.dtos.ActaComiteDTO;
import co.siri.posesiones.dtos.DecisionesActaComiteDTO;
import co.siri.posesiones.dtos.VotantesDto;
import co.siri.posesiones.dtos.comiteII.InfoActaComiteDTO;
import co.siri.posesiones.entities.ResultadoComite;
import co.siri.posesiones.entities.SesionComiteEntity;
import co.siri.posesiones.repositories.ActaComiteRepository;
import co.siri.posesiones.repositories.IResultadoComiteRepository;
import co.siri.posesiones.services.IActaComiteServices;
import co.siri.posesiones.services.ISolipService;

@Service
public class ActaComiteServicesImpl implements IActaComiteServices {

	private final ActaComiteRepository repository;
	private final IResultadoComiteRepository comiteRepository;
	private final TipoPlatillaRepository tipoplantillaRepository;
	private final ISolipService solipService;
	private static String ASISTIO = "asistió ";
	private static String ASISTIERON = "asistieron ";
	private static String ASISTENCIA_SINGULAR = ", como invitado a la sesión.";
	private static String ASISTENCIA_PLURAL = " como invitados a la sesión.";

	public ActaComiteServicesImpl(ActaComiteRepository repository, IResultadoComiteRepository comiteRepository,
								  TipoPlatillaRepository tipoplantillaRepository,ISolipService solipService) {
		this.repository = repository;
		this.comiteRepository = comiteRepository;
		this.tipoplantillaRepository = tipoplantillaRepository;
		this.solipService = solipService;
	}

	@Override
	public HashMap<String, Object> consultarComites(List<String> estados, Long idsesion, Integer anotacionEsNula) {
		HashMap<String, Object> resultado = new HashMap<>();
		List<Object[]> results = repository.getInfoComite(estados, idsesion, anotacionEsNula);
		String numeroActa = generarNumeroActa(idsesion);
		resultado.put("numeroActa", numeroActa);
		resultado.put("tramites", results.stream()
				.map(result -> new InfoActaComiteDTO((String) result[0], (String) result[1], (String) result[2],
						(String) result[3], clobToString((Clob) result[4]), (int) result[5], (String) result[6]))
				.collect(Collectors.toList()));
		return resultado;
	}

	@Override
	public String actualizarAnotacionComite(ResultadoComite resultadoComite) {
		StringBuilder builder = new StringBuilder();
		Optional<ResultadoComite> resultadoConsulta = comiteRepository.findById(resultadoComite.getIdresultadocomite());
		if (resultadoConsulta.isPresent()) {
			builder.append(resultadoComite.getAnotacion());
			resultadoConsulta.get().setAnotacion(builder.toString());
			resultadoConsulta.get().setIpcliente(resultadoComite.getIpcliente());
			resultadoConsulta.get().setIdusuario(resultadoComite.getIdusuario());
			comiteRepository.save(resultadoConsulta.get());
		}
		return resultadoComite.getAnotacion();
	}

	@Override
	public ActaComiteDTO getInfoArmarActa(List<String> estado, Long idsesion, Integer anotacionEsNula,
										  List<Integer> idtipomiembrocomite, String format) {
		ActaComiteDTO comiteDTO = new ActaComiteDTO();
		List<Object[]> infoSesionComite = repository.getInfoSesionComite(idsesion);
		SimpleDateFormat sdfNormal = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
		SimpleDateFormat sdfMonth = new SimpleDateFormat("dd-MMM-yyyy", new Locale("es", "ES"));
		infoSesionComite.stream().findFirst().map(sesionComite -> {
			comiteDTO.setFechaComite(sesionComite[0] == null ? "" : sdfNormal.format(sesionComite[0]));
			comiteDTO.setFechaMesComite(
					sesionComite[0] == null ? "" : sdfMonth.format(sesionComite[0]).replace("-", " de "));
			comiteDTO.setHoraInicioComite(sesionComite[1] == null ? "" : String.valueOf(sesionComite[1]));
			comiteDTO.setHoraFinComite(sesionComite[2] == null ? "" : String.valueOf(sesionComite[2]));
			comiteDTO.setTipoSesion((String) sesionComite[3] == null ? "" : (String) sesionComite[3]);
			comiteDTO.setTipoModalidad(sesionComite[5] == null ? "" : (String) sesionComite[5]);
			comiteDTO.setCanal(sesionComite[6] == null ? "" : (String) sesionComite[6]);
			comiteDTO.setNumeroActa(sesionComite[7] == null ? "" : String.valueOf(sesionComite[7]));
			return comiteDTO;
		});
		List<Object[]> miembrosComite = repository.getInfoMiembrosComite(idsesion, idtipomiembrocomite);
		comiteDTO.setMiembrosComite(getMiembrosComite(miembrosComite, 0));
		comiteDTO.setAsistenteComite(getMiembrosComite(miembrosComite, 1));
		List<Object[]> invitadosComite = repository.getInfoAsistenteComite(idsesion);
		comiteDTO.setInvitadosComite(getInvitadosComite(invitadosComite));
		List<Object[]> decisiones = repository.getInfoDecisionesComite(estado, idsesion, anotacionEsNula);
		comiteDTO.setDecisiones(getDecisionesComite(decisiones));
		String estados = validarInformacion(comiteDTO);
		comiteDTO.setEstado(estados);

		//String prueba = solipService.consultarFirmaDocumento("19233242");

		if(comiteDTO.getTipoModalidad().equalsIgnoreCase(Constantes.NO_PRESENCIAL)) {
			List<Object[]> infoVotacion = repository.consultarInfoVotacion(idsesion);
			comiteDTO.setVotantes(getVotantesDto(infoVotacion));
		}

		if (estados.equals("Exitoso")) {
			comiteDTO.setVarios(getVarios(idsesion));
			try {
				Map<String, String> parameters = initializeParameters(comiteDTO);
				String documentoBase64 = validateFormat(format, parameters);
				comiteDTO.setData(documentoBase64);
				return comiteDTO;
			} catch (Exception e) {
				throw new ExcepcionPersonalizada(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			return comiteDTO;
		}
	}

	public String validarInformacion(ActaComiteDTO dto) {
		StringBuilder builder = new StringBuilder();
		builder.append("No Exitoso, ");
		if (dto.getFechaComite().trim().isBlank()) {
			builder.append("Campo requerido nulo ").append("Fecha Comite,");
		}
		if (dto.getHoraInicioComite().trim().isBlank()) {
			builder.append("Campo requerido nulo ").append("Hora Inicio Comite, ");
		}
		if (dto.getHoraFinComite().trim().isBlank()) {
			builder.append("Campo requerido nulo ").append("Hora Fin Comite, ");
		}
		if (dto.getTipoSesion().trim().isBlank()) {
			builder.append("Campo requerido nulo ").append("Tipo Sesion, ");
		}
		if(dto.getTipoModalidad().trim().isBlank()) {
			builder.append("Campo requerido nulo ").append("Tipo Modalidad, ");
		}
		if (dto.getCanal().trim().isBlank()) {
			builder.append("Campo requerido nulo ").append("Canal, ");
		}
		if (dto.getMiembrosComite().trim().isBlank()) {
			builder.append("Campo requerido nulo ").append("Miembros Comite, ");
		}
		if (dto.getAsistenteComite().trim().isBlank()) {
			builder.append("Campo requerido nulo ").append("Asistentes Comite, ");
		}
		if (dto.getDecisiones().size() == 0) {
			builder.append("Campo requerido nulo ").append("Decisiones, ");
		}

		if (!builder.toString().contains("Campo requerido nulo")) {
			builder = new StringBuilder();
			builder.append("Exitoso");
		}
		return builder.toString();
	}

	public String validateFormat(String format, Map<String, String> parameters) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream;
		String headerDocument = "SUPERINTENDENCIA FINANCIERA DE COLOMBIA";
		String watermarkText = "VISTA PREVIA";
		switch (format.toLowerCase()) {
			case "pdf":
				GeneratePdf generatePdf = new GeneratePdf();
				byteArrayOutputStream = generatePdf.generatePdf(parameters, 3.20, 3.20, 2.54, 2.54, headerDocument,
						watermarkText);
				return org.apache.commons.codec.binary.Base64.encodeBase64String(byteArrayOutputStream.toByteArray());
			case "docx":
				GenerateDocx generateDocx = new GenerateDocx();
				ByteArrayOutputStream documentStream = generateDocx.generateDocx(parameters, 3.20, 3.20, 2.54, 2.54,
						headerDocument);
				return org.apache.commons.codec.binary.Base64.encodeBase64String(documentStream.toByteArray());
			default:
				throw new IllegalArgumentException("Formato no soportado: " + format);
		}
	}

	private String generarNumeroActa(Long idSesion) {
		String result = null;
		Random random = new Random();
		Optional<SesionComiteEntity> entity = repository.findById(idSesion);
		if (entity.isPresent()) {
			if (entity.get().getNumeroActa() == null) {
				entity.get().setNumeroActa(random.nextInt((9999 - 1000) + 1) + 1000);
				repository.save(entity.get());
				result = String.valueOf(entity.get().getNumeroActa());
			} else {
				result = String.valueOf(entity.get().getNumeroActa());
			}
		}
		return result;
	}

	public Map<String, String> convertParamsToStringMap(Map<String, Object> parameters) {
		Map<String, String> stringMap = new HashMap<>();
		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			stringMap.put(entry.getKey(), entry.getValue().toString());
		}
		return stringMap;
	}

	private Map<String, String> initializeParameters(ActaComiteDTO comiteDTO) {
		Map<String, String> parameters = new HashMap<>();

		String plantillaNombre = obtenerPlantilla(comiteDTO.getTipoModalidad()); // Obtener el nombre de la plantilla
		Optional<TipoPlantillaEntity> plantilla = tipoplantillaRepository.findByNombre(plantillaNombre);

		String[] valores = {
				Optional.ofNullable(comiteDTO.getTipoSesion()).map(String::toUpperCase).orElse(""), // 0
				Optional.ofNullable(comiteDTO.getNumeroActa()).orElse(""), // 1
				Optional.ofNullable(comiteDTO.getFechaComite()).orElse(""), // 2
				Optional.ofNullable(comiteDTO.getFechaMesComite()).orElse(""), // 3
				Optional.ofNullable(comiteDTO.getHoraInicioComite()).orElse(""), // 4
				Optional.ofNullable(comiteDTO.getTipoModalidad()).orElse(""), // 5
				Optional.ofNullable(comiteDTO.getCanal()).orElse(""), // 6
				Optional.ofNullable(comiteDTO.getMiembrosComite()).orElse(""), // 7
				Optional.ofNullable(comiteDTO.getAsistenteComite()).orElse(""), // 8
				Optional.ofNullable(comiteDTO.getInvitadosComite()).orElse(""), // 9
				formatDecisiones(comiteDTO.getDecisiones()), // 10
				Optional.ofNullable(comiteDTO.getVarios()).orElse(""), // 11
				Optional.ofNullable(comiteDTO.getHoraFinComite()).orElse(""), // 12
				formatVotaciones(comiteDTO.getVotantes())
		};

		String bodyData = plantilla.map(tp -> replacePlaceholders(tp.getTexto(), valores))
				.orElse("Plantilla no encontrada.");

		parameters.put("BodyData", bodyData);

		return parameters;
	}

	public String obtenerPlantilla(String tipoModalidad) {
		if ("Virtual".equalsIgnoreCase(tipoModalidad)) {
			return "Virtual";
		} else if ("No presencial".equalsIgnoreCase(tipoModalidad)) { // Tabla. Anexo 1
			return "No Presencial";
		} else if ("Presencial".equalsIgnoreCase(tipoModalidad)) {
			return "Presencial";
		} else {
			throw new ExcepcionPersonalizada(ConstantesComiteII.NO_PLANTILLA, HttpStatus.BAD_REQUEST);
		}
	}

	public String formatDecisiones(List<DecisionesActaComiteDTO> decisiones) {
		StringBuilder builder = new StringBuilder();
		int delegaturaIndex = 2; // Iniciar en 2 para las delegaturas
		int subIndex = 1; // Subíndice para enumeración adicional

		for (DecisionesActaComiteDTO decision : decisiones) {
			builder.append("<b>").append(delegaturaIndex).append(".").append(subIndex).append(" ")
					.append(decision.getDelegatura()).append("</b><br/>");

			int decisionIndex = 1;
			for (Map.Entry<String, Object> entry : decision.getDecisiones().entrySet()) {
				builder.append("<b>").append(delegaturaIndex).append(".").append(subIndex).append(".")
						.append(decisionIndex).append(" ").append(entry.getKey()).append(": </b><br/>");

				List<Object[]> items = (List<Object[]>) entry.getValue();
				int itemIndex = 1;
				for (Object[] item : items) {
					builder.append(delegaturaIndex).append(".").append(subIndex).append(".").append(decisionIndex)
							.append(".").append(itemIndex).append(" ").append(item[0]).append("<br/>");
					itemIndex++;
				}
				decisionIndex++;
			}
			builder.append("<br/>");
			subIndex++;
		}
		return builder.toString();
	}
	
	private String formatVotaciones(List<VotantesDto> listVotantes) {
		if(listVotantes != null && !listVotantes.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			listVotantes.stream().forEach(votante -> {
				builder.append("<tr>");
				builder.append("<td>");
				builder.append(votante.getNombreComite());
				builder.append("</td>");
				builder.append("<td>");
				builder.append(votante.getDiaVotacion());
				builder.append("</td>");
				builder.append("<td>");
				builder.append(votante.getHoraVotacion());
				builder.append("</td>");
				builder.append("</tr>");
			});
			return builder.toString();
		}
		return "";
	}

	public String replacePlaceholders(String template, String[] values) {
		for (int i = 0; i < values.length; i++) {
			template = template.replace("{" + i + "}", values[i]);
		}
		return template;
	}

	@Override
	public String getVarios(Long idsesion) {
		return clobToString((Clob) repository.getVarios(idsesion));
	}

	public String clobToString(Clob clob) {
		StringBuilder stringBuilder = new StringBuilder();
		try (Reader reader = clob.getCharacterStream(); BufferedReader bufferedReader = new BufferedReader(reader)) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = stringBuilder.toString();
		return result;
	}

	public String getMiembrosComite(List<Object[]> list, int orderCall) {
		StringBuilder builder = new StringBuilder();
		if (orderCall == 0) {
			list.stream().filter(objeto -> (int) objeto[2] != 4).map(objeto -> objeto[0] + " " + objeto[1])
					.forEachOrdered(cadena -> builder.append(cadena));
		} else if (orderCall == 1) {
			list.stream().filter(objeto -> (int) objeto[2] == 4).map(objeto -> objeto[0] + " " + objeto[1])
					.forEach(cadena -> builder.append(cadena));
		}
		return builder.toString();
	}
	
	public List<VotantesDto> getVotantesDto(List<Object[]> list){
		List<VotantesDto> resultado = new ArrayList<>();
		list.stream().forEach(item -> {
			Object[] aux = item;
			Timestamp fecha = (Timestamp)aux[1];
			String[] infoFechas = obtenerFechaHora(fecha);
			VotantesDto votantesDto = VotantesDto.builder()
					.nombreComite(aux[0].toString())
					.diaVotacion(infoFechas[0])
					.horaVotacion(infoFechas[1] + " " + infoFechas[2])
					.build();
			resultado.add(votantesDto);
		});
		return resultado;
	}
	
	public String[] obtenerFechaHora(Timestamp fecha) {
		Date currentDate = new Date (fecha.getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
		String date = dateFormat.format(currentDate);
		return date.split(" ");
	}

	public List<DecisionesActaComiteDTO> getDecisionesComite(List<Object[]> list) {
		DecisionesActaComiteDTO dto = null;
		List<DecisionesActaComiteDTO> result = new ArrayList<>();
		HashMap<String, String> delegaturas = new HashMap<>();
		HashMap<String, Object> estado = null;
		int idDelegatura = 0;
		if (list.size() > 0) {
			for (Object[] objeto : list) {
				estado = new HashMap<>();
				if (!delegaturas.containsValue(objeto[0])) {
					dto = new DecisionesActaComiteDTO();
					idDelegatura = idDelegatura + 1;
					dto.setDelegatura((String) objeto[0]);
					delegaturas.put(String.valueOf(idDelegatura), (String) objeto[0]);
					List<Object[]> registroFiltrados = list.stream().filter(registro -> registro[0].equals(objeto[0]))
							.map(registro -> new Object[] { registro[1], registro[2], registro[3] })
							.collect(Collectors.toList());
					for (Object[] decision : registroFiltrados) {
						List<Object[]> registroEstado = null;
						if (decision[0].equals(objeto[1])) {
							if (!estado.containsKey(decision[0])) {
								registroEstado = registroFiltrados.stream()
										.filter(registro -> registro[0].equals(objeto[1]))
										.map(registro -> new Object[] {registro[1] , clobToString((Clob) registro[2])})
										.collect(Collectors.toList());
								estado.put((String) objeto[1].toString().toUpperCase(), registroEstado);
							}
						} else {
							if (!estado.containsKey(decision[0])) {
								registroEstado = registroFiltrados.stream()
										.filter(registro -> registro[0].equals(decision[0]))
										.map(registro -> new Object[] {registro[1], clobToString((Clob) registro[2])})
										.collect(Collectors.toList());
								estado.put((String) decision[0].toString().toUpperCase(), registroEstado);
							}
						}
					}
					dto.setDecisiones(estado);
					result.add(dto);
				}
			}
		}
		return result;
	}

	public String getInvitadosComite(List<Object[]> asistentes) {
		StringBuilder builder = new StringBuilder();
		if (asistentes.size() == 1) {
			builder.append(ASISTIO);
			for (Object[] invitado : asistentes) {
				builder.append(invitado[0]).append(ASISTENCIA_SINGULAR);
			}
		} else {
			builder.append(ASISTIERON);
			for (Object[] invitado : asistentes) {
				builder.append(invitado[0]).append(", ");
			}
			builder.append(ASISTENCIA_PLURAL);
		}
		return builder.toString();
	}

	@Override
	public String actualizarVarios(SesionComiteEntity sesionEntity) {
		StringBuilder builder = new StringBuilder();
		Optional<SesionComiteEntity> sesionComite = repository.findById(sesionEntity.getId());
		if (sesionComite.isPresent()) {
			builder.append(sesionEntity.getVarios());
			sesionComite.get().setVarios(builder.toString());
			sesionComite.get().setIdUsuario(sesionEntity.getIdUsuario());
			sesionComite.get().setIpCliente(sesionEntity.getIpCliente());
			repository.save(sesionComite.get());
		}
		return sesionEntity.getVarios();
	}
}
