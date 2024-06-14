package com.example.demo;

import com.example.demo.model.NotificationType;
import com.example.demo.service.RateLimiterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import com.example.demo.Notification;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RateLimiterServiceTest {

    private RateLimiterService rateLimiterService;

    @BeforeEach
    public void setUp() {
        rateLimiterService = new RateLimiterService();
    }

    @Test
    public void testCanSendNotification() {
        String recipient = "test@example.com";

        // Test STATUS notifications
        Notification statusNotification = new Notification(NotificationType.STATUS, recipient, LocalDateTime.now());
        assertTrue(rateLimiterService.canSendNotification(recipient, NotificationType.STATUS));
        rateLimiterService.canSendNotification(recipient, NotificationType.STATUS); // Send second notification
        assertFalse(rateLimiterService.canSendNotification(recipient, NotificationType.STATUS)); // Exceeds limit

        // Test NEWS notifications
        Notification newsNotification = new Notification(NotificationType.NEWS, recipient, LocalDateTime.now());
        assertTrue(rateLimiterService.canSendNotification(recipient, NotificationType.NEWS));
        assertFalse(rateLimiterService.canSendNotification(recipient, NotificationType.NEWS)); // Exceeds limit

        // Test MARKETING notifications
        Notification marketingNotification = new Notification(NotificationType.MARKETING, recipient, LocalDateTime.now());
        assertTrue(rateLimiterService.canSendNotification(recipient, NotificationType.MARKETING));
        rateLimiterService.canSendNotification(recipient, NotificationType.MARKETING); // Send second notification
        rateLimiterService.canSendNotification(recipient, NotificationType.MARKETING); // Send third notification
        assertFalse(rateLimiterService.canSendNotification(recipient, NotificationType.MARKETING)); // Exceeds limit
    }
}
