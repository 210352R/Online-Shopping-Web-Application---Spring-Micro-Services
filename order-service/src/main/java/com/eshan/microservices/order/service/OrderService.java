package com.eshan.microservices.order.service;

import com.eshan.microservices.order.client.InventoryClient;
import com.eshan.microservices.order.dto.OrderRequest;
import com.eshan.microservices.order.dto.OrderResponse;
import com.eshan.microservices.order.model.Order;
import com.eshan.microservices.order.repository.OrderRepository;
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
            return new OrderResponse(order.getId(), order.getOrderNumber(), order.getSkuCode(), order.getPrice(), order.getQuantity());
        }



    }
}
