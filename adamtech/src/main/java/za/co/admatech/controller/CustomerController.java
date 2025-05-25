package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Customer;
import za.co.admatech.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public Customer create(@RequestBody Customer customer){
        return customerService.create(customer);
    }

    @GetMapping("/read/{customerID}")
    public Customer read(@PathVariable String customerID){
        return customerService.read(customerID);
    }

    @PutMapping("/update")
    public Customer update(@RequestBody Customer customer){
        return customerService.update(customer);
    }

    @RequestMapping(value = "/delete/{customerID}", method = RequestMethod.DELETE)
    public Customer delete(@PathVariable String customerID){
        return customerService.delete(customerID);
    }
}
