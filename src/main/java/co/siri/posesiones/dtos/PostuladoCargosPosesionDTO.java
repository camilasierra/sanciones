package co.siri.posesiones.dtos;

import lombok.Data;

import java.util.Date;

/**
 * DTO para representar los cargos con posesión de un postulante.
 * Contiene información sobre los cargos que ha ocupado el postulante, incluyendo la entidad,
 * cargo, fechas de inicio y fin, estado de actividad, observaciones y estado del cargo.
 *
 * <p>Autor: John Macias</p>
 */
@Data
public class PostuladoCargosPosesionDTO {

    /**
     * El ID de la persona.
     */
    private Long idPersona;

    /**
     * La entidad en la que trabajó el postulante.
     */
    private String entidad;

    /**
     * El nombre del cargo que ocupó el postulante.
     */
    private String cargo;

    /**
     * La fecha desde la que el postulante ocupó el cargo.
     */
    private Date fechaDesde;

    /**
     * La fecha hasta la que el postulante ocupó el cargo.
     */
    private Date fechaHasta;

    /**
     * El estado de actividad del cargo, puede ser 'SI' o 'NO'.
     */
    private String activo;

    /**
     * Observaciones adicionales sobre el cargo.
     */
    private String observacion;

    /**
     * El estado del cargo.
     */
    private String estado;
}

