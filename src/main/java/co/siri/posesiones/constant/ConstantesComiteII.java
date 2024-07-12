package co.siri.posesiones.constant;

import java.util.Arrays;
import java.util.List;

public class ConstantesComiteII {
     public static List<String> PLANTILLAS_DS = Arrays.asList(
            "autorizado_SIN_Defensor", //0
            "autorizado_SIN_otrosCargos", //1
            "word_NEGADO_otrosCargos", //2
            "word_NEGADO_Defensor", //3
            "autorizado_CON_otrosCargos", //4
            "autorizado_CON_defensor", //5
            "word_SUSPENDIDO_otrosCargos", //6
            "word_SUSPENDIDO_Defensor", //7
            "word_DesisExpreso", //8
            "word_DesisTacito" //9
    );

     public static List<String> ESTADOS_TS = Arrays.asList(
            "Autorizado",
            "Negado",
            "Suspendido",
            "Desistimiento tácito",
            "Desistimiento expreso"
    );

     public static List<String> LISTADOS_CARGO = Arrays.asList(
            "Defensor del Consumidor Financiero",
            "Miembro de la Junta Directiva o Consejo de Dirección  o de Administración",
            "Representante Legal",
            "Oficial de Cumplimiento"
    );
    public static final String NO_DATA = "No se encontraron datos.";
    public static final String NO_PLANTILLA = "No se encontró una plantilla para los criterios especificados.";
    public static final String FORMAT_PDF = "pdf";
    public static final String FORMAT_DOCX = "docx";
    public static final String ARIAL = "Arial";
}
