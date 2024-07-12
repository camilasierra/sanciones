package co.siri.posesiones.dtos;

import lombok.Data;

import java.util.Date;

/**
 * DTO para representar los cargos sin posesión de un postulante.
 * Contiene información sobre los cargos que no han tenido posesión por parte del postulante,
 * incluyendo la entidad, cargo, área de desempeño, motivo de retiro y fechas de inicio y retiro.
 *
 * <p>Autor: John Macias</p>
 */
@Data
public class PostuladoCargosSinPosesionDTO {

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
     * El área de desempeño del cargo.
     */
    private String areaDesempenio;

    /**
     * El motivo de retiro del postulante del cargo.
     */
    private String motivoRetiro;

    /**
     * La fecha de inicio del cargo.
     */
    private Date fechaInicio;

    /**
     * La fecha de retiro del cargo.
     */
    private Date fechaRetiro;
}

