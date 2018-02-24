package pl.com.bottega.inventory.domain.repositories;

import pl.com.bottega.inventory.domain.Product;
import java.util.Map;
import java.util.Optional;

public interface ProductRepository {


    void save(Product product);


    Map<String, Long>  getUnprocessables(Map<String, Long> products);

    boolean isPresent(String skuCode);

    Optional<Product> get(String skuCode);

    Product orElseUpdate(Optional<Product> skuCodeOptional, Product product);
}
