package com.eshan.microservices.order.controller;

import com.eshan.microservices.order.dto.OrderRequest;
import com.eshan.microservices.order.dto.OrderResponse;
import com.eshan.microservices.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse addOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Adding order");
        return orderService.placeOrder(orderRequest);
    }

}
