package co.siri.posesiones.dtos;

import lombok.Data;

import java.util.Date;

/**
 * DTO para representar los estudios de un postulante.
 * Contiene información sobre el nivel educativo, institución, carrera, área de conocimiento,
 * estado del estudio, fecha de terminación y tarjeta profesional del postulante.
 *
 * <p>Autor: John Macias</p>
 */
@Data
public class PostuladoEstudioDTO {

    /**
     * El ID de la persona.
     */
    private Long idPersona;

    /**
     * El nivel educativo alcanzado por el postulante.
     */
    private String nivelEducativo;

    /**
     * La institución educativa donde estudió el postulante.
     */
    private String institucionEducativa;

    /**
     * El nombre de la carrera estudiada por el postulante.
     */
    private String nombreCarrera;

    /**
     * El área de conocimiento de la carrera.
     */
    private String areaConocimiento;

    /**
     * El estado del estudio (completado, en curso, etc.).
     */
    private String estado;

    /**
     * La fecha de terminación del estudio.
     */
    private Date fechaTerminacion;

    /**
     * La tarjeta profesional del postulante.
     */
    private String tarjetaProfesional;
}
