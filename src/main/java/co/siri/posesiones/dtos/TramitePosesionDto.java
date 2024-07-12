package co.siri.posesiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TramitePosesionDto {
    private Long idTramitePosesion;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String razonSocial;
    private Long tipoEntidad;
    private Long codigoEntidad;
    private String siglaEntidad;
    private String tipoDocumento;
    private String numeroDocumento;
    private String numeroRadicacion;
    private Date fechaPrimeraTransmision;
    private Date fechaUltimaTransmision;
    private String asignado;
}
