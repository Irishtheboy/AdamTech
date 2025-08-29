package za.co.admatech.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.admatech.domain.Cart;
import za.co.admatech.repository.CartRepository;
import za.co.admatech.service.cart_domain_service.CartService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceUnitTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    private Cart testCart;

    @BeforeEach
    void setUp() {
        testCart = new Cart.Builder()
                .id("1")
                .customerId("123")
                .build();
    }

    @Test
    void create_ValidCart_ReturnsCart() {
        when(cartRepository.save(testCart)).thenReturn(testCart);

        Cart result = cartService.create(testCart);

        assertNotNull(result);
        assertEquals(testCart, result);
        verify(cartRepository).save(testCart);
    }

    @Test
    void create_NullCart_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cartService.create(null));
        assertEquals("Cart data is null", exception.getMessage());
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void read_ValidStringId_ReturnsCart() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(testCart));

        Cart result = cartService.read("1");

        assertNotNull(result);
        assertEquals(testCart, result);
        verify(cartRepository).findById(1L);
    }

    @Test
    void read_NonExistingId_ThrowsEntityNotFoundException() {
        when(cartRepository.findById(999L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> cartService.read("999"));
        assertEquals("Cart with ID 999 not found", exception.getMessage());
        verify(cartRepository).findById(999L);
    }

    @Test
    void read_InvalidIdFormat_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cartService.read("invalid"));
        assertEquals("Invalid cart ID format: invalid", exception.getMessage());
        verify(cartRepository, never()).findById(anyLong());
    }

    @Test
    void update_ValidCart_ReturnsUpdatedCart() {
        when(cartRepository.existsById(1L)).thenReturn(true);
        when(cartRepository.save(testCart)).thenReturn(testCart);

        Cart result = cartService.update(testCart);

        assertNotNull(result);
        assertEquals(testCart, result);
        verify(cartRepository).existsById(1L);
        verify(cartRepository).save(testCart);
    }

    @Test
    void update_CartWithNullId_ThrowsIllegalArgumentException() {
        Cart cartWithoutId = new Cart.Builder()
                .customerId("123")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cartService.update(cartWithoutId));
        assertEquals("Missing cart ID", exception.getMessage());
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void update_NonExistingCart_ThrowsEntityNotFoundException() {
        when(cartRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> cartService.update(testCart));
        assertEquals("Cart with ID 1 not found", exception.getMessage());
        verify(cartRepository).existsById(1L);
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void update_InvalidIdFormat_ThrowsIllegalArgumentException() {
        Cart cartWithInvalidId = new Cart.Builder()
                .id("invalid")
                .customerId("123")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cartService.update(cartWithInvalidId));
        assertEquals("Invalid cart ID format: invalid", exception.getMessage());
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void delete_ExistingCartId_ReturnsTrue() {
        when(cartRepository.existsById(1L)).thenReturn(true);
        doNothing().when(cartRepository).deleteById(1L);

        boolean result = cartService.delete("1");

        assertTrue(result);
        verify(cartRepository).existsById(1L);
        verify(cartRepository).deleteById(1L);
    }

    @Test
    void delete_NonExistingCartId_ReturnsFalse() {
        when(cartRepository.existsById(999L)).thenReturn(false);

        boolean result = cartService.delete("999");

        assertFalse(result);
        verify(cartRepository).existsById(999L);
        verify(cartRepository, never()).deleteById(anyLong());
    }

    @Test
    void delete_InvalidIdFormat_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cartService.delete("invalid"));
        assertEquals("Invalid cart ID format: invalid", exception.getMessage());
        verify(cartRepository, never()).deleteById(anyLong());
    }

    @Test
    void getAll_CartsExist_ReturnsListOfCarts() {
        Cart cart2 = new Cart.Builder()
                .id("2")
                .customerId("456")
                .build();

        List<Cart> expectedCarts = Arrays.asList(testCart, cart2);
        when(cartRepository.findAll()).thenReturn(expectedCarts);

        List<Cart> result = cartService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedCarts, result);
        verify(cartRepository).findAll();
    }

    @Test
    void getAll_NoCarts_ReturnsEmptyList() {
        when(cartRepository.findAll()).thenReturn(Arrays.asList());

        List<Cart> result = cartService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cartRepository).findAll();
    }
}
