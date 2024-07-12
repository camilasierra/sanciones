package co.siri.posesiones.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.siri.posesiones.dtos.MensajeSolicitudOut;
import co.siri.posesiones.dtos.SubirArchivoActaComiteDTO;
import co.siri.posesiones.entities.ArchivoTramitePosesion;
import co.siri.posesiones.entities.SesionComite;
import co.siri.posesiones.entities.SesionComiteReunion;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.ArchivoTramitePosesionRepository;
import co.siri.posesiones.repositories.SesionComiteRepository;
import co.siri.posesiones.repositories.SesionComiteReunionRepository;
import co.siri.posesiones.services.IFinalizacionComiteService;
import java.util.Base64;

@Service
public class FinalizacionComiteServiceImp implements IFinalizacionComiteService {

	@Autowired
	private ArchivoTramitePosesionRepository archivoRepo;

	@Autowired
	private SesionComiteRepository comiteRepo;

	@Autowired
	private SesionComiteReunionRepository comiteReunionRepo;

	@Override
	@Transactional
	public MensajeSolicitudOut subirActaComite(SubirArchivoActaComiteDTO actaComiteJSON) throws ExcepcionPersonalizada {
		MensajeSolicitudOut resp = new MensajeSolicitudOut();
		try {
			ArchivoTramitePosesion actaComite = new ArchivoTramitePosesion();

			byte[] archivoData = Base64.getDecoder().decode(actaComiteJSON.getArchivo_base64());

			actaComite.setArchivo(archivoData);
			actaComite.setContenttype("application/pdf");
			actaComite.setLongitud(Long.valueOf(actaComiteJSON.getArchivo_base64().length()));
			actaComite.setNombre(actaComiteJSON.getNombre_archivo());

			archivoRepo.save(actaComite);

			System.out.println(" valor id actaComite -> " + actaComite.getIdArchivoTramitePosesion());

			if (actaComite.getIdArchivoTramitePosesion() != null && actaComiteJSON.getIdsesioncomite() != null) {

				SesionComite sesionComite = comiteRepo.getReferenceById(actaComiteJSON.getIdsesioncomite());

				SesionComiteReunion comiteReunion = new SesionComiteReunion();

				comiteReunion.setIdSesionComite(actaComiteJSON.getIdsesioncomite());
				comiteReunion.setFechaInicioComite(sesionComite.getFechaComite());
				comiteReunion.setFechaHoraFinComite(sesionComite.getHoraFinComite());
				comiteReunion.setIdArchivoCorreosVoto(actaComite.getIdArchivoTramitePosesion());
				comiteReunion.setIteracion(Long.valueOf(1));
				comiteReunion.setIdUsuario(actaComiteJSON.getIdusuario());
				comiteReunion.setIpCliente(actaComiteJSON.getIp_cliente());

				comiteReunionRepo.save(comiteReunion);

			}
			resp.setExitoso(true);
			resp.setMensaje("Se subió acta de comité correctamente");

		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setExitoso(false);
			resp.setMensaje("Error al subir archivo de acta de comité");

		}
		return resp;
	}

	@Override
	@Transactional
	public MensajeSolicitudOut oficializarActaComite(Long idsesioncomite) throws ExcepcionPersonalizada {
		MensajeSolicitudOut resp = new MensajeSolicitudOut();
		try {
			SesionComite sesionComite = comiteRepo.getReferenceById(idsesioncomite);

			ArchivoTramitePosesion actaComite = archivoRepo.getActaSesionComite(idsesioncomite);
			if (actaComite != null) {
				sesionComite.setOficial(true);
				comiteRepo.save(sesionComite);
				resp.setExitoso(true);
				resp.setMensaje("Acta oficializada exitosamente");
			}else {
				resp.setExitoso(false);
				resp.setMensaje("No se encuentra subido el acta de comité dentro de la sesión.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setExitoso(false);
			resp.setMensaje("Error al oficializar el acta de comité");
		}
		return resp;
	}

}
