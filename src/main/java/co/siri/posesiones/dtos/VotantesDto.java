package co.siri.posesiones.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VotantesDto {

	private String nombreComite;
	
	private String diaVotacion;
	
	private String horaVotacion;
}
