package com.arqsoft.medici.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arqsoft.medici.application.NotificacionService;
import com.arqsoft.medici.domain.dto.NotificacionDTO;
import com.arqsoft.medici.infrastructure.rest.puerto.INotificacionController;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/notificacion")
public class NotificacionController implements INotificacionController {
	@Autowired
	private NotificacionService notificacionService;
	
	@Override
	public void sendNotificacionUsuario(NotificacionDTO request) {
        NotificacionService service = new NotificacionService();

        try {
            service.sendSoldProductNotificationToSeller(request.getEmail(), request.getNombreUsuario(), request.getNombreProducto());

            System.out.println("Email enviado al usuario satisfactoriamente.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
	
	@Override
	public void sendNotificacionVendedor(NotificacionDTO request) {
        NotificacionService service = new NotificacionService();

        try {

            service.sendPurchaseSuccessToBuyer(request.getEmail(), request.getNombreUsuario(), request.getNombreProducto());

            System.out.println("Email enviado al vendedor satisfactoriamente.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
}
