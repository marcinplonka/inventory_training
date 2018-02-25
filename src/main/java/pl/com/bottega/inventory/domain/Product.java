package pl.com.bottega.inventory.domain;

import pl.com.bottega.inventory.domain.commands.AddProductCommand;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    private String skuCode;
    private Long amount;

    public Product() {
    }

    public Product(String skuCode, Long amount) {
        this.skuCode = skuCode;
        this.amount = amount;
    }

    public Product(AddProductCommand cmd) {
        this.skuCode = cmd.getSkuCode();
        this.amount = cmd.getAmount();
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Product getDecreased(Product other) {
        return new Product(skuCode, amount - other.amount);
    }

    public Product getIncreased(Product other) {
        return new Product(skuCode, amount + other.amount);
    }
}
