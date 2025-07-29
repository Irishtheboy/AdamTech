package za.co.admatech.factory;

import org.springframework.core.annotation.Order;
import za.co.admatech.domain.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressFactoryTest {

    private static Address a1 = AddressFactory.createAddress(1L, "123", "Main St", "Cape Town", "WC", "8000", "A");
    private static Address a2 = AddressFactory.createAddress(2L, "456", "Park Rd", "Johannesburg", "GP", "2000", "B");
    private static Address a3 = AddressFactory.createAddress(3L, "789", "Beach Ave", "Durban", "KZN", "4000", "C");

    @Test
    @Order(1)
    public void testCreateAddress1() {
        assertNotNull(a1);
        assertNotNull(a1.getAddressId());
        System.out.println(a1.toString());
    }

    @Test
    @Order(2)
    public void testCreateAddress2() {
        assertNotNull(a2);
        assertNotNull(a2.getAddressId());
        System.out.println(a2.toString());
    }

    @Test
    @Order(3)
    public void testCreateAddress3() {
        assertNotNull(a3);
        assertNotNull(a3.getAddressId());
        System.out.println(a3.toString());
    }
}