package co.siri.posesiones.dtos;

import lombok.Getter;
import lombok.Setter;


public interface FirmaAuditoraProjection {

    Long getIdTramitePosesion();
    Long getIdPersonaFirmaAuditoraTramite();
    String getEsParteDeFirmaAuditora();
    String getNumeroMercantil();
    String getCamaraComercio();
    String getTarjetaProfesional();
    String getTipoDoc();
    String getIdentificacion();
    String getNombre();
    String getEntidad();
    String getRevisorFiscalOtraEntidad();
}

