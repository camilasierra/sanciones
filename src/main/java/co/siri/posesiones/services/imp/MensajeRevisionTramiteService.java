package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.ComentariosTramiteRequestDTO;
import co.siri.posesiones.dtos.MensajeRevisionResponseDTO;
import co.siri.posesiones.entities.AsignacionTramiteAnalistas;
import co.siri.posesiones.entities.MensajeRevisionTramite;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.AsinacionTramiteAnalistaRepository;
import co.siri.posesiones.repositories.MensajeRevisionTramiteRepository;
import co.siri.posesiones.services.IMensajeRevisionTramiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

import static co.siri.posesiones.utilidades.Constantes.ERRORCONSULTA;


@Service
public class MensajeRevisionTramiteService implements IMensajeRevisionTramiteService {

    @Autowired
    private MensajeRevisionTramiteRepository mensajeRevisionTramiteRepository;

    @Autowired
    private AsinacionTramiteAnalistaRepository asinacionTramiteAnalistaRepository;

    /**
     * Metodo para traer lista de mensajes asociados al tramite en revision
     * ordenados del mas reciente al incio
     * @param idTramitePosesion  id del tramite posesion
     * @return
     */
    @Override
    public List<MensajeRevisionResponseDTO> getMensajesRevisionTramite(int idTramitePosesion) {
        try{
            List<Object[]> response = mensajeRevisionTramiteRepository.getMensajesRevisionTramite(idTramitePosesion);
            if (response.isEmpty()) {
                throw new NoSuchElementException("No se encontró resultado para el ID del trámite: " + idTramitePosesion);
            }
            //se realiza mapeo de la respusta por posuciones en el DTO correspondiente
            List<MensajeRevisionResponseDTO> mensajes = new ArrayList<>();
            for (Object[] resultado : response) {
                MensajeRevisionResponseDTO mensaje = new MensajeRevisionResponseDTO(
                        (Integer) resultado[0],
                        (Integer) resultado[1],
                        (String) resultado[2],
                        (Integer) resultado[3],
                        (resultado[4] != null) ? resultado[4].toString().charAt(0) : null,
                        (String) resultado[5],
                        (String) resultado[6],
                        (Date) resultado[7]
                );
                mensajes.add(mensaje);
            }
            return mensajes;
        }catch (ExcepcionPersonalizada ex){
            throw  new ExcepcionPersonalizada(ERRORCONSULTA + " " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Metodo Encargado de realizar el guardado de informacion de los comentarios realizados
     * por los roles permitidios, asociados a un tramite a traves de su idTramitePosesion
     * @param requestDTO Objeto de llenado de informacion
     * @throws SQLException
     */
    @Override
    public void saveComentariosTramite(ComentariosTramiteRequestDTO requestDTO) {
        Objects.requireNonNull(requestDTO, "El objeto de llenado no puede ser nulo");

        try {
            // Validar que el idTramite esté asociado a un analista y exista en la tabla asignaciontramiteanalista
            AsignacionTramiteAnalistas idTramite = asinacionTramiteAnalistaRepository.findByIdTramitePosesion(requestDTO.getIdTramitePosesion());
            if (idTramite == null) {
                throw new ExcepcionPersonalizada(" El id del tramite: " + requestDTO.getIdTramitePosesion() + " debe estar asociado a la tabla ASIGNACIONTRAMITEANALISTA", HttpStatus.NO_CONTENT);
            }
            MensajeRevisionTramite response = MensajeRevisionTramite.builder()
                    .idTramitePosesion(requestDTO.getIdTramitePosesion())
                    .texto(requestDTO.getTexto())
                    .idTipoDestinoMensaje(requestDTO.getIdTipoMensaje())
                    .inBorrado(requestDTO.getInBorrado())
                    .idUsuario(requestDTO.getUser().getIdusuario())
                    .ipCliente(requestDTO.getUser().getIpUsuario())
                    .rol(requestDTO.getRol())
                    .fechaNotificaicon(new Date())
                    .build();
            // Guardar la entidad en la base de datos
            mensajeRevisionTramiteRepository.save(response);
        } catch (ExcepcionPersonalizada ex) {
            throw new ExcepcionPersonalizada(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
