package co.siri.posesiones.dtos.comiteII;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenerarOficioDto {
    private String estadoTramite;
    private Long sesionComite;
    private Integer sinAsignacion;
}
