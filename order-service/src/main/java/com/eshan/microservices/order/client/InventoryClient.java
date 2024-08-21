package com.eshan.microservices.order.client;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "inventory" , url = "http://localhost:8082")

public interface InventoryClient {


    @RequestMapping(method = RequestMethod.GET, value = "/api/inventory")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) ;

    default boolean fallbackMethod(String code, Integer quantity, Throwable throwable) {
        System.out.println("Cannot get inventory for skucode " + code + ", failure reason: " + throwable.getMessage());
        return false;
    }

}
