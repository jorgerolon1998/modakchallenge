package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;

@AllArgsConstructor
@Data
public class RateLimitRule {
    private NotificationType type;
    private int maxRequests;
    private Duration period;

}
