package pl.com.bottega.inventory.api.handlers;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.inventory.domain.Product;
import pl.com.bottega.inventory.domain.commands.AddProductCommand;
import pl.com.bottega.inventory.domain.commands.Command;
import pl.com.bottega.inventory.domain.repositories.ProductRepository;

@Component
public class AddProductHandler implements Handler<AddProductCommand, Void> {

    private ProductRepository productRepository;

    public AddProductHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Void handle(AddProductCommand command) {
        Product current = new Product(command);
        if(productRepository.skuIsPresent(current.getSkuCode())) {
            Product updated = current.getIncreased(current);
            productRepository.merge(updated);
        }else {
            productRepository.save(current);
        }
        return null;
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return AddProductCommand.class;
    }
}
