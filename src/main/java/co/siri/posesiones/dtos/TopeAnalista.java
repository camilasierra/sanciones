package co.siri.posesiones.dtos;
import lombok.Getter;

@Getter
public enum TopeAnalista {

    CARGA_MAXIMA_ANALISTA_BASE("cargaMaximaAnalistaBase"),
    CARGA_MAXIMA_ANALISTA_APOYO("cargaMaximaAnalistaApoyo"),

    DIFERENCIA_CARGA("diferenciaMaximaCarga");

    private final String cargaMaxima;

    // Constructor
    TopeAnalista(String cargaMaxima) {
        this.cargaMaxima = cargaMaxima;
    }

    // Getter
    public String getCargaMaxima() {
        return cargaMaxima;
    }
}
