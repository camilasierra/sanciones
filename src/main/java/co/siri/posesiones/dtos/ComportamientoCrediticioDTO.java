package co.siri.posesiones.dtos;

import java.util.Date;

/***
 * Interfaz realizada para recibir la respuesta del servicio que se encarga de traer la informacion
 * del comportamiento crediticio
 */
public interface ComportamientoCrediticioDTO {

    String getClaseCentral();

    String getNombreCentral();

    String getTipoObligacion();

    Date getFechaReporte();

    String getMoraDe();

    Date getFechaPago();

    String getCalidadReporteCrediticio();

    String getNombreArchivoJustificacion();

    String getJustificacion();

    String getNombreArchivoCertificacion();

    String getCertificacion();

    Long getIdComportamientoCrediticio();

    Long getIdPersona();

    Long getIdTipoClaseCentral();

     Long getIdTipoObligacion();
     Long getIdTipoClasificacionMora();
     Long getIdTipoEstadoDeuda();
     Long getIdTipoCalidadReporteCrediticio();
     String getEmpresaReporta();
     Long getIdArchivoJustificacion();
     Long getIdArchivoCertificacion();

     Long getIdUsuario();

     String getIpCliente();
     String getSeparator();

     String getTypeArchivoJustificacion();

     String getTypeArchivoCertificacion();




}
