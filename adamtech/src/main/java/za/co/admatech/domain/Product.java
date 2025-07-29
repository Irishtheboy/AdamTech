package za.co.admatech.domain;

import jakarta.persistence.*;
import za.co.admatech.domain.enums.InventoryStatus;
import za.co.admatech.domain.enums.PaymentStatus;
import za.co.admatech.domain.enums.ProductType;

@Entity
public class Product {
    @Id
    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String sku;

    @Embedded
    @Column(nullable = false)
    private Money price;

    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType productType;

    // Public no-arg constructor
    public Product() {}



    private Product(Builder builder) {
        this.productId = builder.productId;
        this.name = builder.name;
        this.description = builder.description;
        this.sku = builder.sku;
        this.price = builder.price;
        this.category = builder.category;
        this.productType = builder.productType;
    }

    public static class Builder {
        private String productId;
        private String name;
        private String description;
        private String sku;
        private Money price;
        private Category category;
        private ProductType productType;

        public Builder productId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder sku(String sku) {
            this.sku = sku;
            return this;
        }

        public Builder price(Money price) {
            this.price = price;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder productType(ProductType productType) {
            this.productType = productType;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }

    public Product copy() {
        return new Builder()
                .productId(this.productId)
                .name(this.name)
                .description(this.description)
                .sku(this.sku)
                .price(this.price)
                .category(this.category)
                .productType(this.productType)
                .build();
    }

    // Getters and setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public Money getPrice() { return price; }
    public void setPrice(Money price) { this.price = price; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public ProductType getProductType() { return productType; }
    public void setProductType(ProductType productType) { this.productType = productType; }
}