package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.AccionAporteDTO;
import co.siri.posesiones.dtos.IAccionAporte;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.ReporteAntecedentesRepository;
import co.siri.posesiones.services.IReporteAntecedentesService;
import co.siri.posesiones.utilidades.Constantes;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que implementa la interfaz {@link IReporteAntecedentesService} para gestionar los reportes de antecedentes.
 * <p>
 * Autor: John Macias
 */
@Slf4j
@Service
public class ReporteAntecedentesService implements IReporteAntecedentesService {

    @Autowired
    private ReporteAntecedentesRepository reporteAntecedentesRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene una lista de acciones de aporte para una persona específica.
     *
     * @param idPersona el ID de la persona para la que se desean obtener las acciones de aporte
     * @return una lista de objetos {@link AccionAporteDTO} que representan las acciones de aporte
     * @throws ExcepcionPersonalizada si ocurre algún error durante la obtención de las acciones de aporte
     */
    @Override
    public List<AccionAporteDTO> obtenerAccionesAporte(Long idPersona) throws ExcepcionPersonalizada {
        log.info(Constantes.INICIO_SERVICIO_ACCIONES_APORTE);
        List<IAccionAporte> accionesAporte = reporteAntecedentesRepository.obtenerAccionesAporte(idPersona);
        if(accionesAporte.isEmpty()){
            log.info(MessageFormat.format(Constantes.NO_APORTES_ACCIONES, idPersona));
            return new ArrayList<>();
        }
        log.info(accionesAporte.get(0).getEntidadVigilada());
        log.info(Constantes.CONSULTA_EXITOSA);
        return accionesAporte.stream().map(accionAporte -> modelMapper.map(accionAporte, AccionAporteDTO.class)).toList();
    }
}
