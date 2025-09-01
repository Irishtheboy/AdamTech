package za.co.admatech.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.DTO.*;
import za.co.admatech.domain.Customer;
import za.co.admatech.service.CustomerService;

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
            // Validation
            if (request.getFirstName() == null || request.getFirstName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("First name is required"));
            }
            if (request.getLastName() == null || request.getLastName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Last name is required"));
            }
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Email is required"));
            }
            if (request.getPhoneNumber() == null || request.getPhoneNumber().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Phone number is required"));
            }

            // Check if email already exists
            Optional<Customer> existingCustomer = customerService.findByEmail(request.getEmail());
            if (existingCustomer.isPresent()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Email already exists"));
            }

            // Create customer from request
            Customer customer = customerService.createFromRegistrationRequest(request);
            Customer savedCustomer = customerService.create(customer);
            
            return ResponseEntity.ok(new RegistrationResponse(savedCustomer, "Customer created successfully"));
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
            // TODO: Add proper password validation here
            // For now, we'll accept any password for demonstration
            session.setAttribute("customerId", customer.getCustomerId());
            return ResponseEntity.ok(new LoginResponse(customer, "Login successful"));
        }

        return ResponseEntity.status(401).body(new ErrorResponse("Invalid email or password"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(new SuccessResponse("Logged out successfully"));
    }



}
