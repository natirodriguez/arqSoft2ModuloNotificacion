package com.arqsoft.medici.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.arqsoft.medici.application.NotificacionService;
import com.arqsoft.medici.domain.dto.NotificacionDTO;
import com.arqsoft.medici.infrastructure.rest.puerto.INotificacionController;
import jakarta.mail.MessagingException;

@RestController
public class NotificacionController implements INotificacionController {
	
	//@Autowired
	private NotificacionService notificacionService = new NotificacionService();
	
	@Override
	public void sendNotificacionUsuario(NotificacionDTO request) {
        //NotificacionService service = new NotificacionService();

        try {
        	notificacionService.sendSoldProductNotificationToSeller(request.getEmail(), request.getNombreUsuario(), request.getNombreProducto());

            System.out.println("Email enviado al usuario satisfactoriamente.");
        } catch (MessagingException e) {
        	//El envio de mail no debe lanzar error que confunda al cliente de si la
            //transaccion se hizo o no
        }
	}
	
	@Override
	public void sendNotificacionVendedor(NotificacionDTO request) {
        //NotificacionService service = new NotificacionService();

        try {

        	notificacionService.sendPurchaseSuccessToBuyer(request.getEmail(), request.getNombreUsuario(), request.getNombreProducto());

            System.out.println("Email enviado al vendedor satisfactoriamente.");
        } catch (Exception e) {
           //El envio de mail no debe lanzar error que confunda al cliente de si la
           //transaccion se hizo o no
        }
	}

	public NotificacionService getNotificacionService() {
		return notificacionService;
	}

	public void setNotificacionService(NotificacionService notificacionService) {
		this.notificacionService = notificacionService;
	}
}
