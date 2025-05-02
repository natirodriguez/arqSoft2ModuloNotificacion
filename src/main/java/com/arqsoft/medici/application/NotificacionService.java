package com.arqsoft.medici.application;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService implements INotificacionService {
    private String fromEmail;
	@Autowired
    private Properties emailProperties;
    
    public NotificacionService() {
        loadEmailProperties();
        fromEmail = emailProperties.getProperty("mail.smtp.username");
    }
    
    private void loadEmailProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find application.properties");
            }
            emailProperties = new Properties();
            emailProperties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load email properties", e);
        }
    }
    
    private Session createSession() {
        return Session.getInstance(emailProperties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                String username = emailProperties.getProperty("mail.smtp.username");
                String password = emailProperties.getProperty("mail.smtp.password");
                return new PasswordAuthentication(username, password);
            }
        });
    }

    private void sendEmail(String toEmail, String subject, String body) throws MessagingException {
    	Session session = createSession();
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }

	@Override
    public void sendSoldProductNotificationToSeller(String sellerEmail, String sellerName, String productName) throws MessagingException {
        String subject = "Compra exitosa";
        String body = "Buenas " + sellerName + ",\n\n" +
                "Tu compra del \"" + productName + "\" fue exitosa.\n\n" +
                "¡Gracias! ¡Vuelvas prontos!";
        sendEmail(sellerEmail, subject, body);
    }

	@Override
    public void sendPurchaseSuccessToBuyer(String buyerEmail, String buyerName, String productName) throws MessagingException {
        String subject = "Notificación venta de producto";
        String body = "Buenas " + buyerName + ",\n\n" +
                "¡Buenas noticias! Tu producto \"" + productName + "\" ha tenido ventas.\n\n";
        sendEmail(buyerEmail, subject, body);
    }
}
