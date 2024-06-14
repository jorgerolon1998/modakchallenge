package com.example.demo.controller;

import com.example.demo.Notification;
import com.example.demo.service.RateLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private RateLimiterService rateLimiterService;

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody Notification notification) {
        if (rateLimiterService.canSendNotification(notification.getRecipient(), notification.getType())) {
            return ResponseEntity.ok("Notification sent");
        } else {
            return ResponseEntity.status(429).body("Rate limit exceeded");
        }
    }
}
