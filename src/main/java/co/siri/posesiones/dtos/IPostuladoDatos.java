package co.siri.posesiones.dtos;

import java.util.Date;

/**
 * Interfaz para representar los datos personales de un postulante.
 * Proporciona métodos para obtener información sobre los datos personales del postulante, incluyendo nombres, apellidos,
 * identificación, género, fecha de nacimiento, nacionalidad, ciudad de nacimiento, ciudad de residencia,
 * dirección, contactos, estado civil, datos del cónyuge, y datos de notificación.
 *
 * <p>Autor: John Macias</p>
 */
public interface IPostuladoDatos {

    /**
     * Obtiene el ID de la persona.
     *
     * @return el ID de la persona.
     */
    Long getIdPersona();

    /**
     * Obtiene el primer nombre del postulante.
     *
     * @return el primer nombre del postulante.
     */
    String getPrimerNombre();

    /**
     * Obtiene el segundo nombre del postulante.
     *
     * @return el segundo nombre del postulante.
     */
    String getSegundoNombre();

    /**
     * Obtiene el primer apellido del postulante.
     *
     * @return el primer apellido del postulante.
     */
    String getPrimerApellido();

    /**
     * Obtiene el segundo apellido del postulante.
     *
     * @return el segundo apellido del postulante.
     */
    String getSegundoApellido();

    /**
     * Obtiene la identificación del postulante.
     *
     * @return la identificación del postulante.
     */
    String getIdentificacion();

    String getTipoIdentifiacion();


    /**
     * Obtiene el género del postulante.
     *
     * @return el género del postulante.
     */
    String getGenero();

    /**
     * Obtiene la fecha de nacimiento del postulante.
     *
     * @return la fecha de nacimiento del postulante.
     */
    Date getFechaNacimiento();

    /**
     * Obtiene la nacionalidad del postulante.
     *
     * @return la nacionalidad del postulante.
     */
    String getNacionalidad();

    /**
     * Obtiene la ciudad de nacimiento del postulante.
     *
     * @return la ciudad de nacimiento del postulante.
     */
    String getCiudadNacimiento();

    /**
     * Obtiene la ciudad de residencia del postulante.
     *
     * @return la ciudad de residencia del postulante.
     */
    String getCiudadResidencia();

    /**
     * Obtiene la dirección de residencia del postulante.
     *
     * @return la dirección de residencia del postulante.
     */
    String getDireccionResidencia();

    /**
     * Obtiene el número de contacto del postulante.
     *
     * @return el número de contacto del postulante.
     */
    String getNumeroContacto();

    /**
     * Obtiene el estado civil del postulante.
     *
     * @return el estado civil del postulante.
     */
    String getEstadoCivil();

    /**
     * Obtiene el nombre del cónyuge del postulante.
     *
     * @return el nombre del cónyuge del postulante.
     */
    String getNombreConyuge();

    /**
     * Obtiene la identificación del cónyuge del postulante.
     *
     * @return la identificación del cónyuge del postulante.
     */
    String getIdentificacionConyuge();

    /**
     * Obtiene la ocupación actual del cónyuge del postulante.
     *
     * @return la ocupación del cónyuge del postulante.
     */
    String getOcupacionConyuge();

    /**
     * Obtiene la empresa donde trabaja el cónyuge del postulante.
     *
     * @return la empresa donde trabaja el cónyuge del postulante.
     */
    String getEmpresaOcupacionConyuge();

    /**
     * Obtiene la ciudad de notificación del postulante.
     *
     * @return la ciudad de notificación del postulante.
     */
    String getCiudadNotificacion();

    /**
     * Obtiene la dirección de notificación del postulante.
     *
     * @return la dirección de notificación del postulante.
     */
    String getDireccionNotificacion();

    /**
     * Obtiene el correo electrónico del postulante.
     *
     * @return el correo electrónico del postulante.
     */
    String getEmail();
}
