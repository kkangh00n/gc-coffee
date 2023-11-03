package com.example.gccoffee.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
    private final String productId;
    private String productName;
    private Category category;
    private long price;
    private String description;
    private final LocalDateTime createAt;
    private LocalDateTime updatedAt;

    public Product(String productId, String productName, Category category, long price){
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.createAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void setProductName(String productName) {
        this.productName = productName;
        this.updatedAt = LocalDateTime.now();
    }

    public void setCategory(Category category) {
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

    public void setPrice(long price) {
        this.price = price;
        this.updatedAt = LocalDateTime.now();
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
}
