package pl.com.bottega.inventory.api.handlers;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.inventory.domain.Product;
import pl.com.bottega.inventory.domain.commands.AddProductCommand;
import pl.com.bottega.inventory.domain.commands.Command;
import pl.com.bottega.inventory.domain.commands.Validatable;
import pl.com.bottega.inventory.domain.repositories.ProductRepository;
import pl.com.bottega.inventory.infrastructure.PurchaseMaker;

@Component
public class AddProductHandler implements Handler<AddProductCommand, Void> {

    private ProductRepository productRepository;

    public AddProductHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Void handle(AddProductCommand command) {
        productRepository.save(new Product(command));
        return null;
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return AddProductCommand.class;
    }
}
