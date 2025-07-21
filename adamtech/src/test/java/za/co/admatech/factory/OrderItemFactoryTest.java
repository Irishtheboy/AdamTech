/*OrderItemFactory Test Class
  Naqeebah Khan 219099073
  17 May 2025*/
package za.co.admatech.factory;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.OrderItem;
import za.co.admatech.domain.Product;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.domain.enums.ProductCategory;
import za.co.admatech.domain.enums.ProductType;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderItemFactoryTest {
    private static Product product;
    private static Order order;
    private static OrderItem orderItem;
    private static Customer customer;

    @BeforeAll
    public static void setUp() {
        product = ProductFactory.createProduct(
                987L,
                "Test Item",
                "Test Desc",
                new Money(2323, "ZAR"),
                ProductCategory.GAMING,
                ProductType.PERIPHERAL
        );
        customer = CustomerFactory.createCustomer(
                988L,
                "Jane",
                "Smith",
                "jane.smith@example.com",
                "0987654321",
                CartFactory.createCart(989L, null, List.of()),
                List.of(AddressFactory.createAddress(
                        990L,
                        (short) 12,
                        "Main Street",
                        "Suburb",
                        "City",
                        "Province",
                        (short) 1234
                )),
                List.of()
        );
        order = (Order) OrderFactory.createOrder(
                991L,
                LocalDate.of(2020, 1, 1),
                OrderStatus.COMPLETED,
                new Money(2323, "ZAR"),
                List.of(),
                customer
        );
        orderItem = OrderItemFactory.createOrderItem(
                992L,
                1,
                new Money(2323, "ZAR"),
                (za.co.admatech.domain.Order) order,
                product
        );
    }

    private static OrderItem updatedOrderItem;

    @Test
    void createOrderItem1() {
        assertNotNull(orderItem);
        System.out.println(orderItem);

    }

    @Test
    void read() {
        OrderItem read = orderItem;
        assertNotNull(read);
        System.out.println(read);

    }

    @Test
    void update() {
        updatedOrderItem = new OrderItem.Builder()
                .copy(orderItem)
                .setQuantity(10)
                .build();
        assertNotNull(updatedOrderItem);
        assertEquals(10, updatedOrderItem.getQuantity());
        System.out.println(updatedOrderItem);

    }

    @Test
    void delete() {
        orderItem = null;
        assertNull(orderItem);
        System.out.println("Order item deleted successfully");

    }
}