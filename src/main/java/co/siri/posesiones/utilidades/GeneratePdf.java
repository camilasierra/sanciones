package co.siri.posesiones.utilidades;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class GeneratePdf {

    public ByteArrayOutputStream generatePdf(Map<String, String> parametros, Double left, Double right, Double top, Double bottom, String headerText, String watermarkText) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.LETTER);

        // Set margins
        document.setMargins(top.floatValue() * 28.35f, right.floatValue() * 28.35f, bottom.floatValue() * 28.35f, left.floatValue() * 28.35f); // Convert cm to points

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        // Add a blank page to ensure at least one page exists
        pdfDoc.addNewPage();

        // Add headers/footers to the first page before adding content
        createHeader(document, 1, headerText, fontBold);
        createFooter(document, 1, font);
        addWatermarkToPages(document, 1, fontBold, watermarkText);

        // Add body content
        if (parametros != null && parametros.containsKey("BodyData")) {
            addBodyContent(document, parametros.get("BodyData"), font);
        } else {
            throw new IllegalArgumentException("El parámetro 'BodyData' es requerido y no puede ser nulo.");
        }

        // Add headers/footers to each page
        for (int i = 2; i <= pdfDoc.getNumberOfPages(); i++) {
            try {
                createHeader(document, i, headerText, fontBold);
                createFooter(document, i, font);
                addWatermarkToPages(document, i, fontBold, watermarkText);
            } catch (Exception e) {
                log.error("Error al crear el encabezado/pie de página en la página " + i + ": ", e);
            }
        }

        document.close();
        return byteArrayOutputStream;
    }

    public void createHeader(Document document, int pageIndex, String headerText, PdfFont fontBold) {
        PdfPage pdfPage = document.getPdfDocument().getPage(pageIndex);
        if (pdfPage != null) {
            Rectangle pageSize = pdfPage.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(pdfPage);
            Canvas canvas = new Canvas(pdfCanvas, pageSize);
            Paragraph header = new Paragraph(headerText)
                    .setFont(fontBold)
                    .setFontSize(12)
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.CENTER);
            canvas.showTextAligned(header, pageSize.getWidth() / 2, pageSize.getHeight() - 40, TextAlignment.CENTER);
            canvas.close();
        } else {
            log.error("La página en el índice " + pageIndex + " no existe.");
        }
    }

    public void createFooter(Document document, int pageIndex, PdfFont font) {
        PdfPage pdfPage = document.getPdfDocument().getPage(pageIndex);
        if (pdfPage != null) {
            Rectangle pageSize = pdfPage.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(pdfPage);
            Canvas canvas = new Canvas(pdfCanvas, pageSize);
            Paragraph footer = new Paragraph(String.valueOf(pageIndex))
                    .setFont(font)
                    .setFontSize(10);
            canvas.showTextAligned(footer, pageSize.getWidth() - 40, 20, TextAlignment.CENTER);
            canvas.close();
        } else {
            log.error("La página en el índice " + pageIndex + " no existe.");
        }
    }

    public void addWatermarkToPages(Document document, int pageIndex, PdfFont font, String watermarkText) {
        PdfPage pdfPage = document.getPdfDocument().getPage(pageIndex);
        if (pdfPage != null) {
            Rectangle pageSize = pdfPage.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(pdfPage);
            Canvas canvas = new Canvas(pdfCanvas, pageSize);
            Paragraph watermark = new Paragraph(watermarkText)
                    .setFont(font)
                    .setFontSize(60)
                    .setFontColor(ColorConstants.GRAY)
                    .setOpacity(0.2f)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setWidth(500)
                    .setRotationAngle(45);
            canvas.showTextAligned(watermark, pageSize.getWidth() / 2, (pageSize.getHeight() / 2) - 250, TextAlignment.CENTER);
            canvas.close();
        }
    }

    private void addBodyContent(Document document, String bodyData, PdfFont font) {
        while (bodyData.contains("data:image/png;base64,")) {
            int imageStartIndex = bodyData.indexOf("data:image/png;base64,");
            int imageEndIndex = findImageEndIndex(bodyData, imageStartIndex);

            if (imageEndIndex != -1) {
                String base64Image = bodyData.substring(imageStartIndex + 22, imageEndIndex);
                String textBeforeImage = bodyData.substring(0, imageStartIndex);
                String textAfterImage = bodyData.substring(imageEndIndex);

                // Add the text before the image
                addParagraphs(document, textBeforeImage, font);

                // Add the image itself
                addImage(document, base64Image);

                // Continue processing the remaining text after the image
                bodyData = textAfterImage;
            } else {
                log.error("No se pudo encontrar el final de la imagen base64 en bodyData.");
                break;
            }
        }

        // Add remaining body content if any
        if (!bodyData.isEmpty()) {
            if (bodyData.contains("<table")) {
                String tableContent = bodyData.substring(bodyData.indexOf("<table"), bodyData.indexOf("</table>") + 8);
                bodyData = bodyData.replace(tableContent, "");
                addParagraphs(document, bodyData, font);
                addTable(document, tableContent, font);
            } else {
                addParagraphs(document, bodyData, font);
            }
        }
    }

    private int findImageEndIndex(String bodyData, int imageStartIndex) {
        int quoteEndIndex = bodyData.indexOf("\"", imageStartIndex);
        int angleBracketEndIndex = bodyData.indexOf("<", imageStartIndex);

        if (quoteEndIndex == -1 && angleBracketEndIndex == -1) {
            return bodyData.length(); // If no valid end found, consider the whole string
        } else if (quoteEndIndex == -1) {
            return angleBracketEndIndex;
        } else if (angleBracketEndIndex == -1) {
            return quoteEndIndex;
        } else {
            return Math.min(quoteEndIndex, angleBracketEndIndex);
        }
    }

    private void addParagraphs(Document document, String bodyData, PdfFont font) {
        String[] paragraphs = bodyData.split("<br/>");

        for (String paragraphText : paragraphs) {
            Paragraph paragraph = new Paragraph();

            boolean isIndented = paragraphText.contains("<div>");
            if (isIndented) {
                paragraphText = paragraphText.replace("<div>", "").replace("</div>", "");
                paragraph.setMarginLeft(30);
            }

            boolean isCentered = paragraphText.contains("<center>");
            if (isCentered) {
                paragraph.setTextAlignment(TextAlignment.CENTER);
                paragraphText = paragraphText.replace("<center>", "").replace("</center>", "");
            } else {
                paragraph.setTextAlignment(TextAlignment.JUSTIFIED);
            }

            String[] sections = paragraphText.split("(<b>|</b>)");
            boolean bold = false;

            for (String section : sections) {
                if (section.isEmpty()) {
                    bold = !bold;
                } else {
                    Text text = new Text(section.trim()).setFont(font).setFontSize(12);
                    if (bold) {
                        text.setBold();
                    }
                    paragraph.add(text);
                }
            }

            document.add(paragraph);
        }
    }


    private void addTable(Document document, String tableContent, PdfFont font) {
        tableContent = tableContent.replace("<table>", "").replace("</table>", "")
                .replace("<thead>", "").replace("</thead>", "")
                .replace("<tbody>", "").replace("</tbody>", "");

        float[] columnWidths = {300, 60, 60}; // Adjust column widths as needed
        Table table = new Table(columnWidths);
        table.setWidth(document.getPdfDocument().getDefaultPageSize().getWidth() - (document.getLeftMargin() + 20) - (document.getRightMargin() + 20));
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        String[] rows = tableContent.split("<tr>");

        for (String row : rows) {
            if (row.trim().isEmpty()) continue;

            row = row.replace("</tr>", "");

            if (row.contains("<th>")) {
                String[] headers = row.split("<th>");

                for (String header : headers) {
                    if (header.trim().isEmpty()) continue;

                    header = header.replace("</th>", "");
                    Paragraph headerParagraph = new Paragraph(header.trim()).setFont(font).setFontSize(12).setBold();
                    headerParagraph.setTextAlignment(TextAlignment.CENTER);
                    Cell cell = new Cell().add(headerParagraph);
                    table.addHeaderCell(cell);
                }
            } else {
                String[] cells = row.split("<td>");

                for (String cell : cells) {
                    if (cell.trim().isEmpty()) continue;

                    cell = cell.replace("</td>", "");
                    Paragraph cellParagraph = new Paragraph(cell.trim()).setFont(font).setFontSize(12);
                    Cell tableCell = new Cell().add(cellParagraph);
                    table.addCell(tableCell);
                }
            }
        }

        document.add(table);
    }

    private void addImage(Document document, String base64Image) {
        try {
            byte[] imageBytes = Base64.decodeBase64(base64Image);
            ImageData imageData = ImageDataFactory.create(imageBytes);
            Image image = new Image(imageData);

            // Define a fixed height for the image
            float fixedHeight = 65; // Fixed height in points (1 point = 1/72 inch)

            // Calculate the proportional width
            float aspectRatio = image.getImageWidth() / image.getImageHeight();
            float adjustedWidth = fixedHeight * aspectRatio;

            // Set the image size
            image.scaleToFit(adjustedWidth, fixedHeight);
            image.setHorizontalAlignment(HorizontalAlignment.LEFT);

            document.add(image);
        } catch (Exception e) {
            log.error("Error adding image to PDF: ", e);
        }
    }
}
