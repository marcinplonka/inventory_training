package pl.com.bottega.inventory.api.dtos;

import java.util.Map;

public class PurchaseProductResponseDto {
    private boolean success;
    private Map<String, Long> missingProducts;
    private Map<String, Long> purchasedProducts;

    public PurchaseProductResponseDto() {
    }

    public PurchaseProductResponseDto(boolean success, Map<String, Long> products) {
        this.success = success;
        if(success) {
            this.purchasedProducts = products;
        }
        else {
            this.missingProducts = products;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Long> getMissingProducts() {
        return missingProducts;
    }

    public void setMissingProducts(Map<String, Long> missingProducts) {
        this.missingProducts = missingProducts;
    }

    public Map<String, Long> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(Map<String, Long> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }
}