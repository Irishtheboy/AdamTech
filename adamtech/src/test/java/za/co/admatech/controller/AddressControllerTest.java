package za.co.admatech.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import za.co.admatech.domain.Address;
import za.co.admatech.factory.AddressFactory;
import za.co.admatech.repository.AddressRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AddressRepository addressRepository;

    private Address address;

    private static final String BASE_URL = "/address";

    @BeforeEach
    public void setup() {
        address = AddressFactory.createAddress(
                (short) 12,
                "Devin's Chapman",
                "Cravenwood",
                "Mulburrey",
                "Lancashire",
                (short) 1299
        );
        address = addressRepository.save(address);
        System.out.println("Persisted address: " + address + ", ID: " + address.getAddressId());
        assertNotNull(address.getAddressId(), "Persisted address should have an ID");
    }

    @Test
    @Order(1)
    void create() {
        String url = BASE_URL + "/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Address> request = new HttpEntity<>(address, headers);

        try {
            ResponseEntity<Address> response = restTemplate.postForEntity(url, request, Address.class);
            assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected 200 OK");
            assertNotNull(response.getBody(), "Response body should not be null");
            assertNotNull(response.getBody().getAddressId(), "Address ID should be generated");
            assertEquals(address.getStreetNumber(), response.getBody().getStreetNumber());
            assertEquals(address.getStreetName(), response.getBody().getStreetName());
            assertEquals(address.getSuburb(), response.getBody().getSuburb());
            assertEquals(address.getCity(), response.getBody().getCity());
            assertEquals(address.getProvince(), response.getBody().getProvince());
            assertEquals(address.getPostalCode(), response.getBody().getPostalCode());

            address = response.getBody();
            System.out.println("Created address: " + address + ", ID: " + address.getAddressId());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.err.println("Error response: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;
        }
    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + address.getAddressId();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Address> response = restTemplate.getForEntity(url, Address.class);
            assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected 200 OK");
            assertNotNull(response.getBody(), "Response body should not be null");
            assertEquals(address.getAddressId(), response.getBody().getAddressId());
            assertEquals(address.getStreetNumber(), response.getBody().getStreetNumber());
            assertEquals(address.getStreetName(), response.getBody().getStreetName());
            assertEquals(address.getSuburb(), response.getBody().getSuburb());
            assertEquals(address.getCity(), response.getBody().getCity());
            assertEquals(address.getProvince(), response.getBody().getProvince());
            assertEquals(address.getPostalCode(), response.getBody().getPostalCode());

            System.out.println("Read address: " + response.getBody());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.err.println("Error response: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;
        }
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Address> request = new HttpEntity<>(updatedAddress, headers);

        try {
            ResponseEntity<Address> response = restTemplate.exchange(url, HttpMethod.PUT, request, Address.class);
            assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected 200 OK");
            assertNotNull(response.getBody(), "Response body should not be null");
            assertEquals(address.getAddressId(), response.getBody().getAddressId(), "Address ID should match");
            assertEquals("Updated Devin's Chapman", response.getBody().getStreetName(), "Street name should be updated");
            assertEquals("Updated Cravenwood", response.getBody().getSuburb(), "Suburb should be updated");
            assertEquals(address.getStreetNumber(), response.getBody().getStreetNumber(), "Street number should match");
            assertEquals(address.getCity(), response.getBody().getCity(), "City should match");
            assertEquals(address.getProvince(), response.getBody().getProvince(), "Province should match");
            assertEquals(address.getPostalCode(), response.getBody().getPostalCode(), "Postal code should match");

            address = response.getBody();
            System.out.println("Updated address: " + address);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.err.println("Error response: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;
        }
    }

    @Test
    @Order(4)
    void delete() {
        String url = BASE_URL + "/delete/" + address.getAddressId();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Void> deleteResponse = restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
            assertEquals(HttpStatus.OK, deleteResponse.getStatusCode(), "Expected 200 OK for delete");


            Optional<Address> dbAddress = addressRepository.findById(address.getAddressId());
            assertFalse(dbAddress.isPresent(), "Address should not exist in database after delete");

            ResponseEntity<Address> readResponse = restTemplate.getForEntity(BASE_URL + "/read/" + address.getAddressId(), Address.class);
            assertEquals(HttpStatus.NOT_FOUND, readResponse.getStatusCode(), "Expected 404 Not Found after delete");
            assertNull(readResponse.getBody(), "Response body should be null after delete");

            System.out.println("Deleted address: ID " + address.getAddressId());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.err.println("Error response: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;
        }
    }

    @Test
    @Order(5)
    void getAll() {
        Address newAddress = AddressFactory.createAddress(
                (short) 15,
                "New Street",
                "New Suburb",
                "New City",
                "New Province",
                (short) 2000
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Address> createRequest = new HttpEntity<>(newAddress, headers);

        try {
            ResponseEntity<Address> createResponse = restTemplate.postForEntity(BASE_URL + "/create", createRequest, Address.class);
            assertEquals(HttpStatus.OK, createResponse.getStatusCode(), "Expected 200 OK for create");

            String url = BASE_URL + "/getAll";
            HttpEntity<Void> request = new HttpEntity<>(headers);
            ResponseEntity<Address[]> response = restTemplate.getForEntity(url, Address[].class);
            assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected 200 OK");
            assertNotNull(response.getBody(), "Response body should not be null");
            assertTrue(response.getBody().length > 0, "Expected at least one address");

            System.out.println("Get All Addresses:");
            for (Address a : response.getBody()) {
                System.out.println(a);
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.err.println("Error response: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;
        }
    }
}