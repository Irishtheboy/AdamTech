package za.co.admatech.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressControllerTest {

    @Test
    @Order(1)
    void create() {
    }

    @Test
    @Order(2)
    void read() {
    }

    @Test
    @Order(3)
    void update() {
    }

    @Test
    @Order(4)
    void delete() {
    }
}