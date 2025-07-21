/*





AddressServiceTest.java */ package za.co.admatech.service;

import org.junit.jupiter.api.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.context.SpringBootTest; import za.co.admatech.domain.Address; import za.co.admatech.factory.AddressFactory; import za.co.admatech.service.address_domain_service.AddressService;

import jakarta.transaction.Transactional; import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class) @Transactional class AddressServiceTest { @Autowired private AddressService addressService;

    private static Address address;

    @BeforeAll
    public static void setup() {
        address = AddressFactory.createAddress(
                12L,
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
        Address created = addressService.create(address);
        assertNotNull(created);
        assertEquals(address.getAddressID(), created.getAddressID());
        assertEquals(address.getStreetName(), created.getStreetName());
        System.out.println("Created Address: " + created);

        // Update address for subsequent tests
        address = created;
    }

    @Test
    @Order(2)
    void read() {
        Address read = addressService.read(address.getAddressID());
        assertNotNull(read);
        assertEquals(address.getAddressID(), read.getAddressID());
        assertEquals(address.getStreetName(), read.getStreetName());
        System.out.println("Read Address: " + read);
    }

    @Test
    @Order(3)
    void update() {
        Address updatedAddress = new Address.Builder()
                .copy(address)
                .setStreetName("Updated Devin's Chapman")
                .setSuburb("Updated Cravenwood")
                .build();
        Address updated = addressService.update(updatedAddress);
        assertNotNull(updated);
        assertEquals(address.getAddressID(), updated.getAddressID());
        assertEquals("Updated Devin's Chapman", updated.getStreetName());
        assertEquals("Updated Cravenwood", updated.getSuburb());
        System.out.println("Updated Address: " + updated);
    }

    @Test
    @Order(4)
    void delete() {
        assertDoesNotThrow(() -> addressService.delete(address.getAddressID()));
        assertNull(addressService.read(address.getAddressID()));
        System.out.println("Deleted Address: " + address.getAddressID());
    }

    @Test
    @Order(5)
    void getAll() {
        List<Address> addresses = addressService.getAll();
        assertNotNull(addresses);
        assertTrue(addresses.size() >= 0);
        System.out.println("All Addresses: " + addresses);
    }

}