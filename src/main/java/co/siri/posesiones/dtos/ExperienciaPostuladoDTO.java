package co.siri.posesiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO para representar la experiencia de un postulante.
 * Contiene información sobre la experiencia laboral del postulante, incluyendo datos de la empresa,
 * cargo, tipo de cargo, área de desempeño, motivo de retiro, y fechas de inicio y retiro.
 *
 * <p>Autor: John Macias</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienciaPostuladoDTO {

    /**
     * El ID de la persona.
     */
    private Long idPersona;

    /**
     * El sector.
     */
    private String sector;

    /**
     * La clase de sociedad de la empresa donde trabajó el postulante.
     */
    private String claseDeSociedad;

    /**
     * La razón social de la empresa donde trabajó el postulante.
     */
    private String razonSocial;

    /**
     * El nombre del cargo que desempeñó el postulante.
     */
    private String nombreCargo;

    /**
     * El tipo de cargo que desempeñó el postulante.
     */
    private String tipoCargo;

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

    /**
     * Nombre del archivo del certificado.
     */
    private String nombreArchivo;

    /**
     * Content type del certificado.
     */
    private String contentTypeArchivo;

    /**
     *
     * Certificado.
     */
    private String archivo;

    /**
     * Longitud del certificado.
     */
    private Long longitudArchivo;
}
