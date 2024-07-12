package co.siri.posesiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CargaAnalistaDTO {
    private int id;
    private Long identificacion;
    private BigDecimal cargaTotal;
    private BigDecimal disponibilidad;

    public void incrementarCarga(BigDecimal peso) {
        this.cargaTotal = this.cargaTotal.add(peso);
    }

    public void decrementarCarga(BigDecimal peso) {
        this.cargaTotal = this.cargaTotal.subtract(peso);
    }
}
