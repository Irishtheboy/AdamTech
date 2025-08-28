package za.co.admatech.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Customer;
import za.co.admatech.service.CustomerService;

import java.util.List; // <-- correct import

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<Customer> create(@RequestBody Customer customer){
        return ResponseEntity.ok(customerService.create(customer));
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
        return ResponseEntity.ok(customerService.getAll()); // <-- use instance, not class
    }


    @GetMapping("/me")
    public ResponseEntity<Customer> getLoggedInCustomer(HttpSession session) {
        // Option 1: Using session attribute (replace "customerId" with your session key)
        Long customerId = (Long) session.getAttribute("customerId");

        if (customerId != null) {
            Customer customer = customerService.read(customerId);
            if (customer != null) {
                return ResponseEntity.ok(customer);
            }
        }

        // Option 2: Temporary: return first customer as test
        List<Customer> allCustomers = customerService.getAll();
        if (!allCustomers.isEmpty()) {
            return ResponseEntity.ok(allCustomers.get(0));
        }

        return ResponseEntity.notFound().build();
    }

}
