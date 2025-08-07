package za.co.admatech.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(nullable = false)
    private String categoryId;

    @Column(nullable = false)
    private String parentCategoryId;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parentCategoryId", referencedColumnName = "categoryId", insertable = false, updatable = false)
    private Category parentCategory;

    // Public no-arg constructor
    public Category() {}



    private Category(Builder builder) {
        this.categoryId = builder.categoryId;
        this.parentCategoryId = builder.parentCategoryId;
        this.name = builder.name;
        this.parentCategory = builder.parentCategory;
    }

    public static class Builder {
        private String categoryId;
        private String parentCategoryId;
        private String name;
        private Category parentCategory;

        public Builder categoryId(String categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder parentCategoryId(String parentCategoryId) {
            this.parentCategoryId = parentCategoryId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder parentCategory(Category parentCategory) {
            this.parentCategory = parentCategory;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }

    public Category copy() {
        return new Builder()
                .categoryId(this.categoryId)
                .parentCategoryId(this.parentCategoryId)
                .name(this.name)
                .parentCategory(this.parentCategory)
                .build();
    }

    // Getters and setters
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
    public String getParentCategoryId() { return parentCategoryId; }
    public void setParentCategoryId(String parentCategoryId) { this.parentCategoryId = parentCategoryId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Category getParentCategory() { return parentCategory; }
    public void setParentCategory(Category parentCategory) { this.parentCategory = parentCategory; }
}