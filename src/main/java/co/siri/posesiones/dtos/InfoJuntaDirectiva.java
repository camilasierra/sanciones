package co.siri.posesiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoJuntaDirectiva {
    private String calidadJuntaDirectiva;
    private String renglonJuntaDirectiva;
}
