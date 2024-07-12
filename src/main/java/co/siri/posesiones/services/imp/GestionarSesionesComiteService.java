package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.*;
import co.siri.posesiones.entities.SesionComite;
import co.siri.posesiones.entities.TipoModalidadSesion;
import co.siri.posesiones.entities.TipoSesion;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.SesionComiteRepository;
import co.siri.posesiones.repositories.TipoModalidadSesionRepository;
import co.siri.posesiones.repositories.TipoSesionComiteRepository;
import co.siri.posesiones.services.IGestionarSesionesComiteService;
import co.siri.posesiones.utilidades.dto.PaginacionDTO;
import co.siri.posesiones.utilidades.dto.PaginacionInDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class GestionarSesionesComiteService implements IGestionarSesionesComiteService {

    private TipoSesion tipoSesion;
    private TipoModalidadSesion tipoModalidad;
    private final TipoSesionComiteRepository tipoSesionComiteRepository;
    private final TipoModalidadSesionRepository tipoModalidadSesionRepository;

    private final SesionComiteRepository sesionComiteRepository;
    private final ModelMapper modelMapper;
    private static final String ERROR_FECHA_MENOR = "La fecha de la sesión de comité debe ser mayor o igual a la del sistema";
    private static final String ERROR_FECHA_EXISTENTE = "Ya existe una sesión programada para esta fecha.";

    @Override
    public PaginacionDTO obtenerListaSesionesComite(PaginacionInDTO paginado) throws ExcepcionPersonalizada{
        PaginacionDTO resultado = new PaginacionDTO();
        if(paginado.getPaginaActual() == null){
            resultado.setTotalElements(0L);
            resultado.setTotalPages(0);
            resultado.setContenido(new ArrayList<>());
            return resultado;
        }
        Pageable pageable = PageRequest.of(paginado.getPaginaActual(), paginado.getTamanoPagina());
        Page<SesionComite> sesionesComite = sesionComiteRepository.obtenerSesionesComite(pageable);
        List<SesionComiteOutDTO> sesiones =
                sesionesComite.getContent().stream().map(sesion -> modelMapper.map(sesion, SesionComiteOutDTO.class)).toList();
        resultado.setTotalElements(sesionesComite.getTotalElements());
        resultado.setTotalPages(sesionesComite.getTotalPages());
        resultado.setContenido(sesiones);
        return resultado;
    }

    @Transactional
    public MensajeSolicitudOut crearSesionComite(SesionComiteInDTO sesionComite) throws ExcepcionPersonalizada{
        MensajeSolicitudOut respuesta = new MensajeSolicitudOut();
        if(isDateInvalid(sesionComite.getFechaComite())){
            respuesta.setMensaje(ERROR_FECHA_MENOR);
            respuesta.setExitoso(false);
            return respuesta;
        }
        Optional<SesionComite> existeFecha =
                sesionComiteRepository.findByfechaComite(sesionComite.getFechaComite());
        if(existeFecha.isPresent()){
            respuesta.setMensaje(ERROR_FECHA_EXISTENTE);
            respuesta.setExitoso(false);
            return respuesta;
        }
        tipoSesion = tipoSesionComiteRepository
                .findByIdTipoSesion(sesionComite.getIdTipoSesionComite());
        tipoModalidad = tipoModalidadSesionRepository
                .findByIdTipoModalidad(sesionComite.getIdModalidadComite());
        SesionComite nuevaSesion = new SesionComite();
            nuevaSesion.setFechaComite(sesionComite.getFechaComite());
            nuevaSesion.setTipoSesion(tipoSesion);
            nuevaSesion.setTipoModalidad(tipoModalidad);
            nuevaSesion.setIdUsuario(sesionComite.getIdusuario());
            nuevaSesion.setIpCliente(sesionComite.getIpcliente());
        sesionComiteRepository.save(nuevaSesion);
        respuesta.setExitoso(true);
        respuesta.setMensaje("Se ha creado la reunión correctamente");
        return respuesta;
    }

    @Override
    public MensajeSolicitudOut editarSesionComite(SesionComiteInDTO sesionComite) throws ExcepcionPersonalizada{
        MensajeSolicitudOut respuesta = new MensajeSolicitudOut();
        if(isDateInvalid(sesionComite.getFechaComite())){
            respuesta.setMensaje(ERROR_FECHA_MENOR);
            respuesta.setExitoso(false);
            return respuesta;
        }
        Optional<SesionComite> existente = sesionComiteRepository.findById(sesionComite.getIdSesion());
        if (existente.isEmpty()) {
            respuesta.setMensaje("La sesión no existe");
            respuesta.setExitoso(false);
            return respuesta;
        }
        Optional<SesionComite> conflicto = sesionComiteRepository.findByfechaComite(sesionComite.getFechaComite());
        if (conflicto.isPresent() && !conflicto.get().getIdSesionComite().equals(sesionComite.getIdSesion())) {
            respuesta.setMensaje(ERROR_FECHA_EXISTENTE);
            respuesta.setExitoso(false);
            return respuesta;
        }
        tipoSesion = tipoSesionComiteRepository
                .findByIdTipoSesion(sesionComite.getIdTipoSesionComite());
        tipoModalidad = tipoModalidadSesionRepository
                .findByIdTipoModalidad(sesionComite.getIdModalidadComite());
        SesionComite sesionExistente = existente.get();
        sesionExistente.setFechaComite(sesionComite.getFechaComite());
        sesionExistente.setTipoSesion(tipoSesion);
        sesionExistente.setTipoModalidad(tipoModalidad);
        sesionExistente.setIpCliente(sesionComite.getIpcliente());
        sesionExistente.setIdUsuario(sesionComite.getIdusuario());
        sesionComiteRepository.save(sesionExistente);
        respuesta.setExitoso(true);
        respuesta.setMensaje("Se ha editado la sesión de comité correctamente");
        return respuesta;
    }

    @Override
    public List<TipoModalidadComiteDTO> obtenerModalidades() throws ExcepcionPersonalizada{
        try {
            List<TipoModalidadSesion> modalidades = tipoModalidadSesionRepository.obtenerListaModalidades();
            return modalidades.stream().map(modalidad ->
                    modelMapper.map(modalidad, TipoModalidadComiteDTO.class)).toList();
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<TipoSesionComiteDTO> obtenerSesiones() throws ExcepcionPersonalizada{
        try {
            List<TipoSesion> sesiones = tipoSesionComiteRepository.obtenerListaSesiones();
            return sesiones.stream().map(sesion ->
                    modelMapper.map(sesion, TipoSesionComiteDTO.class)).toList();
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static boolean isDateInvalid(Date date) {
        LocalDate localDate = convertToLocalDate(date);
        return localDate.isBefore(LocalDate.now());
    }

    private static LocalDate convertToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

}
