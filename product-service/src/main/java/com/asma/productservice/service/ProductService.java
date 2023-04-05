package com.asma.productservice.service;

import com.asma.productservice.dto.ProductCreatedDto;
import com.asma.productservice.dto.ProductDto;
import com.asma.productservice.model.Product;
import com.asma.productservice.publisher.ProductEventPublisher;
import com.asma.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
    @Autowired
    private ProductEventPublisher productEventPublisher;
    @Autowired
    private ProductRepository productRepository;
    public Product save(ProductDto productDto) {
        Product productToCreate = new Product(productDto.getId(), productDto.getLibelle(), productDto.getPrice());
        Product createdProduct = productRepository.save(productToCreate);
        ProductCreatedDto productCreatedDto = new ProductCreatedDto(createdProduct.getId(), productDto.getQuantity());
        productEventPublisher.publishProductEvent(productCreatedDto);
        return  createdProduct;
    }

}
