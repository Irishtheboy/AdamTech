package za.co.admatech.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.DTO.LoginRequest;
import za.co.admatech.config.JwtUtils;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.Customer;
import za.co.admatech.service.CustomerService;
import java.util.stream.Collectors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public CustomerController(CustomerService customerService,
                              AuthenticationManager authenticationManager,
                              JwtUtils jwtUtils) {
        this.customerService = customerService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
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
//    @GetMapping("/me")
//    public ResponseEntity<Customer> getLoggedInCustomer(HttpSession session) {
//        String email = (String) session.getAttribute("email");
//
//        if (email == null) {
//            return ResponseEntity.status(401).build(); // ❌ No session → Unauthorized
//        }
//
//        Customer customer = customerService.read(email);
//        if (customer == null) {
//            return ResponseEntity.status(401).build(); // ❌ Session invalid or user deleted
//        }
//
//        return ResponseEntity.ok(customer); // ✅ Valid session + user exists
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("=== LOGIN ATTEMPT ===");
            System.out.println("Email: " + request.getEmail());
            System.out.println("Password: " + request.getPassword());

            // Debug: Check if user exists
            Customer customer = customerService.read(request.getEmail());
            if (customer != null) {
                System.out.println("Customer found: " + customer.getEmail());
                System.out.println("Stored password hash: " + customer.getPassword());
            } else {
                System.out.println("Customer NOT found");
                return ResponseEntity.status(401).body("Invalid email or password");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            Customer authCustomer = (Customer) authentication.getPrincipal();

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("email", authCustomer.getEmail());
            response.put("role", authCustomer.getRole());

            System.out.println("=== LOGIN SUCCESS ===");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("=== LOGIN FAILED ===");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    // 🔒 Secure endpoint to get logged-in customer (now uses JWT)
    @GetMapping("/me")
    public ResponseEntity<Customer> getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Customer customer = customerService.read(email);
        if (customer == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(customer);
    }

    // ✅ Fix roles for existing users
    @PostMapping("/fix-user-roles")
    public ResponseEntity<String> fixUserRoles() {
        try {
            List<Customer> customers = customerService.getAll();
            int fixed = 0;

            for (Customer customer : customers) {
                if (customer.getRole() == null || customer.getRole().trim().isEmpty()) {
                    // Set default role as USER
                    customer.setRole("ROLE_USER");
                    customerService.update(customer);
                    fixed++;
                    System.out.println("Fixed role for: " + customer.getEmail() + " -> ROLE_USER");
                }
            }

            return ResponseEntity.ok("Fixed roles for " + fixed + " users. Default: ROLE_USER");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ✅ Special endpoint to make a user admin
    @PostMapping("/make-admin/{email}")
    public ResponseEntity<String> makeUserAdmin(@PathVariable String email) {
        try {
            Customer customer = customerService.read(email);
            if (customer == null) {
                return ResponseEntity.badRequest().body("User not found");
            }

            customer.setRole("ROLE_ADMIN");
            customerService.update(customer);

            System.out.println("User " + email + " promoted to ADMIN");
            return ResponseEntity.ok("User " + email + " is now an ADMIN");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ✅ Get all users (for admin panel)
    @GetMapping("/admin/users")
    public ResponseEntity<List<Customer>> getAllUsers() {
        // In a real app, you'd check for admin role here
        return ResponseEntity.ok(customerService.getAll());
    }
}
