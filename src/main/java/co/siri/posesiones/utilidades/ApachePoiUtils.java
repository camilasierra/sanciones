package co.siri.posesiones.utilidades;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ApachePoiUtils {

	public static final String ALINEACION_CENTRADO = "CENTRADO"; 
	
	public static final String ALINEACION_JUSTIFICADO = "JUSTIFICADO";
	
	public static final String ALINEACION_DERECHA = "DERECHA";
	
	public static final String TAMANO_FUENTE_20 = "20";
	
	public static final String TAMANO_FUENTE_18 = "18";
	
	public static final String TAMANO_FUENTE_16 = "16";
	
	public static final String TAMANO_FUENTE_12 = "12";
	
	public static final String TAMANO_FUENTE_10 = "10";
	
	public static final String TAMANO_FUENTE_9 = "9";
	
	public static final String NEGRITA = "S";
	
	public static final String SUBRAYADO = "S";
	
	public static final String NO_NEGRITA_SUBRAYADO = "N";
	
	private static final int CANTIDAD_MINIMA_PARAMETROS = 6;
	
	public static final String COLOR_NEGRO_ACTA = "000000";
	
	public static final String NUMERO_RADICACION_TEMPLATE = "Número de Radicación : {0}";
	
	public static final String TRAMITE_TEMPLATE = "Trámite : 241 POSESIONES";
	
	public static final String ACTIVIDAD_TEMPLATE = "Actividad : 33 REQUERIMIENTO A LA ENTIDAD";
	
	public static final String DOCTOR_TEMPLATE = "Respetado (a) doctor (a):";
	
	public static final String CORDIALMENTE_TEMPLATE = "Cordialmente,";
	
	
	/**
	 * Metodo encargado de escribir un parrafo en la plantilla
	 * @param template archivo donde se escribe el parrafo
	 * @param indexado indica si el parrafo debe ser indexado para alineacion Justificada
	 * @param pParametros arreglo con las configuraciones de parrafo
	 *  pParametros[0] indica la alineacion del parrafo CENTRADO o JUSTIFICADO
	 *  pParametros[1] indica el tamaño de la fuente (Numero entero)
	 *  pParametros[2] indica si el texto es en negrilla (S o N)
	 *  pParametros[3] indica el texto del parrafo
	 *  pParametros[4] indica el color del texto
	 *  pParametros[5] indica la cantidad de saltos al final del parrafo (Numero entero)
	 *  pParametros[6] indica si el parrafo debe ir subrayado (S o N)
	 *  pParametros[7] indica la cantidad de retornos de carro al finalizar el parrafo (Numero entero)
	 *  pParametros[8] indica la cantidad de tabs que debe tener el parrafo (Numero entero)
	 */
	public static void escribirParrafo(XWPFDocument template,boolean indexado,String ... pParametros) {
		if(pParametros.length >= CANTIDAD_MINIMA_PARAMETROS) {
			XWPFParagraph para = template.createParagraph();
			para.setAlignment( pParametros[0].equalsIgnoreCase(ALINEACION_CENTRADO) ? ParagraphAlignment.CENTER : ParagraphAlignment.BOTH);
			if(indexado) {
				para.setIndentationLeft(-720);
				para.setIndentationRight(-280);
			}
			XWPFRun run = para.createRun();
			run.setFontSize(Integer.parseInt(pParametros[1]));
			run.setBold(pParametros[2].equalsIgnoreCase("S"));
			run.setText(pParametros[3]);
			run.setColor(pParametros[4]);
			run.setUnderline(pParametros[6].equalsIgnoreCase("S") ? UnderlinePatterns.SINGLE : UnderlinePatterns.NONE);
			agregarSaltos(run, Integer.parseInt(pParametros[5]));
			agregarRetornosCarro(run, Integer.parseInt(pParametros[7]));
			agregarTabs(run, Integer.parseInt(pParametros[8]));
		}
	}
	
	/**
	 * Metodo encargado de escribir un parrafo en la plantilla con la fuente Calibri Light
	 * @param template archivo donde se escribe el parrafo
	 * @param indexado indica si el parrafo debe ser indexado para alineacion Justificada
	 * @param pParametros arreglo con las configuraciones de parrafo
	 *  pParametros[0] indica la alineacion del parrafo CENTRADO o JUSTIFICADO
	 *  pParametros[1] indica el tamaño de la fuente (Numero entero)
	 *  pParametros[2] indica si el texto es en negrilla (S o N)
	 *  pParametros[3] indica el texto del parrafo
	 *  pParametros[4] indica el color del texto
	 *  pParametros[5] indica la cantidad de saltos al final del parrafo (Numero entero)
	 *  pParametros[6] indica si el parrafo debe ir subrayado (S o N)
	 *  pParametros[7] indica la cantidad de retornos de carro al finalizar el parrafo (Numero entero)
	 *  pParametros[8] indica la cantidad de tabs que debe tener el parrafo (Numero entero)
	 */
	public static void escribirParrafoFuenteCalibri(XWPFDocument template,boolean indexado,String ... pParametros) {
		if(pParametros.length >= CANTIDAD_MINIMA_PARAMETROS) {
			XWPFParagraph para = template.createParagraph();
			validarAlineacionParrafo(para, pParametros[0]);
			if(indexado) {
				para.setIndentationLeft(-720);
				para.setIndentationRight(-280);
			}
			XWPFRun run = para.createRun();
			run.setFontSize(Integer.parseInt(pParametros[1]));
			run.setBold(pParametros[2].equalsIgnoreCase("S"));
			run.setText(pParametros[3]);
			run.setColor(pParametros[4]);
			run.setFontFamily("Arial");
			run.setUnderline(pParametros[6].equalsIgnoreCase("S") ? UnderlinePatterns.SINGLE : UnderlinePatterns.NONE);
			agregarSaltos(run, Integer.parseInt(pParametros[5]));
			agregarRetornosCarro(run, Integer.parseInt(pParametros[7]));
			agregarTabs(run, Integer.parseInt(pParametros[8]));
		}
	}
	
	private static void validarAlineacionParrafo(XWPFParagraph para,String alineacion) {
		switch (alineacion) {
			case ALINEACION_CENTRADO:
				para.setAlignment(ParagraphAlignment.CENTER);
				break;
			case ALINEACION_JUSTIFICADO:
				para.setAlignment(ParagraphAlignment.BOTH);
				break;
			default:
				para.setAlignment(ParagraphAlignment.RIGHT);
				break;
		}
	}
	
	/**
	 * Metodo encargado de escribir un texto sobre un parrafo
	 * @param para parrafo donde se va a escribir el texto
	 * @param pParametros arreglo con las configuraciones del texto
	 *  pParametros[0] indica el tamaño de la fuente (Numero entero)
	 *  pParametros[1] indica si el texto es en negrilla (S o N)
	 *  pParametros[2] indica el texto para agregar
	 *  pParametros[3] indica el color del texto
	 *  pParametros[4] indica si el texto debe ir subrayado (S o N)
	 *  pParametros[5] indica la cantidad de tabs
	 */
	public static void agregarTextoParrafo(XWPFParagraph para,boolean agregarRetorno,String ... pParametros) {
		XWPFRun run = para.createRun();
		run.setFontSize(Integer.parseInt(pParametros[0]));
		run.setBold(pParametros[1].equalsIgnoreCase("S"));
		run.setText(pParametros[2]);
		run.setColor(pParametros[3]);
		run.setUnderline(pParametros[4].equalsIgnoreCase("S") ? UnderlinePatterns.SINGLE : UnderlinePatterns.NONE);
		agregarTabs(run, Integer.parseInt(pParametros[5]));
		if(agregarRetorno) {
			run.addCarriageReturn();
		}
	}
	
	/**
	 * Metodo encargado de escribir un texto sobre un parrafo con la fuente Calibri Light
	 * @param para parrafo donde se va a escribir el texto
	 * @param pParametros arreglo con las configuraciones del texto
	 *  pParametros[0] indica el tamaño de la fuente (Numero entero)
	 *  pParametros[1] indica si el texto es en negrilla (S o N)
	 *  pParametros[2] indica el texto para agregar
	 *  pParametros[3] indica el color del texto
	 *  pParametros[4] indica si el texto debe ir subrayado (S o N)
	 *  pParametros[5] indica la cantidad de tabs
	 */
	public static void agregarTextoParrafoFuenteCalibri(XWPFParagraph para,boolean agregarRetorno,String ... pParametros) {
		XWPFRun run = para.createRun();
		run.setFontSize(Integer.parseInt(pParametros[0]));
		run.setBold(pParametros[1].equalsIgnoreCase("S"));
		run.setText(pParametros[2]);
		run.setColor(pParametros[3]);
		run.setUnderline(pParametros[4].equalsIgnoreCase("S") ? UnderlinePatterns.SINGLE : UnderlinePatterns.NONE);
		run.setFontFamily("Arial");
		agregarTabs(run, Integer.parseInt(pParametros[5]));
		if(agregarRetorno) {
			run.addCarriageReturn();
		}
	}
	
	public static XWPFParagraph crearParrafoIndexado(XWPFDocument template) {
		XWPFParagraph para = template.createParagraph();
		para.setIndentationLeft(-720);
		para.setIndentationRight(-280);
		return para;
	}
	
	public static XWPFParagraph crearParrafoIndexadoAlineacion(XWPFDocument template, boolean blnJustificado) {
		XWPFParagraph para = template.createParagraph();
		para.setIndentationLeft(-720);
		para.setIndentationRight(-280);
		if(blnJustificado) {
			para.setAlignment(ParagraphAlignment.BOTH);
		}else {
			para.setAlignment(ParagraphAlignment.CENTER);
		}
		return para;
	}
	
	public static XWPFRun crearRunParrafo(XWPFDocument template) {
		XWPFParagraph para = template.createParagraph();
		para.setIndentationLeft(-720);
		para.setIndentationRight(-280);
		return para.createRun();
	}
	
	public static void agregarImagenFinalDocumento(XWPFDocument template) throws Exception {
		InputStream imageData = obtenerFirma();
		XWPFParagraph para12 = template.createParagraph();
		para12.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun run12 = para12.createRun();
		run12.addPicture(imageData, XWPFDocument.PICTURE_TYPE_PNG, "firma.jpg",
				Units.toEMU(80), //ANCHO  2.51
				Units.toEMU(55)); //ALTO  1.59
	}
	
	private static void agregarSaltos(XWPFRun run,int pCantidad) {
		if(pCantidad > 0) {
			for(int i = 0; i < pCantidad; i++) {
				run.addBreak();
			}
		}
	}
	
	private static void agregarRetornosCarro(XWPFRun run,int pCantidad) {
		if(pCantidad > 0) {
			for(int i = 0; i < pCantidad; i++) {
				run.addCarriageReturn();
			}
		}
	}
	
	private static void agregarTabs(XWPFRun run,int pCantidad) {
		if(pCantidad > 0) {
			for(int i = 0; i < pCantidad; i++) {
				run.addTab();
			}
		}
	}
	
	private static InputStream obtenerFirma() throws IOException {
		Resource resource = new ClassPathResource("template/firma.jpg");
		return resource.getInputStream();
	}
}
