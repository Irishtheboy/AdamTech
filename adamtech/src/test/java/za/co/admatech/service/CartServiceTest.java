package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import za.co.admatech.domain.*;
import za.co.admatech.factory.AddressFactory;
import za.co.admatech.factory.CartFactory;
import za.co.admatech.factory.CustomerFactory;
import za.co.admatech.repository.AddressRepository;
import za.co.admatech.repository.CustomerRepository;
import za.co.admatech.service.cart_domain_service.ICartService;

import jakarta.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class CartServiceTest {

    @Autowired
    private ICartService service;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    private static Cart cart;
    private static Customer customer;
    private static Address address;

    @BeforeAll
    public static void setUp() {
        address = AddressFactory.createAddress(
                2L,
                "101",
                "Tech Avenue",
                "Cape Town",
                "Western Cape",
                "Limpopo",
                "4343"
        );

        customer = createMockCustomer();

         cart = CartFactory.createCart(
                "1",                                // String id
                customer.getCustomerId(),           // String customerId
                List.<CartItem>of(),                // Empty typed list
                customer                            // Customer object
        );

    }

    private static Customer createMockCustomer() {
        Address mockAddress = AddressFactory.createAddress(
                999L,
                "150",
                "Mock Street",
                "Mock City",
                "Mock Province",
                "Mock Region",
                "9999"
        );

        return CustomerFactory.createCustomer(
                "cust-999",
                "MockFirstName",
                "MockLastName",
                "mock@example.com",
                mockAddress
        );
    }

    @Test
    @Order(1)
    void create() {
        addressRepository.save(address);
        customerRepository.save(customer);

        Cart created = service.create(cart);
        assertNotNull(created);
        assertEquals(cart.getId(), created.getId());
        System.out.println("Created Cart: " + created);

        cart = created; // update static cart reference for next tests
    }

    @Test
    @Order(2)
    void read() {
        Cart read = service.read(cart.getId());
        assertNotNull(read);
        assertEquals(cart.getId(), read.getId());
        System.out.println("Read Cart: " + read);
    }

    @Test
    @Order(3)
    void update() {
        Cart updatedCart = new Cart.Builder()
                .copy(cart)
                .customer(customer)   // builder method naming style
                .build();

        Cart updated = service.update(updatedCart);
        assertNotNull(updated);
        assertEquals(cart.getId(), updated.getId());
        System.out.println("Updated Cart: " + updated);

        cart = updated; // update for next tests
    }

    @Test
    @Order(4)
    void delete() {
        assertDoesNotThrow(() -> service.delete(cart.getId()));
        assertNull(service.read(cart.getId()));
        System.out.println("Deleted Cart: " + cart.getId());
    }

    @Test
    @Order(5)
    void getAll() {
        List<Cart> carts = service.getAll();
        assertNotNull(carts);
        assertTrue(carts.size() >= 0);
        System.out.println("All Carts: " + carts);
    }
}
