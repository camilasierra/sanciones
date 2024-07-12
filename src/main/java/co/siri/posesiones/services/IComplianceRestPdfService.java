package co.siri.posesiones.services;

import co.siri.posesiones.dtos.PdfRequestDTO;

import java.io.IOException;

public interface IComplianceRestPdfService {

    public byte[] getPdfCompliance(PdfRequestDTO request);
}
