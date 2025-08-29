package za.co.admatech.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.admatech.domain.Category;
import za.co.admatech.repository.CategoryRepository;
import za.co.admatech.service.category_domain_service.CategoryService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceUnitTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category testCategory;

    @BeforeEach
    void setUp() {
        testCategory = new Category();
        testCategory.setCategoryId("1");
        testCategory.setName("Electronics");
    }

    @Test
    void create_ValidCategory_ReturnsCategory() {
        when(categoryRepository.save(testCategory)).thenReturn(testCategory);

        Category result = categoryService.create(testCategory);

        assertNotNull(result);
        assertEquals(testCategory, result);
        verify(categoryRepository).save(testCategory);
    }

    @Test
    void create_NullCategory_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> categoryService.create(null));
        assertEquals("Category data is null", exception.getMessage());
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void read_ValidStringId_ReturnsCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));

        Category result = categoryService.read("1");

        assertNotNull(result);
        assertEquals(testCategory, result);
        verify(categoryRepository).findById(1L);
    }

    @Test
    void read_NonExistingId_ThrowsEntityNotFoundException() {
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> categoryService.read("999"));
        assertEquals("Category with ID 999 not found", exception.getMessage());
        verify(categoryRepository).findById(999L);
    }

    @Test
    void read_InvalidIdFormat_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> categoryService.read("invalid"));
        assertEquals("Invalid category ID format: invalid", exception.getMessage());
        verify(categoryRepository, never()).findById(anyLong());
    }

    @Test
    void update_ValidCategory_ReturnsUpdatedCategory() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(categoryRepository.save(testCategory)).thenReturn(testCategory);

        Category result = categoryService.update(testCategory);

        assertNotNull(result);
        assertEquals(testCategory, result);
        verify(categoryRepository).existsById(1L);
        verify(categoryRepository).save(testCategory);
    }

    @Test
    void update_CategoryWithNullId_ThrowsIllegalArgumentException() {
        Category categoryWithoutId = new Category();
        categoryWithoutId.setName("Electronics");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> categoryService.update(categoryWithoutId));
        assertEquals("Missing Category ID", exception.getMessage());
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void update_NonExistingCategory_ThrowsEntityNotFoundException() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> categoryService.update(testCategory));
        assertEquals("Category with ID 1 not found", exception.getMessage());
        verify(categoryRepository).existsById(1L);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void update_InvalidIdFormat_ThrowsIllegalArgumentException() {
        Category categoryWithInvalidId = new Category();
        categoryWithInvalidId.setCategoryId("invalid");
        categoryWithInvalidId.setName("Electronics");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> categoryService.update(categoryWithInvalidId));
        assertEquals("Invalid category ID format: invalid", exception.getMessage());
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void delete_ExistingCategoryId_ReturnsTrue() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(1L);

        boolean result = categoryService.delete("1");

        assertTrue(result);
        verify(categoryRepository).existsById(1L);
        verify(categoryRepository).deleteById(1L);
    }

    @Test
    void delete_NonExistingCategoryId_ReturnsFalse() {
        when(categoryRepository.existsById(999L)).thenReturn(false);

        boolean result = categoryService.delete("999");

        assertFalse(result);
        verify(categoryRepository).existsById(999L);
        verify(categoryRepository, never()).deleteById(anyLong());
    }

    @Test
    void delete_InvalidIdFormat_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> categoryService.delete("invalid"));
        assertEquals("Invalid category ID format: invalid", exception.getMessage());
        verify(categoryRepository, never()).deleteById(anyLong());
    }

    @Test
    void getAll_CategoriesExist_ReturnsListOfCategories() {
        Category category2 = new Category();
        category2.setCategoryId("2");
        category2.setName("Books");

        List<Category> expectedCategories = Arrays.asList(testCategory, category2);
        when(categoryRepository.findAll()).thenReturn(expectedCategories);

        List<Category> result = categoryService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedCategories, result);
        verify(categoryRepository).findAll();
    }

    @Test
    void getAll_NoCategories_ReturnsEmptyList() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList());

        List<Category> result = categoryService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(categoryRepository).findAll();
    }
}
