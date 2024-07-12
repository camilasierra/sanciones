package co.siri.posesiones.utilidades;

import java.util.Date;

/**
 * Clase que contiene las constantes utilizadas en el servicio de Postulado.
 * Proporciona mensajes de log y de error para las diferentes operaciones realizadas en el servicio.
 *
 * <p>Autor: John Macias</p>
 */
public class Constantes {

	/**
	 * Constructor privado para evitar la instanciación de la clase de constantes.
	 */
	private Constantes() {
		throw new IllegalStateException("Clase Utilitaria");
	}

	/**
	 * Mensaje de inicio de servicio para obtener datos del postulado.
	 */
	public static final String INICIO_SERVICIO_DATOS_POSTULADO = "Inicio de servicio para obtener datos postulado";

	/**
	 * Mensaje de error cuando no es posible obtener datos del postulado.
	 */
	public static final String NO_DATOS_POSTULADO = "No es posible obtener datos del postulado";

	/**
	 * Mensaje de consulta exitosa.
	 */
	public static final String CONSULTA_EXITOSA = "Consulta exitosa";

	/**
	 * Mensaje de guardado exitoso.
	 */
	public static final String GUARDADO_EXITOSO = "Guardado exitoso";

	/**
	 * Mensaje de actualización exitoso.
	 */
	public static final String ACTUALIZACION_EXITOSA = "Actualización exitosa";

	public  static final String GUARDADOEXITOSO = "Guardado exitoso";

	public  static final String ERRORGUARDADO = "Error al guardar";

	/**
	 * Mensaje de inicio de servicio para obtener estudios del postulado.
	 */
	public static final String INICIO_SERVICIO_ESTUDIOS_POSTULADO = "Inicio de servicio para obtener estudios postulado";

	/**
	 * Mensaje de error cuando el postulado no tiene estudios registrados.
	 */
	public static final String NO_ESTUDIOS_POSTULADO = "El postulado no tiene estudios registrados";

	/**
	 * Mensaje de inicio de servicio para obtener cargos sin posesión del postulado.
	 */
	public static final String INICIO_SERVICIO_SIN_CARGOS = "Inicio de servicio para obtener cargos sin posesión postulado";

	/**
	 * Mensaje de error cuando el postulado no tiene cargos sin posesión registrados.
	 */
	public static final String NO_DATOS_SIN_CARGO = "El postulado no tiene cargos sin posesión registrados";

	/**
	 * Mensaje de inicio de servicio para obtener cargos con posesión del postulado.
	 */
	public static final String INICIO_SERVICIO_CARGOS = "Inicio de servicio para obtener cargos con posesión postulado";

	/**
	 * Mensaje de error cuando el postulado no tiene cargos con posesión registrados.
	 */
	public static final String NO_DATOS_CARGOS = "El postulado no tiene cargos con posesión registrados";

	/**
	 * Mensaje de inicio de servicio para obtener experiencia del postulado.
	 */
	public static final String INICIO_SERVICIO_EXPERIENCIA_POSTULADO = "Inicio de servicio para obtener experiencia postulado";

	/**
	 * Mensaje de inicio de servicio para actualizar el estado del trámite.
	 */
	public static final String INICIO_SERVICIO_ACTUALIZAR_ESTADO_TRAMITE= "Inicio de servicio para actualizar el estado del trámite";

	/**
	 * Mensaje de error cuando el postulado no tiene experiencia.
	 */
	public static final String NO_DATOS_EXPERIENCIA = "El postulado no tiene experiencia";

	/**
	 * Formato para manejo de fechas
	 */
	public static final String FORMATO_YYYY_MM_DD = "yyyy/MM/dd";

	/**
	 * Valor para si
	 */
	public static final String SI = "Si";

	/**
	 * Valor para no
	 */
	public static final String NO = "No";

	/**
	 * Valor para horas
	 */
	public static final String HORAS = "Horas";

	/**
	 * Valor para extension vacia
	 */
	public static final String EXTENSION_NA = "N/A";

    /**
     * Mensaje de inicio de servicio para obtener textos de evaluación
     */
    public static final String INICIO_SERVICIO_OBTENER_TEXTO = "Inicio servicio para obtener textos de evaluación";

    /**
     * Mensaje de error cuando no existen textos de evaluación
     */
    public static final String NO_TEXTO_TRAMITE = "No es posible obtener textos de evaluación para la información enviada";

    /**
     * Mensaje de inicio de servicio para guardar un texto de evaluación
     */
    public static final String INICIO_SERVICIO_GUARDAR_TEXTO_TRAMITE = "Inicio de servicio para guardar un texto de evaluación";

    /**
     * Mensaje de guardado exitoso para textos de evaluación
     */
    public static final String GUARDADO_EXITOSO_TEXTO = "Guardado exitoso de texto de evaluación";

