package co.siri.posesiones.services.imp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.siri.posesiones.entities.Entidad;
import co.siri.posesiones.entities.Persona;
import co.siri.posesiones.entities.TextoEvaluacionTramite;
import co.siri.posesiones.entities.TipoCargo;
import co.siri.posesiones.entities.TramitePosesion;
import co.siri.posesiones.exceptions.ExcepcionPersonalizada;
import co.siri.posesiones.repositories.CargoRepository;
import co.siri.posesiones.repositories.EntidadRepository;
import co.siri.posesiones.repositories.PersonaRepository;
import co.siri.posesiones.repositories.TextoEvaluacionTramiteRepository;
import co.siri.posesiones.repositories.TramitePosesionRepository;
import co.siri.posesiones.services.IGeneradorDocumentoPlantillaService;
import co.siri.posesiones.utilidades.ApachePoiUtils;
import co.siri.posesiones.utilidades.Constantes;
import co.siri.posesiones.utilidades.Utilidades;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GeneradorDocumentoPlantillaService implements IGeneradorDocumentoPlantillaService {

	private final TramitePosesionRepository tramitePosesionRepository;
	
	private final PersonaRepository personaRepository;
	
	private final EntidadRepository entidadRepository;
	
	private final CargoRepository cargoRepository;
	
	private final TextoEvaluacionTramiteRepository textoEvaluacionTramiteRepository;

	@Override
	public String generarOficioDevolucion(Boolean entidad,Long idTramite) {
		try {
			TramitePosesion tramitePosesion = tramitePosesionRepository.findById(idTramite).orElse(null);
			if(null == tramitePosesion) {
				throw new ExcepcionPersonalizada(Constantes.ERRROR_TRAMITE_NO_ENCONTRADO, HttpStatus.NOT_FOUND);
			}
			InputStream is = new ByteArrayInputStream(templateToByteArray(Constantes.PLANTILLA_DEVOLUCION));
			XWPFDocument template = new XWPFDocument(is);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			agregarTextoDoctor(template);
			agregarSegundoTexto(template,tramitePosesion);
			agregarTercerTexto(template,tramitePosesion);
			agregarCuartoTexto(template,tramitePosesion);
			agregarTextoDoctor(template);
			agregarCandidato(template,tramitePosesion);
			agregarInfoTramite(template,tramitePosesion);
			if(entidad.booleanValue()) {
				agregarPrimerParrafoEntidad(template, tramitePosesion);
			}
			agregarTextoAnalista(template, tramitePosesion);
			if(entidad.booleanValue()) {
				agregarSegundoParrafoEntidad(template, tramitePosesion);
			}
			agregarFirma(template);
			template.write(outputStream);
			return Base64.getEncoder().encodeToString(outputStream.toByteArray());
		}catch(Exception e) {
			throw new ExcepcionPersonalizada(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	private void agregarTextoDoctor(XWPFDocument template) {
		ApachePoiUtils.escribirParrafoFuenteCalibri(template,true,
				ApachePoiUtils.ALINEACION_JUSTIFICADO,
				ApachePoiUtils.TAMANO_FUENTE_12,
				ApachePoiUtils.NO_NEGRITA_SUBRAYADO,
				"Doctor (a)",
				ApachePoiUtils.COLOR_NEGRO_ACTA,
				"0", ApachePoiUtils.NO_NEGRITA_SUBRAYADO,"1","0");
	}
	
	private void agregarSegundoTexto(XWPFDocument template,TramitePosesion tramitePosesion) {
		ApachePoiUtils.escribirParrafoFuenteCalibri(template,true,
				ApachePoiUtils.ALINEACION_JUSTIFICADO,
				ApachePoiUtils.TAMANO_FUENTE_12,
				ApachePoiUtils.NEGRITA,
				tramitePosesion.getCandidatizadoPor(),
				ApachePoiUtils.COLOR_NEGRO_ACTA,
				"0", ApachePoiUtils.NO_NEGRITA_SUBRAYADO,"1","0");
	}
	
	private void agregarTercerTexto(XWPFDocument template,TramitePosesion tramitePosesion) {
		Entidad entidad = entidadRepository.findById(tramitePosesion.getIdEntidad()).orElse(null);
		ApachePoiUtils.escribirParrafoFuenteCalibri(template,true,
				ApachePoiUtils.ALINEACION_JUSTIFICADO,
				ApachePoiUtils.TAMANO_FUENTE_12,
				ApachePoiUtils.NO_NEGRITA_SUBRAYADO,
				null == entidad ? "" : entidad.getSigla(),
				ApachePoiUtils.COLOR_NEGRO_ACTA,
				"0", ApachePoiUtils.NO_NEGRITA_SUBRAYADO,"1","0");
	}
	
	private void agregarCuartoTexto(XWPFDocument template,TramitePosesion tramitePosesion) {
		ApachePoiUtils.escribirParrafoFuenteCalibri(template,true,
				ApachePoiUtils.ALINEACION_JUSTIFICADO,
				ApachePoiUtils.TAMANO_FUENTE_12,
				ApachePoiUtils.NO_NEGRITA_SUBRAYADO,
				"Bogotá D.C",
				ApachePoiUtils.COLOR_NEGRO_ACTA,
				"0", ApachePoiUtils.NO_NEGRITA_SUBRAYADO,"1","0");
	}
	
	private void agregarCandidato(XWPFDocument template,TramitePosesion tramitePosesion) {
		Persona persona = personaRepository.findById(tramitePosesion.getIdPersona()).orElse(null);
		ApachePoiUtils.escribirParrafoFuenteCalibri(template,true,
				ApachePoiUtils.ALINEACION_JUSTIFICADO,
				ApachePoiUtils.TAMANO_FUENTE_12,
				ApachePoiUtils.NEGRITA,
				Utilidades.obtenerNombrePersona(persona),
				ApachePoiUtils.COLOR_NEGRO_ACTA,
				"0", ApachePoiUtils.NO_NEGRITA_SUBRAYADO,"1","0");
		ApachePoiUtils.escribirParrafoFuenteCalibri(template,true,
				ApachePoiUtils.ALINEACION_JUSTIFICADO,
				ApachePoiUtils.TAMANO_FUENTE_12,
				ApachePoiUtils.NEGRITA,
				null == tramitePosesion.getEmailNotificacionPostulado() ? "" : tramitePosesion.getEmailNotificacionPostulado(),
				ApachePoiUtils.COLOR_NEGRO_ACTA,
				"0", ApachePoiUtils.NO_NEGRITA_SUBRAYADO,"1","0");
	}
	
	private void agregarInfoTramite(XWPFDocument template,TramitePosesion tramitePosesion) {
		ApachePoiUtils.escribirParrafoFuenteCalibri(template,true,
				ApachePoiUtils.ALINEACION_JUSTIFICADO,
				ApachePoiUtils.TAMANO_FUENTE_12,
				ApachePoiUtils.NEGRITA,
				Utilidades.getMensajeFormat(ApachePoiUtils.NUMERO_RADICACION_TEMPLATE, tramitePosesion.getNumeroRadicacion()),
				ApachePoiUtils.COLOR_NEGRO_ACTA,
				"0", ApachePoiUtils.NO_NEGRITA_SUBRAYADO,"1","0");
		ApachePoiUtils.escribirParrafoFuenteCalibri(template,true,
				ApachePoiUtils.ALINEACION_JUSTIFICADO,
				ApachePoiUtils.TAMANO_FUENTE_12,
				ApachePoiUtils.NEGRITA,
				ApachePoiUtils.TRAMITE_TEMPLATE,
				ApachePoiUtils.COLOR_NEGRO_ACTA,
				"0", ApachePoiUtils.NO_NEGRITA_SUBRAYADO,"1","0");
		ApachePoiUtils.escribirParrafoFuenteCalibri(template,true,
				ApachePoiUtils.ALINEACION_JUSTIFICADO,
				ApachePoiUtils.TAMANO_FUENTE_12,
				ApachePoiUtils.NEGRITA,
				ApachePoiUtils.ACTIVIDAD_TEMPLATE,
				ApachePoiUtils.COLOR_NEGRO_ACTA,
				"0", ApachePoiUtils.NO_NEGRITA_SUBRAYADO,"1","0");
	}
	
	private void agregarPrimerParrafoEntidad(XWPFDocument template,TramitePosesion tramitePosesion) {
		Persona persona = personaRepository.findById(tramitePosesion.getIdPersona()).orElse(null);
		TipoCargo tipoCargo = cargoRepository.findById(tramitePosesion.getIdTipoCargo()).orElse(null);
		ApachePoiUtils.escribirParrafoFuenteCalibri(template,true,
				ApachePoiUtils.ALINEACION_JUSTIFICADO,
				ApachePoiUtils.TAMANO_FUENTE_12,
				ApachePoiUtils.NO_NEGRITA_SUBRAYADO,
				ApachePoiUtils.DOCTOR_TEMPLATE,
				ApachePoiUtils.COLOR_NEGRO_ACTA,
				"0", ApachePoiUtils.NO_NEGRITA_SUBRAYADO,"1","0");
		ApachePoiUtils.escribirParrafoFuenteCalibri(template,true,
				ApachePoiUtils.ALINEACION_JUSTIFICADO,
				ApachePoiUtils.TAMANO_FUENTE_12,
				ApachePoiUtils.NO_NEGRITA_SUBRAYADO,
				Utilidades.getMensajeFormat(Constantes.PRIMER_PARRAFO_ENTIDAD,
						Utilidades.obtenerNombrePersona(persona),
						null == tipoCargo ? "" : tipoCargo.getNombre(),
						null == tramitePosesion.getCargoResponsableTramite() ? "" : tramitePosesion.getCargoResponsableTramite()),
				ApachePoiUtils.COLOR_NEGRO_ACTA,
				"0", ApachePoiUtils.NO_NEGRITA_SUBRAYADO,"1","0");
	}
	
	private void agregarTextoAnalista(XWPFDocument template,TramitePosesion tramitePosesion) {
		List<TextoEvaluacionTramite> listadoTextos = textoEvaluacionTramiteRepository.findByIdTramitePosesionAndIdTipoTextoTramite(tramitePosesion.getIdTramitePosesion(), Long.valueOf(2));
		String textoAnalista = "";
		if(null !=listadoTextos && !listadoTextos.isEmpty()) {
			textoAnalista = listadoTextos.get(0).getTexto();
		}
		ApachePoiUtils.escribirParrafoFuenteCalibri(template,true,
				ApachePoiUtils.ALINEACION_JUSTIFICADO,
				ApachePoiUtils.TAMANO_FUENTE_12,
				ApachePoiUtils.NEGRITA,
				textoAnalista,
				ApachePoiUtils.COLOR_NEGRO_ACTA,
				"0", ApachePoiUtils.NO_NEGRITA_SUBRAYADO,"1","0");
	}
	
	private void agregarSegundoParrafoEntidad(XWPFDocument template,TramitePosesion tramitePosesion) {
		ApachePoiUtils.escribirParrafoFuenteCalibri(template,true,
				ApachePoiUtils.ALINEACION_JUSTIFICADO,
				ApachePoiUtils.TAMANO_FUENTE_12,
				ApachePoiUtils.NO_NEGRITA_SUBRAYADO,
				Constantes.SEGUNDO_PARRAFO_ENTIDAD,
				ApachePoiUtils.COLOR_NEGRO_ACTA,
				"0", ApachePoiUtils.NO_NEGRITA_SUBRAYADO,"1","0");
	}
	
	private void agregarFirma(XWPFDocument template) throws Exception {
		ApachePoiUtils.escribirParrafoFuenteCalibri(template,true,
				ApachePoiUtils.ALINEACION_JUSTIFICADO,
				ApachePoiUtils.TAMANO_FUENTE_12,
				ApachePoiUtils.NO_NEGRITA_SUBRAYADO,
				ApachePoiUtils.CORDIALMENTE_TEMPLATE,
				ApachePoiUtils.COLOR_NEGRO_ACTA,
				"0", ApachePoiUtils.NO_NEGRITA_SUBRAYADO,"1","0");
		ApachePoiUtils.agregarImagenFinalDocumento(template);
		agregarTextoPostFirma(template);
	}
	
	private void agregarTextoPostFirma(XWPFDocument template) {
		String[] datos = Constantes.INFORMACION_POST_FIRMA;
		for (int i = 0; i < datos.length; i++) {
			ApachePoiUtils.escribirParrafoFuenteCalibri(template,true,
					ApachePoiUtils.ALINEACION_JUSTIFICADO,
					ApachePoiUtils.TAMANO_FUENTE_12,
					i == 0 ? ApachePoiUtils.NEGRITA : ApachePoiUtils.NO_NEGRITA_SUBRAYADO,
					datos[i],
					ApachePoiUtils.COLOR_NEGRO_ACTA,
					"0", ApachePoiUtils.NO_NEGRITA_SUBRAYADO,"1","0");
		}
	}
	
	private byte[] templateToByteArray(String templateName) throws IOException {
	   	Resource resource = new ClassPathResource("template/" + templateName);
		InputStream src = resource.getInputStream();
		return IOUtils.toByteArray(src);
	}
}
