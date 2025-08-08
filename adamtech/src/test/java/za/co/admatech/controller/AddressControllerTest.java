package za.co.admatech.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import za.co.admatech.domain.Address;
import za.co.admatech.factory.AddressFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)

class AddressControllerTest {
    private static Address address;
    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/address";

    @BeforeAll
    public static void setup() {
        address = AddressFactory.createAddress(
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
        String url = BASE_URL + "/create";
        ResponseEntity<Address> response = this.restTemplate.postForEntity(url, address, Address.class);
        System.out.println(response);
        assertNotNull(response);
        assertEquals(address.getAddressID(), response.getBody().getAddressID());
        System.out.println("Created_address: " + response.getBody());
    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + address.getAddressID();
        ResponseEntity<Address> response = this.restTemplate.getForEntity(url, Address.class);
        System.out.println(response);
        assertNotNull(response);
        assertEquals(address.getAddressID(), response.getBody().getAddressID());
        System.out.println("Read_address: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        Address updatedAddress = new Address.Builder()
                .copy(address)
                .setStreetName("Updated Devin's Chapman")
                .setSuburb("Updated Cravenwood")
                .build();
        String url = BASE_URL + "/update";
        ResponseEntity<Address> response = this.restTemplate.postForEntity(url, updatedAddress, Address.class);
        System.out.println(response);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(updatedAddress.getAddressID(), response.getBody().getAddressID());
        assertEquals("Updated Devin's Chapman", response.getBody().getStreetName());
        assertEquals("Updated Cravenwood", response.getBody().getSuburb());
        System.out.println("Updated_address: " + response.getBody());
    }

    @Test
    @Order(4)
    void delete() {
        String url = BASE_URL + "/delete/" + address.getAddressID();
        this.restTemplate.delete(url);
        ResponseEntity<Address> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + address.getAddressID(), Address.class);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        System.out.println("Deleted_address: " + response.getBody());
    }
}
