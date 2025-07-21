/*





AddressControllerTest.java



Author: Rorisang Makgana (230602363) */ package za.co.admatech.controller;

import org.junit.jupiter.api.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.boot.test.web.server.LocalServerPort; import org.springframework.http.HttpMethod; import org.springframework.http.HttpStatus; import org.springframework.http.ResponseEntity; import org.springframework.web.client.RestClientException; import za.co.admatech.domain.Address; import za.co.admatech.factory.AddressFactory;

import jakarta.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) @TestMethodOrder(MethodOrderer.OrderAnnotation.class) @Transactional class AddressControllerTest { @LocalServerPort private int port;

    @Autowired
    private org.springframework.boot.test.web.client.TestRestTemplate restTemplate;

    private String baseUrl;

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

    @BeforeEach
    public void init() {
        baseUrl = "http://localhost:" + port + "/addresses";
    }

    @Test
    @Order(1)
    void create() {
        ResponseEntity<Address> response = restTemplate.postForEntity(baseUrl, address, Address.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(address.getAddressID(), response.getBody().getAddressID());
        assertEquals(address.getStreetName(), response.getBody().getStreetName());
        System.out.println("Created Address: " + response.getBody());

        // Update address for subsequent tests
        address = response.getBody();
    }

    @Test
    @Order(2)
    void read() {
        String url = baseUrl + "/" + address.getAddressID();
        ResponseEntity<Address> response = restTemplate.getForEntity(url, Address.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(address.getAddressID(), response.getBody().getAddressID());
        assertEquals(address.getStreetName(), response.getBody().getStreetName());
        System.out.println("Read Address: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        Address updatedAddress = new Address.Builder()
                .copy(address)
                .setStreetName("Updated Devin's Chapman")
                .setSuburb("Updated Cravenwood")
                .build();
        ResponseEntity<Address> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, new org.springframework.http.HttpEntity<>(updatedAddress), Address.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedAddress.getAddressID(), response.getBody().getAddressID());
        assertEquals("Updated Devin's Chapman", response.getBody().getStreetName());
        assertEquals("Updated Cravenwood", response.getBody().getSuburb());
        System.out.println("Updated Address: " + response.getBody());
    }

    @Test
    @Order(4)
    void delete() {
        String url = baseUrl + "/" + address.getAddressID();
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        System.out.println("Deleted Address: " + address.getAddressID());

        // Verify deletion
        ResponseEntity<Address> readResponse = restTemplate.getForEntity(url, Address.class);
        assertEquals(HttpStatus.NOT_FOUND, readResponse.getStatusCode());
    }

    @Test
    @Order(5)
    void getAll() {
        ResponseEntity<Address[]> response = restTemplate.getForEntity(baseUrl, Address[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 0);
        System.out.println("All Addresses: " + List.of(response.getBody()));
    }

}