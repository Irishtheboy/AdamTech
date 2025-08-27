package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Payment;
import za.co.admatech.service.PaymentService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService service;

    @Autowired
    public PaymentController(PaymentService service) {
        this.service = service;
    }

    // Create a new payment
    @PostMapping("/create")
    public ResponseEntity<Payment> create(@RequestBody Payment payment) {
        return ResponseEntity.ok(service.create(payment));
    }

    // Read a payment by ID
    @GetMapping("/read/{id}")
    public ResponseEntity<Payment> read(@PathVariable Long id) {
        Payment payment = service.read(id);
        return payment != null ? ResponseEntity.ok(payment) : ResponseEntity.notFound().build();
    }

    // Update a payment
    @PutMapping("/update/{id}")
    public ResponseEntity<Payment> update(@PathVariable Long id, @RequestBody Payment payment) {
        Payment updatedPayment = new Payment.Builder()
                .copy(payment)
                .setId(id)
                .build();
        Payment updated = service.update(updatedPayment);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Delete a payment
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Get all payments
    @GetMapping("/getAll")
    public ResponseEntity<List<Payment>> getAll() {
        return ResponseEntity.ok(service.getPayments());
    }
}
