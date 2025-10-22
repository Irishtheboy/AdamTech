package za.co.admatech.domain;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import java.util.Base64;

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


    @Lob
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private byte[] imageData;


    @Transient
    private String imageBase64;

    public Product() {
    }

    protected Product(Builder builder) {
        this.productId = builder.productId;
        this.name = builder.name;
        this.description = builder.description;
        this.sku = builder.sku;
        this.price = builder.price;
        this.categoryId = builder.categoryId;
        this.imageData = builder.imageData;
        this.imageBase64 = builder.imageBase64;


        if (this.imageBase64 != null && !this.imageBase64.isEmpty()) {
            try {
                String base64Data = this.imageBase64;
                if (base64Data.contains(",")) {
                    base64Data = base64Data.split(",")[1];
                }
                this.imageData = Base64.getDecoder().decode(base64Data);
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid Base64 image data: " + e.getMessage());
                this.imageData = null;
            }
        }
    }

    // Getters and setters
    public Long getProductId() { return productId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getSku() { return sku; }
    public Money getPrice() { return price; }
    public String getCategoryId() { return categoryId; }
    public byte[] getImageData() { return imageData; }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getImageBase64() {
        // Convert imageData to Base64 when requested
        if (imageData != null && imageData.length > 0) {
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageData);
        }
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
        // Convert Base64 to imageData
        if (imageBase64 != null && !imageBase64.isEmpty()) {
            try {
                String base64Data = imageBase64;
                if (base64Data.contains(",")) {
                    base64Data = base64Data.split(",")[1];
                }
                this.imageData = Base64.getDecoder().decode(base64Data);
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid Base64 image data: " + e.getMessage());
                this.imageData = null;
            }
        }
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
        private byte[] imageData;
        private String imageBase64;

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

        public Builder setImageBase64(String imageBase64) {
            this.imageBase64 = imageBase64;
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
            this.imageBase64 = product.imageBase64;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}