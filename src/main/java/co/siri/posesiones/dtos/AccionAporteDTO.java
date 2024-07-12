package co.siri.posesiones.dtos;

import lombok.Data;

/**
 * Data Transfer Object (DTO) para representar una acción de aporte.
 * Contiene información sobre la entidad vigilada, nombre de la entidad,
 * razón social, NIT, participación y si está inscrita en bolsa.
 * <p>
 * Autor: John Macias
 */
@Data
public class AccionAporteDTO {

    /**
     * La entidad vigilada.
     */
    private String entidadVigilada;

    /**
     * La razón social de la entidad.
     */
    private String razonSocial;

    /**
     * El NIT de la entidad.
     */
    private String nit;

    /**
     * La participación de la entidad.
     */
    private Long participacion;

    /**
     * Indica si la entidad está inscrita en bolsa.
     */
    private String inscritaEnBolsa;
}