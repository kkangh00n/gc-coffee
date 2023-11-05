package com.example.gccoffee.repository.product;

import static com.example.gccoffee.repository.JdbcUtils.toLocalDateTime;

import com.example.gccoffee.model.product.Category;
import com.example.gccoffee.model.product.Product;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDBRepository implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductDBRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("select * from products", productRowMapper);
    }

    @Override
    public Product insert(Product product) {
        int update = jdbcTemplate.update(
            "insert into products(product_id, product_name, category, price, description, created_at, updated_at) VALUES(:productId, :productName, :category, :price, :description, :createdAt, :updatedAt)",
            toParamMap(product));
        if(update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return product;
    }

    @Override
    public Optional<Product> findById(String productId) {
        try{
            return Optional.of(
                jdbcTemplate.queryForObject("select * from products where product_id = :productId", Collections.singletonMap("productId", productId), productRowMapper)
            );
        } catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findByName(String productName) {
        try{
            return Optional.of(
                jdbcTemplate.queryForObject("select * from products where product_name = :productName", Collections.singletonMap("productName", productName), productRowMapper)
            );
        } catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return jdbcTemplate.query("select * from products where category = :category",
            Collections.singletonMap("category", category.toString()),
            productRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from products", Collections.emptyMap());
    }

    private static final RowMapper<Product> productRowMapper= (resultSet, i) -> {
        String productId = resultSet.getString("product_id");
        String productName = resultSet.getString("product_name");
        Category category = Category.valueOf(resultSet.getString("category"));
        long price = resultSet.getLong("price");
        String description = resultSet.getString("description");
        LocalDateTime createAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

        return new Product(productId, productName, category, price, description, createAt, updatedAt);
    };

    private static Map<String, Object> toParamMap(Product product){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("productId", product.getProductId());
        paramMap.put("productName", product.getProductName());
        paramMap.put("category", product.getCategory().toString());
        paramMap.put("price", product.getPrice());
        paramMap.put("description", product.getDescription());
        paramMap.put("createdAt", product.getCreateAt());
        paramMap.put("updatedAt", product.getUpdatedAt());
        return paramMap;
    }
}
