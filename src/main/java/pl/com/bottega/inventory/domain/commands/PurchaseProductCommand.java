package pl.com.bottega.inventory.domain.commands;

import java.util.Map;

public class PurchaseProductCommand implements Validatable, Command {

    Map<String, Long> products;

    @Override
    public void validate(ValidationErrors errors) {
        for (String skuCode : products.keySet())
        {
            validatePresenceOf(skuCode, "skuCode", errors);
            validateAmount(products.get(skuCode), "amount", errors);
        }
    }

    public PurchaseProductCommand() {
    }

    public PurchaseProductCommand(Map<String, Long> products) {
        this.products = products;
    }

    public Map<String, Long> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Long> products) {
        this.products = products;
    }
}
