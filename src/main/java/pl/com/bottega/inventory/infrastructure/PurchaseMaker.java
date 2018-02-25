package pl.com.bottega.inventory.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.inventory.api.dtos.PurchaseProductResponseDto;
import pl.com.bottega.inventory.domain.Product;
import pl.com.bottega.inventory.domain.commands.InvalidCommandException;
import pl.com.bottega.inventory.domain.commands.PurchaseProductCommand;
import pl.com.bottega.inventory.domain.commands.Validatable;
import pl.com.bottega.inventory.domain.repositories.ProductRepository;

import javax.transaction.Transactional;
import java.util.Map;

@Component
public class PurchaseMaker {

    private ProductRepository repository;
    private Validatable.ValidationErrors errors;

    public PurchaseMaker(ProductRepository repository, Validatable.ValidationErrors errors) {
        this.repository = repository;
        this.errors = errors;
    }

    public void validatePresenceInRepo(PurchaseProductCommand command) {
        for (String skuCode : command.getProducts().keySet()) {
            if(!repository.skuIsPresent(skuCode)) {
                errors.add(skuCode, "no such sku");
            }
        }
    }


    public PurchaseProductResponseDto run(PurchaseProductCommand command) {
        boolean success;
        Map<String, Long> unprocessables = repository.getUnprocessables(command.getProducts());
        success = unprocessables.isEmpty();

        if (success) {
            decreaseProductsAmount(command.getProducts());
            return new PurchaseProductResponseDto(success, command.getProducts());
        }
        else {
            return new PurchaseProductResponseDto(success, unprocessables);
        }
    }

    @Transactional
    void decreaseProductsAmount(Map<String, Long> products) {
        for (String skuCode : products.keySet()) {
            Product current = new Product(skuCode, products.get(skuCode));
            if(repository.skuIsPresent(skuCode)) {
                Product updated = repository.getBySkuCode(skuCode).get().getDecreased(current);
                repository.merge(updated);
            }
        }
    }
}
