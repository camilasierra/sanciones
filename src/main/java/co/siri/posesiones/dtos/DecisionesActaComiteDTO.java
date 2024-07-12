package co.siri.posesiones.dtos;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DecisionesActaComiteDTO {

	private String delegatura;
	private HashMap<String, Object> decisiones;
	
}
