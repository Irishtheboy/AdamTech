package za.co.admatech.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import za.co.admatech.factory.MoneyFactory;
import za.co.admatech.factory.ProductFactory;
import za.co.admatech.repository.ProductRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private Money testPrice;

    @BeforeEach
    void setUp() {
        testPrice = MoneyFactory.buildMoney(150000, "ZAR");

        testProduct = ProductFactory.buildProduct(
                1L, "Gaming Laptop", "High-performance gaming laptop", 
                "LAPTOP-001", testPrice, "electronics"
        );
    }

    @Test
    void create_ShouldReturnSavedProduct_WhenValidProduct() {
        // Given
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // When
        Product result = productService.create(testProduct);

        // Then
        assertNotNull(result);
        assertEquals(testProduct.getProductId(), result.getProductId());
        assertEquals(testProduct.getName(), result.getName());
        assertEquals(testProduct.getDescription(), result.getDescription());
        assertEquals(testProduct.getSku(), result.getSku());
        assertEquals(testProduct.getPrice().getAmount(), result.getPrice().getAmount());
        verify(productRepository).save(testProduct);
    }

    @Test
    void read_ShouldReturnProduct_WhenProductExists() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        // When
        Product result = productService.read(1L);

        // Then
        assertNotNull(result);
        assertEquals(testProduct.getProductId(), result.getProductId());
        assertEquals(testProduct.getName(), result.getName());
        verify(productRepository).findById(1L);
    }

    @Test
    void read_ShouldReturnNull_WhenProductDoesNotExist() {
        // Given
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        Product result = productService.read(999L);

        // Then
        assertNull(result);
        verify(productRepository).findById(999L);
    }

    @Test
    void update_ShouldReturnUpdatedProduct_WhenValidProduct() {
        // Given
        Product updatedProduct = ProductFactory.buildProduct(
                1L, "Updated Gaming Laptop", "Updated high-performance gaming laptop", 
                "LAPTOP-001-V2", testPrice, "electronics"
        );
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // When
        Product result = productService.update(updatedProduct);

        // Then
        assertNotNull(result);
        assertEquals(updatedProduct.getName(), result.getName());
        assertEquals(updatedProduct.getDescription(), result.getDescription());
        assertEquals(updatedProduct.getSku(), result.getSku());
        verify(productRepository).save(updatedProduct);
    }

    @Test
    void delete_ShouldReturnTrue_WhenProductExists() {
        // Given
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        // When
        boolean result = productService.delete(1L);

        // Then
        assertTrue(result);
        verify(productRepository).existsById(1L);
        verify(productRepository).deleteById(1L);
    }

    @Test
    void delete_ShouldReturnFalse_WhenProductDoesNotExist() {
        // Given
        when(productRepository.existsById(999L)).thenReturn(false);

        // When
        boolean result = productService.delete(999L);

        // Then
        assertFalse(result);
        verify(productRepository).existsById(999L);
        verify(productRepository, never()).deleteById(anyLong());
    }

    @Test
    void getAll_ShouldReturnListOfProducts() {
        // Given
        Product product2 = ProductFactory.buildProduct(
                2L, "Smartphone", "Latest smartphone", 
                "PHONE-001", testPrice, "electronics"
        );
        List<Product> products = Arrays.asList(testProduct, product2);
        when(productRepository.findAll()).thenReturn(products);

        // When
        List<Product> result = productService.getAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testProduct.getProductId(), result.get(0).getProductId());
        assertEquals(product2.getProductId(), result.get(1).getProductId());
        verify(productRepository).findAll();
    }

    @Test
    void getAll_ShouldReturnEmptyList_WhenNoProducts() {
        // Given
        when(productRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Product> result = productService.getAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository).findAll();
    }
}
