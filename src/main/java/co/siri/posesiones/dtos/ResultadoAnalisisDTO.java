package co.siri.posesiones.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultadoAnalisisDTO {
    private String TITULO;
    private String TEXTO;
    @JsonProperty("separator")
    private String separator;
    private int IDTEXTOEVALUACIONTRAMITE;
    private int IDTRAMITEPOSESION;
    private int IDTIPOTEXTOEVALUACIONTRAMITE;
    private int IDTIPOSECCIONTRAMITEPOSESION;
    private int IDUSUARIO;
    private String IPCLIENTE;
}
