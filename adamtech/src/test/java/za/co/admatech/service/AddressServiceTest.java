package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Address;
import za.co.admatech.factory.AddressFactory;
import za.co.admatech.service.address_domain_service.AddressService;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressServiceTest {
    private static Address address;
    private AddressService addressService;
    @BeforeAll
    public static void setup() {
        address = AddressFactory.createAddress(
                (long) 12,
                (short) 12,
                "Devin's Chapman",
                "Cravenwood",
                "Mulburrey",
                "Lancashire",
                (short) 1299
        );
    }

    @Test
    @Order(1)
    void create() {
    }

    @Test
    @Order(2)
    void read() {
    }

    @Test
    @Order(3)
    void update() {
    }

    @Test
    @Order(4)
    void delete() {
    }
}