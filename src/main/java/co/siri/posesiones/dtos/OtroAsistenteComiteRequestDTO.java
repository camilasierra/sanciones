package co.siri.posesiones.dtos;

import lombok.Data;

@Data
public class OtroAsistenteComiteRequestDTO {
    private Integer idSesionComite;
    private String nombreOtroAsistente;
    private String calidadAsistente;
    private Integer idUsuario;
    private String ipCliente;
}