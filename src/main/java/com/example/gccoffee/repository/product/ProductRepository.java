package com.example.gccoffee.repository.product;

import com.example.gccoffee.model.product.Category;
import com.example.gccoffee.model.product.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Product insert(Product product);

    Optional<Product> findById(String productId);

    Optional<Product> findByName(String productName);

    List<Product> findByCategory(Category category);

    void deleteAll();

}
