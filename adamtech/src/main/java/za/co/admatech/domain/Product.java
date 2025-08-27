/*
 * Product.java
 * Product Class
 * Author: Seymour Lawrence (230185991)
 * Date: 11 May 2025
 */
package za.co.admatech.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String name;

    private String description;

    private String sku;

    @Embedded
    private Money price;

    private String categoryId;

    public Product() {
    }

    protected Product(Builder builder) {
        this.productId = builder.productId;
        this.name = builder.name;
        this.description = builder.description;
        this.sku = builder.sku;
        this.price = builder.price;
        this.categoryId = builder.categoryId;
    }

    public Long getProductId() {
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
        return price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sku='" + sku + '\'' +
                ", price=" + price +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }

    public static class Builder {
        private Long productId;
        private String name;
        private String description;
        private String sku;
        private Money price;
        private String categoryId;

        public Builder setProductId(Long productId) {
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
            this.price = product.price;
            this.categoryId = product.categoryId;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}