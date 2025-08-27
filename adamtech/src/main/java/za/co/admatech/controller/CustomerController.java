package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Customer;
import za.co.admatech.service.CustomerService;
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public Customer create(@RequestBody Customer customer){
        return customerService.create(customer);
    }

    @GetMapping("/read/{customerID}")
    public Customer read(@PathVariable Long customerID){
        return customerService.read(customerID);
    }

    @PutMapping("/update")
    public Customer update(@RequestBody Customer customer){
        return customerService.update(customer);
    }

    @RequestMapping(value = "/delete/{customerID}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public boolean delete(@PathVariable Long customerID){
        return customerService.delete(customerID);
    }

    @GetMapping("/getall")
    public java.util.List<Customer> getAll(){
        return customerService.getAll();
    }
}
