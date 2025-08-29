package za.co.admatech.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.admatech.domain.CartOrder;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.repository.CartOrderRepository;
import za.co.admatech.service.cartorder_domain_service.CartOrderService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartOrderServiceUnitTest {

    @Mock
    private CartOrderRepository cartOrderRepository;

    @InjectMocks
    private CartOrderService cartOrderService;

    private CartOrder testCartOrder;

    @BeforeEach
    void setUp() {
        testCartOrder = new CartOrder();
        testCartOrder.setId("1");
        testCartOrder.setOrderStatus(OrderStatus.PENDING);
    }

    @Test
    void create_ValidCartOrder_ReturnsCartOrder() {
        when(cartOrderRepository.save(testCartOrder)).thenReturn(testCartOrder);

        CartOrder result = cartOrderService.create(testCartOrder);

        assertNotNull(result);
        assertEquals(testCartOrder, result);
        verify(cartOrderRepository).save(testCartOrder);
    }

    @Test
    void create_NullCartOrder_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cartOrderService.create(null));
        assertEquals("CartOrder data is null", exception.getMessage());
        verify(cartOrderRepository, never()).save(any(CartOrder.class));
    }

    @Test
    void read_ValidStringId_ReturnsCartOrder() {
        when(cartOrderRepository.findById(1L)).thenReturn(Optional.of(testCartOrder));

        CartOrder result = cartOrderService.read("1");

        assertNotNull(result);
        assertEquals(testCartOrder, result);
        verify(cartOrderRepository).findById(1L);
    }

    @Test
    void read_NonExistingId_ThrowsEntityNotFoundException() {
        when(cartOrderRepository.findById(999L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> cartOrderService.read("999"));
        assertEquals("CartOrder with ID 999 not found", exception.getMessage());
        verify(cartOrderRepository).findById(999L);
    }

    @Test
    void read_InvalidIdFormat_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cartOrderService.read("invalid"));
        assertEquals("Invalid cart order ID format: invalid", exception.getMessage());
        verify(cartOrderRepository, never()).findById(anyLong());
    }

    @Test
    void update_ValidCartOrder_ReturnsUpdatedCartOrder() {
        when(cartOrderRepository.existsById(1L)).thenReturn(true);
        when(cartOrderRepository.save(testCartOrder)).thenReturn(testCartOrder);

        CartOrder result = cartOrderService.update(testCartOrder);

        assertNotNull(result);
        assertEquals(testCartOrder, result);
        verify(cartOrderRepository).existsById(1L);
        verify(cartOrderRepository).save(testCartOrder);
    }

    @Test
    void update_CartOrderWithNullId_ThrowsIllegalArgumentException() {
        CartOrder cartOrderWithoutId = new CartOrder();
        cartOrderWithoutId.setOrderStatus(OrderStatus.PENDING);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cartOrderService.update(cartOrderWithoutId));
        assertEquals("Missing CartOrder ID", exception.getMessage());
        verify(cartOrderRepository, never()).save(any(CartOrder.class));
    }

    @Test
    void update_NonExistingCartOrder_ThrowsEntityNotFoundException() {
        when(cartOrderRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> cartOrderService.update(testCartOrder));
        assertEquals("CartOrder with ID 1 not found", exception.getMessage());
        verify(cartOrderRepository).existsById(1L);
        verify(cartOrderRepository, never()).save(any(CartOrder.class));
    }

    @Test
    void update_InvalidIdFormat_ThrowsIllegalArgumentException() {
        CartOrder cartOrderWithInvalidId = new CartOrder();
        cartOrderWithInvalidId.setId("invalid");
        cartOrderWithInvalidId.setOrderStatus(OrderStatus.PENDING);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cartOrderService.update(cartOrderWithInvalidId));
        assertEquals("Invalid cart order ID format: invalid", exception.getMessage());
        verify(cartOrderRepository, never()).save(any(CartOrder.class));
    }

    @Test
    void delete_ExistingCartOrderId_ReturnsTrue() {
        when(cartOrderRepository.existsById(1L)).thenReturn(true);
        doNothing().when(cartOrderRepository).deleteById(1L);

        boolean result = cartOrderService.delete("1");

        assertTrue(result);
        verify(cartOrderRepository).existsById(1L);
        verify(cartOrderRepository).deleteById(1L);
    }

    @Test
    void delete_NonExistingCartOrderId_ReturnsFalse() {
        when(cartOrderRepository.existsById(999L)).thenReturn(false);

        boolean result = cartOrderService.delete("999");

        assertFalse(result);
        verify(cartOrderRepository).existsById(999L);
        verify(cartOrderRepository, never()).deleteById(anyLong());
    }

    @Test
    void delete_InvalidIdFormat_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cartOrderService.delete("invalid"));
        assertEquals("Invalid cart order ID format: invalid", exception.getMessage());
        verify(cartOrderRepository, never()).deleteById(anyLong());
    }

    @Test
    void getAll_CartOrdersExist_ReturnsListOfCartOrders() {
        CartOrder cartOrder2 = new CartOrder();
        cartOrder2.setId("2");
        cartOrder2.setOrderStatus(OrderStatus.CONFIRMED);

        List<CartOrder> expectedCartOrders = Arrays.asList(testCartOrder, cartOrder2);
        when(cartOrderRepository.findAll()).thenReturn(expectedCartOrders);

        List<CartOrder> result = cartOrderService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedCartOrders, result);
        verify(cartOrderRepository).findAll();
    }

    @Test
    void getAll_NoCartOrders_ReturnsEmptyList() {
        when(cartOrderRepository.findAll()).thenReturn(Arrays.asList());

        List<CartOrder> result = cartOrderService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cartOrderRepository).findAll();
    }
}
