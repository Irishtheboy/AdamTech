package za.co.admatech.factory;
/**
 * AddressFactoryTest.java
 * AddressFactoryTest Factory Test class
 *
 * Author: Rorisang Makgana(230602363)
 */

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Address;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressFactoryTest {

    private AddressFactory addressFactory;
    Address address = addressFactory.createAddress(
            07111L,
            (short) 77,
            "Bakersdraft",
            "Kuils River",
            "Cape Town",
            "Western Cape",
            (short) 7440);

    @Test
    @Order(1)
    void createAddress() {
        assertNotNull(address);
        System.out.println(address);
    }

}