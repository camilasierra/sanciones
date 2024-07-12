package co.siri.posesiones.utilidades;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

public class GenerateDocx {

    private static final double CM_TO_TWIPS = 567.0;

    public ByteArrayOutputStream generateDocx(Map<String, String> parametros, Double left, Double right, Double top, Double bottom, String headerText) {
        Map<String, String> parameters = parametros;
        XWPFDocument document = new XWPFDocument();

        // Ajustar márgenes
        setDocumentMargins(document, left, right, top, bottom); // Márgenes en cm (izquierda, derecha, superior, inferior)

        // Agregar encabezado
        addHeader(document, headerText);

        // Agregar contenido del bodyData
        addBodyContent(document, parameters.get("BodyData"));

        // Agregar pie de página con número de página
        addFooter(document);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            document.write(byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream;
    }

    private void setDocumentMargins(XWPFDocument document, double leftCm, double rightCm, double topCm, double bottomCm) {
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf((long) (leftCm * CM_TO_TWIPS)));
        pageMar.setRight(BigInteger.valueOf((long) (rightCm * CM_TO_TWIPS)));
        pageMar.setTop(BigInteger.valueOf((long) (topCm * CM_TO_TWIPS)));
        pageMar.setBottom(BigInteger.valueOf((long) (bottomCm * CM_TO_TWIPS)));
    }

    private void addHeader(XWPFDocument document, String headerText) {
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(document);
        XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

        XWPFParagraph paragraph = header.createParagraph();

        // Verificar si el párrafo debe centrarse
        boolean isCentered = headerText.contains("<center>");
        if (isCentered) {
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            headerText = headerText.replace("<center>", "").replace("</center>", "");
        } else {
            paragraph.setAlignment(ParagraphAlignment.BOTH);
        }

        // Dividir el texto del encabezado por etiquetas de negrita
        String[] sections = headerText.split("(<b>|</b>)");
        boolean bold = false;

        for (String section : sections) {
            if (section.isEmpty()) {
                // Cambiar el estado de negrita cuando se encuentra una etiqueta vacía
                bold = !bold;
            } else {
                // Crear un nuevo run y establecer el texto, la fuente y el tamaño
                XWPFRun run = paragraph.createRun();
                run.setText(section.trim());
                run.setBold(bold);
                run.setFontFamily("Arial"); // Establecer la fuente como Arial
                run.setFontSize(12); // Establecer el tamaño de la fuente a 12
            }
        }
    }

    private void addFooter(XWPFDocument document) {
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(document);
        XWPFFooter footer = headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);

