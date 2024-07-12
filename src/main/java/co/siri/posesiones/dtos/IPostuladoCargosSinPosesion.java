package co.siri.posesiones.dtos;

import java.util.Date;

/**
 * Interfaz para representar los cargos sin posesión ocupados por un postulante.
 * Proporciona métodos para obtener información sobre la entidad, cargo, área de desempeño,
 * motivo de retiro y fechas de inicio y retiro del cargo.
 *
 * <p>Autor: John Macias</p>
 */
public interface IPostuladoCargosSinPosesion {

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
}
