package za.co.admatech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.admatech.domain.Money;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/money")
public class MoneyController {

    // For demo: return a Money object with values from query params
    @GetMapping("/create")
    public ResponseEntity<Money> createMoney(
            @RequestParam BigDecimal amount,
            @RequestParam String currency
    ) {
        if (amount == null || currency == null || currency.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Money money = new Money.Builder()
                .amount(amount)
                .currency(currency)
                .build();

        return ResponseEntity.ok(money);
    }

    // Example: convert Money to string representation (simulate usage)
    @PostMapping("/format")
    public ResponseEntity<Map<String, String>> formatMoney(@RequestBody Money money) {
        if (money == null || money.getAmount() == null || money.getCurrency() == null) {
            return ResponseEntity.badRequest().build();
        }

        Map<String, String> result = new HashMap<>();
        result.put("formatted", money.getCurrency() + " " + money.getAmount());

        return ResponseEntity.ok(result);
    }
}
