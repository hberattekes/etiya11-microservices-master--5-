package com.etiya.productservice.config;
import com.etiya.productservice.entities.Product;
import com.etiya.productservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
@Configuration
public class OrderEventConsumer {
    @Bean
    CommandLineRunner seedProducts(ProductRepository repository) {
        return args -> {
            if (repository.count() > 0) return;
            Product p = new Product();
            p.setName("Laptop");
            p.setUnitPrice(new BigDecimal("15000.00"));
            p.setStock(100);
            p.setDescription("Demo product");
            repository.save(p);
        };
    }
}