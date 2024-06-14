package com.example.demo;

import com.example.demo.model.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String recipient;
    private LocalDateTime timestamp;

    public Notification(NotificationType type, String recipient, LocalDateTime timestamp) {
        this.type = type;
        this.recipient = recipient;
        this.timestamp = timestamp;
    }
}
