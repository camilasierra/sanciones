package co.siri.posesiones.dtos;

import lombok.Data;

import java.util.Date;

/**
 * DTO para representar los datos personales de un postulante.
 * Contiene información sobre los datos personales del postulante, incluyendo nombres, apellidos,
 * identificación, género, fecha de nacimiento, nacionalidad, ciudad de nacimiento, ciudad de residencia,
 * dirección, contactos, estado civil, datos del cónyuge, y datos de notificación.
 *
 * <p>Autor: John Macias</p>
 */
@Data
public class PostuladoDatosDTO {

    /**
     * El ID de la persona.
     */
    private Long idPersona;

    /**
     * El primer nombre del postulante.
     */
    private String primerNombre;

    /**
     * El segundo nombre del postulante.
     */
    private String segundoNombre;

    /**
     * El primer apellido del postulante.
     */
    private String primerApellido;

    /**
     * El segundo apellido del postulante.
     */
    private String segundoApellido;

    /**
     * La identificación del postulante.
     */
    private String identificacion;

    private String tipoIdentifiacion;

    /**
     * El género del postulante.
     */
    private String genero;

    /**
     * La fecha de nacimiento del postulante.
     */
    private Date fechaNacimiento;

    /**
     * La nacionalidad del postulante.
     */
    private String nacionalidad;

    /**
     * La ciudad de nacimiento del postulante.
     */
    private String ciudadNacimiento;

    /**
     * La ciudad de residencia del postulante.
     */
    private String ciudadResidencia;

    /**
     * La dirección de residencia del postulante.
     */
    private String direccionResidencia;

    /**
     * El número de contacto del postulante.
     */
    private String numeroContacto;

    /**
     * El estado civil del postulante.
     */
    private String estadoCivil;

    /**
     * El nombre del cónyuge del postulante.
     */
    private String nombreConyuge;

    /**
     * La identificación del cónyuge del postulante.
     */
    private String identificacionConyuge;

    /**
     * La ocupación actual del cónyuge del postulante.
     */
    private String ocupacionConyuge;

    /**
     * La empresa donde trabaja el cónyuge del postulante.
     */
    private String empresaOcupacionConyuge;

    /**
     * La ciudad de notificación del postulante.
     */
    private String ciudadNotificacion;

    /**
     * La dirección de notificación del postulante.
     */
    private String direccionNotificacion;

    /**
     * El correo electrónico del postulante.
     */
    private String email;
}

