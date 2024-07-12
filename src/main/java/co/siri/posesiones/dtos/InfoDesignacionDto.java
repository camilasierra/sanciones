package co.siri.posesiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoDesignacionDto {

	private String documentoDesignacion;
	
	private String numeroDocumentoDesignacion;
	
	private String fechaDocumentoDesignacion;
	
	private String nombreCandidatizo;
	
	private String enRepresentacion;
	
	private String fechaAceptacion;
	
	private String vinculoEntidad;
	
	private String certificacionAmv;
	
	private String conflictoInteres;
	
	private String horasMensualesDedicacion;
}