	public static final String NOMBRE_ARCHIVO_CONFLICTO_INTERESES = "Archivo conflicto de interés";

	public static final String NOMBRE_ARCHIVO_TRAMITE_POSESION = "Archivo trámite Posesión";

	public static final String NOMBRE_ARCHIVO_ACTA_SIN_FIRMA = "Archivo antes de firma";

	public static final String NOMBRE_ARCHIVO_ACTA_CON_FIRMA = "Archivo firmado";
	
	 //constante para almacenar el id que se guarda en antecedente segun hu23
    public static final Long ID_TIPO_CONVENIO_PARA_ANTECEDENTE_TRAMITE_POSESION = 3L;
    
  //constante para almacenar el campo reportado que se guarda en antecedente segun hu23
    public static final char REPORTADO_PARA_ANTECEDENTE_TRAMITE_POSESION = 0;

	/**
	 * Mensaje de trámite no encontrado
	 */
	public static final String TRAMITE_NO_ENCONTRADO = "Trámite con id {0} no encontrado";

	/**
	 * Mensaje para consultas generico
	 */
	public static final String ERRORCONSULTA = "Error en la consulta";

	/**
	 * Mensaje de inicio de servicio acciones aporte
	 */
	 public static final String INICIO_SERVICIO_ACCIONES_APORTE = "Inicio de servicio para obtener acciones aporte";

	/**
	 * Mensaje no se obtuvo acciones aporte
	 */
	 public static final String NO_APORTES_ACCIONES = "No se obtuvo acciones aportes para la persona con id {0}";

	/**
	 * Fecha generica del sistema
	 */
	public static final Date FECHA_SISTEMA = new Date();

	/**
	 * EndPoint para consumo de API getToken de broker Compliance
	 */
	public  static  final String WS_URL_TOKEN_PDF_SERVICE = "https://app.compliance.com.co/validador/apiPublica/TokenService/token";

	/**
	 * EndPoint para consumo de API consultaConsolidada de Compliance
	 */
	public  static  final String WS_URL_PDF_SERVICE_POST = "https://app.compliance.com.co/validador/ws/ConsultaConsolidadaService/consultaConsolidada/soloRiesgo/true";

	/**
	 * EndPoint para consumo de API consultaConsolidada de Compliance
	 */
	public  static  final String WS_URL_PDF_SERVICE_PDF_GET = "https://app.compliance.com.co/validador/api/ReportePdfService/";
	
	/**
	 * Mensaje cuando no se encuentra el tramite solicitado
	 */
	public static final String ERRROR_TRAMITE_NO_ENCONTRADO = "No se encontro un tramite correspondiente al id solicitado";
	
	/**
	 * Plantilla para devolver tramite
	 */
	public static final String PLANTILLA_DEVOLUCION = "plantilla_oficio.docx";
	
	/**
	 * Plantilla primer parrafo cuando se devuelve a la entidad
	 */
	public static final String PRIMER_PARRAFO_ENTIDAD = "Con el fin de continuar con la evaluación de la solicitud de posesión del señor (a) {0}, postulado como {1} de la compañía que usted representa, de manera atenta se requiere de parte de la {2}, en su condición de responsable del trámite:";

	/**
	 * Plantilla segundo parrafo cuando se devuelve a la entidad
	 */
	public static final String SEGUNDO_PARRAFO_ENTIDAD = "Así las cosas, una vez haya atendido en forma completa este requerimiento, es necesario ingresar al aplicativo para retransmitir el radicado anotado teniendo en cuenta el plazo señalado en la Ley 1755 de 2015 que sustituye el Título II de la Ley 1437 de 2011, es decir un (1) mes, so pena de decretar el desistimiento de la presente solicitud.";
	
	/**
	 * Plantilla texto despues de la firma
	 */
	public static final String[] INFORMACION_POST_FIRMA = {"MA CATALINA ESPERANZA C CRUZ GARCIA","PROFESIONAL ESPECIALIZADO","400010-GRUPO DE AUTORIZACIONES Y POSESIONES","Copia a:","Elaboró:","MA CATALINA ESPERANZA C CRUZ GARCIA","Revisó y aprobó:","MA CATALINA ESPERANZA C CRUZ GARCIA"};

	/**
	 * 	Nombre del parametro para Radicar en Solip
	 */
	public static final String PARAMETRO_URL_RADICAR = "urlWSRadicar";

	/**
	 * Nombre para la plantilla cuando es No Presencial
	 */
	public static final String NO_PRESENCIAL = "No presencial";
	
	/**
	 * Base para una imagen en base64 al generar PDF
	 */
	public static final String BASE_IMAGEN = "data:image/png;base64,";
}
