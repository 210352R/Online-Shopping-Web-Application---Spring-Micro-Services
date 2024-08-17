package com.eshan.microservices.product.service;

import com.eshan.microservices.product.dto.ProductRequest;
import com.eshan.microservices.product.model.Product;
import com.eshan.microservices.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .id(productRequest.id())
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product created sucessfully with id: {}", product.getId());
    }




}

