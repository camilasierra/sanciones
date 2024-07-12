package co.siri.posesiones.utilidades;

import co.siri.posesiones.entities.Persona;

import javax.sql.rowset.serial.SerialClob;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Utilidades {

	private Utilidades() {
	}

	public static String obtenerNombrePersona(Persona candidato) {
		if(null == candidato) {
			return "";
		}
		//return String.format("%s %s %s %s", candidato.getPrimerNombre(),
	//					candidato.getSegundoNombre() == null ? "": candidato.getSegundoNombre(),
	//					candidato.getPrimerApellido(), candidato.getSegundoApellido() == null ? "": candidato.getSegundoApellido());
		return "john doe";
	}
	public static String formatearFecha(Date fecha, String formato) {
		return new SimpleDateFormat(formato).format(fecha);
	}
	
	public static String getMensajeFormat(String template,Object ... pParametros) {
		return MessageFormat.format(template, pParametros);
	}

	/***
	 * Metodo para convertir un clob a string
	 * @param clob
	 * @return
	 */
	public static String convertClobToString(Clob clob) {
		if (clob == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		try (Reader reader = clob.getCharacterStream();
			 BufferedReader br = new BufferedReader(reader)) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
		} catch (SQLException | IOException e) {
			throw new RuntimeException("Error converting CLOB to String", e);
		}
		return sb.toString().trim();
	}
}
