package pl.com.bottega.inventory.ui;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.inventory.api.CommandGateway;
import pl.com.bottega.inventory.api.dtos.PurchaseProductResponseDto;
import pl.com.bottega.inventory.domain.commands.AddProductCommand;
import pl.com.bottega.inventory.domain.commands.PurchaseProductCommand;

import java.util.Map;

@RestController
public class InventoryController {

    private CommandGateway gateway;

    public InventoryController(CommandGateway gateway) {
        this.gateway = gateway;
    }

    @PostMapping("/inventory")
    public void add(@RequestBody AddProductCommand command){
        gateway.execute(command);
    }

    @PostMapping("/purchase")
    public PurchaseProductResponseDto purchase(@RequestBody Map<String, Long> products) {
        return gateway.execute(new PurchaseProductCommand(products));
    }
}
