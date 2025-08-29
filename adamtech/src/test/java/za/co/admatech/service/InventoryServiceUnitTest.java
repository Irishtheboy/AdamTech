package za.co.admatech.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.admatech.domain.Inventory;
import za.co.admatech.domain.enums.InventoryStatus;
import za.co.admatech.repository.InventoryRepository;
import za.co.admatech.service.inventory_domain_service.InventoryService;
import za.co.admatech.util.Helper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceUnitTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private Inventory testInventory;

    @BeforeEach
    void setUp() {
        testInventory = new Inventory();
        testInventory.setInventoryId("1");
        testInventory.setQuantity(10);
        testInventory.setInventoryStatus(InventoryStatus.IN_STOCK);
        testInventory.setProductId("product123");
    }

    @Test
    void create_ValidInventory_ReturnsInventory() {
        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidInventoryStatus(testInventory.getInventoryStatus())).thenReturn(true);
            when(inventoryRepository.save(testInventory)).thenReturn(testInventory);

            Inventory result = inventoryService.create(testInventory);

            assertNotNull(result);
            assertEquals(testInventory, result);
            mockedHelper.verify(() -> Helper.isValidInventoryStatus(testInventory.getInventoryStatus()));
            verify(inventoryRepository).save(testInventory);
        }
    }

    @Test
    void create_NullInventory_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> inventoryService.create(null));
        assertEquals("Invalid inventory data", exception.getMessage());
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    void create_InventoryWithNegativeQuantity_ThrowsIllegalArgumentException() {
        Inventory invalidInventory = new Inventory();
        invalidInventory.setInventoryId("1");
        invalidInventory.setQuantity(-1);
        invalidInventory.setInventoryStatus(InventoryStatus.IN_STOCK);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> inventoryService.create(invalidInventory));
        assertEquals("Invalid inventory data", exception.getMessage());
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    void create_InventoryWithInvalidStatus_ThrowsIllegalArgumentException() {
        Inventory invalidInventory = new Inventory();
        invalidInventory.setInventoryId("1");
        invalidInventory.setQuantity(10);
        invalidInventory.setInventoryStatus(null);

        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidInventoryStatus(null)).thenReturn(false);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inventoryService.create(invalidInventory));
            assertEquals("Invalid inventory data", exception.getMessage());
            mockedHelper.verify(() -> Helper.isValidInventoryStatus(null));
            verify(inventoryRepository, never()).save(any(Inventory.class));
        }
    }

    @Test
    void read_ValidStringId_ReturnsInventory() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(testInventory));

        Inventory result = inventoryService.read("1");

        assertNotNull(result);
        assertEquals(testInventory, result);
        verify(inventoryRepository).findById(1L);
    }

    @Test
    void read_NonExistingId_ThrowsEntityNotFoundException() {
        when(inventoryRepository.findById(999L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> inventoryService.read("999"));
        assertEquals("Inventory with ID 999 not found", exception.getMessage());
        verify(inventoryRepository).findById(999L);
    }

    @Test
    void read_InvalidIdFormat_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> inventoryService.read("invalid"));
        assertEquals("Invalid inventory ID format: invalid", exception.getMessage());
        verify(inventoryRepository, never()).findById(anyLong());
    }

    @Test
    void update_ValidInventory_ReturnsUpdatedInventory() {
        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidInventoryStatus(testInventory.getInventoryStatus())).thenReturn(true);
            when(inventoryRepository.existsById(1L)).thenReturn(true);
            when(inventoryRepository.save(testInventory)).thenReturn(testInventory);

            Inventory result = inventoryService.update(testInventory);

            assertNotNull(result);
            assertEquals(testInventory, result);
            mockedHelper.verify(() -> Helper.isValidInventoryStatus(testInventory.getInventoryStatus()));
            verify(inventoryRepository).existsById(1L);
            verify(inventoryRepository).save(testInventory);
        }
    }

    @Test
    void update_InventoryWithNullId_ThrowsIllegalArgumentException() {
        Inventory inventoryWithoutId = new Inventory();
        inventoryWithoutId.setQuantity(10);
        inventoryWithoutId.setInventoryStatus(InventoryStatus.IN_STOCK);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> inventoryService.update(inventoryWithoutId));
        assertEquals("Invalid inventory data or missing ID", exception.getMessage());
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    void update_InventoryWithNegativeQuantity_ThrowsIllegalArgumentException() {
        Inventory invalidInventory = new Inventory();
        invalidInventory.setInventoryId("1");
        invalidInventory.setQuantity(-1);
        invalidInventory.setInventoryStatus(InventoryStatus.IN_STOCK);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> inventoryService.update(invalidInventory));
        assertEquals("Invalid inventory data or missing ID", exception.getMessage());
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    void update_InventoryWithInvalidStatus_ThrowsIllegalArgumentException() {
        Inventory invalidInventory = new Inventory();
        invalidInventory.setInventoryId("1");
        invalidInventory.setQuantity(10);
        invalidInventory.setInventoryStatus(null);

        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidInventoryStatus(null)).thenReturn(false);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inventoryService.update(invalidInventory));
            assertEquals("Invalid inventory data or missing ID", exception.getMessage());
            mockedHelper.verify(() -> Helper.isValidInventoryStatus(null));
            verify(inventoryRepository, never()).save(any(Inventory.class));
        }
    }

    @Test
    void update_NonExistingInventory_ThrowsEntityNotFoundException() {
        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidInventoryStatus(testInventory.getInventoryStatus())).thenReturn(true);
            when(inventoryRepository.existsById(1L)).thenReturn(false);

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> inventoryService.update(testInventory));
            assertEquals("Inventory with ID 1 not found", exception.getMessage());
            mockedHelper.verify(() -> Helper.isValidInventoryStatus(testInventory.getInventoryStatus()));
            verify(inventoryRepository).existsById(1L);
            verify(inventoryRepository, never()).save(any(Inventory.class));
        }
    }

    @Test
    void update_InvalidIdFormat_ThrowsIllegalArgumentException() {
        Inventory inventoryWithInvalidId = new Inventory();
        inventoryWithInvalidId.setInventoryId("invalid");
        inventoryWithInvalidId.setQuantity(10);
        inventoryWithInvalidId.setInventoryStatus(InventoryStatus.IN_STOCK);

        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidInventoryStatus(InventoryStatus.IN_STOCK)).thenReturn(true);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> inventoryService.update(inventoryWithInvalidId));
            assertEquals("Invalid inventory ID format: invalid", exception.getMessage());
            verify(inventoryRepository, never()).save(any(Inventory.class));
        }
    }

    @Test
    void delete_ExistingInventoryId_ReturnsTrue() {
        when(inventoryRepository.existsById(1L)).thenReturn(true);
        doNothing().when(inventoryRepository).deleteById(1L);

        boolean result = inventoryService.delete("1");

        assertTrue(result);
        verify(inventoryRepository).existsById(1L);
        verify(inventoryRepository).deleteById(1L);
    }

    @Test
    void delete_NonExistingInventoryId_ReturnsFalse() {
        when(inventoryRepository.existsById(999L)).thenReturn(false);

        boolean result = inventoryService.delete("999");

        assertFalse(result);
        verify(inventoryRepository).existsById(999L);
        verify(inventoryRepository, never()).deleteById(anyLong());
    }

    @Test
    void delete_InvalidIdFormat_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> inventoryService.delete("invalid"));
        assertEquals("Invalid inventory ID format: invalid", exception.getMessage());
        verify(inventoryRepository, never()).deleteById(anyLong());
    }

    @Test
    void getAll_InventoriesExist_ReturnsListOfInventories() {
        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId("2");
        inventory2.setQuantity(5);
        inventory2.setInventoryStatus(InventoryStatus.OUT_OF_STOCK);
        inventory2.setProductId("product456");

        List<Inventory> expectedInventories = Arrays.asList(testInventory, inventory2);
        when(inventoryRepository.findAll()).thenReturn(expectedInventories);

        List<Inventory> result = inventoryService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedInventories, result);
        verify(inventoryRepository).findAll();
    }

    @Test
    void getAll_NoInventories_ReturnsEmptyList() {
        when(inventoryRepository.findAll()).thenReturn(Arrays.asList());

        List<Inventory> result = inventoryService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(inventoryRepository).findAll();
    }

    @Test
    void create_InventoryWithZeroQuantity_ReturnsInventory() {
        Inventory zeroQuantityInventory = new Inventory();
        zeroQuantityInventory.setInventoryId("1");
        zeroQuantityInventory.setQuantity(0);
        zeroQuantityInventory.setInventoryStatus(InventoryStatus.OUT_OF_STOCK);

        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidInventoryStatus(InventoryStatus.OUT_OF_STOCK)).thenReturn(true);
            when(inventoryRepository.save(zeroQuantityInventory)).thenReturn(zeroQuantityInventory);

            Inventory result = inventoryService.create(zeroQuantityInventory);

            assertNotNull(result);
            assertEquals(zeroQuantityInventory, result);
            mockedHelper.verify(() -> Helper.isValidInventoryStatus(InventoryStatus.OUT_OF_STOCK));
            verify(inventoryRepository).save(zeroQuantityInventory);
        }
    }
}
