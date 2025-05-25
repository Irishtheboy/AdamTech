/*
 * Product.java
 * Product Class
 * Author: Seymour Lawrence (230185991)
 * Date: 11 May 2025
 */
package za.co.admatech.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {

    @Id
    private String productId;
    private String name;
    private String description;
    private String sku;
    private int priceAmount;
    private String priceCurrency;
    private String categoryId;

    public Product() {
    }

    protected Product(Builder builder) {
        this.productId = builder.productId;
        this.name = builder.name;
        this.description = builder.description;
        this.sku = builder.sku;
        this.priceAmount = builder.price != null ? builder.price.getAmount() : 0;
        this.priceCurrency = builder.price != null ? builder.price.getCurrency() : null;
        this.categoryId = builder.categoryId;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSku() {
        return sku;
    }

    public Money getPrice() {
        return new Money.Builder()
                .setAmount(priceAmount)
                .setCurrency(priceCurrency)
                .build();
    }

    public String getCategoryId() {
        return categoryId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sku='" + sku + '\'' +
                ", price=" + getPrice() +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }

    public static class Builder {
        private String productId;
        private String name;
        private String description;
        private String sku;
        private Money price;
        private String categoryId;

        public Builder setProductId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setSku(String sku) {
            this.sku = sku;
            return this;
        }

        public Builder setPrice(Money price) {
            this.price = price;
            return this;
        }

        public Builder setCategoryId(String categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder copy(Product product) {
            this.productId = product.productId;
            this.name = product.name;
            this.description = product.description;
            this.sku = product.sku;
            this.price = product.getPrice();
            this.categoryId = product.categoryId;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}