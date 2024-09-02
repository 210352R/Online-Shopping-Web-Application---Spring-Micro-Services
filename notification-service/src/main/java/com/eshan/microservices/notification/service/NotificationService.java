package com.eshan.microservices.notification.service;

import com.eshan.microservices.notification.order.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class NotificationService {

    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent){
        System.out.println("Notification sent to " + orderPlacedEvent.getEmail() + " for order id " + orderPlacedEvent.getOrderId());
    }

}
