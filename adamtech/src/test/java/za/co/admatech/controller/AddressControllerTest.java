/*
 * AddressControllerTest.java
 * Author: Naqeebah Khan (219099073)
 * Date: 24 May 2025
 */
package za.co.admatech.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import za.co.admatech.domain.Address;
import za.co.admatech.factory.AddressFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class AddressControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static Address address;

    private static final String BASE_URL = "/address";

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
        ResponseEntity<Address> response = restTemplate.postForEntity(url, address, Address.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getAddressId());
        assertEquals(address.getStreetNumber(), response.getBody().getStreetNumber());
        assertEquals(address.getStreetName(), response.getBody().getStreetName());
        assertEquals(address.getSuburb(), response.getBody().getSuburb());
        assertEquals(address.getCity(), response.getBody().getCity());
        assertEquals(address.getProvince(), response.getBody().getProvince());
        assertEquals(address.getPostalCode(), response.getBody().getPostalCode());

        address = response.getBody(); // Update reference for later tests
        System.out.println("Created address: " + address);
    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + address.getAddressId();
        ResponseEntity<Address> response = restTemplate.getForEntity(url, Address.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(address.getAddressId(), response.getBody().getAddressId());
        assertEquals(address.getStreetNumber(), response.getBody().getStreetNumber());
        assertEquals(address.getStreetName(), response.getBody().getStreetName());
        assertEquals(address.getSuburb(), response.getBody().getSuburb());
        assertEquals(address.getCity(), response.getBody().getCity());
        assertEquals(address.getProvince(), response.getBody().getProvince());
        assertEquals(address.getPostalCode(), response.getBody().getPostalCode());

        System.out.println("Read address: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        Address updatedAddress = new Address.Builder()
                .copy(address)
                .setStreetName("Updated Devin's Chapman")
                .setSuburb("Updated Cravenwood")
                .build();

        String url = BASE_URL + "/update/" + address.getAddressId();
        HttpEntity<Address> request = new HttpEntity<>(updatedAddress);
        ResponseEntity<Address> response = restTemplate.exchange(url, HttpMethod.PUT, request, Address.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(address.getAddressId(), response.getBody().getAddressId());
        assertEquals("Updated Devin's Chapman", response.getBody().getStreetName());
        assertEquals("Updated Cravenwood", response.getBody().getSuburb());
        assertEquals(address.getStreetNumber(), response.getBody().getStreetNumber());
        assertEquals(address.getCity(), response.getBody().getCity());
        assertEquals(address.getProvince(), response.getBody().getProvince());
        assertEquals(address.getPostalCode(), response.getBody().getPostalCode());

        address = response.getBody(); // Update reference
        System.out.println("Updated address: " + address);
    }

    @Test
    @Order(4)
    void delete() {
        String url = BASE_URL + "/delete/" + address.getAddressId();
        restTemplate.delete(url);

        ResponseEntity<Address> response = restTemplate.getForEntity(BASE_URL + "/read/" + address.getAddressId(), Address.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        System.out.println("Deleted address: ID " + address.getAddressId());
    }

    @Test
    @Order(5)
    void getAll() {
        // Create a new address to ensure the list is non-empty
        Address newAddress = AddressFactory.createAddress(
                (short) 15,
                "New Street",
                "New Suburb",
                "New City",
                "New Province",
                (short) 2000
        );
        restTemplate.postForEntity(BASE_URL + "/create", newAddress, Address.class);

        String url = BASE_URL + "/getAll";
        ResponseEntity<Address[]> response = restTemplate.getForEntity(url, Address[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);

        System.out.println("Get All Addresses:");
        for (Address a : response.getBody()) {
            System.out.println(a);
        }
    }
}