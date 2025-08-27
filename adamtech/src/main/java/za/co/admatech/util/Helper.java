package za.co.admatech.util;

import org.apache.commons.validator.routines.EmailValidator;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.enums.InventoryStatus;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.domain.enums.PaymentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Helper {
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    public static boolean isValidPostalCode(short postalCode) {
        return postalCode >= 1000 && postalCode <= 9999;
    }

    public static boolean isValidStreetNumber(short streetNumber) {
        return streetNumber >= 1 && streetNumber <= 99999;
    }

    public static boolean isValidLocalDate(LocalDate date) {
        if (date == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return !date.isAfter(today) && date.getYear() >= 1900;
    }

    public static boolean isValidLocalDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        return !dateTime.isAfter(now) && dateTime.getYear() >= 1900;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            return false;
        }
        return true;
    }

    public static boolean isValidPaymentStatus(PaymentStatus status) {
        return status != null;
    }

    public static PaymentStatus getPaymentStatusFromString(String input) {
        if (input == null) return null;
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.name().equalsIgnoreCase(input)) return status;
        }
        return null;
    }

    public static boolean isValidOrderStatus(String input) {
        if (input == null || input.isEmpty()) return false;
        for (OrderStatus status : OrderStatus.values()) {
            if (status.name().equalsIgnoreCase(input)) return true;
        }
        return false;
    }

    public static boolean isValidOrderStatus(OrderStatus status) {
        return status != null;
    }

    public static OrderStatus getOrderStatusFromString(String input) {
        if (input == null) return null;
        for (OrderStatus status : OrderStatus.values()) {
            if (status.name().equalsIgnoreCase(input)) return status;
        }
        return null;
    }

    public static boolean isValidInventoryStatus(String input) {
        if (input == null || input.isEmpty()) return false;
        for (InventoryStatus status : InventoryStatus.values()) {
            if (status.name().equalsIgnoreCase(input)) return true;
        }
        return false;
    }

    public static InventoryStatus getInventoryStatusFromString(String input) {
        if (input == null) return null;
        for (InventoryStatus status : InventoryStatus.values()) {
            if (status.name().equalsIgnoreCase(input)) return status;
        }
        return null;
    }

    public static boolean isValidInventoryStatus(InventoryStatus inventoryStatus) {
        return inventoryStatus != null;
    }

    public static InventoryStatus getInventoryStatusFromString(InventoryStatus inventoryStatus) {
        return inventoryStatus;
    }

    public static Cart isValidCartID(Cart cartID) {
        return cartID; // Simplified; assumes cart is valid if not null
    }
}