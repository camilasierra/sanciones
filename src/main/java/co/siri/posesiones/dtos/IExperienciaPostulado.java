package co.siri.posesiones.dtos;

import java.sql.Blob;
import java.util.Date;

/**
 * Interfaz para representar la información laboral de un postulante.
 * Proporciona métodos para obtener información sobre la empresa, cargo y fechas de trabajo del postulante.
 *
 * <p>Autor: John Macias</p>
 */
public interface IExperienciaPostulado {

    /**
     * Obtiene el ID de la persona.
     *
     * @return el ID de la persona.
     */
    Long getIdPersona();

    /**
     * Obtiene el sector.
     *
     * @return el sector.
     */
    String getSector();

    /**
     * Obtiene la clase de sociedad de la empresa donde trabajó el postulante.
     *
     * @return la clase de sociedad de la empresa.
     */
    String getClaseDeSociedad();

    /**
     * Obtiene la razón social de la empresa donde trabajó el postulante.
     *
     * @return la razón social de la empresa.
     */
    String getRazonSocial();

    /**
     * Obtiene el nombre del cargo que desempeñó el postulante.
     *
     * @return el nombre del cargo.
     */
    String getNombreCargo();

    /**
     * Obtiene el tipo de cargo que desempeñó el postulante.
     *
     * @return el tipo de cargo.
     */
    String getTipoCargo();

    /**
     * Obtiene el área de desempeño del cargo.
     *
     * @return el área de desempeño del cargo.
     */
    String getAreaDesempenio();

    /**
     * Obtiene el motivo de retiro del postulante del cargo.
     *
     * @return el motivo de retiro.
     */
    String getMotivoRetiro();

    /**
     * Obtiene la fecha de inicio del cargo.
     *
     * @return la fecha de inicio.
     */
    Date getFechaInicio();

    /**
     * Obtiene la fecha de retiro del cargo.
     *
     * @return la fecha de retiro.
     */
    Date getFechaRetiro();

    /**
     * Obtiene el nombre del archivo del certificado.
     *
     * @return el nombre del archivo.
     */
    String getNombreArchivo();

    /**
     * Obtiene el content type del certificado.
     *
     * @return content type del certificado
     */
    String getContentTypeArchivo();

    /**
     * Obtiene el certificado.
     *
     * @return certificado
     */
    Blob getArchivo();

    /**
     * Obtiene Longitud del certificado.
     *
     * @return longitud del certificado.
     */
    Long getLongitudArchivo();
}




