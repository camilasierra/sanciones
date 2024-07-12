package co.siri.posesiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InfoTramiteListAnalistasDTO {

    private String numeroRadicado;

    private String postulado;

    private String cargo;

    private List<AnalistaTramitesCargaDTO> listaAnalistasCarga;

}
