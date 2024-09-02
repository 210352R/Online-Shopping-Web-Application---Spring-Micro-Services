package com.eshan.microservices.notification.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor

public class OrderPlacedEvent {
    private final String orderId;
    private final String email;

}
