package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.*;
import co.siri.posesiones.entities.AsignacionTramiteAnalistas;
import co.siri.posesiones.entities.Persona;
import co.siri.posesiones.entities.TramitePosesion;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.*;
import co.siri.posesiones.services.ICoordinadorService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CoordinadorService implements ICoordinadorService {

    private static final Logger logger = LoggerFactory.getLogger(ICoordinadorService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final CoordinadorRepository repository;

    private final ParametroRepository parametroRepository;

    private final TramitePosesionRepository tramitePosesionRepository;

    private final AnalistaRepository analistaRepository;

    private final AsinacionTramiteAnalistaRepository asignacionAnalistaRepository;

    private final PersonaRepository personaRepository;

    public CoordinadorService(CoordinadorRepository repository, ParametroRepository parametroRepository, TramitePosesionRepository tramitePosesionRepository, AnalistaRepository analistaRepository,
                              AsinacionTramiteAnalistaRepository asignacionAnalistaRepository,
                              PersonaRepository personaRepository) {
        this.repository = repository;
        this.parametroRepository = parametroRepository;
        this.tramitePosesionRepository = tramitePosesionRepository;
        this.analistaRepository = analistaRepository;
        this.asignacionAnalistaRepository = asignacionAnalistaRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    public List<EscritorioTramitesResponseDTO> asignados(FiltroAvanzadoEscritorioDto filtro) {
        String candidato = filtro.getCandidato() != null ? filtro.getCandidato().replaceAll("\\s", "") : null;
        try {
            return repository.asignados(filtro.getIdentificacion(), filtro.getRadicado(), filtro.getEntidad(), candidato, filtro.getEstadoTramite(), filtro.getCargo());
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<EscritorioTramitesResponseDTO> noAsignados(FiltroAvanzadoEscritorioDto filtro) {
        String candidato = filtro.getCandidato() != null ? filtro.getCandidato().replaceAll("\\s", "") : null;
        try {
            return repository.noAsignados(filtro.getIdentificacion(), filtro.getRadicado(), filtro.getEntidad(), candidato, filtro.getEstadoTramite(), filtro.getCargo());
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String asignacionAutomatica(AsignacionAutomaticaDTO asignacionAutomaticaDTO) {
        AtomicInteger totalAsignados = new AtomicInteger(0);
        AtomicInteger totalNoAsignados = new AtomicInteger(0);

        List<TramitePosesion> tramites = tramitePosesionRepository.findByAsignadaFalseOrderByPrioridadDescFechaAsc();
        List<CargaAnalistaDTO> analistasBase = obtenerListaAnalistas(TopeAnalista.CARGA_MAXIMA_ANALISTA_BASE.getCargaMaxima(), 1);
        List<CargaAnalistaDTO> analistasApoyo = obtenerListaAnalistas(TopeAnalista.CARGA_MAXIMA_ANALISTA_APOYO.getCargaMaxima(), 2);

        if (tramites.isEmpty()) {
            return "Actualmente no hay trámites pendientes de asignación.";
        }

        if (analistasBase.isEmpty() && analistasApoyo.isEmpty()) {
            return "No hay analistas disponibles en este momento.";
        }

        Map<Long, List<TramitePosesion>> agrupacion = tramites.stream()
                .filter(tramite -> tramite.getIdPersona() != null)
                .collect(Collectors.groupingBy(TramitePosesion::getIdPersona));

        List<TramitePosesion> listaTramitesAgrupados = new ArrayList<>();

        agrupacion.forEach((idPersona, grupos) -> {
            for (int i = 0; i < grupos.size(); i += 4) {
                List<TramitePosesion> grupoAnidado = grupos.subList(i, Math.min(i + 4, grupos.size()));
                CargaAnalistaDTO analistaTramite = encontrarAnalistaAdecuado(grupoAnidado, analistasBase, analistasApoyo);

                if (Objects.nonNull(analistaTramite) && analistaTramite.getId() != 0) {
                    for (TramitePosesion tramite : grupoAnidado) {
                        try {
                            AsignacionTramiteAnalistas asignacion = new AsignacionTramiteAnalistas();
                            asignacion.setIdAnalistaTramitePosesion((long) analistaTramite.getId());
                            asignacion.setIdTramitePosesion(tramite.getIdTramitePosesion());
                            asignacion.setIpCliente(asignacionAutomaticaDTO.getIpcliente());
                            asignacion.setIdUsuario(asignacionAutomaticaDTO.getIdusuario());
                            asignacionAnalistaRepository.save(asignacion);
                            totalAsignados.incrementAndGet();
                        } catch (Exception e) {
                            logger.error("Error creando la asignacion automatica ", e);
                            totalNoAsignados.incrementAndGet();
                        }
                    }
                } else {
                    listaTramitesAgrupados.addAll(grupoAnidado);
                    totalNoAsignados.addAndGet(grupoAnidado.size());
                }
            }
        });

        // Asignar Trámites no agrupados
        listaTramitesAgrupados.forEach(tramite -> {
            CargaAnalistaDTO analistaTramite = encontrarAnalistaAdecuado(listaTramitesAgrupados, analistasBase, analistasApoyo);

            if (Objects.nonNull(analistaTramite) && analistaTramite.getId() != 0) {
                try {
                    AsignacionTramiteAnalistas asignacion = new AsignacionTramiteAnalistas();
                    asignacion.setIdAnalistaTramitePosesion(analistaTramite.getIdentificacion());
                    asignacion.setIdTramitePosesion(tramite.getIdTramitePosesion());
                    asignacionAnalistaRepository.save(asignacion);
                    totalAsignados.incrementAndGet();
                } catch (Exception e) {
                    logger.error("Error creando la asignacion automatica ", e);
                    totalNoAsignados.incrementAndGet();
                }
            } else {
                totalNoAsignados.incrementAndGet();
            }
        });

        asignacionFinal(analistasBase, analistasApoyo);

        return mostrarResultadosAsignacion(totalAsignados.get(), totalNoAsignados.get());
    }

    @Override
    public InfoTramiteListAnalistasDTO infoTramiteAnalitas(Long idTramitePosesion) {

        TramitePosesion tramitePosesion = tramitePosesionRepository.getReferenceById(idTramitePosesion);

        if (Objects.nonNull(tramitePosesion)) {
            List<Object[]> listaAnalistasTramiteCarga = analistaRepository.findAnalistasTramitesCarga();
            List<AnalistaTramitesCargaDTO> dtos = new ArrayList<>();

            for (Object[] result : listaAnalistasTramiteCarga) {
                AnalistaTramitesCargaDTO dto = new AnalistaTramitesCargaDTO();
                dto.setIdAnalistaTramite((String) result[0]);
                dto.setNombreAnalistaTramite((String) result[1]);
                dto.setTramites(((Number) result[2]).intValue());
                dto.setCarga(((BigDecimal) result[3]).doubleValue());
                dtos.add(dto);
            }

            InfoTramiteListAnalistasDTO inforResult = new InfoTramiteListAnalistasDTO();
            inforResult.setNumeroRadicado(tramitePosesion.getNumeroRadicacion());
            inforResult.setPostulado(obtenerNombrePersona(tramitePosesion.getIdPersona()));
            inforResult.setCargo(tramitePosesion.getNombreCargo());
            inforResult.setListaAnalistasCarga(dtos);
            return inforResult;
        }

        return null;
    }

    @Override
    @Transactional
    public void asignacionManual(AsignacionManualDTO asignacionManualDTO) {

        List<Object[]> analista = analistaRepository.findAnalistaByidentificacion(String.valueOf(asignacionManualDTO.getIdAnalistaPosesion()));

        if (analista != null && !analista.isEmpty()) {
            Object[] result = analista.get(0);
            AnalistaTramitePosesionDTO analistaTramitePosesionDTO = new AnalistaTramitePosesionDTO();
            analistaTramitePosesionDTO.setIdAnalistaTramitePosesion((Integer) result[0]);
            analistaTramitePosesionDTO.setNombre((String) result[1]);

            AsignacionTramiteAnalistas asignacion = new AsignacionTramiteAnalistas();
            asignacion.setIdAnalistaTramitePosesion(Long.valueOf(analistaTramitePosesionDTO.getIdAnalistaTramitePosesion()));
            asignacion.setIdTramitePosesion(asignacionManualDTO.getIdTramitePosesion());
            asignacion.setIpCliente(asignacionManualDTO.getIpCliente());
            asignacion.setIdUsuario(asignacionManualDTO.getIdUsuario());

            try {
                logger.info("Info asignacionAnalistaRepository.save", asignacion);
                asignacionAnalistaRepository.save(asignacion);
            } catch (Exception e) {
                logger.error("Error en CoordinadorService  asignacionManual", e);
                e.printStackTrace();
            }
        }
    }

    List<CargaAnalistaDTO> obtenerListaAnalistas(String nombreTopeAnalista, Integer tipoAnalista) {

        List<CargaAnalistaDTO> dtos = new ArrayList<>();

        List<Object[]> results = analistaRepository.findCargaAnalistas(nombreTopeAnalista, tipoAnalista);

        for (Object[] result : results) {
            CargaAnalistaDTO dto = new CargaAnalistaDTO(
                    safeConvertToInteger(result[0]),
                    safeConvertToLong(result[1]),
                    safeConvertToBigDecimal(result[2]),
                    safeConvertToBigDecimal(result[3])
            );
            dtos.add(dto);
        }

        return dtos;
    }

    private Integer safeConvertToInteger(Object obj) {
        if (obj instanceof Integer) {
            return (Integer) obj;
        } else if (obj instanceof String) {
            try {
                return Integer.parseInt((String) obj);
            } catch (NumberFormatException e) {
                System.err.println("Error converting to Integer: " + e.getMessage());
                return null;
            }
        } else if (obj instanceof Long) {
            return ((Long) obj).intValue();
        }
        return null;
    }

    private Long safeConvertToLong(Object obj) {
        if (obj instanceof Long) {
            return (Long) obj;
        } else if (obj instanceof String) {
            try {
                return Long.parseLong((String) obj);
            } catch (NumberFormatException e) {
                System.err.println("Error converting to Long: " + e.getMessage());
                return null;
            }
        }
        return null;
    }

    private BigDecimal safeConvertToBigDecimal(Object obj) {
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        } else if (obj instanceof String) {
            try {
                return new BigDecimal((String) obj);
            } catch (NumberFormatException e) {
                System.err.println("Error converting to BigDecimal: " + e.getMessage());
                return null;
            }
        } else if (obj instanceof Integer) {
            return BigDecimal.valueOf((Integer) obj);
        } else if (obj instanceof Long) {
            return BigDecimal.valueOf((Long) obj);
        }
        return null;
    }

    Long instanciaLong(String convertir){
        long valor;
        try {
            valor = Long.parseLong(convertir);
        } catch (NumberFormatException | NullPointerException nfe) {
            return 0L;
        }
        return valor;
    }

    String obtenerNombrePersona(Long idPersona) {
        Persona persona = personaRepository.getReferenceById(idPersona);
        StringBuilder nombre = new StringBuilder();
        nombre.append(persona.getPrimerNombre()).append(" ")
                .append(persona.getSegundoNombre()).append(" ")
                .append(persona.getPrimerApellido()).append(" ")
                .append(persona.getSegundoApellido());
        return nombre.toString();
    }

    private CargaAnalistaDTO encontrarAnalistaAdecuado(List<TramitePosesion> tramites, List<CargaAnalistaDTO> analistasBase, List<CargaAnalistaDTO> analistasApoyo) {
        CargaAnalistaDTO analistaAdecuado = null;
        BigDecimal cargaMinima = BigDecimal.valueOf(Double.MAX_VALUE);
        BigDecimal tramitesSize = BigDecimal.valueOf(tramites.size() * 0.7);

        analistaAdecuado = obtenerAnalistaConCargaMinima(analistasBase, cargaMinima, tramitesSize, TopeAnalista.CARGA_MAXIMA_ANALISTA_BASE.getCargaMaxima());

        if (analistaAdecuado == null) {

            //Si no encuentro analista base se toma los analistas de apoyo
            analistaAdecuado = obtenerAnalistaConCargaMinima(analistasApoyo, cargaMinima, tramitesSize, TopeAnalista.CARGA_MAXIMA_ANALISTA_APOYO.getCargaMaxima());
        }

        return analistaAdecuado;
    }

    private CargaAnalistaDTO obtenerAnalistaConCargaMinima(List<CargaAnalistaDTO> analistas, BigDecimal cargaMinima, BigDecimal tramitesSize, String tipoAnalista) {

        BigDecimal maximoAnalista = obtenerTopeAnalistaByName(tipoAnalista);

        CargaAnalistaDTO analistaAdecuado = null;

        for (CargaAnalistaDTO analista : analistas) {
            if (Objects.nonNull(analista.getCargaTotal())) {
                BigDecimal cargaActual = analista.getCargaTotal();

                if (cargaActual.compareTo(cargaMinima) < 0 && cargaActual.add(tramitesSize).compareTo(maximoAnalista) <= 0) {
                    cargaMinima = cargaActual;
                    analistaAdecuado = analista;
                } else {
                    analistaAdecuado = analista;
                }
            }
        }
        return analistaAdecuado;
    }

    private void transferirTramite(CargaAnalistaDTO from, CargaAnalistaDTO to) {
        // buscar el trámite más antiguo o con menor prioridad del analista
        List<TramiteCargaDTO> tramites = obtenerTramitePosesion(from);

        if (!tramites.isEmpty()) {
            TramiteCargaDTO tramiteParaTransferir = tramites.get(0);

            // Actualizar la asignación del trámite en la base de datos
            AsignacionTramiteAnalistas asignacionActual = asignacionAnalistaRepository.findByIdTramitePosesion(tramiteParaTransferir.getIdTramitePosesion());
            asignacionActual.setIdAnalistaTramitePosesion((long) to.getId());
            asignacionAnalistaRepository.save(asignacionActual);

            from.decrementarCarga(BigDecimal.valueOf(tramiteParaTransferir.getCarga()));
            to.incrementarCarga(BigDecimal.valueOf(tramiteParaTransferir.getCarga()));
        }
    }

    private void ajustarReparto(List<CargaAnalistaDTO> analistas) {
        boolean hayDiferencia = true;
        while (hayDiferencia) {

            if (!analistas.isEmpty()) {

                List<CargaAnalistaDTO> mutableList = new ArrayList<>(analistas);

                mutableList.sort(Comparator.comparing(CargaAnalistaDTO::getCargaTotal, Comparator.nullsLast(Comparator.naturalOrder())));

                CargaAnalistaDTO analistaConMenosCarga = analistas.get(0);
                CargaAnalistaDTO analistaConMasCarga = analistas.get(analistas.size() - 1);

                if (Objects.nonNull(analistaConMasCarga.getCargaTotal()) && Objects.nonNull(analistaConMenosCarga.getCargaTotal())) {
                    BigDecimal diferencia = analistaConMasCarga.getCargaTotal().subtract(analistaConMenosCarga.getCargaTotal());

                    if (diferencia.compareTo(obtenerTopeAnalistaByName(TopeAnalista.DIFERENCIA_CARGA.getCargaMaxima())) > 0) {
                        transferirTramite(analistaConMasCarga, analistaConMenosCarga);
                    } else {
                        hayDiferencia = false;
                    }
                }
            }
        }
    }

    private void asignacionFinal(List<CargaAnalistaDTO> analistasBase, List<CargaAnalistaDTO> analistasApoyo) {
        List<CargaAnalistaDTO> todosAnalistas = Stream.concat(analistasBase.stream(), analistasApoyo.stream())
                .filter(cargaAnalistaDTO -> cargaAnalistaDTO.getCargaTotal() != null)
                .toList();

        if (!todosAnalistas.isEmpty()) {
            ajustarReparto(todosAnalistas);
            for (CargaAnalistaDTO analista : todosAnalistas) {
                List<TramiteCargaDTO> tramitesDelAnalista = obtenerTramitePosesion(analista);
                for (TramiteCargaDTO tramite : tramitesDelAnalista) {
                   // Llamar al procedimiento almacenado
                    try {
                        llamarProcedimientoAlmacenado(tramite.getIdTramitePosesion(), (long) analista.getId());
                    } catch (Exception e) {
                        logger.error("Error al llamar al procedimiento almacenado para trámite ID: " + tramite.getIdTramitePosesion() + " y analista ID: " + analista.getIdentificacion(), e);
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private List<TramiteCargaDTO> obtenerTramitePosesion(CargaAnalistaDTO analista) {
        try {
            List<Object[]> results = tramitePosesionRepository.findTramiteParaTransferir(analista.getId());

            return results.stream()
                    .filter(Objects::nonNull)
                    .map(result -> {
                        TramiteCargaDTO dto = new TramiteCargaDTO();
                        dto.setIdTramitePosesion(((Number) result[0]).longValue());
                        dto.setCarga(((BigDecimal) result[1]).doubleValue());
                        return dto;
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private BigDecimal obtenerTopeAnalistaByName(String nombre) {
        String maximaDiferencia = parametroRepository.findParametrosByNames(Collections.singletonList(nombre)).get(0).getValor();
        return new BigDecimal(maximaDiferencia);
    }

    private String mostrarResultadosAsignacion(int totalAsignados, int totalNoAsignados) {
        StringBuilder mensaje = new StringBuilder();

        mensaje.append("Total de trámites asignados: ").append(totalAsignados).append("\n").append("Total de trámites no asignados: ").append(totalNoAsignados).append("\n");
        return mensaje.toString();
    }

    private void llamarProcedimientoAlmacenado(Long idTramite, Long idAnalista) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_SIRI_FOREST")
                .withProcedureName("pd_asignar_tramite")
                .declareParameters(
                        new SqlParameter("idtramite", Types.NUMERIC),
                        new SqlParameter("idanalistatramiteposesion", Types.NUMERIC),
                        new SqlOutParameter("resultado_n", Types.NUMERIC),
                        new SqlOutParameter("resultado_v", Types.VARCHAR)
                );

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("idtramite", idTramite);
        inParams.put("idanalistatramiteposesion", idAnalista);
    }
}
