package com.asma.productservice.controller;

import com.asma.productservice.dto.ProductDto;
import com.asma.productservice.model.Product;
import com.asma.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;


    @PostMapping(value = "/products")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto productDto){
       return new ResponseEntity<Product>(productService.save(productDto),HttpStatus.CREATED);

    }
}
