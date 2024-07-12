package co.siri.posesiones.services.imp;

import co.siri.posesiones.dtos.MensajeRevisionTramiteDto;
import co.siri.posesiones.entities.MensajeRevisionTramite;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.MensajeRepository;
import co.siri.posesiones.services.IMensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MensajeService implements IMensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Override
    public List<MensajeRevisionTramite> mensajeEnTramite(Long idTipoDestinoMensaje) {
        try {
            List<MensajeRevisionTramite> result = mensajeRepository.findMensajeRevisionTramiteByIdTipoDestinoMensajeOrderByIdMensajeRevisionTramiteDesc (idTipoDestinoMensaje);
            List<MensajeRevisionTramite> listMensajeRevisionTramite = new ArrayList<>();

            for (MensajeRevisionTramite mensajeRevisionTramite : result) {
                if (mensajeRevisionTramite.getInBorrado().equals('N')) {
                    listMensajeRevisionTramite.add(mensajeRevisionTramite);
                }
            }
            return listMensajeRevisionTramite;

        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<MensajeRevisionTramite> mensajeBorrado() {
        try {
            return mensajeRepository.mensajesEnBorrado();
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void guardarMensaje(MensajeRevisionTramiteDto mensajeRevisionTramiteDto) {
        try {
            for (int i = 0; i < mensajeRevisionTramiteDto.getIdTipoDestinoMensaje().size(); i++) {
                MensajeRevisionTramite mensaje = new MensajeRevisionTramite();
                mensaje.setIdTramitePosesion(mensajeRevisionTramiteDto.getIdTramitePosesion());
                mensaje.setTexto(mensajeRevisionTramiteDto.getTexto());
                mensaje.setIdTipoDestinoMensaje(mensajeRevisionTramiteDto.getIdTipoDestinoMensaje().get(i));
                mensaje.setInBorrado('N');
                mensaje.setIdUsuario(mensajeRevisionTramiteDto.getIdUsuario());
                mensaje.setIpCliente(mensajeRevisionTramiteDto.getIpCliente());
                mensaje.setFechaNotificaicon(new Date());
                mensaje.setRol("Coordinador");
                mensajeRepository.save(mensaje);

            }
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void eliminarMensaje(Long idMensajeRevisionTramite) {
        try {
            Optional<MensajeRevisionTramite> mensaje = mensajeRepository.findById(idMensajeRevisionTramite);
            MensajeRevisionTramite mensajeUpdate = new MensajeRevisionTramite();
            if (mensaje.isPresent()) {
                mensajeUpdate = mensaje.get();
                mensajeUpdate.setInBorrado('S');
                mensajeRepository.save(mensajeUpdate);
            }
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error en consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
