package za.co.admatech.factory;
/**
 * CustomerFactoryTest.java
 * CustomerFactoryTest Factory Test class
 *
 * Author: Rorisang Makgana(230602363)
 */

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerFactoryTest {
    private static Customer customer;
    private static Address address;
    private static Cart cart;
    @BeforeAll
    public static void setup(){
        address = AddressFactory.createAddress(
                (long)12,
                (short) 12,
                "Devin's Chapman",
                "Cravenwood",
                "Mulburrey",
                "Lancashire",
                (short) 1299
        );
        customer = CustomerFactory.createCustomer(
                7l,
                "Rorisang",
                "Makgana",
                "radamtech@corporate.adamtect",
                "0111111111",
                null,
                List.of(),
                List.of()
        );
    }
    @Test
    @Order(1)
    void testCreatedCustomer(){
        assertNotNull(customer);
        System.out.println(customer);
    }

}