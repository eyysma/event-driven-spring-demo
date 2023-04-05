package com.asma.productservice.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String libelle;
    private Double price;
    private int quantity;
}
