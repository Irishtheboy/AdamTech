package za.co.admatech.factory;


import za.co.admatech.domain.Category;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.ProductType;
import za.co.admatech.util.Helper;

public class ProductFactory {
    public static Product createProduct(String productId, String name, String description, String sku, Money price, Category category, ProductType productType) {
        if (Helper.isNullOrEmpty(productId) || Helper.isNullOrEmpty(name) || Helper.isNullOrEmpty(description) || Helper.isNullOrEmpty(sku) || price == null || category == null || productType == null) {
            return null;
        }
        return new Product.Builder()
                .productId(productId)
                .name(name)
                .description(description)
                .sku(sku)
                .price(price)
                .category(category)
                .productType(productType)
                .build();
    }

    public static Product createProduct(String name, String description, String sku, Money price, Category category, ProductType productType) {
        return createProduct(Helper.generateId(), name, description, sku, price, category, productType);
    }
}