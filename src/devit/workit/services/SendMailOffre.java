/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devit.workit.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import devit.workit.entites.SessionWorkit;
import java.io.File;
import static java.lang.ProcessBuilder.Redirect.to;
import java.sql.SQLException;
import static java.sql.Timestamp.from;
import static java.time.LocalDateTime.from;
import static java.util.Date.from;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author ASUS
 */
public class SendMailOffre {

    public static void sendMail(String recepient) throws Exception {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "noreplay.espritwork@gmail.com";
        //Your gmail password
        String password = "esprit12";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        //Prepare email message
        Message message = prepareMessage(session, myAccountEmail, recepient);

        //Send mail
        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) throws MessagingException, SQLException {
        RandomCRUD rancrud = new RandomCRUD();
        String Code = "hi";
        try {
            Code = rancrud.ShowRandom1();
        } catch (SQLException ex) {
        }
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Confirmation for offer");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("This is message body " + Code);
            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            MimeBodyPart imagePart = new MimeBodyPart();
            imagePart.setFileName("Qr Code");
            imagePart.setText("After talking to your (specific person), I am writing to confirm for the offer. Pending our meeting, please accept,\n" +
"This is code Qr here to Accept\n" +
"\n" +
"Hope to meet you.\n" +
"EspritWork Team ! Merci !");
            imagePart.attachFile("C:/Users/ASUS/Desktop/codenameone/Sprint_java/src/QrCode/"+Code+".png");
            multipart.addBodyPart(imagePart);
          /*  messageBodyPart = new MimeBodyPart();
            String filename = "C:/Users/ASUS/Desktop/codenameone/Sprint_java/src/QrCode/" + Code + ".png";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("Qr Code");*/
            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            return message;
        } catch (Exception ex) {

        }
        return null;
    }

    public static void generate_qr(String image_name, String qrCodeData) {
        try {
            String filePath = "C:/Users/ASUS/Desktop/codenameone/Sprint_java/src/QrCode/" + image_name + ".png";
            String charset = "UTF-8"; // or "ISO-8859-1"
            Map< EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap< EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 200, 200, hintMap);
            MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
                    .lastIndexOf('.') + 1), new File(filePath));
            System.out.println("QR Code image created successfully!");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
