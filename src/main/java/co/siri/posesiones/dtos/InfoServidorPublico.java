package co.siri.posesiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoServidorPublico {
    private String cargoServidorPublico;
    private String entidadServidorPublico;
}
