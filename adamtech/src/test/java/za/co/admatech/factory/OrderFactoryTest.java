/*OrderFactory Test Class
  Naqeebah Khan 219099073
  17 May 2025*/
package za.co.admatech.factory;

import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Order;
import za.co.admatech.domain.enums.OrderStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class OrderFactoryTest {

    private static Order order1 = OrderFactory.createOrder(
            "CUSTORD1001",
            LocalDate.of(2025, 05, 05),
            OrderStatus.PENDING,
            new Money.Builder()
                    .setAmount(450)
                    .build()

    );

    public static Order order2 = OrderFactory.createOrder(
            "CUSTORD1002",
            LocalDate.of(2025,06,06),
            OrderStatus.CONFIRMED,
            new Money.Builder()
                    .setAmount(850)
                    .build()
    );

    private static Order updatedOrder;

    @Test
//    @Order(1)
    public void createOrder1(){
        assertNotNull(order1);
        System.out.println(order1.toString());
    }

    @Test
//    @Order(2)
    void createOrder2(){
        assertNotNull(order2);
        System.out.println(order2.toString());
    }

    @Test
//    @Order(3)
    void read(){
        Order read = order1;
        assertNotNull(read);
        System.out.println(read);
    }



    @Test
//    @Order(4)
    void update(){
        updatedOrder =  new Order.Builder()
                .copy(order1)
                .setOrderStatus(OrderStatus.SHIPPED)
                .build();
        assertNotNull(updatedOrder);
        assertEquals(OrderStatus.SHIPPED, updatedOrder.getOrderStatus());
        System.out.println(updatedOrder);
    }

    @Test
//    @Order(5)
    void delete(){
        order1 = null;
        assertNull(order1);
        System.out.println("Order deleted successfully");
    }


    }


  
