/*
ProductFactory.java
Author: Seymour Lawrence (230185991) */
package za.co.admatech.factory;

import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.ProductCategory;
import za.co.admatech.domain.enums.ProductType;
import za.co.admatech.util.Helper;

public class ProductFactory {
    public static Product createProduct(Long id,
                                        String productName,
                                        String productDescription,
                                        Money amount,
                                        ProductCategory category,
                                        ProductType productType)
    {
        if (Helper.isNullOrEmpty(productName) ||
                amount == null ||
                category == null) {
            throw new IllegalArgumentException("Product name, amount, and category must be valid");
        }
        if (!Helper.isValidProductName(productName)) {
        throw new IllegalArgumentException("Invalid product name");
        }
        return new Product.Builder()
                .setProductId(id)
                .setProductName(productName)
                .setProductDescription(productDescription)
                .setProductPriceAmount(amount)
                .setCategory(category)
                .setProductType(productType)
                .build();
    }
}