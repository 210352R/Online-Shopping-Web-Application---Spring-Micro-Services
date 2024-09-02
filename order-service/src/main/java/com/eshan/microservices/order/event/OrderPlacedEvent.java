package com.eshan.microservices.order.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderPlacedEvent {
    private String orderNumber;
    private String email;
}
