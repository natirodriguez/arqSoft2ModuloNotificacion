package com.arqsoft.medici.application;

import jakarta.mail.MessagingException;

public interface INotificacionService {
	void sendEmail(String toEmail, String subject, String body) throws MessagingException;
	void sendSoldProductNotificationToSeller(String sellerEmail, String sellerName, String productName) throws MessagingException ;
	void sendPurchaseSuccessToBuyer(String buyerEmail, String buyerName, String productName) throws MessagingException;
}
