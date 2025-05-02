package com.arqsoft.medici.application;

import jakarta.mail.MessagingException;

public interface INotificacionService {
	void sendSoldProductNotificationToSeller(String sellerEmail, String sellerName, String productName) throws MessagingException ;
	void sendPurchaseSuccessToBuyer(String buyerEmail, String buyerName, String productName) throws MessagingException;
}
