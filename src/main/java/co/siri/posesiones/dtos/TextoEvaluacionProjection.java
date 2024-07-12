package co.siri.posesiones.dtos;

import java.sql.Clob;

/**
 * Proyección de evaluación de texto que define los métodos para acceder a los datos de evaluación de texto.
 * Esta interfaz es utilizada para mapear las consultas de la base de datos a objetos de Java.
 */
public interface TextoEvaluacionProjection {

    /**
     * Obtiene el ID del texto del trámite.
     *
     * @return el ID del texto del trámite.
     */
    Long getIdTextoTramite();

    /**
     * Obtiene el ID del trámite de posesión.
     *
     * @return el ID del trámite de posesión.
     */
    Long getIdTramitePosesion();

    /**
     * Obtiene el título del texto.
     *
     * @return el título del texto.
     */
    String getTitulo();

    /**
     * Obtiene el ID del tipo de texto del trámite.
     *
     * @return el ID del tipo de texto del trámite.
     */
    Long getIdTipoTextoTramite();

    /**
     * Obtiene el ID del tipo de sección del trámite.
     *
     * @return el ID del tipo de sección del trámite.
     */
    Long getIdTipoSeccionTramite();

    /**
     * Obtiene el texto del trámite.
     *
     * @return el texto del trámite.
     */
    Clob getTexto();

    /**
     * Obtiene el ID del usuario.
     *
     * @return el ID del usuario.
     */
    Long getIdUsuario();

    /**
     * Obtiene la dirección IP del cliente.
     *
     * @return la dirección IP del cliente.
     */
    String getIpCliente();
}