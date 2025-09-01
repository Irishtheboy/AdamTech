package za.co.admatech.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.DTO.*;
import za.co.admatech.domain.Customer;
import za.co.admatech.service.CustomerService;
import za.co.admatech.util.Helper;

import java.util.List; // <-- correct import
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
    public ResponseEntity<?> create(@RequestBody CustomerRegistrationRequest request){
        try {
            // Enhanced validation with Helper class
            if (request.getFirstName() == null || request.getFirstName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("First name is required"));
            }
            if (!Helper.isValidName(request.getFirstName())) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Invalid first name. Must be 2-50 characters with letters, spaces, hyphens, or apostrophes only."));
            }
            
            if (request.getLastName() == null || request.getLastName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Last name is required"));
            }
            if (!Helper.isValidName(request.getLastName())) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Invalid last name. Must be 2-50 characters with letters, spaces, hyphens, or apostrophes only."));
            }
            
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Email is required"));
            }
            if (!Helper.isValidEmail(request.getEmail())) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Invalid email address"));
            }
            
            if (request.getPhoneNumber() == null || request.getPhoneNumber().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Phone number is required"));
            }
            if (!Helper.isValidPhoneNumber(request.getPhoneNumber())) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Invalid phone number. Must be a valid international or local format."));
            }
            
            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Password is required"));
            }
            if (!Helper.isValidPassword(request.getPassword())) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Invalid password. Must be at least 8 characters long."));
            }
            
            // Address validation
            if (request.getAddress() == null) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Address is required"));
            }
            if (!Helper.isValidStreetAddress(request.getAddress().getStreet())) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Invalid street address. Must be 5-100 characters long."));
            }
            if (!Helper.isValidCity(request.getAddress().getCity())) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Invalid city name. Must be 2-50 characters with letters, spaces, hyphens, or apostrophes only."));
            }

            // Check if email already exists
            Optional<Customer> existingCustomer = customerService.findByEmail(request.getEmail());
            if (existingCustomer.isPresent()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Email already exists"));
            }

            // Create customer from request (validation happens in service layer)
            Customer customer = customerService.createFromRegistrationRequest(request);
            Customer savedCustomer = customerService.create(customer);
            
            return ResponseEntity.ok(new RegistrationResponse(savedCustomer, "Customer created successfully"));
        } catch (IllegalArgumentException e) {
            // Catch validation exceptions from service layer
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Registration failed: " + e.getMessage()));
        }
    }

    @GetMapping("/read/{customerID}")
    public Customer read(@PathVariable Long customerID){
        return customerService.read(customerID);
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> update(@RequestBody Customer customer){
        Customer updatedCustomer = customerService.update(customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @RequestMapping("/delete/{customerID}")
    public ResponseEntity<Void> delete(@PathVariable Long customerID){
        boolean deleted = customerService.delete(customerID);
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


    @GetMapping("/me")
    public ResponseEntity<?> getLoggedInCustomer(HttpSession session) {
        Long customerId = (Long) session.getAttribute("customerId");

        if (customerId != null) {
            Customer customer = customerService.read(customerId);
            if (customer != null) {
                return ResponseEntity.ok(customer);
            }
        }

        return ResponseEntity.status(401).body(new ErrorResponse("Not authenticated"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Email is required"));
        }

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Password is required"));
        }

        Optional<Customer> optionalCustomer = customerService.findByEmail(request.getEmail());

        if(optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            
            // Verify password using BCrypt
            if (Helper.verifyPassword(request.getPassword(), customer.getPasswordHash())) {
                session.setAttribute("customerId", customer.getCustomerId());
                return ResponseEntity.ok(new LoginResponse(customer, "Login successful"));
            } else {
                return ResponseEntity.status(401).body(new ErrorResponse("Invalid email or password"));
            }
        }

        return ResponseEntity.status(401).body(new ErrorResponse("Invalid email or password"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(new SuccessResponse("Logged out successfully"));
    }



}
