package com.example.gccoffee.service;

import com.example.gccoffee.model.product.Category;
import com.example.gccoffee.model.product.Product;
import java.util.List;

public interface ProductService {

    List<Product> getProductsByCategory(Category category);

    List<Product> getAllProducts();

    Product createProduct(String productName, Category category, long price);

    Product createProduct(String productName, Category category, long price, String description);

}
