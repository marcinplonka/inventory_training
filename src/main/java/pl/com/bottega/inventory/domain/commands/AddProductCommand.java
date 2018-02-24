package pl.com.bottega.inventory.domain.commands;


public class AddProductCommand implements Validatable, Command {

    private String skuCode;
    private Long amount;

    @Override
    public void validate(ValidationErrors errors) {
        validatePresenceOf(skuCode, "SkuCode", errors);
        validateAmount(amount, "amount", errors);
    }

    public AddProductCommand() {
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
}
