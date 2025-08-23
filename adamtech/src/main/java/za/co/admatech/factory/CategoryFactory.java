package za.co.admatech.factory;


import za.co.admatech.domain.Category;
import za.co.admatech.util.Helper;

public class CategoryFactory {
    public static Category createCategory(String categoryId, String parentCategoryId, String name, Category parentCategory) {
        if (Helper.isNullOrEmpty(categoryId) || Helper.isNullOrEmpty(name)) {
            return null;
        }
        return new Category.Builder()
                .categoryId(categoryId)
                .parentCategoryId(parentCategoryId)
                .name(name)
                .parentCategory(parentCategory)
                .build();
    }

    public static Category createCategory(String name, Category parentCategory) {
        return createCategory(Helper.generateId(), null, name, parentCategory);
    }

    public static Category createCategory(String name) {
        return createCategory(Helper.generateId(), null, name, null);
    }
}