        XWPFParagraph paragraph = footer.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);

        XWPFRun run = paragraph.createRun();
        run.setFontFamily("Arial");
        run.setFontSize(12);

        // Insertar el número de página
        run = paragraph.createRun();
        run.getCTR().addNewFldChar().setFldCharType(STFldCharType.BEGIN);
        run = paragraph.createRun();
        run.getCTR().addNewInstrText().setStringValue("PAGE");
        run = paragraph.createRun();
        run.getCTR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
        run = paragraph.createRun();
        run.getCTR().addNewT().setStringValue("1");
        run = paragraph.createRun();
        run.getCTR().addNewFldChar().setFldCharType(STFldCharType.END);

        run.setFontFamily("Arial");
        run.setFontSize(12);
    }

    private void addBodyContent(XWPFDocument document, String bodyData) {
        // Check if the bodyData contains a table
        if (bodyData.contains("<table")) {
            // Extract the table content
            String tableContent = bodyData.substring(bodyData.indexOf("<table"), bodyData.indexOf("</table>") + 8);
            bodyData = bodyData.replace(tableContent, ""); // Remove table content from bodyData

            // Add the remaining body content
            addParagraphs(document, bodyData);

            // Add the table content
            addTable(document, tableContent);
        } else {
            // Add the body content
            addParagraphs(document, bodyData);
        }
    }

    private void addParagraphs(XWPFDocument document, String bodyData) {
        // Dividir los párrafos por las etiquetas <br/>
        String[] paragraphs = bodyData.split("<br/>");

        for (String paragraphText : paragraphs) {
            // Verificar si el párrafo debe tener sangría
            boolean isIndented = paragraphText.contains("<div>");
            if (isIndented) {
                paragraphText = paragraphText.replace("<div>", "").replace("</div>", "");
            }

            XWPFParagraph paragraph = document.createParagraph();

            // Verificar si el párrafo debe centrarse
            boolean isCentered = paragraphText.contains("<center>");
            if (isCentered) {
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                paragraphText = paragraphText.replace("<center>", "").replace("</center>", "");
            } else {
                paragraph.setAlignment(ParagraphAlignment.BOTH);
            }

            // Dividir el texto del párrafo por etiquetas de negrita
            String[] sections = paragraphText.split("(<b>|</b>)");
            boolean bold = false;

            for (String section : sections) {
                if (section.isEmpty()) {
                    // Cambiar el estado de negrita cuando se encuentra una etiqueta vacía
                    bold = !bold;
                } else {
                    // Crear un nuevo run y establecer el texto
                    XWPFRun run = paragraph.createRun();
                    run.setText(section.trim());
                    run.setBold(bold);
                    run.setFontFamily("Arial"); // Establecer la fuente como Arial
                    run.setFontSize(12); // Establecer el tamaño de la fuente a 12

                    // Aplicar sangría si está dentro de un <div>
                    if (isIndented) {
                        paragraph.setIndentationLeft(720); // Sangría de media pulgada (720 twips)
                    }
                }
            }
        }
    }


    private void addTable(XWPFDocument document, String tableContent) {
        // Create a table with three columns
        XWPFTable table = document.createTable(1, 3);

        // Create the table grid and set column widths
        CTTbl cTTbl = table.getCTTbl();
        CTTblGrid tblGrid = cTTbl.addNewTblGrid();
        tblGrid.addNewGridCol().setW(BigInteger.valueOf(5000));
        tblGrid.addNewGridCol().setW(BigInteger.valueOf(2500));
        tblGrid.addNewGridCol().setW(BigInteger.valueOf(2500));

        // Set the table width to 100% of the page width
        table.setWidth("100%");

        // Set table borders
        CTTblPr tblPr = cTTbl.getTblPr();
        if (tblPr == null) tblPr = cTTbl.addNewTblPr();
        CTTblBorders borders = tblPr.addNewTblBorders();
        CTBorder hBorder = borders.addNewInsideH();
        hBorder.setVal(STBorder.SINGLE);
        hBorder.setSz(BigInteger.valueOf(4));
        hBorder.setColor("000000");
        CTBorder vBorder = borders.addNewInsideV();
        vBorder.setVal(STBorder.SINGLE);
        vBorder.setSz(BigInteger.valueOf(4));
        vBorder.setColor("000000");
        CTBorder lBorder = borders.addNewLeft();
        lBorder.setVal(STBorder.SINGLE);
        lBorder.setSz(BigInteger.valueOf(4));
        lBorder.setColor("000000");
        CTBorder rBorder = borders.addNewRight();
        rBorder.setVal(STBorder.SINGLE);
        rBorder.setSz(BigInteger.valueOf(4));
        rBorder.setColor("000000");
        CTBorder tBorder = borders.addNewTop();
        tBorder.setVal(STBorder.SINGLE);
        tBorder.setSz(BigInteger.valueOf(4));
        tBorder.setColor("000000");
        CTBorder bBorder = borders.addNewBottom();
        bBorder.setVal(STBorder.SINGLE);
        bBorder.setSz(BigInteger.valueOf(4));
        bBorder.setColor("000000");

        // Remove <table>, <thead>, <tbody> and their corresponding closing tags
        tableContent = tableContent.replace("<table>", "").replace("</table>", "")
                .replace("<thead>", "").replace("</thead>", "")
                .replace("<tbody>", "").replace("</tbody>", "");

        // Split the content into rows
        String[] rows = tableContent.split("<tr>");

        boolean isHeader = true;
        for (String row : rows) {
            if (row.trim().isEmpty()) continue;

            // Remove </tr> tags
            row = row.replace("</tr>", "");

            // Split the row into cells
            String[] cells = row.split("(<th>|<td>)");

            XWPFTableRow tableRow;
            if (isHeader) {
                tableRow = table.getRow(0); // Get the first row for header
            } else {
                tableRow = table.createRow(); // Create a new row for body
            }

            for (int i = 0; i < cells.length; i++) {
                String cell = cells[i].replace("</th>", "").replace("</td>", "").trim();

                XWPFTableCell tableCell;
                if (isHeader && i < tableRow.getTableCells().size()) {
                    tableCell = tableRow.getCell(i);
                } else {
                    tableCell = tableRow.getCell(i);
                    if (tableCell == null) {
                        tableCell = tableRow.addNewTableCell();
                    }
                }

                XWPFParagraph cellParagraph = tableCell.addParagraph();
                cellParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun run = cellParagraph.createRun();
                run.setText(cell);
                run.setFontFamily("Arial");
                run.setFontSize(12);
                run.setBold(isHeader); // Set bold for header row

                // Set cell borders
                CTTcPr tcPr = tableCell.getCTTc().addNewTcPr();
                CTTcBorders tcBorders = tcPr.addNewTcBorders();
                tcBorders.addNewTop().setVal(STBorder.SINGLE);
                tcBorders.addNewBottom().setVal(STBorder.SINGLE);
                tcBorders.addNewLeft().setVal(STBorder.SINGLE);
                tcBorders.addNewRight().setVal(STBorder.SINGLE);
            }
            isHeader = false; // After first row, treat the rest as body rows
        }
    }
}
