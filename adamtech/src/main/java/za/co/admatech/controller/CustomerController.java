package za.co.admatech.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.DTO.LoginRequest;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.Customer;
import za.co.admatech.service.CustomerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        Cart cart = new Cart(); // new empty cart
        customer.setCart(cart); // link cart to customer
        return ResponseEntity.ok(customerService.create(customer));
    }

    // ✅ Read by email (PK)
    @GetMapping("/read/{email}")
    public Customer read(@PathVariable String email) {
        return customerService.read(email);
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> update(@RequestBody Customer customer) {
        Customer updatedCustomer = customerService.update(customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    // ✅ Delete by email (PK)
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Void> delete(@PathVariable String email) {
        boolean deleted = customerService.delete(email);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }

    // 🔒 Secure endpoint to get logged-in customer
    @GetMapping("/me")
    public ResponseEntity<Customer> getLoggedInCustomer(HttpSession session) {
        String email = (String) session.getAttribute("email");

        if (email == null) {
            return ResponseEntity.status(401).build(); // ❌ No session → Unauthorized
        }

        Customer customer = customerService.read(email);
        if (customer == null) {
            return ResponseEntity.status(401).build(); // ❌ Session invalid or user deleted
        }

        return ResponseEntity.ok(customer); // ✅ Valid session + user exists
    }

    // ✅ Login (check password and store email in session)
    @PostMapping("/login")
    public ResponseEntity<Customer> login(@RequestBody LoginRequest request, HttpSession session) {
        Optional<Customer> optionalCustomer = customerService.findByEmail(request.getEmail());

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(request.getPassword(), customer.getPassword())) {
                session.setAttribute("email", customer.getEmail()); // ✅ login successful
                return ResponseEntity.ok(customer);
            } else {
                return ResponseEntity.status(401).build(); // ❌ invalid password
            }
        }

        return ResponseEntity.status(401).build(); // ❌ invalid email
    }
}
