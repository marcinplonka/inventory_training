package pl.com.bottega.inventory.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.inventory.domain.Product;
import pl.com.bottega.inventory.domain.commands.AddProductCommand;
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
        if (isPresent(product.getSkuCode())) {
            product.updateAmount(em.find(Product.class, product.getSkuCode()).getAmount());
            em.merge(product);
            em.flush();
        }
        else {
            em.persist(product);
            em.flush();
        }
    }


    @Override
    public Map<String, Long> getUnprocessables(Map<String, Long> products) {
        Map<String, Long> unprocessables = new HashMap<>();
        for (String skuCode : products.keySet()) {
            if(em.find(Product.class, skuCode).getAmount() < products.get(skuCode)) {
                unprocessables.put(skuCode, products.get(skuCode));
            }
        }
        return unprocessables;
    }

    @Override
    public boolean isPresent(String skuCode) {
        return this.get(skuCode).isPresent();
    }

    @Override
    public Optional<Product> get(String id) {
        return Optional.ofNullable(em.find(Product.class, id));
    }





    @Override
    public Product orElseUpdate(Optional<Product> skuCodeOptional, Product product) {

        if(skuCodeOptional.isPresent()) {
            Long newAmount = product.getAmount() + skuCodeOptional.get().getAmount();
            product.setAmount(newAmount);
            return product;
        }
        else {
            return product;
        }
    }
}
