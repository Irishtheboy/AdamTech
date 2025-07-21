/*





Category.java



Category Class



Author: Unknown



Date: 11 May 2025 */ package za.co.admatech.domain;

import jakarta.persistence.*; import jakarta.validation.constraints.NotNull; import za.co.admatech.domain.enums.ProductCategory;

@Entity @Table(name = "category") public class Category { @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ProductCategory productCategory;

    @NotNull
    private String productName;

    public Long getId() {
        return id;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", productCategory=" + productCategory +
                ", productName='" + productName + '\'' +
                '}';
    }

    protected Category() {
    }

    protected Category(Builder builder) {
        this.id = builder.id;
        this.productCategory = builder.productCategory;
        this.productName = builder.productName;
    }

    public static class Builder {
        private Long id;
        private ProductCategory productCategory;
        private String productName;

        public Builder setID(Long id) {
            this.id = id;
            return this;
        }

        public Builder setProductCategory(ProductCategory productCategory) {
            this.productCategory = productCategory;
            return this;
        }

        public Builder setProductName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder copy(Category category) {
            this.id = category.getId();
            this.productCategory = category.getProductCategory();
            this.productName = category.getProductName();
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }

}