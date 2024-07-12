package co.siri.posesiones.utilidades;

import java.text.MessageFormat;

public enum CamposRadicarTramiteEnum {
	CAMPO_ENCABEZADO("<?xml version='1.0' encoding='UTF-8'?>"),
	CAMPO_ARCHIVO("<parametro nombre=\"Archivo\">{0}</parametro>"),
	CAMPO_HASANEXOS("<parametro nombre=\"HasAnexos\">{0}</parametro>"),
	CAMPO_SOLICITUDREMITENTE("<parametro nombre=\"SolicitudRemitente\">{0}</parametro>"),
	CAMPO_FECHASOLICITUD("<parametro nombre=\"FechaSolicitud\">{0}</parametro>"),
	CAMPO_NOMBREARCHIVO("<parametro nombre=\"NombreArchivo\">{0}</parametro>"),
	CAMPO_TIPOENTIDAD("<parametro nombre=\"TipoEntidad\">{0}</parametro>"),
	CAMPO_CODIGOENTIDAD("<parametro nombre=\"CodigoEntidad\">{0}</parametro>"),
	CAMPO_NOFOLIOS("<parametro nombre=\"NoFolios\">{0}</parametro>"),
	CAMPO_CODIGOTRAMITE("<parametro nombre=\"CodigoTramite\">{0}</parametro>"),
	CAMPO_CIERRE_ENTRADA("</entrada>"),
	CAMPO_ENTRADA("<entrada>");

	private final String valor;

	private CamposRadicarTramiteEnum (String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	public String getMensajeFormat(Object ... pParametros) {
		return MessageFormat.format(valor, pParametros);
	}
}
