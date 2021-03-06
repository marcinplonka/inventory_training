package pl.com.bottega.inventory.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.inventory.domain.Product;
import pl.com.bottega.inventory.domain.commands.InvalidCommandException;
import pl.com.bottega.inventory.domain.repositories.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryProductRepository implements ProductRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public void save(Product product) {
        em.persist(product);
    }

    @Override
    @Transactional
    public void merge(Product product) {
        em.merge(product);
    }


    @Override
    public Map<String, Long> getUnprocessables(Map<String, Long> products) {
        Map<String, Long> unprocessables = new HashMap<>();
        for (String skuCode : products.keySet()) {
            Product current = em.find(Product.class, skuCode);
            if (current != null && em.find(Product.class, skuCode).getAmount() < products.get(skuCode)) {
                unprocessables.put(skuCode, products.get(skuCode));
            }
        }
        return unprocessables;
    }

    @Override
    public boolean skuIsPresent(String skuCode) {
        return getBySkuCode(skuCode).isPresent();
    }

    @Override
    public Optional<Product> getBySkuCode(String skuCode) {
        return Optional.ofNullable(em.find(Product.class, skuCode));
    }
}
