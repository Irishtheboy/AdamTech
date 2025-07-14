package za.co.admatech.factory;

import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.ProductType;

public class ProductFactory {
    public static Product createProduct(
            Long productId,
            String productName,
            Money productPriceAmount,
            String productCategory,
            ProductType productType
    ) {
        return new Product.Builder()
                .setProductId(productId)
                .setProductName(productName)
                .setProductPriceAmount(productPriceAmount)
                .setProductCategory(productCategory)
                .setProductType(productType)
                .build();
    }
}