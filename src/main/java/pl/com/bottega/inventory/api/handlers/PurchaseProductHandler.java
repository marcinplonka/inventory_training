package pl.com.bottega.inventory.api.handlers;

import org.springframework.stereotype.Component;
import pl.com.bottega.inventory.api.dtos.PurchaseProductResponseDto;
import pl.com.bottega.inventory.domain.commands.Command;
import pl.com.bottega.inventory.domain.commands.InvalidCommandException;
import pl.com.bottega.inventory.domain.commands.PurchaseProductCommand;
import pl.com.bottega.inventory.domain.commands.Validatable;
import pl.com.bottega.inventory.infrastructure.PurchaseMaker;

@Component
public class PurchaseProductHandler implements Handler<PurchaseProductCommand, PurchaseProductResponseDto> {


    private PurchaseMaker purchaseMaker;
    private Validatable.ValidationErrors errors;

    public PurchaseProductHandler(PurchaseMaker purchaseMaker, Validatable.ValidationErrors errors) {
        this.purchaseMaker = purchaseMaker;
        this.errors = errors;
    }

    @Override
    public PurchaseProductResponseDto handle(PurchaseProductCommand command) {
        purchaseMaker.validatePresenceInRepo(command);
        if(!errors.any())
            return purchaseMaker.run(command);
        throw new InvalidCommandException(errors);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return PurchaseProductCommand.class;
    }
}
