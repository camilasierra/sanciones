package co.siri.posesiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionTramiteAnalistaDto {

    private Long idAsignacionTramiteAnalista;

    private Long idTramitePosesion;

    private Long idAanalistaTramitePosesion;

    private Long carga;

    private Long idUsuario;

    private Long ipCliente;
}
