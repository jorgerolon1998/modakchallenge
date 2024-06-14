package com.example.demo.service;

import com.example.demo.model.NotificationType;
import com.example.demo.model.RateLimitRule;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RateLimiterService {

    private final ConcurrentHashMap<String, ConcurrentHashMap<NotificationType, ConcurrentLinkedQueue<LocalDateTime>>> notifications = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<NotificationType, RateLimitRule> rateLimitRules = new ConcurrentHashMap<>();

    public RateLimiterService() {
        rateLimitRules.put(NotificationType.STATUS, new RateLimitRule(NotificationType.STATUS, 2, Duration.ofMinutes(1)));
        rateLimitRules.put(NotificationType.NEWS, new RateLimitRule(NotificationType.NEWS, 1, Duration.ofDays(1)));
        rateLimitRules.put(NotificationType.MARKETING, new RateLimitRule(NotificationType.MARKETING, 3, Duration.ofHours(1)));
    }

    public boolean canSendNotification(String recipient, NotificationType type) {
        RateLimitRule rule = rateLimitRules.get(type);
        if (rule == null) {
            return true; 
        }

        notifications.putIfAbsent(recipient, new ConcurrentHashMap<>());
        ConcurrentHashMap<NotificationType, ConcurrentLinkedQueue<LocalDateTime>> recipientNotifications = notifications.get(recipient);
        recipientNotifications.putIfAbsent(type, new ConcurrentLinkedQueue<>());

        ConcurrentLinkedQueue<LocalDateTime> timestamps = recipientNotifications.get(type);
        LocalDateTime now = LocalDateTime.now();

        while (!timestamps.isEmpty() && timestamps.peek().isBefore(now.minus(rule.getPeriod()))) {
            timestamps.poll();
        }

        if (timestamps.size() < rule.getMaxRequests()) {
            timestamps.add(now);
            return true;
        }

        return false;
    }
}
