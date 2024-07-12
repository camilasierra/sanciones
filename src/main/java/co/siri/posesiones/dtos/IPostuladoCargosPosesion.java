package co.siri.posesiones.dtos;

import java.util.Date;

/**
 * Interfase para representar los datos de los cargos de posesión postulados.
 * Proporciona métodos para obtener información sobre los cargos que ha ocupado un postulante.
 */
public interface IPostuladoCargosPosesion {

    /**
     * Obtiene el ID de la persona.
     *
     * @return el ID de la persona.
     */
    Long getIdPersona();

    /**
     * Obtiene la entidad en la que trabajó el postulante.
     *
     * @return el nombre de la entidad.
     */
    String getEntidad();

    /**
     * Obtiene el nombre del cargo que ocupó el postulante.
     *
     * @return el nombre del cargo.
     */
    String getCargo();

    /**
     * Obtiene la fecha desde la que el postulante ocupó el cargo.
     *
     * @return la fecha de inicio del cargo.
     */
    Date getFechaDesde();

    /**
     * Obtiene la fecha hasta la que el postulante ocupó el cargo.
     *
     * @return la fecha de fin del cargo.
     */
    Date getFechaHasta();

    /**
     * Obtiene el estado de actividad del cargo.
     *
     * @return "SI" si el cargo está activo, "NO" si no lo está.
     */
    String getActivo();

    /**
     * Obtiene las observaciones adicionales sobre el cargo.
     *
     * @return las observaciones del cargo.
     */
    String getObservacion();

    /**
     * Obtiene el estado del cargo.
     *
     * @return el estado del cargo.
     */
    String getEstado();
}
