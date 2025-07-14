/*
 * Product.java
 * Product Class
 * Author: Seymour Lawrence (230185991)
 * Date: 11 May 2025
 */
package za.co.admatech.domain;

import jakarta.persistence.*;
import za.co.admatech.domain.enums.ProductType;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private String productDescription;
    @Embedded
    private Money productPriceAmount;
    private String productCategory;
    @Enumerated(EnumType.STRING)
    private ProductType productType;


    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

/*
    public Money getPrice() {
        return new Money.Builder()
                .setAmount(priceAmount)
                .setCurrency(priceCurrency)
                .build();
    }
 */

    public String getProductCategory() {
        return productCategory;
    }

    public Money getProductPriceAmount() {
        return productPriceAmount;
    }
    public ProductType getProductType() {
        return productType;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productPriceAmount=" + productPriceAmount +
                ", productCategory='" + productCategory + '\'' +
                ", productType=" + productType +
                '}';
    }

    protected Product(){}

    protected Product(Builder builder) {
        this.productId = builder.productId;
        this.productName = builder.productName;
        this.productDescription = builder.productDescription;
        this.productPriceAmount = builder.productPriceAmount;
        this.productCategory = builder.productCategory;
        this.productType = builder.productType;
    }
    public static class Builder {
        private Long productId;
        private String productName;
        private String productDescription;
        private Money productPriceAmount;
        private String productCategory;
        private ProductType productType;

        public Builder setProductId(Long productId) {
            this.productId = productId;
            return this;
        }
        public Builder setProductName(String productName) {
            this.productName = productName;
            return this;
        }
        public Builder setProductDescription(String productDescription) {
            this.productDescription = productDescription;
            return this;
        }
        public Builder setProductPriceAmount(Money productPriceAmount) {
            this.productPriceAmount = productPriceAmount;
            return this;
        }
        public Builder setProductCategory(String productCategory) {
            this.productCategory = productCategory;
            return this;
        }
        public Builder setProductType(ProductType productType) {
            this.productType = productType;
            return this;
        }

        public Builder copy(Product product) {
            this.productId = product.productId;
            this.productName = product.productName;
            this.productDescription = product.productDescription;
            this.productPriceAmount = product.productPriceAmount;
            this.productCategory = product.productCategory;
            this.productType = product.productType;
            return this;
        }
        public Product build() {
            return new Product(this);
        }
    }
}