package co.siri.posesiones.services;

import co.siri.posesiones.dtos.comiteII.GenerarOficioDto;

import java.io.IOException;
import java.util.List;

public interface ComiteOficioService {
    List<String> generateDocument(GenerarOficioDto oficioDto);
    byte[] generateDocumentAndZip(GenerarOficioDto oficioDto) throws IOException;
    String radicacionSolip(List<String> base64Documents);
}
