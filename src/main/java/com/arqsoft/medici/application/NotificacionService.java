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

	@Override
    public void sendEmail(String toEmail, String subject, String body) throws MessagingException {
    	Session session = createSession();
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setContent(body, "text/html; charset=UTF-8");

        Transport.send(message);
    }

	@Override
    public void sendSoldProductNotificationToSeller(String sellerEmail, String sellerName, String productName) throws MessagingException {
        String subject = "Compra exitosa";
        String body = "Buenas " + sellerName + ",\n\n" +
                "Tu compra del \"" + productName + "\" fue exitosa.\n\n" +
                "¡Gracias! ¡Vuelvas prontos!";
        String template = createEmail(body);

        sendEmail(sellerEmail, subject, template);
    }

	@Override
    public void sendPurchaseSuccessToBuyer(String buyerEmail, String buyerName, String productName) throws MessagingException {
        String subject = "Notificación venta de producto";
        String body = "Buenas " + buyerName + ",\n\n" +
                "¡Buenas noticias! Tu producto \"" + productName + "\" ha tenido ventas.\n\n";
        String template = createEmail(body);
        
        sendEmail(buyerEmail, subject, template);
    }
	
	private String createEmail(String bodyContent) {
        String template = "<!DOCTYPE html>" +
            "<html lang=\"es\">" +
            "<head>" +
            "  <meta charset=\"UTF-8\" />" +
            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />" +
            "  <title>Notificación de Ventas</title>" +
            "  <style>" +
            "    body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }" +
            "    header { background-color: #005BBB; color: white; padding: 10px; font-size: 24px; font-weight: bold; text-align: center; }" +
            "    main { background-color: white; padding: 20px; margin: 15px 0; border-radius: 6px; color: #333; font-size: 16px; }" +
            "    footer { background-color: #e2e2e2; padding: 15px; text-align: center; font-size: 14px; color: #555; border-radius: 6px; }" +
            "  </style>" +
            "</head>" +
            "<body>" +
            "  <header>Notificación de Ventas</header>" +
            "  <main>" + bodyContent + "</main>" +
            "  <footer>Libre Mercado</footer>" +
            "</body>" +
            "</html>";

        return template;
    }
}
