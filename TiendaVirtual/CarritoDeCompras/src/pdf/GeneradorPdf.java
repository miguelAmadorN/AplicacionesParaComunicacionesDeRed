/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author miguel
 */
public class GeneradorPdf {
    private static final String LOGO = "src/pdf/LogoCML.png";
    public static final String RUTA_RECIBOS = "Archivos/Pedidos/";
    
    public static void crearReciboDeCompra(String nombre ) 
    throws IOException 
    {
        PDDocument document = new PDDocument();
        File imagen = new File(LOGO);
        PDImageXObject pdImage = PDImageXObject.createFromFile(imagen.getAbsolutePath(), document);

        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.drawImage(pdImage, 50, 700, 100, 100);

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
        contentStream.setNonStrokingColor(Color.GREEN);

        contentStream.newLineAtOffset(10, 750);

        String text1 = "                                                  CML Express";
        String text2 = "                                      "
                + "                    Gracias por tu preferencia";
        String text3 = "   Recibo de Compra";
        //Adding text in the form of string
        contentStream.showText(text1);

        contentStream.setLeading(50f);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 15);
        contentStream.setNonStrokingColor(Color.RED);
        contentStream.newLine();
        contentStream.showText(text2);

        contentStream.setLeading(50f);
        contentStream.setFont(PDType1Font.COURIER_BOLD, 20);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLine();
        contentStream.showText(text3);

        //Ending the content stream
        contentStream.endText();
        contentStream.close();

        PDDocumentInformation pdd = document.getDocumentInformation();
        pdd.setAuthor("CML Express");
        pdd.setTitle("Recibo de compra");
        pdd.setSubject("¡Gracias por su preferencia!");
        document.save(new File(RUTA_RECIBOS + nombre));
        document.close();
    }
    
}
