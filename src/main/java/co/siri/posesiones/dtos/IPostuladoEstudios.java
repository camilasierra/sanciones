package co.siri.posesiones.dtos;

import java.util.Date;

/**
 * Interfaz para representar los estudios de un postulante.
 * Proporciona métodos para obtener información sobre el nivel educativo, institución, carrera, área de conocimiento,
 * estado del estudio, fecha de terminación y tarjeta profesional del postulante.
 *
 * <p>Autor: John Macias</p>
 */
public interface IPostuladoEstudios {

    /**
     * Obtiene el ID de la persona.
     *
     * @return el ID de la persona.
     */
    Long getIdPersona();

    /**
     * Obtiene el nivel educativo alcanzado por el postulante.
     *
     * @return el nivel educativo.
     */
    String getNivelEducativo();

    /**
     * Obtiene la institución educativa donde estudió el postulante.
     *
     * @return la institución educativa.
     */
    String getInstitucionEducativa();

    /**
     * Obtiene el nombre de la carrera estudiada por el postulante.
     *
     * @return el nombre de la carrera.
     */
    String getNombreCarrera();

    /**
     * Obtiene el área de conocimiento de la carrera.
     *
     * @return el área de conocimiento.
     */
    String getAreaConocimiento();

    /**
     * Obtiene el estado del estudio (completado, en curso, etc.).
     *
     * @return el estado del estudio.
     */
    String getEstado();

    /**
     * Obtiene la fecha de terminación del estudio.
     *
     * @return la fecha de terminación.
     */
    Date getFechaTerminacion();

    /**
     * Obtiene la tarjeta profesional del postulante.
     *
     * @return la tarjeta profesional.
     */
    String getTarjetaProfesional();
}
