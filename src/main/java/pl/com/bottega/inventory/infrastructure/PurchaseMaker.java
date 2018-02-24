package pl.com.bottega.inventory.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.inventory.api.dtos.PurchaseProductResponseDto;
import pl.com.bottega.inventory.domain.Product;
import pl.com.bottega.inventory.domain.commands.PurchaseProductCommand;
import pl.com.bottega.inventory.domain.commands.Validatable;
import pl.com.bottega.inventory.domain.repositories.ProductRepository;
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
            if(!repository.isPresent(skuCode))
                errors.add("skuCode", "no such sku");
        }
    }


    public PurchaseProductResponseDto run(PurchaseProductCommand command) {
        boolean success;
        Map<String, Long> unprocessables = repository.getUnprocessables(command.getProducts());
        success = unprocessables.isEmpty();

        if (success) {
            this.updateProducts(command.getProducts());
            return new PurchaseProductResponseDto(success, command.getProducts());

        }
        else {
            return new PurchaseProductResponseDto(success, unprocessables);
        }
    }

    private void updateProducts(Map<String, Long> products) {
        for (String skuCode : products.keySet()) {
            Product product = repository.get(skuCode).get();
            product.decreaseAmount(products.get(skuCode));
            repository.save(product);
        }
    }
}
