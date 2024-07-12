package co.siri.posesiones.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/***
 * Interfaz realiza para recibir la informacion de investigaciones que tenga una persona
 * informacion del sisema de informacion de poseciones de la entidad
 */
public interface InvestigacionesPersonasDTO {

    String getTipoInvestigacion();

    String getEntidadInvestiga();

    @JsonProperty(value = "MATERIAINVESTIGACION")
    String getMateriainvestigacion();

    String getTipoActo();

    String getNumero();

    String getEstadoInvestigacion();

    Date getFechaDesde();

    String getSeparator();

    Long getIdInvestigacionPersona();

    Long getIdPersona();

    Long getIdTipoInvestigacion();

    @JsonProperty(value = "IDTIPOENTIDADINVESTIGACIONSANC")
    Long getIdtipoentidadinvestigacionsanc();

    @JsonProperty(value = "IDTIPOACTOINVESTIGACIONSANCION")
    Long getIdtipoactoinvestigacionsancion();

    Long getIdTipo();
    Long getIdTipoEstadoInvestigacion();

    Long getIdUsuario();

    String getIpCLiente();

    Date getFechaHasta();
}
