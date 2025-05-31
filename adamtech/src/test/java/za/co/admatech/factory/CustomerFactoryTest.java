package za.co.admatech.factory;
/**
 * CustomerFactoryTest.java
 * CustomerFactoryTest Factory Test class
 *
 * Author: Rorisang Makgana(230602363)
 */

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerFactoryTest {
    private CustomerFactory customerFactory;
    private AddressFactory addressFactory;
    private static Cart cart;
    private Address address = addressFactory.createAddress(
            07111L,
            (short) 77,
            "Bakersdraft",
            "Kuils River",
            "Cape Town",
            "Western Cape",
            (short) 7440);

    private Customer customer = customerFactory.createCustomer(
            "222AABB1792",
            "Dennis",
            "Estrange",
            "estranged@commons.net",
            //cart,
            address
    );
    @Test
    @Order(1)
    void testCreatedCustomer(){
        assertNotNull(customer);
        System.out.println(customer);
    }

}