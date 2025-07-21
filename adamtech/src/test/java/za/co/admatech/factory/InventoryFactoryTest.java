package za.co.admatech.factory;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Inventory;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.InventoryStatus;
import za.co.admatech.domain.enums.ProductType;
import za.co.admatech.util.Helper;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryFactoryTest {

    private static Product product1 = new Product.Builder()
            .setProductId(1001L)
            .setProductName("Gaming Mouse")
            .setProductDescription("RGB wireless gaming mouse")
            .setProductPriceAmount(new Money(799, "ZAR"))
            .setProductCategory("GAMING")
            .setProductType(ProductType.PERIPHERAL)
            .build();

    private static Product product2 = new Product.Builder()
            .setProductId(1002L)
            .setProductName("SSD 1TB")
            .setProductDescription("Solid State Drive")
            .setProductPriceAmount(new Money(799, "ZAR"))
            .setProductCategory("STORAGE")
            .setProductType(ProductType.PERIPHERAL)
            .build();

    private static Inventory i1 = InventoryFactory.createInventory(
            1L, product1, 100, InventoryStatus.IN_STOCK
    );

    private static Inventory i2 = InventoryFactory.createInventory(
            2L, product2, 5, InventoryStatus.LOW_STOCK
    );

    private static Inventory i3 = InventoryFactory.createInventory(
            3L, null, 10, InventoryStatus.OUT_OF_STOCK // ❌ Invalid product
    );

    @Test
    @Order(1)
    void testCreateInventoryValid_1() {
        assertNotNull(i1);
        assertEquals("Gaming Mouse", i1.getProductId().getProductName());
        System.out.println("Inventory 1: " + i1);
    }

    @Test
    @Order(2)
    void testCreateInventoryValid_2() {
        assertNotNull(i2);
        assertEquals(InventoryStatus.LOW_STOCK, i2.getInventoryStatus());
        System.out.println("Inventory 2: " + i2);
    }

    @Test
    @Order(3)
    void testCreateInventoryInvalid() {
        assertNull(i3);
        System.out.println("Inventory 3: Creation failed as expected due to null product.");
    }
}
