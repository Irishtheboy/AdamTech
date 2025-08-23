package za.co.admatech.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
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

    @LocalServerPort
    private int port;

    private final ObjectMapper mapper = new ObjectMapper();

    private String getBaseUrl() {
        return "http://localhost:" + port + "/address";
    }

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
    void a_create() throws Exception {
        String url = getBaseUrl() + "/create";
        ResponseEntity<String> response = restTemplate.postForEntity(url, address, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Address createdAddress = mapper.readValue(response.getBody(), Address.class);
        assertNotNull(createdAddress);
        assertEquals(address.getAddressID(), createdAddress.getAddressID());

        System.out.println("Created_address: " + createdAddress);
    }

    @Test
    void b_read() throws Exception {
        String url = getBaseUrl() + "/read/" + address.getAddressID();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Address readAddress = mapper.readValue(response.getBody(), Address.class);
        assertNotNull(readAddress);
        assertEquals(address.getAddressID(), readAddress.getAddressID());

        System.out.println("Read_address: " + readAddress);
    }

    @Test
    void c_update() throws Exception {
        Address updatedAddress = new Address.Builder()
                .copy(address)
                .setStreetName("Updated Devin's Chapman")
                .setSuburb("Updated Cravenwood")
                .build();

        String url = getBaseUrl() + "/update";
        restTemplate.put(url, updatedAddress);

        // Fetch updated
        ResponseEntity<String> response = restTemplate.getForEntity(getBaseUrl() + "/read/" + address.getAddressID(), String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Address body = mapper.readValue(response.getBody(), Address.class);
        assertEquals("Updated Devin's Chapman", body.getStreetName());
        assertEquals("Updated Cravenwood", body.getSuburb());

        System.out.println("Updated_address: " + body);
    }

    @Test
    void d_delete() {
        String url = getBaseUrl() + "/delete/" + address.getAddressID();
        restTemplate.delete(url);

        // Check deleted
        ResponseEntity<String> response = restTemplate.getForEntity(getBaseUrl() + "/read/" + address.getAddressID(), String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        System.out.println("Deleted_address check response: " + response.getStatusCode());
    }

    @Test
    void e_getAll() throws Exception {
        String url = getBaseUrl() + "/getAll";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Address[] addresses = mapper.readValue(response.getBody(), Address[].class);
        assertNotNull(addresses);

        System.out.println("Get All Addresses:");
        for (Address a : addresses) {
            System.out.println(a);
        }
    }
}
