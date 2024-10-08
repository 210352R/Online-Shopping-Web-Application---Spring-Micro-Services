package com.eshan.microservices.order.service;

import com.eshan.microservices.order.client.InventoryClient;
import com.eshan.microservices.order.dto.OrderRequest;
import com.eshan.microservices.order.dto.OrderResponse;
import com.eshan.microservices.order.event.OrderPlacedEvent;
import com.eshan.microservices.order.model.Order;
import com.eshan.microservices.order.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public OrderResponse placeOrder(OrderRequest orderRequest) {

        if (!inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity())) {
            throw new RuntimeException("Product "+orderRequest.skuCode() +"  is out of stock");
        }
        else{
            log.info("Placing order: {}", orderRequest);
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price().multiply(BigDecimal.valueOf(orderRequest.quantity())));
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            orderRepository.save(order);

            // Add kafka Topic -------------------- OrderPlaced Event
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(), orderRequest.userDetails().email());
            log.info("Sending OrderPlacedEvent: {}", orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("OrderPlacedEvent sent successfully");


            return new OrderResponse(order.getId(), order.getOrderNumber(), order.getSkuCode(), order.getPrice(), order.getQuantity());
        }



    }
}
