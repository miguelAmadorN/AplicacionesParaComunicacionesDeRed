/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Correo;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CORREO {
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
       public CORREO(String correoServidor, String Password,String rutaArchivo,
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

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(CORREO_SERVIDOR));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(CORREO_DESTINO));
            message.setSubject(ASUNTO);
            message.setText(MENSAJE);
          //  message.setFileName(NOMBREFILE);
           // message.setDataHandler(new DataHandler(new FileDataSource(RUTAFILE)));
            
            Transport.send(message);
            

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}