/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Correo;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class CorreoAdjunto {
    String CORREO_SERVIDOR;
    String PASSWORD;
    String RUTAFILE;
    String NOMBREFILE;
    String CORREO_DESTINO;
    String ASUNTO;
    String MENSAJE;
    /*
    CML.Express.ventas@gmail.com
    pass: cmlexpress
    */
       ///----------------------------
       public CorreoAdjunto(String correoServidor, String Password,String rutaArchivo,
               String nombreArchivo,String destino,String asunto,String mensaje)
       {
            
            this.CORREO_SERVIDOR=correoServidor;
            this.PASSWORD=Password;
            this.RUTAFILE=rutaArchivo;
            this.NOMBREFILE=nombreArchivo;
            this.CORREO_DESTINO=destino;
            this.MENSAJE=mensaje;
            this.ASUNTO=asunto;
            
        }
    
    
    
    public void SendMail() 
    {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.user",CORREO_SERVIDOR);
       
        Session session = Session.getInstance(properties,new javax.mail.Authenticator() {
                    @Override
                    protected  PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(CORREO_SERVIDOR, PASSWORD);
                    }
                });

        try {
          
            
            BodyPart texto = new MimeBodyPart();
            texto.setText(MENSAJE);
            
            
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(RUTAFILE)));
            adjunto.setFileName(NOMBREFILE);
            
            MimeMultipart multiParte = new MimeMultipart();

            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(adjunto);
            
            
            MimeMessage message = new MimeMessage(session);

            // Se rellena el From
            message.setFrom(new InternetAddress(CORREO_SERVIDOR));

            // Se rellenan los destinatarios
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(CORREO_DESTINO));

            // Se rellena el subject
            message.setSubject(ASUNTO);

            // Se mete el texto y la foto adjunta.
            message.setContent(multiParte);
            
            
            Transport t = session.getTransport("smtp");
            t.connect(CORREO_SERVIDOR,PASSWORD);
            t.sendMessage(message,message.getAllRecipients());
            t.close();
            
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}