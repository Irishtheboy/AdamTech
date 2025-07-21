package za.co.admatech.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import za.co.admatech.domain.Address;
import za.co.admatech.domain.Customer;
import za.co.admatech.factory.AddressFactory;
import za.co.admatech.factory.CustomerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.class)
class CustomerControllerTest {
    private static Customer customer;
    private static Address address;
    protected TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/customer";

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
    void create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Customer> response = this.restTemplate.postForEntity(url, customer, Customer.class);
        System.out.println(response);
        assertNotNull(response);
        assertEquals(customer.getCustomerID(), response.getBody().getCustomerID());
        System.out.println("Created_customer: " + response.getBody());
    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + customer.getCustomerID();
        ResponseEntity<Customer> response = this.restTemplate.getForEntity(url, Customer.class);
        System.out.println(response);
        assertNotNull(response);
        assertEquals(customer.getCustomerID(), response.getBody().getCustomerID());
        System.out.println("Read_customer: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        Customer updatedCustomer = new Customer.Builder()
                .copy(customer)
                .setFirstName("UpdatedRorisang") // Example: Modify firstName
                .setLastName("UpdatedMakgana")  // Example: Modify lastName
                .build();
        String url = BASE_URL + "/update/";
        ResponseEntity<Customer> response = this.restTemplate.postForEntity(url, updatedCustomer, Customer.class);
        System.out.println(response);
        assertNotNull(response);
        assertEquals(updatedCustomer.getCustomerID(), response.getBody().getCustomerID());
        System.out.println("Updated_customer: " + response.getBody());

    }

    @Test
    @Order(4)
    void delete() {
        String url = BASE_URL + "/delete/" + customer.getCustomerID();
        this.restTemplate.delete(url);

        ResponseEntity<Customer> response = this.restTemplate.getForEntity(BASE_URL + "/delete/" + customer.getCustomerID(), Customer.class);
        assertNotNull(response);
        assertEquals(404, response.getStatusCode().value());
        System.out.println("Deleted_customer: " + response.getBody());
    }
}
