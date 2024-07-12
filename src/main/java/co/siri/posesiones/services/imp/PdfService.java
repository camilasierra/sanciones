package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.*;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.services.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class PdfService implements IPdfService {

    @Autowired
    private IPostuladoService iPostuladoService;

    @Autowired
    private ITramitePosesionService iTramitePosesionService;

    @Autowired
    private IComportamientoCrediticioService iComportamientoCrediticioService;

    @Autowired
    private IInvestigacionPersonaService investigacionPersonaService;

    @Autowired
    private InhabilidadPosesionService inhabilidadPosesionService;

    @Autowired
    private IReporteAntecedentesService iReporteAntecedentesService;

    @Override
    public String generarPDFHojaVidaPostulado(Long idTramite, Long idPersona) throws ExcepcionPersonalizada {
        CompletableFuture<PostuladoDatosDTO> datosPostuladoFuture = obtenerDatosPostuladoAsync(idTramite)
                .exceptionally(ex -> new PostuladoDatosDTO());
        CompletableFuture<List<PostuladoEstudioDTO>> estudiosPostuladoFuture = obtenerEstudiosPostuladoAsync(idPersona)
                .exceptionally(ex -> Collections.emptyList());
        CompletableFuture<List<PostuladoCargosSinPosesionDTO>> cargosSinPosesionPostuladoFuture = obtenerCargosSinPosesionPostuladoAsync(idPersona)
                .exceptionally(ex -> Collections.emptyList());
        CompletableFuture<List<PostuladoCargosPosesionDTO>> cargosPosesionPostuladoFuture = obtenerCargosPosesionesPostuladoAsync(idPersona)
                .exceptionally(ex -> Collections.emptyList());
        CompletableFuture<List<ExperienciaPostuladoDTO>> experienciaPostuladoFuture = obtenerExperienciaPostuladoAsync(idPersona)
                .exceptionally(ex -> Collections.emptyList());
        CompletableFuture<DatosBasicosDto> datosBasicosTramiteFuture = obtenerDatosBasicosTramite(idTramite)
                .exceptionally(ex -> new DatosBasicosDto());
        CompletableFuture<InfoNombramientoDto> datosNombramientoFuture = obtenerDatosNombramiento(idTramite)
                .exceptionally(ex -> new InfoNombramientoDto());
        CompletableFuture<InfoDesignacionDto> infoDesignacionFuture = obtenerInfoDesignacion(idTramite)
                .exceptionally(ex -> new InfoDesignacionDto());
        CompletableFuture<InfoContactoEntidad> infoContactoEntidadFuture = obtenerInfoContactoEntidad(idTramite)
                .exceptionally(ex -> new InfoContactoEntidad());
        CompletableFuture<InfoServidorPublico> infoServidorPublicoFuture = obtenerInfoServidorPublico(idTramite)
                .exceptionally(ex -> new InfoServidorPublico());
        CompletableFuture<InfoOtrosRepresentantes> infoRepresentantesFuture = obtenerInfoRepresentantes(idTramite)
                .exceptionally(ex -> new InfoOtrosRepresentantes());
        CompletableFuture<InfoJuntaDirectiva> infoJuntaDirectivaFuture = obtenerInfoJuntaDirectiva(idTramite)
                .exceptionally(ex -> new InfoJuntaDirectiva());
        CompletableFuture<InfoAdicionalDefensorConsumidor> infoAdicionalDefensorFuture = obtenerInfoAdicionalDefensor(idTramite)
                .exceptionally(ex -> new InfoAdicionalDefensorConsumidor());
        CompletableFuture<List<ComportamientoCrediticioDTO>> comportamientoCrediticioFuture = obtenerComportamientoCrediticio(idPersona)
                .exceptionally(ex -> Collections.emptyList());
        CompletableFuture<List<InvestigacionesPersonasDTO>> investigacionesPersonaFuture = obtenerInvestigacionesPersona(idPersona)
                .exceptionally(ex -> Collections.emptyList());
        CompletableFuture<List<Map<String, Object>>> sancionesEnFirmeFuture = obtenerSancionesEnFirme(idTramite)
                .exceptionally(ex -> Collections.emptyList());
        CompletableFuture<List<InhabilidadPosesionProjection>> inhabilidadesComunesRevisorFiscalFuture = obtenerInhabilidadesComunesRevisorFiscal(idTramite)
                .exceptionally(ex -> Collections.emptyList());
        CompletableFuture<List<InhabilidadPosesionProjection>> inhabilidadesComunesMiembroJuntaFuture = obtenerInhabilidadesMiembroJunta(idTramite)
                .exceptionally(ex -> Collections.emptyList());
        CompletableFuture<List<InhabilidadPosesionProjection>> inhabilidadesComunesDefensorFuture = obtenerInhabilidadesComunesDefensorConsumidor(idTramite)
                .exceptionally(ex -> Collections.emptyList());
        CompletableFuture<List<InhabilidadPosesionProjection>> inhabilidadesComunesFuncionarioFuture = obtenerInhabilidadesComunesFuncionarioResponsable(idTramite)
                .exceptionally(ex -> Collections.emptyList());
        CompletableFuture<List<Map<String, Object>>> sancionesFirmeGeneralesFuture = obtenerSancionesFirmeGenerales(idTramite)
                .exceptionally(ex -> Collections.emptyList());
        CompletableFuture<List<InhabilidadPosesionProjection>> otrasSituacionesFuture = obtenerOtrasSituaciones(idTramite)
                .exceptionally(ex -> Collections.emptyList());
        CompletableFuture<List<Map<String, Object>>> inhabilidadesPosesionFuture = obtenerInhabilidadesPosesion(idTramite)
                .exceptionally(ex -> Collections.emptyList());
        CompletableFuture<List<AccionAporteDTO>> accionesAportesFuture = obtenerAccionesAportes(idPersona)
                .exceptionally(ex -> Collections.emptyList());

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                datosPostuladoFuture,
                estudiosPostuladoFuture,
                cargosSinPosesionPostuladoFuture,
                cargosPosesionPostuladoFuture,
                experienciaPostuladoFuture,
                datosBasicosTramiteFuture,
                datosNombramientoFuture,
                infoDesignacionFuture,
                infoContactoEntidadFuture,
                infoServidorPublicoFuture,
                infoRepresentantesFuture,
                infoJuntaDirectivaFuture,
                infoAdicionalDefensorFuture,
                comportamientoCrediticioFuture,
                investigacionesPersonaFuture,
                sancionesEnFirmeFuture,
                inhabilidadesComunesRevisorFiscalFuture,
                inhabilidadesComunesMiembroJuntaFuture,
                inhabilidadesComunesDefensorFuture,
                inhabilidadesComunesFuncionarioFuture,
                sancionesFirmeGeneralesFuture,
                otrasSituacionesFuture,
                inhabilidadesPosesionFuture,
                accionesAportesFuture
        );

        allFutures.join();

        try {
            PostuladoDatosDTO postuladoDatos = datosPostuladoFuture.get();
            List<PostuladoEstudioDTO> postuladoEstudio = estudiosPostuladoFuture.get();
            List<PostuladoCargosSinPosesionDTO> postuladoCargosSinPosesion = cargosSinPosesionPostuladoFuture.get();
            List<PostuladoCargosPosesionDTO> postuladoCargosPosesion = cargosPosesionPostuladoFuture.get();
            List<ExperienciaPostuladoDTO> expecienciaPostulado = experienciaPostuladoFuture.get();
            DatosBasicosDto datosBasicosTramite = datosBasicosTramiteFuture.get();
            InfoNombramientoDto datosNombramiento = datosNombramientoFuture.get();
            InfoDesignacionDto infoDesignacion = infoDesignacionFuture.get();
            InfoContactoEntidad infoContactoEntidad = infoContactoEntidadFuture.get();
            InfoServidorPublico infoServidorPublico = infoServidorPublicoFuture.get();
            InfoOtrosRepresentantes infoRepresentantes = infoRepresentantesFuture.get();
            InfoJuntaDirectiva infoJuntaDirectiva = infoJuntaDirectivaFuture.get();
            InfoAdicionalDefensorConsumidor infoAdicionalDefensor = infoAdicionalDefensorFuture.get();
            List<ComportamientoCrediticioDTO> comportamientoCrediticio = comportamientoCrediticioFuture.get();
            List<InvestigacionesPersonasDTO> investigacionesPersona = investigacionesPersonaFuture.get();
            List<Map<String, Object>> sancionesEnFirme = sancionesEnFirmeFuture.get();
            List<InhabilidadPosesionProjection> inhabilidadesComunesRevisorFiscal = inhabilidadesComunesRevisorFiscalFuture.get();
            List<InhabilidadPosesionProjection> inhabilidadesComunesMiembroJunta = inhabilidadesComunesMiembroJuntaFuture.get();
            List<InhabilidadPosesionProjection> inhabilidadesComunesDefensor = inhabilidadesComunesDefensorFuture.get();
            List<InhabilidadPosesionProjection> inhabilidadesComunesFuncionario = inhabilidadesComunesFuncionarioFuture.get();
            List<Map<String, Object>> sancionesFirmeGenerales = sancionesFirmeGeneralesFuture.get();
            List<InhabilidadPosesionProjection> otrasSituaciones = otrasSituacionesFuture.get();
            List<Map<String, Object>> inhabilidadesPosesion = inhabilidadesPosesionFuture.get();
            List<AccionAporteDTO> accionesAportes = accionesAportesFuture.get();

            CompletableFuture<String> resultado = crearPDFHojaVida(postuladoDatos, postuladoEstudio, postuladoCargosSinPosesion, postuladoCargosPosesion, expecienciaPostulado, datosBasicosTramite,
                    datosNombramiento,
                    infoDesignacion,
                    infoContactoEntidad,
                    infoServidorPublico,
                    infoRepresentantes,
                    infoJuntaDirectiva,
                    infoAdicionalDefensor,
                    comportamientoCrediticio,
                    investigacionesPersona,
                    sancionesEnFirme,
                    inhabilidadesComunesRevisorFiscal,
                    sancionesFirmeGenerales,
                    otrasSituaciones,
                    inhabilidadesPosesion,
                    accionesAportes,
                    inhabilidadesComunesMiembroJunta,
                    inhabilidadesComunesDefensor,
                    inhabilidadesComunesFuncionario
                    );

            String pdfBase64 = resultado.get();


            return pdfBase64;


        } catch (InterruptedException | ExecutionException ex) {
            log.error(ex.getMessage());
            throw new ExcepcionPersonalizada("Error al generar hoja de vida del postulado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private CompletableFuture<String> crearPDFHojaVida(
            PostuladoDatosDTO postuladoDatos,
            List<PostuladoEstudioDTO> postuladoEstudio,
            List<PostuladoCargosSinPosesionDTO> postuladoCargosSinPosesion,
            List<PostuladoCargosPosesionDTO> postuladoCargosPosesion,
            List<ExperienciaPostuladoDTO> experienciaPostulado,
            DatosBasicosDto datosBasicosTramite,
            InfoNombramientoDto datosNombramiento,
            InfoDesignacionDto infoDesignacion,
            InfoContactoEntidad infoContactoEntidad,
            InfoServidorPublico infoServidorPublico,
            InfoOtrosRepresentantes infoRepresentantes,
            InfoJuntaDirectiva infoJuntaDirectiva,
            InfoAdicionalDefensorConsumidor infoAdicionalDefensor,
            List<ComportamientoCrediticioDTO> comportamientoCrediticio,
            List<InvestigacionesPersonasDTO> investigacionesPersona,
            List<Map<String, Object>> sancionesEnFirme,
            List<InhabilidadPosesionProjection> inhabilidadesComunesRevisorFiscal,
            List<Map<String, Object>> sancionesFirmeGenerales,
            List<InhabilidadPosesionProjection> otrasSituaciones,
            List<Map<String, Object>> inhabilidadesPosesion,
            List<AccionAporteDTO> accionesAportes,
            List<InhabilidadPosesionProjection> inhabilidadesComunesMiembroJunta,
            List<InhabilidadPosesionProjection> inhabilidadesComunesDefensor,
            List<InhabilidadPosesionProjection> inhabilidadesComunesFuncionario) throws ExcepcionPersonalizada {

        CompletableFuture<JasperPrint> datosBasicosFuture = crearHojaDatosBasicos(postuladoDatos);
        CompletableFuture<JasperPrint> estudiosFuture = crearHojaEstudios(postuladoEstudio);
        CompletableFuture<JasperPrint> experienciaFuture = crearHojaExperiencia(postuladoCargosSinPosesion, postuladoCargosPosesion, experienciaPostulado);

        CompletableFuture<JasperPrint> tramiteFuture = crearHojaTramite(datosBasicosTramite,
                datosNombramiento,
                infoDesignacion,
                infoContactoEntidad,
                infoServidorPublico,
                infoRepresentantes,
                infoJuntaDirectiva,
                infoAdicionalDefensor);

        CompletableFuture<JasperPrint> declaracionesFuture = crearHojaDeclaraciones(
                comportamientoCrediticio,
                investigacionesPersona,
                sancionesEnFirme,
                inhabilidadesComunesRevisorFiscal,
                sancionesFirmeGenerales,
                otrasSituaciones,
                inhabilidadesPosesion,
                accionesAportes,
                inhabilidadesComunesMiembroJunta,
                inhabilidadesComunesDefensor,
                inhabilidadesComunesFuncionario);

        List<CompletableFuture<Void>> futures = List.of(
                datosBasicosFuture.thenApply(result -> null),
                estudiosFuture.thenApply(result -> null),
                experienciaFuture.thenApply(result -> null),
                tramiteFuture.thenApply(result -> null),
                declaracionesFuture.thenApply(result -> null)
        );

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenComposeAsync(v -> {
                    try {
                        List<JasperPrint> jasperPrintList = List.of(
                                datosBasicosFuture.get(),
                                estudiosFuture.get(),
                                experienciaFuture.get(),
                                tramiteFuture.get(),
                                declaracionesFuture.get()
                        );
                        String pdfBase64 = unificarPDFs(jasperPrintList);
                        return CompletableFuture.completedFuture(pdfBase64);
                    } catch (InterruptedException | ExecutionException | JRException e) {
                        throw new RuntimeException("Error al unificar los PDFs", e);
                    }
                });
    }


    @Async("taskExecutor")
    public CompletableFuture<JasperPrint> crearHojaDatosBasicos(PostuladoDatosDTO postuladoDatos) throws ExcepcionPersonalizada {
        try {
            InputStream templateStream = getClass().getResourceAsStream("/template/reports/datosBasicos.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

            Map<String, Object> parameters = new HashMap<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            parameters.put("db_primerNombre", getValueOrDefault(postuladoDatos.getPrimerNombre()));
            parameters.put("db_segundoNombre", getValueOrDefault(postuladoDatos.getSegundoNombre()));
            parameters.put("db_primerApellido", getValueOrDefault(postuladoDatos.getPrimerApellido()));
            parameters.put("db_segundoApellido", getValueOrDefault(postuladoDatos.getSegundoApellido()));
            parameters.put("db_identificacion", getValueOrDefault(postuladoDatos.getIdentificacion()));
            parameters.put("db_genero", getValueOrDefault(postuladoDatos.getGenero()));

            Date fechaNacimiento = postuladoDatos.getFechaNacimiento();
            parameters.put("db_fechaNacimiento", fechaNacimiento != null ? dateFormat.format(fechaNacimiento) : "-");

            parameters.put("db_nacionalidad", getValueOrDefault(postuladoDatos.getNacionalidad()));
            parameters.put("db_ciudadNacimiento", getValueOrDefault(postuladoDatos.getCiudadNacimiento()));
            parameters.put("db_ciudadResidencia", getValueOrDefault(postuladoDatos.getCiudadResidencia()));
            parameters.put("db_direccionResidencia", getValueOrDefault(postuladoDatos.getDireccionResidencia()));
            parameters.put("db_telefono", getValueOrDefault(postuladoDatos.getNumeroContacto()));
            parameters.put("db_estadoCivil", getValueOrDefault(postuladoDatos.getEstadoCivil()));
            parameters.put("db_nombreConyuge", getValueOrDefault(postuladoDatos.getNombreConyuge()));
            parameters.put("db_identificacionConyuge", getValueOrDefault(postuladoDatos.getIdentificacionConyuge()));
            parameters.put("db_cargoConyuge", getValueOrDefault(postuladoDatos.getOcupacionConyuge()));
            parameters.put("db_empresaConyuge", getValueOrDefault(postuladoDatos.getEmpresaOcupacionConyuge()));
            parameters.put("db_ciudadNotificacion", getValueOrDefault(postuladoDatos.getCiudadNotificacion()));
            parameters.put("db_direccionNotificacion", getValueOrDefault(postuladoDatos.getDireccionNotificacion()));
            parameters.put("db_emailNotificacion", getValueOrDefault(postuladoDatos.getEmail()));

            URL imageDirUrl = getClass().getClassLoader().getResource("template/static/svg/");
            parameters.put("imageDir", imageDirUrl.toString());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return CompletableFuture.completedFuture(jasperPrint);
        } catch (Exception ex) {
            log.info(ex.getMessage());
            throw new ExcepcionPersonalizada("Error al crear el PDF de Datos Básicos", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Async("taskExecutor")
    public CompletableFuture<JasperPrint> crearHojaEstudios(List<PostuladoEstudioDTO> postuladoEstudio) throws ExcepcionPersonalizada {
        try {
            List<Map<String, String>> paramList = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            for (PostuladoEstudioDTO estudio : postuladoEstudio) {
                Map<String, String> subreportParams = new HashMap<>();
                subreportParams.put("es_nivelEducativo", getValueOrDefault(estudio.getNivelEducativo()));
                subreportParams.put("es_institucionEducativa", getValueOrDefault(estudio.getInstitucionEducativa()));
                subreportParams.put("es_carrera", getValueOrDefault(estudio.getNombreCarrera()));
                subreportParams.put("es_areaConocimiento", getValueOrDefault(estudio.getAreaConocimiento()));
                subreportParams.put("es_estado", getValueOrDefault(estudio.getEstado()));

                Date fechaTerminacion = estudio.getFechaTerminacion();
                subreportParams.put("es_fechaFinalizacion", fechaTerminacion != null ? dateFormat.format(fechaTerminacion) : "-");

                subreportParams.put("es_tarjetaProfesional", getValueOrDefault(estudio.getTarjetaProfesional()));
                paramList.add(subreportParams);
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(paramList);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("estudiosList", dataSource);

            URL imageDirUrl = getClass().getClassLoader().getResource("template/static/svg/");
            parameters.put("imageDir", imageDirUrl.toString());

            InputStream templateStream = getClass().getResourceAsStream("/template/reports/estudios.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

            InputStream subreportStream = getClass().getResourceAsStream("/template/reports/subreporteEstudios.jrxml");
            JasperReport jasperSubReport = JasperCompileManager.compileReport(subreportStream);
            parameters.put("subreporteEstudios", jasperSubReport);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            return CompletableFuture.completedFuture(jasperPrint);
        } catch (Exception ex) {
            log.info(ex.getMessage());
            throw new ExcepcionPersonalizada("Error al crear el PDF de Datos Básicos", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<JasperPrint> crearHojaExperiencia(List<PostuladoCargosSinPosesionDTO> postuladoCargosSinPosesion,
                                                               List<PostuladoCargosPosesionDTO> postuladoCargosPosesion,
                                                               List<ExperienciaPostuladoDTO> experienciaPostulado)  throws ExcepcionPersonalizada {
        try {
            List<Map<String, String>> paramListExperiencia = new ArrayList<>();
            List<Map<String, String>> paramListSinPosesion = new ArrayList<>();
            List<Map<String, String>> paramListPosesion = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            for (PostuladoCargosPosesionDTO cargosPosesion : postuladoCargosPosesion) {
                Map<String, String> posesionParams = new HashMap<>();
                posesionParams.put("ex_cpEntidad", getValueOrDefault(cargosPosesion.getEntidad()));
                posesionParams.put("ex_cpCargo", getValueOrDefault(cargosPosesion.getCargo()));
                Date cpFechaInicio = cargosPosesion.getFechaDesde();
                posesionParams.put("ex_cpFechaInicio", cpFechaInicio != null ? dateFormat.format(cpFechaInicio) : "-");
                Date cpFechaFinalizacion = cargosPosesion.getFechaHasta();
                posesionParams.put("ex_cpFechaFinalizacion", cpFechaFinalizacion != null ? dateFormat.format(cpFechaFinalizacion) : "-");
                posesionParams.put("ex_cpActivo", getValueOrDefault(cargosPosesion.getActivo()));
                posesionParams.put("ex_cpObservacion", getValueOrDefault(cargosPosesion.getObservacion()));
                posesionParams.put("ex_cpEstado", getValueOrDefault(cargosPosesion.getEstado()));
                paramListPosesion.add(posesionParams);
            }

            for(PostuladoCargosSinPosesionDTO cargosSinPosesion: postuladoCargosSinPosesion){
                Map<String, String> sinPosesionParams = new HashMap<>();
                sinPosesionParams.put("ex_cspEntidad", getValueOrDefault(cargosSinPosesion.getEntidad()));
                sinPosesionParams.put("ex_cspCargo", getValueOrDefault(cargosSinPosesion.getCargo()));
                sinPosesionParams.put("ex_cspAreaDesempenio", getValueOrDefault(cargosSinPosesion.getAreaDesempenio()));
                sinPosesionParams.put("ex_cspMotivoRetiro", getValueOrDefault(cargosSinPosesion.getMotivoRetiro()));
                Date cspFechaInicio = cargosSinPosesion.getFechaInicio();
                sinPosesionParams.put("ex_cspFechaInicio", cspFechaInicio != null ? dateFormat.format(cspFechaInicio) : "-");
                Date cspFechaFinalizacion = cargosSinPosesion.getFechaRetiro();
                sinPosesionParams.put("ex_cspFechaFinalizacion", cspFechaFinalizacion != null ? dateFormat.format(cspFechaFinalizacion) : "-" );
                paramListSinPosesion.add(sinPosesionParams);
            }
            for(ExperienciaPostuladoDTO experiencia: experienciaPostulado) {
                Map<String, String> experienciaParams = new HashMap<>();
                experienciaParams.put("ex_esnvSector", getValueOrDefault(experiencia.getSector()));
                experienciaParams.put("ex_esnvClaseSociedad", getValueOrDefault(experiencia.getClaseDeSociedad()));
                experienciaParams.put("ex_esnvRazonSocial", getValueOrDefault(experiencia.getRazonSocial()));
                experienciaParams.put("ex_esnvNombreCargo", getValueOrDefault(experiencia.getNombreCargo()));
                experienciaParams.put("ex_esnvTipoCargo", getValueOrDefault(experiencia.getNombreArchivo()));
                experienciaParams.put("ex_esnvAreaDesempenio", getValueOrDefault(experiencia.getAreaDesempenio()));
                experienciaParams.put("ex_esnvMotivoRetiro", getValueOrDefault(experiencia.getMotivoRetiro()));
                Date esnvFechaInicio = experiencia.getFechaInicio();
                experienciaParams.put("ex_esnvFechaInicio", esnvFechaInicio != null ? dateFormat.format(esnvFechaInicio) : "-");
                Date esnvFechaFinalizacion = experiencia.getFechaRetiro();
                experienciaParams.put("ex_esnvFechaFinalizacion", esnvFechaFinalizacion != null ? dateFormat.format(esnvFechaFinalizacion) : "-");
                paramListExperiencia.add(experienciaParams);
            }
            JRBeanCollectionDataSource dataSourceCargos = new JRBeanCollectionDataSource(paramListPosesion);
            JRBeanCollectionDataSource dataSourceSinCargos = new JRBeanCollectionDataSource(paramListSinPosesion);
            JRBeanCollectionDataSource dataSourceExperiencia = new JRBeanCollectionDataSource(paramListExperiencia);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("posesionesList", dataSourceCargos);
            parameters.put("sinPosesionesList", dataSourceSinCargos);
            parameters.put("experienciaList", dataSourceExperiencia);

            URL imageDirUrl = getClass().getClassLoader().getResource("template/static/svg/");
            parameters.put("imageDir", imageDirUrl.toString());

            InputStream templateStream = getClass().getResourceAsStream("/template/reports/experiencia.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return CompletableFuture.completedFuture(jasperPrint);
        } catch (Exception ex) {
            log.info(ex.getMessage());
            throw new ExcepcionPersonalizada("Error al crear el PDF de Datos Básicos", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<JasperPrint> crearHojaTramite(DatosBasicosDto datosBasicosTramite,
                                                           InfoNombramientoDto datosNombramiento,
                                                           InfoDesignacionDto infoDesignacion,
                                                           InfoContactoEntidad infoContactoEntidad,
                                                           InfoServidorPublico infoServidorPublico ,
                                                           InfoOtrosRepresentantes infoRepresentantes,
                                                           InfoJuntaDirectiva infoJuntaDirectiva,
                                                           InfoAdicionalDefensorConsumidor infoAdicionalDefensor)  throws ExcepcionPersonalizada {
        try {
            InputStream templateStream = getClass().getResourceAsStream("/template/reports/tramite.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

            Map<String, Object> parameters = new HashMap<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            //Sección Datos Básicos
            parameters.put("tm_nombreCandidato", getValueOrDefault(datosBasicosTramite.getNombreCandidato()));
            parameters.put("tm_identificacion", getValueOrDefault(datosBasicosTramite.getNumeroIdentificacion()));
            parameters.put("tm_cargo", getValueOrDefault(datosBasicosTramite.getCargoRealizar()));
            parameters.put("tm_servidorPublico", getValueOrDefault(datosBasicosTramite.getServidorPublico()));
            parameters.put("tm_entidad", getValueOrDefault(datosBasicosTramite.getEntidad()));

            //Sección designación
            parameters.put("tm_documentoDesignacion", getValueOrDefault(infoDesignacion.getDocumentoDesignacion()));
            parameters.put("tm_numerpDocumentoDesignacion", getValueOrDefault(infoDesignacion.getNumeroDocumentoDesignacion()));
            parameters.put("tm_fechaDocumentoDesignacion", getValueOrDefault(infoDesignacion.getFechaDocumentoDesignacion()));
            parameters.put("tm_nombreCandidatizo", getValueOrDefault(infoDesignacion.getNombreCandidatizo()));
            parameters.put("tm_enRepresentacion", getValueOrDefault(infoDesignacion.getEnRepresentacion()));
            parameters.put("tm_fechaAceptacion", getValueOrDefault(infoDesignacion.getFechaAceptacion()));
            parameters.put("tm_vinculoEntidad", getValueOrDefault(infoDesignacion.getVinculoEntidad()));
            parameters.put("tm_certificacionVigenteAMV", getValueOrDefault(infoDesignacion.getCertificacionAmv()));
            parameters.put("tm_conflictoInteresPosesion", getValueOrDefault(infoDesignacion.getConflictoInteres()));
            parameters.put("tm_horasMensualesDedicacion", getValueOrDefault(infoDesignacion.getHorasMensualesDedicacion()));

            //Sección nombramiento
            parameters.put("tm_claseNombramiento", getValueOrDefault(datosNombramiento.getClaseNombramiento()));
            parameters.put("tm_cargoPostula", getValueOrDefault(datosNombramiento.getCargoDesempenar()));
            parameters.put("tm_nombreCargo", getValueOrDefault(datosNombramiento.getCargoPostulado()));
            parameters.put("tm_calidadCargo", getValueOrDefault(datosNombramiento.getCalidadCargo()));
            parameters.put("tm_personaReemplaza", getValueOrDefault(datosNombramiento.getPersonaReemplazar()));
            parameters.put("tm_reemplazaIdentificacion", getValueOrDefault(datosNombramiento.getNumeroIdentificacion()));
            parameters.put("tm_motivoReemplazo", getValueOrDefault(datosNombramiento.getMotivoReemplazo()));
            parameters.put("tm_organoDesigna", getValueOrDefault(datosNombramiento.getOrganoDesigno()));

            // Sección datos contacto entidad
            parameters.put("tm_personaCargoTramite", getValueOrDefault(infoContactoEntidad.getPersonaResponsable()));
            parameters.put("tm_cargoContactoEntidad", getValueOrDefault(infoContactoEntidad.getCargo()));
            parameters.put("tm_emailResponsableEntidad", getValueOrDefault(infoContactoEntidad.getEmailResponsableEntidad()));
            parameters.put("tm_telefonoNotificacionContacto", getValueOrDefault(infoContactoEntidad.getTelNotificacion()));
            parameters.put("tm_extensionContactoEntidad", getValueOrDefault(infoContactoEntidad.getExtension()));
            parameters.put("tm_direccionNotificacionContacto", getValueOrDefault(infoContactoEntidad.getDireccionNotificacion()));
            parameters.put("tm_postuladoAutorizaNotificacion", getValueOrDefault(infoContactoEntidad.getNotificacionElectronica()));
            parameters.put("tm_emailNotificacionContacto", getValueOrDefault(infoContactoEntidad.getEmailNotificacion()));

            // Sección servidor público
            parameters.put("tm_cargoServidorPublico", getValueOrDefault(infoServidorPublico.getCargoServidorPublico()));
            parameters.put("tm_entidadServidorPublico", getValueOrDefault(infoServidorPublico.getEntidadServidorPublico()));

            // Sección datos adicionales defensor
            parameters.put("tm_facultadoEjercerConciliador", getValueOrDefault(infoAdicionalDefensor.getFacultadConciliador()));
            parameters.put("tm_centroConciliacion", getValueOrDefault(infoAdicionalDefensor.getCentroConciliaciónInscrito()));
            parameters.put("tm_paisDefensor", getValueOrDefault(infoAdicionalDefensor.getPais()));
            parameters.put("tm_ciudadDefensor", getValueOrDefault(infoAdicionalDefensor.getCiudad()));
            parameters.put("tm_direccionDefensor", getValueOrDefault(infoAdicionalDefensor.getDireccion()));
            parameters.put("tm_numeroTelefonoDefensor", getValueOrDefault(infoAdicionalDefensor.getNumeroTelefono()));
            parameters.put("tm_numeroFaxDefensor", getValueOrDefault(infoAdicionalDefensor.getNumeroFax()));
            parameters.put("tm_numeroCelularDefensor", getValueOrDefault(infoAdicionalDefensor.getNumeroCelular()));
            parameters.put("tm_correoPrincipalDefensor", getValueOrDefault(infoAdicionalDefensor.getCorreoElectronicoPrincipal()));
            parameters.put("tm_correoSecundarioDefensor", getValueOrDefault(infoAdicionalDefensor.getCorreoElectronicoSecunadario()));

            //Sección Otros representantes
            parameters.put("tm_claseOtros", getValueOrDefault(infoRepresentantes.getClaseRepresentante()));
            parameters.put("tm_actividadOtros", getValueOrDefault(infoRepresentantes.getActividadRepresentante()));

            //Sección Junta diectiva
            parameters.put("tm_calidadJunta", getValueOrDefault(infoJuntaDirectiva.getCalidadJuntaDirectiva()));
            parameters.put("tm_renglonJunta", getValueOrDefault(infoJuntaDirectiva.getRenglonJuntaDirectiva()));

            URL imageDirUrl = getClass().getClassLoader().getResource("template/static/svg/");
            parameters.put("imageDir", imageDirUrl.toString());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return CompletableFuture.completedFuture(jasperPrint);
        } catch (Exception ex) {
            log.info(ex.getMessage());
            throw new ExcepcionPersonalizada("Error al crear el PDF de Datos Básicos", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<JasperPrint> crearHojaDeclaraciones(List<ComportamientoCrediticioDTO> comportamientoCrediticio,
                                                                 List<InvestigacionesPersonasDTO> investigacionesPersona,
                                                                 List<Map<String, Object>> sancionesEnFirme,
                                                                 List<InhabilidadPosesionProjection>  inhabilidadesComunesRevisorFiscal,
                                                                 List<Map<String, Object>> sancionesFirmeGenerales,
                                                                 List<InhabilidadPosesionProjection> otrasSituaciones,
                                                                 List<Map<String, Object>> inhabilidadesPosesion,
                                                                 List<AccionAporteDTO> accionesAportes,
                                                                 List<InhabilidadPosesionProjection> inhabilidadesComunesMiembroJunta,
                                                                 List<InhabilidadPosesionProjection> inhabilidadesComunesDefensor,
                                                                 List<InhabilidadPosesionProjection> inhabilidadesComunesFuncionario)  throws ExcepcionPersonalizada {
        try {
            InputStream templateStream = getClass().getResourceAsStream("/template/reports/reporteDeclaraciones.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

            Map<String, Object> parameters = new HashMap<>();
            List<Map<String, Object>> comportamientoCrediticioMap = convertirComportamientoCrediticioMap(comportamientoCrediticio);
            List<Map<String, Object>> investigacionesPersonaMap = convertirInvestigacionesPersona(investigacionesPersona);
            List<Map<String, Object>> sancionesEnFirmeMap = convertirSancionesFirmeMap(sancionesEnFirme);
            List<Map<String, Object>> inhabilidadesRevisorMap = convertirRespuestaMap(inhabilidadesComunesRevisorFiscal);
            List<Map<String, Object>> inhabilidadesMiembroJuntaMap = convertirRespuestaMap(inhabilidadesComunesMiembroJunta);
            List<Map<String, Object>> inhabilidadesDefensorMap = convertirRespuestaMap(inhabilidadesComunesDefensor);
            List<Map<String, Object>> inhabilidadesFuncionarioMap = convertirRespuestaMap(inhabilidadesComunesFuncionario);
            List<Map<String, Object>> otrasSituacionsMap = convertirRespuestaMap(otrasSituaciones);
            List<Map<String, Object>> accionesAportesMap = convertirAccionAporte(accionesAportes);

            JRBeanCollectionDataSource inhabilidadesPosesionDataSource = new JRBeanCollectionDataSource(inhabilidadesPosesion);
            JRBeanCollectionDataSource inhabilidadesRevisorMapDataSource = new JRBeanCollectionDataSource(inhabilidadesRevisorMap);
            JRBeanCollectionDataSource inhabilidadesMiembroMapDataSource = new JRBeanCollectionDataSource(inhabilidadesMiembroJuntaMap);
            JRBeanCollectionDataSource inhabilidadesDefensorMapDataSource = new JRBeanCollectionDataSource(inhabilidadesDefensorMap);
            JRBeanCollectionDataSource inhabilidadesFuncionarioMapDataSource = new JRBeanCollectionDataSource(inhabilidadesFuncionarioMap);
            JRBeanCollectionDataSource sancionesFirmeGeneralesDataSource = new JRBeanCollectionDataSource(sancionesFirmeGenerales);
            JRBeanCollectionDataSource otrasSituacionsMapDataSource = new JRBeanCollectionDataSource(otrasSituacionsMap);
            JRBeanCollectionDataSource comportamientoCrediticioMapDataSource = new JRBeanCollectionDataSource(comportamientoCrediticioMap);
            JRBeanCollectionDataSource investigacionesPersonaMapDataSource = new JRBeanCollectionDataSource(investigacionesPersonaMap);
            JRBeanCollectionDataSource sancionesEnFirmeMapDataSource = new JRBeanCollectionDataSource(sancionesEnFirmeMap);
            JRBeanCollectionDataSource accionesAportesMapDataSource = new JRBeanCollectionDataSource(accionesAportesMap);

            parameters.put("ImpedimentosGeneralesList", inhabilidadesPosesionDataSource);
            parameters.put("comunesRevisorFiscalList", inhabilidadesRevisorMapDataSource );
            parameters.put("comunesMiembroJuntaList", inhabilidadesMiembroMapDataSource );
            parameters.put("comunesDefensorList", inhabilidadesDefensorMapDataSource);
            parameters.put("comunesFuncionarioList", inhabilidadesFuncionarioMapDataSource );
            parameters.put("sancionesFirmeGeneralList", sancionesFirmeGeneralesDataSource);
            parameters.put("otrasSituacionesList", otrasSituacionsMapDataSource);
            parameters.put("comportamientoCrediticioList", comportamientoCrediticioMapDataSource);
            parameters.put("investigacionesList", investigacionesPersonaMapDataSource);
            parameters.put("sancionesList", sancionesEnFirmeMapDataSource);
            parameters.put("accionesAportesList", accionesAportesMapDataSource);

            URL imageDirUrl = getClass().getClassLoader().getResource("template/static/svg/");
            parameters.put("imageDir", imageDirUrl.toString());

            InputStream subreportStream = getClass().getResourceAsStream("/template/reports/subreportePregunta.jrxml");
            JasperReport jasperSubReport = JasperCompileManager.compileReport(subreportStream);
            parameters.put("subreportePregunta", jasperSubReport);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            return CompletableFuture.completedFuture(jasperPrint);
        } catch (Exception ex) {
            log.info(ex.getMessage());
            throw new ExcepcionPersonalizada("Error al crear el PDF de Datos Básicos", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String unificarPDFs(List<JasperPrint> jasperPrintList) throws JRException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();

        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));

        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCreatingBatchModeBookmarks(true);

        exporter.setConfiguration(configuration);
        exporter.exportReport();

        byte[] pdfBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(pdfBytes);
    }

    private List<Map<String, Object>> convertirRespuestaMap(List<InhabilidadPosesionProjection> list) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (InhabilidadPosesionProjection item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("ORDEN", item.getOrden());
            map.put("DESCRIPCION", item.getDescripcion());
            map.put("INHABILITADO", item.getInhabilitado());
            map.put("IDTIPOSECCIONINHABILIDADCARGO", item.getIdtiposeccioninhabilidadcargo());
            map.put("NOMBRE", item.getNombre());
            mapList.add(map);
        }
        return mapList;
    }

    private List<Map<String, Object>> convertirComportamientoCrediticioMap(List<ComportamientoCrediticioDTO> list) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if(!list.isEmpty()){
            for (ComportamientoCrediticioDTO item : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("rp_ccClaseCentral", item.getClaseCentral());
                map.put("rp_ccNombreClaseCentral", item.getNombreCentral());
                map.put("rp_ccTipoObligacion", item.getTipoObligacion());
                map.put("rp_ccFechaReporte", item.getFechaReporte() != null ? sdf.format(item.getFechaReporte()) : null);
                map.put("rp_ccMoraDe", item.getMoraDe());
                map.put("rp_ccEstado", item.getIdTipoEstadoDeuda().toString());
                map.put("rp_ccFechaPago", item.getFechaPago() != null ? sdf.format(item.getFechaPago()) : null);
                map.put("rp_ccCalidadReporte", item.getCalidadReporteCrediticio());
                map.put("rp_ccJustificacionReporte", item.getJustificacion());
                map.put("rp_ccPazSalvo", item.getCertificacion());
                map.put("rp_ccReportadoPor", item.getEmpresaReporta());
                map.put("isComportamientoCrediticioListEmpty", false);
                mapList.add(map);
            }
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("rp_ccClaseCentral", "-");
            map.put("rp_ccNombreClaseCentral", "-");
            map.put("rp_ccTipoObligacion", "-");
            map.put("rp_ccFechaReporte", "-");
            map.put("rp_ccMoraDe", "-");
            map.put("rp_ccEstado", "-");
            map.put("rp_ccFechaPago", "-");
            map.put("rp_ccCalidadReporte", "-");
            map.put("rp_ccJustificacionReporte", "-");
            map.put("rp_ccPazSalvo", "-");
            map.put("rp_ccReportadoPor", "-");
            map.put("isComportamientoCrediticioListEmpty", true);
            mapList.add(map);
        }

        return mapList;
    }

    private List<Map<String, Object>> convertirInvestigacionesPersona(List<InvestigacionesPersonasDTO> list) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if(!list.isEmpty()){
            for (InvestigacionesPersonasDTO item : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("rp_invTipoInvestigacion", item.getTipoInvestigacion());
                map.put("rp_invEntidadInvestiga", item.getEntidadInvestiga());
                map.put("rp_invMateriaInvestigacion", item.getMateriainvestigacion());
                map.put("rp_invTipoActo", item.getTipoActo());
                map.put("rp_invNumero", item.getNumero());
                map.put("rp_invEstado", item.getEstadoInvestigacion());
                map.put("rp_invFechaInicio", item.getFechaDesde() != null ? sdf.format(item.getFechaDesde()) : null);
                map.put("isInvestigacionesListEmpty", false);
                mapList.add(map);
            }
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("rp_invTipoInvestigacion", "-");
            map.put("rp_invEntidadInvestiga", "-");
            map.put("rp_invMateriaInvestigacion", "-");
            map.put("rp_invTipoActo", "-");
            map.put("rp_invNumero", "-");
            map.put("rp_invEstado", "-");
            map.put("rp_invFechaInicio", "-");
            map.put("isInvestigacionesListEmpty", true);
            mapList.add(map);
        }

        return mapList;
    }

    private List<Map<String, Object>> convertirSancionesFirmeMap(List<Map<String, Object>> results) {
        List<Map<String, Object>> mappedResults = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(!results.isEmpty()){
            for (Map<String, Object> result : results) {
                Map<String, Object> map = new HashMap<>();
                map.put("rp_sancTemaClasificacion", result.get("tema") != null ? result.get("tema").toString() : null);
                map.put("rp_sancNumeroActo", result.get("numeroactoadmin") != null ? result.get("numeroactoadmin").toString() : null);
                map.put("rp_sancDescripcion", result.get("descripcion") != null ? result.get("descripcion").toString() : null);
                map.put("rp_sancFechaActoAdmin", result.get("fechaactoadmin") != null ? sdf.format(result.get("fechaactoadmin")) : null);
                map.put("rp_sancTipoEstadoAdmin", result.get("estado") != null ? result.get("estado").toString() : null);
                map.put("isSancionesFirmeListEmpty", false);
                mappedResults.add(map);
            }
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("rp_sancTemaClasificacion", "-");
            map.put("rp_sancNumeroActo", "-");
            map.put("rp_sancDescripcion", "-");
            map.put("rp_sancFechaActoAdmin", "-");
            map.put("rp_sancTipoEstadoAdmin", "-");
            map.put("isSancionesFirmeListEmpty", true);
            mappedResults.add(map);
        }
        return mappedResults;
    }

    private List<Map<String, Object>> convertirAccionAporte(List<AccionAporteDTO> list) {
        List<Map<String, Object>> mapList = new ArrayList<>();

        if(!list.isEmpty()){
            for (AccionAporteDTO item : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("rp_accEntidadVigilada", item.getEntidadVigilada());
                map.put("rp_accRazonSocial", item.getRazonSocial());
                map.put("rp_accNit", item.getNit());
                map.put("rp_accParticipacion", item.getParticipacion() != null ? item.getParticipacion().toString() : null);
                map.put("rp_accInscritaBolsa", item.getInscritaEnBolsa());
                map.put("isAccionesAporteListEmpty", false);
                mapList.add(map);
            }
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("rp_accEntidadVigilada","-");
            map.put("rp_accRazonSocial", "-");
            map.put("rp_accNit", "-");
            map.put("rp_accParticipacion", "-");
            map.put("rp_accInscritaBolsa", "-");
            map.put("isAccionesAporteListEmpty", true);
            mapList.add(map);
        }

        return mapList;
    }

    private String getValueOrDefault(String value) {
        return (value == null || value.isEmpty()) ? "-" : value;
    }

    private CompletableFuture<PostuladoDatosDTO> obtenerDatosPostuladoAsync(Long idTramite) {
        return CompletableFuture.supplyAsync(() -> iPostuladoService.obtenerDatosPostulado(idTramite));
    }

    private CompletableFuture<List<PostuladoEstudioDTO>> obtenerEstudiosPostuladoAsync(Long idPersona) {
        return CompletableFuture.supplyAsync(() -> iPostuladoService.obtenerEstudiosPostulado(idPersona));
    }

    private CompletableFuture<List<PostuladoCargosSinPosesionDTO>> obtenerCargosSinPosesionPostuladoAsync(Long idPersona) {
        return CompletableFuture.supplyAsync(() -> iPostuladoService.obtenerCargosSinPosesionPostulado(idPersona));
    }

    private CompletableFuture<List<PostuladoCargosPosesionDTO>> obtenerCargosPosesionesPostuladoAsync(Long idPersona) {
        return CompletableFuture.supplyAsync(() -> iPostuladoService.obtenerCargosPosesionesPostulado(idPersona));
    }

    private CompletableFuture<List<ExperienciaPostuladoDTO>> obtenerExperienciaPostuladoAsync(Long idPersona) {
        return CompletableFuture.supplyAsync(() -> iPostuladoService.obtenerExperienciaPostulado(idPersona));
    }

    private CompletableFuture<DatosBasicosDto> obtenerDatosBasicosTramite(Long idTramite){
        return CompletableFuture.supplyAsync(() -> iTramitePosesionService.consultarDatosBasicos(idTramite));
    }

    private CompletableFuture<InfoNombramientoDto> obtenerDatosNombramiento(Long idTramite){
        return CompletableFuture.supplyAsync(() -> iTramitePosesionService.consultarInformacionNombramiento(idTramite));
    }

    private CompletableFuture<InfoDesignacionDto> obtenerInfoDesignacion(Long idTramite){
        return CompletableFuture.supplyAsync(() -> iTramitePosesionService.consultarInformacionDesignacion(idTramite));
    }

    private CompletableFuture<InfoContactoEntidad> obtenerInfoContactoEntidad(Long idTramite){
        return CompletableFuture.supplyAsync(() -> iTramitePosesionService.consultarInformacionContactoEntidad(idTramite));
    }

    private CompletableFuture<InfoServidorPublico> obtenerInfoServidorPublico(Long idTramite){
        return CompletableFuture.supplyAsync(() -> iTramitePosesionService.consultarInformacionServidorPublico(idTramite));
    }

    private CompletableFuture<InfoOtrosRepresentantes> obtenerInfoRepresentantes(Long idTramite){
        return CompletableFuture.supplyAsync(() -> iTramitePosesionService.consultarInformacionOtrosRepresentantes(idTramite));
    }

    private CompletableFuture<InfoJuntaDirectiva> obtenerInfoJuntaDirectiva(Long idTramite){
        return CompletableFuture.supplyAsync(() -> iTramitePosesionService.consultarInformacionJuntaDirectiva(idTramite));
    }

    private CompletableFuture<InfoAdicionalDefensorConsumidor> obtenerInfoAdicionalDefensor(Long idTramite){
        return CompletableFuture.supplyAsync(() -> iTramitePosesionService.consultarInformacionAdicionalDefensorConsumidor(idTramite));
    }

    private CompletableFuture<List<ComportamientoCrediticioDTO>> obtenerComportamientoCrediticio(Long idPersona){
        return CompletableFuture.supplyAsync(() -> iComportamientoCrediticioService.getComportamientoCrediticio(idPersona));
    }

    private CompletableFuture<List<InvestigacionesPersonasDTO>> obtenerInvestigacionesPersona(Long idPersona){
        return CompletableFuture.supplyAsync(() -> investigacionPersonaService.getInvestigacionPersona(idPersona));
    }

    private CompletableFuture<List<Map<String, Object>>> obtenerSancionesEnFirme(Long idTramite){
        return CompletableFuture.supplyAsync(() -> inhabilidadPosesionService.getSancionesenfirme(idTramite));
    }


    private CompletableFuture<List<InhabilidadPosesionProjection>>  obtenerInhabilidadesComunesRevisorFiscal(Long idTramite){
        List<Integer> seccion = new ArrayList<>();
        seccion.add(6);
        return CompletableFuture.supplyAsync(() -> inhabilidadPosesionService.obtenerInhabilidadesComunesPorSeccionYTramite(seccion, idTramite.intValue()));
    }

    private CompletableFuture<List<InhabilidadPosesionProjection>>  obtenerInhabilidadesMiembroJunta(Long idTramite){
        List<Integer> seccion = new ArrayList<>();
        seccion.add(5);
        return CompletableFuture.supplyAsync(() -> inhabilidadPosesionService.obtenerInhabilidadesComunesPorSeccionYTramite(seccion, idTramite.intValue()));
    }

    private CompletableFuture<List<InhabilidadPosesionProjection>>  obtenerInhabilidadesComunesDefensorConsumidor(Long idTramite){
        List<Integer> seccion = new ArrayList<>();
        seccion.add(7);
        return CompletableFuture.supplyAsync(() -> inhabilidadPosesionService.obtenerInhabilidadesComunesPorSeccionYTramite(seccion, idTramite.intValue()));
    }

    private CompletableFuture<List<InhabilidadPosesionProjection>>  obtenerInhabilidadesComunesFuncionarioResponsable(Long idTramite){
        List<Integer> seccion = new ArrayList<>();
        seccion.add(6);
        return CompletableFuture.supplyAsync(() -> inhabilidadPosesionService.obtenerInhabilidadesComunesPorSeccionYTramite(seccion, idTramite.intValue()));
    }

    private CompletableFuture<List<Map<String, Object>>> obtenerSancionesFirmeGenerales(Long idTramite){
        return CompletableFuture.supplyAsync(() -> inhabilidadPosesionService.findTipoCargoByIdTramitePosesion(idTramite));
    }

    private CompletableFuture<List<InhabilidadPosesionProjection>>  obtenerOtrasSituaciones(Long idTramite){
        return CompletableFuture.supplyAsync(() -> inhabilidadPosesionService.getOtrasSituaciones(idTramite.intValue()));
    }

    private CompletableFuture<List<Map<String, Object>>> obtenerInhabilidadesPosesion(Long idTramite){
        return CompletableFuture.supplyAsync(() -> inhabilidadPosesionService.findInhabilidadPosesionByTramiteAndSeccion(idTramite));
    }

    private CompletableFuture<List<AccionAporteDTO>> obtenerAccionesAportes(Long idPersona){
        return CompletableFuture.supplyAsync(() -> iReporteAntecedentesService.obtenerAccionesAporte(idPersona));
    }
}


