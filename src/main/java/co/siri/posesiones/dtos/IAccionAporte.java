package co.siri.posesiones.dtos;

/**
 * Interfaz que define los métodos para obtener información sobre una acción de aporte.
 * <p>
 * Autor: John Macias
 */
public interface IAccionAporte {

    /**
     * Obtiene la entidad vigilada.
     *
     * @return la entidad vigilada
     */
    String getEntidadVigilada();

    /**
     * Obtiene la razón social de la entidad.
     *
     * @return la razón social de la entidad
     */
    String getRazonSocial();

    /**
     * Obtiene el NIT de la entidad.
     *
     * @return el NIT de la entidad
     */
    String getNit();

    /**
     * Obtiene la participación de la entidad.
     *
     * @return la participación de la entidad
     */
    Long getParticipacion();

    /**
     * Indica si la entidad está inscrita en bolsa.
     *
     * @return "Sí" si la entidad está inscrita en bolsa, de lo contrario "No"
     */
    String getInscritaEnBolsa();
}
