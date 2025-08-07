package za.co.admatech.factory;

import org.springframework.core.annotation.Order;
import za.co.admatech.domain.*;
import za.co.admatech.factory.CustomerFactory;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerFactoryTest {

    private static Address address = new Address.Builder().addressId(1L).build();

    private static Customer c1 = CustomerFactory.createCustomer("1", "John", "Doe", "john.doe@example.com", address);
    private static Customer c2 = CustomerFactory.createCustomer("2", "Jane", "Smith", "jane.smith@example.com", address);
    private static Customer c3 = CustomerFactory.createCustomer("3", "Bob", "Jones", "bob.jones@example.com", address);

    @Test
    @Order(1)
    public void testCreateCustomer1() {
        assertNotNull(c1);
        assertNotNull(c1.getCustomerId());
        System.out.println(c1.toString());
    }

    @Test
    @Order(2)
    public void testCreateCustomer2() {
        assertNotNull(c2);
        assertNotNull(c2.getCustomerId());
        System.out.println(c2.toString());
    }

    @Test
    @Order(3)
    public void testCreateCustomer3() {
        assertNotNull(c3);
        assertNotNull(c3.getCustomerId());
        System.out.println(c3.toString());
    }
}