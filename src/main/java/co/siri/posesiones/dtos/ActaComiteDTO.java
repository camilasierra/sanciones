package co.siri.posesiones.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActaComiteDTO {
	private String estado;
	private String tipoSesion;
	private String numeroActa;
	private String fechaComite;
	private String fechaMesComite;
	private String horaInicioComite;
	private String tipoModalidad;
	private String canal;
	private String miembrosComite;
	private String asistenteComite;
	private String invitadosComite;
	private List<DecisionesActaComiteDTO> decisiones;
	private List<VotantesDto> votantes;
	private String varios;
	private String horaFinComite;
	private String delegaturasAsistentes;
	private String fechaVotacion;
	private String horaVotacion;
	private String data;
}
