package co.siri.posesiones.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InhabilidadPosesionDTO {

    private String descripcion;
    private String inhabilitado;

    public InhabilidadPosesionDTO(String descripcion, String inhabilitado) {
        this.descripcion = descripcion;
        this.inhabilitado = inhabilitado;
    }
}
