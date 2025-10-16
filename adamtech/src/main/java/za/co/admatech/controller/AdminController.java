package za.co.admatech.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Customer;
import za.co.admatech.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')") // This secures ALL endpoints in this controller
public class AdminController {

    private final CustomerService customerService;

    public AdminController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Get all users (admin only)
    @GetMapping("/users")
    public List<Customer> getAllUsers() {
        return customerService.getAll();
    }

    // Make user admin
    @PostMapping("/make-admin/{email}")
    public String makeAdmin(@PathVariable String email) {
        Customer customer = customerService.read(email);
        if (customer != null) {
            customer.setRole("ROLE_ADMIN");
            customerService.update(customer);
            return "User " + email + " is now an ADMIN";
        }
        return "User not found";
    }

    // Make user regular user
    @PostMapping("/make-user/{email}")
    public String makeUser(@PathVariable String email) {
        Customer customer = customerService.read(email);
        if (customer != null) {
            customer.setRole("ROLE_USER");
            customerService.update(customer);
            return "User " + email + " is now a USER";
        }
        return "User not found";
    }

    // Other admin endpoints...
    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Welcome to Admin Dashboard";
    }
}