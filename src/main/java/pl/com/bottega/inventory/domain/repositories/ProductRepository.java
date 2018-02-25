package pl.com.bottega.inventory.domain.repositories;

import pl.com.bottega.inventory.domain.Product;
import java.util.Map;
import java.util.Optional;

public interface ProductRepository {


    void save(Product product);

    void merge(Product product);


    Map<String, Long>  getUnprocessables(Map<String, Long> products);

    boolean skuIsPresent(String skuCode);

    Optional<Product> getBySkuCode(String skuCode);
}
