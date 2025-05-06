package com.arqsoft.medici.application;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.mail.MessagingException;

@ExtendWith(MockitoExtension.class)
public class NotificacionServiceTest {
	@InjectMocks
	@Spy
	private NotificacionService notificacionService;

    @Test
    public void testSendSoldProductNotificationToSeller() throws MessagingException {
        String sellerEmail = "axlpoli@example.com";
        String sellerName = "Axel";
        String productName = "Freidora";

        notificacionService.sendSoldProductNotificationToSeller(sellerEmail, sellerName, productName);

        verify(notificacionService).sendEmail(
                eq(sellerEmail),
                eq("Compra exitosa"),
                contains("Tu compra del \"" + productName + "\" fue exitosa"));
    }

    @Test
    public void testSendPurchaseSuccessToBuyer() throws MessagingException {
        String buyerEmail = "samsung@example.com";
        String buyerName = "Samsung";
        String productName = "Monitor";

        notificacionService.sendPurchaseSuccessToBuyer(buyerEmail, buyerName, productName);

        verify(notificacionService).sendEmail(
                eq(buyerEmail),
                eq("Notificación venta de producto"),
                contains("¡Buenas noticias! Tu producto \"" + productName + "\" ha tenido ventas."));
    }
}
