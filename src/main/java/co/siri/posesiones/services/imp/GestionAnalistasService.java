package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.ActualizarAnalistaDTO;
import co.siri.posesiones.dtos.AgregarAnalistaDTO;
import co.siri.posesiones.dtos.GestionAnalistasDTO;
import co.siri.posesiones.entities.AnalistaTramitePosesion;
import co.siri.posesiones.entities.PrioridadPorAnalista;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.GestionAnalistasRepository;
import co.siri.posesiones.repositories.PrioridadPorAnalistaRepository;
import co.siri.posesiones.services.IGestionAnalistasService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GestionAnalistasService implements IGestionAnalistasService {

    @Autowired
    private GestionAnalistasRepository repository;

    @Autowired
    private PrioridadPorAnalistaRepository prioridadPorAnalistaRepository;

    @Override
    public List<GestionAnalistasDTO> listarAnalistas() {
        return repository.listarAnalistas();
    }

    @Override
    public AnalistaTramitePosesion agregarAnalista(AgregarAnalistaDTO agregarAnalistaDTO) {
        if (agregarAnalistaDTO.getNombre() == null || agregarAnalistaDTO.getNombre().isEmpty()) {
            throw new ExcepcionPersonalizada("El nombre del analista es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (agregarAnalistaDTO.getIdentificacion() == null || agregarAnalistaDTO.getIdentificacion().isEmpty()) {
            throw new ExcepcionPersonalizada("La identificación del analista es obligatoria.", HttpStatus.BAD_REQUEST);
        }

        AnalistaTramitePosesion nuevoAnalista = new AnalistaTramitePosesion();
        nuevoAnalista.setNombre(agregarAnalistaDTO.getNombre());
        nuevoAnalista.setIdentificacion(agregarAnalistaDTO.getIdentificacion());
        nuevoAnalista.setIdTipoAnalista(agregarAnalistaDTO.getIdTipoAnalista());
        nuevoAnalista.setLimiteCarga(agregarAnalistaDTO.getLimiteCarga());
        nuevoAnalista.setIdUsuario(agregarAnalistaDTO.getIdUsuario());
        nuevoAnalista.setLogin(agregarAnalistaDTO.getLogin());
        nuevoAnalista.setActivo(agregarAnalistaDTO.getActivo().toString());
        nuevoAnalista.setIpCliente(agregarAnalistaDTO.getIpCliente());
        repository.save(nuevoAnalista);

        Long idAnalistaTramitePosesion = nuevoAnalista.getIdAnalistaTramitePosesion();

        creacionAnalistaPrioridad(idAnalistaTramitePosesion, agregarAnalistaDTO.getIdPrioridad(),
                agregarAnalistaDTO.getIpCliente(), agregarAnalistaDTO.getIdUsuario());

        return repository.findById(idAnalistaTramitePosesion).orElseThrow(() -> new ExcepcionPersonalizada(
                "No se pudo encontrar al analista recién creado con ID: " + idAnalistaTramitePosesion,
                HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private void creacionAnalistaPrioridad(Long idAnalista, Long idPrioridad, String ipCliente, Long idUsuario) {
        PrioridadPorAnalista prioridadPorAnalista = new PrioridadPorAnalista();
        prioridadPorAnalista.setIdAnalistaTramitePosesion(idAnalista);
        prioridadPorAnalista.setIdPrioridadTramitePosesion(idPrioridad);
        prioridadPorAnalista.setActivo(1L);
        prioridadPorAnalista.setIpCliente(ipCliente);
        prioridadPorAnalista.setIdUsuario(idUsuario);
        prioridadPorAnalistaRepository.save(prioridadPorAnalista);
    }

    @Override
    public AnalistaTramitePosesion actualizarAnalista(ActualizarAnalistaDTO editarAnalistaDTO) {
        if (editarAnalistaDTO.getIdAnalistaTramitePosesion() == null) {
            throw new ExcepcionPersonalizada("El ID del analista es obligatorio.", HttpStatus.BAD_REQUEST);
        }

        // Buscar el analista por su ID
        Optional<AnalistaTramitePosesion> analistaOptional = repository
                .findById(editarAnalistaDTO.getIdAnalistaTramitePosesion());
        if (analistaOptional.isPresent()) {
            AnalistaTramitePosesion analista = analistaOptional.get();

            analista.setNombre(editarAnalistaDTO.getNombre());
            analista.setIdentificacion(editarAnalistaDTO.getIdentificacion());
            analista.setIdTipoAnalista(editarAnalistaDTO.getIdTipoAnalista());
            analista.setLimiteCarga(editarAnalistaDTO.getLimiteCarga());
            analista.setLogin(editarAnalistaDTO.getLogin());
            analista.setIdUsuario(editarAnalistaDTO.getIdUsuario());
            analista.setIdTipoInactivacionAnalista(editarAnalistaDTO.getIdTipoInactivacionAnalista());
            analista.setActivo(editarAnalistaDTO.getActivo().toString());
            analista.setIpCliente(editarAnalistaDTO.getIpCliente());

            // Guardar los cambios en el analista
            AnalistaTramitePosesion analistaActualizado = repository.save(analista);

            // Buscar la prioridad por analista
            Optional<PrioridadPorAnalista> prioridadOptional = prioridadPorAnalistaRepository
                    .findByIdAnalistaTramitePosesion(editarAnalistaDTO.getIdAnalistaTramitePosesion());

            if (prioridadOptional.isPresent()) {
                PrioridadPorAnalista prioridadPorAnalista = prioridadOptional.get();

                prioridadPorAnalista.setIdPrioridadTramitePosesion(editarAnalistaDTO.getIdPrioridad());
                prioridadPorAnalista.setIpCliente(editarAnalistaDTO.getIpCliente());
                prioridadPorAnalista.setIdUsuario(editarAnalistaDTO.getIdUsuario());
                prioridadPorAnalistaRepository.save(prioridadPorAnalista);
                
            } else {
                creacionAnalistaPrioridad(editarAnalistaDTO.getIdAnalistaTramitePosesion(),
                        editarAnalistaDTO.getIdPrioridad(),
                        editarAnalistaDTO.getIpCliente(),
                        editarAnalistaDTO.getIdUsuario());
            }

            return analistaActualizado;
        } else {
            throw new ExcepcionPersonalizada(
                    "Analista no encontrado con ID: " + editarAnalistaDTO.getIdAnalistaTramitePosesion(),
                    HttpStatus.NOT_FOUND);
        }
    }
}