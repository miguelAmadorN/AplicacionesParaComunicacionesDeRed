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
import carritodecompras.productos.CarritoCompras;
import carritodecompras.productos.EmpresaDeEnvio;
import carritodecompras.productos.InformacionDeEnvio;
import carritodecompras.productos.TarjetaDePago;
import carritodecompras.productos.Usuario;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 *
 * @author miguel
 */
public class GeneradorPdf {
    private static final String LOGO = "src/pdf/LogoCML.png";
    public static final String RUTA_RECIBOS = "Archivos/Pedidos/";
    
    public static void crearReciboDeCompra(CarritoCompras cc, String nombre ) 
    throws IOException 
    {
        double Total = 0;
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
        String text3 = "                    Recibo de Compra";
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
        //Usuario
        Usuario u = cc.getUsuario();
        contentStream.setLeading(20f);
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLine();
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        contentStream.showText("Fecha: " + hourdateFormat.format(date) );
        contentStream.newLine();
        contentStream.showText("Cliente: " + u.getNombres() + " " + u.getPrimerApellido() 
                                + " " + u.getSegundoApellido());
        contentStream.newLine();
        contentStream.showText("Telefono: "+ u.getTelefono());
        
        if(cc.getUsuario().getEmpresaDeEnvio() != null)
        {
            EmpresaDeEnvio ee = cc.getUsuario().getEmpresaDeEnvio();
            contentStream.setLeading(30f);
            contentStream.setFont(PDType1Font.TIMES_BOLD, 15);
            contentStream.newLine();
            contentStream.showText("INFORMACIÓN DE ENVÍO");
            contentStream.setLeading(15f);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.newLine();
            contentStream.showText("Paqueteria: " + ee.getNombre());
            contentStream.newLine();
            contentStream.showText("Tiempo aproximado de entrega: " + ee.getTiempoDeEntrega());
            contentStream.newLine();
            contentStream.showText("Costo de envio: $" + ee.getCosto());
            
            Total += ee.getCosto();
        }
        if(cc.getUsuario().getInformacionDeEnvio() != null)
        {
            InformacionDeEnvio ie = cc.getUsuario().getInformacionDeEnvio();
            contentStream.setLeading(30f);
            contentStream.setFont(PDType1Font.TIMES_BOLD, 15);
            contentStream.newLine();
            contentStream.showText("DIRECCIÓN DE ENVÍO");
            contentStream.setLeading(15f);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.newLine();
            contentStream.showText("Dirección: " + ie.getDireccion());
             contentStream.newLine();
            contentStream.showText("Email: " + ie.getEmail());
             contentStream.newLine();
            contentStream.showText("Recibe: " + ie.getNombreYApellidos());
             contentStream.newLine();
            contentStream.showText("País: " + ie.getPais());
             contentStream.newLine();
            contentStream.showText("Telefono: " + ie.getTelefono());         
        }
        
        int cantidad = cc.getProductoCompra().length;
        contentStream.setLeading(30f);
        contentStream.setFont(PDType1Font.TIMES_BOLD, 15);
        contentStream.newLine();
        contentStream.showText("PRODUCTOS");    
        contentStream.setLeading(15f);
        double subtotal = 0;
        for(int i = 0; i < cantidad; i++)
        {
            subtotal = cc.getProductoCompra()[i].getCantidad() * 
                       cc.getProductoCompra()[i].getProducto().getPrecio();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.newLine();
            contentStream.showText(cc.getProductoCompra()[i].getProducto().getNombre() + 
                    " cu: $" + cc.getProductoCompra()[i].getProducto().getPrecio() +
                    "     unidades: "  + cc.getProductoCompra()[i].getCantidad() + "  $" + subtotal);
            Total += subtotal;
            
        }
        contentStream.newLine();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
        contentStream.showText("                                                     Total: $" + Total);
        if(cc.getUsuario().getTarjetaDePago() != null)
        {
            TarjetaDePago tp = cc.getUsuario().getTarjetaDePago();
             contentStream.setLeading(25f);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.newLine();
            contentStream.showText("El cobro fue realizado a la tarjeta: " + tp.getNumeroDeTajeta());
        }
        contentStream.setLeading(30f);
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.newLine();
        contentStream.showText("                                                         "
                               + "Dudas y/o acalaraciones al 01-800- CLMVENTAS");
        
        
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
