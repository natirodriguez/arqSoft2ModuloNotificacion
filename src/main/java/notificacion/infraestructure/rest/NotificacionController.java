package notificacion.infraestructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import domain.dto.NotificacionDTO;
import jakarta.mail.MessagingException;
import notificacion.application.NotificacionService;
import notification.infraestructure.rest.puerto.INotificacionController;

@RestController
public class NotificacionController implements INotificacionController {
	@Autowired
	private NotificacionService notificacionService;
	
	@Override
	public void sendNotificacion(NotificacionDTO request) {
        NotificacionService service = new NotificacionService();

        try {
            service.sendSoldProductNotificationToSeller("seller@example.com", "SellerName", "Awesome Product");

            service.sendPurchaseSuccessToBuyer("buyer@example.com", "BuyerName", "Awesome Product");

            System.out.println("Emails enviados satisfactoriamente.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
}
