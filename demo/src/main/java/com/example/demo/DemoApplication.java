package com.example.demo;

import com.example.demo.model.NotificationType;
import com.example.demo.service.RateLimiterService;

import java.time.LocalDateTime;
import com.example.demo.Notification;


public class DemoApplication {

	public static void main(String[] args) {
		RateLimiterService rateLimiterService = new RateLimiterService();

		Notification notification1 = new Notification(NotificationType.STATUS, "user@example.com", LocalDateTime.now());
		Notification notification2 = new Notification(NotificationType.NEWS, "user@example.com", LocalDateTime.now());

		sendNotification(rateLimiterService, notification1);
		sendNotification(rateLimiterService, notification2);

		// Simulate sending multiple notifications to test rate limiting
		for (int i = 0; i < 5; i++) {
			sendNotification(rateLimiterService, notification1);
		}
	}

	private static void sendNotification(RateLimiterService rateLimiterService, com.example.demo.Notification notification) {
		if (rateLimiterService.canSendNotification(notification.getRecipient(), notification.getType())) {
			System.out.println("Notification sent: " + notification);
		} else {
			System.out.println("Rate limit exceeded for: " + notification);
		}
	}
}
