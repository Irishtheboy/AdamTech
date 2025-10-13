package za.co.admatech.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "sku")
    private String sku;

    @Embedded
    private Money price;

    @Column(name = "category_id")
    private String categoryId;

    // ✅ Add this field for image
    @Lob
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private byte[] imageData;

    public Product() {
    }

    protected Product(Builder builder) {
        this.productId = builder.productId;
        this.name = builder.name;
        this.description = builder.description;
        this.sku = builder.sku;
        this.price = builder.price;
        this.categoryId = builder.categoryId;
        this.imageData = builder.imageData; // set image from builder
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

    public byte[] getImageData() {
        return imageData;
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
                ", imageData=" + (imageData != null ? imageData.length + " bytes" : "null") +
                '}';
    }

    public static class Builder {
        private Long productId;
        private String name;
        private String description;
        private String sku;
        private Money price;
        private String categoryId;
        private byte[] imageData; // add to builder

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

        public Builder setImageData(byte[] imageData) {
            this.imageData = imageData;
            return this;
        }

        public Builder copy(Product product) {
            this.productId = product.productId;
            this.name = product.name;
            this.description = product.description;
            this.sku = product.sku;
            this.price = product.price;
            this.categoryId = product.categoryId;
            this.imageData = product.imageData;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
