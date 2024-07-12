package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.*;
import co.siri.posesiones.entities.PrioridadTramitePosesion;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.PriorizacionRepository;
import co.siri.posesiones.services.IPrioridadService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrioridadService implements IPrioridadService {
    private static final Logger logger = LoggerFactory.getLogger(IPrioridadService.class);
    private final PriorizacionRepository repository;

    private final ModelMapper modelMapper;

    public PrioridadService(PriorizacionRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PrioridadTramitePosesion> porPrioridades() {
        try {
            List<PrioridadTramitePosesion> porPrioridades = repository.porPrioridades();

            return porPrioridades.stream().map(estaPrioridad -> modelMapper.map(estaPrioridad, PrioridadTramitePosesion.class)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<TipoEntidaResponseDTO> porTipoEntidad() {
        try {
            return repository.porTipoEntidad();
        } catch (Exception e) {
            logger.error("Error en consulta", e);
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<TipoEntidaResponseDTO> filtroporTipoEntidad(String tipoentidad) {
        try {
            return repository.filtrarPorTipoEntidad(tipoentidad);
        } catch (Exception e) {
            logger.error("Error en consulta", e);
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<PrioridadEntidadResponseDTO> porEntidad() {
        try {
            return repository.porEntidad();
        } catch (Exception e) {
            logger.error("Error en consulta", e);
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<PrioridadEntidadResponseDTO> filtroporEntidad(String entidad) {
        try {
            return repository.filtrarPorEntidad(entidad);
        } catch (Exception e) {
            logger.error("Error en consulta", e);
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<PrioridadPersonaResponseDTO> filtroporPersona(String persona) {
        try {
            return repository.filtroPorPersona(persona);
        } catch (Exception e) {
            logger.error("Error en consulta", e);
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<PrioridadPersonaResponseDTO> porPersona() {
        try {
            return repository.porPersona();
        } catch (Exception e) {
            logger.error("Error en consulta", e);
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<PersonayEntidadResponseDTO> porPersonayEntidad() {
        try {
            return repository.porPersonayEntidad();
        } catch (Exception e) {
            logger.error("Error en consulta", e);
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<PersonayEntidadResponseDTO> filtroporPersonayEntidad(String personayentidad) {
        try {

            String personayentidadSinEspacios = personayentidad.replaceAll("\\s", "");
            return repository.filtroPorPersonayEntidad(personayentidadSinEspacios);

        } catch (Exception e) {
            logger.error("Error en consulta", e);
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void guardarPrioridad(PrioridadRequestDTO requestDTO) {
        Integer Idtramite = repository.ultimoId();
        try {
            repository.insertarprioridad(Idtramite, requestDTO.getIdtipoentidad(), requestDTO.getIdentidad(), requestDTO.getEntidadpublica(), requestDTO.getIdpersona(), requestDTO.getNumeroident(), requestDTO.getIdtramite(), requestDTO.getIdusuario(), requestDTO.getIpcliente());
        } catch (Exception e) {
            logger.error("Error en consulta", e);
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Transactional
    public void guardarEntidadPublica(PrioridadRequestDTO requestDTO) {
        Integer Idtramite = repository.EncontrarId(requestDTO.getIdprioridadtramiteposesion());
        Integer IdEntidadCound = repository.EncontrarId(requestDTO.getIdentidad());
        Integer IdEntidad = repository.EncontrarIdEntidad(requestDTO.getIdentidad());
        Integer IdUltimo = repository.ultimoId();
        try {
            if (Idtramite == 1) {
                if (requestDTO.getIdprioridadtramiteposesion() != null) {
                    repository.actualizarprioridad(requestDTO.getIdprioridadtramiteposesion(), requestDTO.getIdtipoentidad(), requestDTO.getIdentidad(), requestDTO.getEntidadpublica(), requestDTO.getIdpersona(), requestDTO.getNumeroident(), requestDTO.getIdtramite(), requestDTO.getIdusuario(), requestDTO.getIpcliente());
                } else {
                    repository.actualizarprioridad(IdEntidad, requestDTO.getIdtipoentidad(), requestDTO.getIdentidad(), requestDTO.getEntidadpublica(), requestDTO.getIdpersona(), requestDTO.getNumeroident(), requestDTO.getIdtramite(), requestDTO.getIdusuario(), requestDTO.getIpcliente());
                }

            } else {
                repository.insertarprioridad(IdUltimo, requestDTO.getIdtipoentidad(), requestDTO.getIdentidad(), requestDTO.getEntidadpublica(), requestDTO.getIdpersona(), requestDTO.getNumeroident(), requestDTO.getIdtramite(), requestDTO.getIdusuario(), requestDTO.getIpcliente());
            }

        } catch (Exception e) {
            logger.error("Error en consulta", e);
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public void eliminarPrioridad(Integer Idtramite) {
        try {
            repository.eliminarPrioridad(Idtramite);
        } catch (Exception e) {
            logger.error("Error en consulta", e);
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<PrioridadDTO> consultaPrioridades() {

        List<Object[]> listaObjectos = repository.consultaPrioridades();

        List<PrioridadDTO> listaResult = new ArrayList<>();
        for (Object[] objetos : listaObjectos) {
            PrioridadDTO prioridadDTO = new PrioridadDTO();
            prioridadDTO.setIdPrioridadTramitePosesion((Integer) objetos[0]);
            prioridadDTO.setNombrePrioridad((String) objetos[1]);
            listaResult.add(prioridadDTO);
        }
        return listaResult;
    }
}
