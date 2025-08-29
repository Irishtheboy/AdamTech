package za.co.admatech.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.admatech.domain.CartItem;
import za.co.admatech.repository.CartItemRepository;
import za.co.admatech.service.cart_item_domain_service.CartItemService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartItemServiceUnitTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartItemService cartItemService;

    private CartItem testCartItem;

    @BeforeEach
    void setUp() {
        testCartItem = new CartItem();
        testCartItem.setId("1");
        testCartItem.setQuantity(5);
        testCartItem.setProductId("product123");
    }

    @Test
    void create_ValidCartItem_ReturnsCartItem() {
        when(cartItemRepository.save(testCartItem)).thenReturn(testCartItem);

        CartItem result = cartItemService.create(testCartItem);

        assertNotNull(result);
        assertEquals(testCartItem, result);
        verify(cartItemRepository).save(testCartItem);
    }

    @Test
    void create_NullCartItem_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cartItemService.create(null));
        assertEquals("Invalid cart item data", exception.getMessage());
        verify(cartItemRepository, never()).save(any(CartItem.class));
    }

    @Test
    void create_CartItemWithNegativeQuantity_ThrowsIllegalArgumentException() {
        CartItem invalidCartItem = new CartItem();
        invalidCartItem.setId("1");
        invalidCartItem.setQuantity(-1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cartItemService.create(invalidCartItem));
        assertEquals("Invalid cart item data", exception.getMessage());
        verify(cartItemRepository, never()).save(any(CartItem.class));
    }

    @Test
    void read_ExistingId_ReturnsCartItem() {
        when(cartItemRepository.findById("1")).thenReturn(Optional.of(testCartItem));

        CartItem result = cartItemService.read("1");

        assertNotNull(result);
        assertEquals(testCartItem, result);
        verify(cartItemRepository).findById("1");
    }

    @Test
    void read_NonExistingId_ThrowsEntityNotFoundException() {
        when(cartItemRepository.findById("nonexistent")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> cartItemService.read("nonexistent"));
        assertEquals("CartItem with ID nonexistent not found", exception.getMessage());
        verify(cartItemRepository).findById("nonexistent");
    }

    @Test
    void update_ValidCartItem_ReturnsUpdatedCartItem() {
        when(cartItemRepository.existsById("1")).thenReturn(true);
        when(cartItemRepository.save(testCartItem)).thenReturn(testCartItem);

        CartItem result = cartItemService.update(testCartItem);

        assertNotNull(result);
        assertEquals(testCartItem, result);
        verify(cartItemRepository).existsById("1");
        verify(cartItemRepository).save(testCartItem);
    }

    @Test
    void update_CartItemWithNullId_ThrowsIllegalArgumentException() {
        CartItem cartItemWithoutId = new CartItem();
        cartItemWithoutId.setQuantity(5);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cartItemService.update(cartItemWithoutId));
        assertEquals("Invalid cart item data or missing ID", exception.getMessage());
        verify(cartItemRepository, never()).save(any(CartItem.class));
    }

    @Test
    void update_CartItemWithNegativeQuantity_ThrowsIllegalArgumentException() {
        CartItem invalidCartItem = new CartItem();
        invalidCartItem.setId("1");
        invalidCartItem.setQuantity(-1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cartItemService.update(invalidCartItem));
        assertEquals("Invalid cart item data or missing ID", exception.getMessage());
        verify(cartItemRepository, never()).save(any(CartItem.class));
    }

    @Test
    void update_NonExistingCartItem_ThrowsEntityNotFoundException() {
        when(cartItemRepository.existsById("1")).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> cartItemService.update(testCartItem));
        assertEquals("CartItem with ID 1 not found", exception.getMessage());
        verify(cartItemRepository).existsById("1");
        verify(cartItemRepository, never()).save(any(CartItem.class));
    }

    @Test
    void delete_ExistingCartItemId_ReturnsTrue() {
        when(cartItemRepository.existsById("1")).thenReturn(true);
        doNothing().when(cartItemRepository).deleteById("1");

        boolean result = cartItemService.delete("1");

        assertTrue(result);
        verify(cartItemRepository).existsById("1");
        verify(cartItemRepository).deleteById("1");
    }

    @Test
    void delete_NonExistingCartItemId_ReturnsFalse() {
        when(cartItemRepository.existsById("nonexistent")).thenReturn(false);

        boolean result = cartItemService.delete("nonexistent");

        assertFalse(result);
        verify(cartItemRepository).existsById("nonexistent");
        verify(cartItemRepository, never()).deleteById(anyString());
    }

    @Test
    void getAll_CartItemsExist_ReturnsListOfCartItems() {
        CartItem cartItem2 = new CartItem();
        cartItem2.setId("2");
        cartItem2.setQuantity(3);
        cartItem2.setProductId("product456");
        cartItem2.setProductId("product456");

        List<CartItem> expectedCartItems = Arrays.asList(testCartItem, cartItem2);
        when(cartItemRepository.findAll()).thenReturn(expectedCartItems);

        List<CartItem> result = cartItemService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedCartItems, result);
        verify(cartItemRepository).findAll();
    }

    @Test
    void getAll_NoCartItems_ReturnsEmptyList() {
        when(cartItemRepository.findAll()).thenReturn(Arrays.asList());

        List<CartItem> result = cartItemService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cartItemRepository).findAll();
    }

    @Test
    void create_CartItemWithZeroQuantity_ReturnsCartItem() {
        CartItem zeroQuantityItem = new CartItem();
        zeroQuantityItem.setId("1");
        zeroQuantityItem.setQuantity(0);
        zeroQuantityItem.setProductId("product123");

        when(cartItemRepository.save(zeroQuantityItem)).thenReturn(zeroQuantityItem);

        CartItem result = cartItemService.create(zeroQuantityItem);

        assertNotNull(result);
        assertEquals(zeroQuantityItem, result);
        verify(cartItemRepository).save(zeroQuantityItem);
    }
}
