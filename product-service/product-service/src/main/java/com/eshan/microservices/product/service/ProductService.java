package com.eshan.microservices.product.service;

import com.eshan.microservices.product.dto.ProductRequest;
import com.eshan.microservices.product.dto.ProductResponse;
import com.eshan.microservices.product.model.Product;
import com.eshan.microservices.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .id(productRequest.id())
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product created sucessfully with id: {}", product.getId());
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }

    public ProductResponse getProduct(String id){
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice())).toList();
    }




}

