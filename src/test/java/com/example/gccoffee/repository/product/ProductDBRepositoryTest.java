package com.example.gccoffee.repository.product;

import static org.assertj.core.api.Assertions.*;

import com.example.gccoffee.model.product.Category;
import com.example.gccoffee.model.product.Product;
import com.zaxxer.hikari.HikariDataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class ProductDBRepositoryTest {

    @TestConfiguration
    static class DataSourceConfig {
        @Bean
        public DataSource dataSource(){
            DataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:h2:mem:test;MODE=MySQL;DB_CLOSE_DELAY=-1")
                .driverClassName("org.h2.Driver")
                .username("sa")
                .password("")
                .type(HikariDataSource.class)
                .build();

            return dataSource;
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource){
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
            jdbcTemplate.getJdbcTemplate().execute("CREATE TABLE products"
                + "("
                + "    product_id VARCHAR(40) PRIMARY KEY,"
                + "    product_name VARCHAR(20) NOT NULL,"
                + "    category VARCHAR(50) NOT NULL,"
                + "    price bigint NOT NULL,"
                + "    description VARCHAR(500) DEFAULT NULL,"
                + "    created_at datetime(6) NOT NULL,"
                + "    updated_at datetime(6) DEFAULT NULL"
                + ")");
            return jdbcTemplate;
        }

        @Bean
        public ProductDBRepository productDBRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
            return new ProductDBRepository(namedParameterJdbcTemplate);
        }
    }

    @Autowired
    ProductDBRepository repository;

    Product product = new Product(UUID.randomUUID().toString(), "new_product", Category.COFFEE_BEAN_PACKAGE, 1000L);


    @Test
    @Order(1)
    @DisplayName("상품을 추가할 수 있다.")
    void insertTest() {
        //given
        repository.insert(product);

        //when
        List<Product> all = repository.findAll();

        //then
        assertThat(all.isEmpty()).isFalse();
    }

    @Test
    @Order(2)
    @DisplayName("상품을 이름으로 조회할 수 있다.")
    void findByNameTest() {
        //when
        Optional<Product> findByNameProduct = repository.findByName("new_product");

        //then
        assertThat(findByNameProduct.isEmpty()).isEqualTo(false);
    }

    @Test
    @Order(3)
    @DisplayName("상품 ID로 조회할 수 있다.")
    void findByIDTest() {
        //when
        Optional<Product> findByIdProduct = repository.findById(product.getProductId());

        //then
        assertThat(findByIdProduct.isEmpty()).isEqualTo(false);
    }
    @Test
    @Order(4)
    @DisplayName("상품 카테고리로 조회할 수 있다.")
    void findByCategoryTest() {
        //when
        List<Product> findByCategory = repository.findByCategory(Category.COFFEE_BEAN_PACKAGE);

        //then
        assertThat(findByCategory.isEmpty()).isEqualTo(false);
    }

    @Test
    @Order(5)
    @DisplayName("상품 카테고리로 조회할 수 있다.")
    void deleteTest() {
        //given
        repository.deleteAll();

        //when
        List<Product> all = repository.findAll();

        //then
        assertThat(all.isEmpty()).isEqualTo(true);
    }

}