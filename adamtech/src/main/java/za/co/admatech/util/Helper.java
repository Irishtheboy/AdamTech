package za.co.admatech.util;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.enums.InventoryStatus;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.domain.enums.PaymentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

public class Helper {
    
    // BCrypt encoder instance (thread-safe)
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    
    // Regex patterns for validation
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[1-9]\\d{1,14}$|^\\d{10}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s'-]{2,50}$");
    private static final Pattern STRONG_PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
    );
    
    // String validation
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
    
    public static boolean isValidString(String s, int minLength, int maxLength) {
        return !isNullOrEmpty(s) && s.length() >= minLength && s.length() <= maxLength;
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    // Enhanced email validation
    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) {
            return false;
        }
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email) && email.length() <= 100;
    }
    
    // Name validation (first name, last name)
    public static boolean isValidName(String name) {
        return !isNullOrEmpty(name) && NAME_PATTERN.matcher(name).matches();
    }
    
    // Password validation and encryption
    public static boolean isValidPassword(String password) {
        if (isNullOrEmpty(password)) {
            return false;
        }
        // Basic validation: at least 8 characters
        return password.length() >= 8;
    }
    
    public static boolean isStrongPassword(String password) {
        if (isNullOrEmpty(password)) {
            return false;
        }
        // Strong password: at least 8 chars, 1 digit, 1 lower, 1 upper, 1 special
        return STRONG_PASSWORD_PATTERN.matcher(password).matches();
    }
    
    public static String hashPassword(String plainPassword) {
        if (isNullOrEmpty(plainPassword)) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return passwordEncoder.encode(plainPassword);
    }
    
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (isNullOrEmpty(plainPassword) || isNullOrEmpty(hashedPassword)) {
            return false;
        }
        return passwordEncoder.matches(plainPassword, hashedPassword);
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

    // Enhanced phone number validation
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (isNullOrEmpty(phoneNumber)) {
            return false;
        }
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }
    
    // South African phone number validation
    public static boolean isValidSAPhoneNumber(String phoneNumber) {
        if (isNullOrEmpty(phoneNumber)) {
            return false;
        }
        // SA phone numbers: +27XXXXXXXXX or 0XXXXXXXXX
        return phoneNumber.matches("^(\\+27|0)[1-9]\\d{8}$");
    }
    
    // Address validation
    public static boolean isValidStreetAddress(String street) {
        return isValidString(street, 5, 100);
    }
    
    public static boolean isValidCity(String city) {
        return !isNullOrEmpty(city) && city.matches("^[a-zA-Z\\s'-]{2,50}$");
    }
    
    public static boolean isValidProvince(String province) {
        return !isNullOrEmpty(province) && province.matches("^[a-zA-Z\\s'-]{2,50}$");
    }

    public static boolean isValidPaymentStatus(PaymentStatus status) {
        return status != null;
    }

    public static PaymentStatus getPaymentStatusFromString(String input) {
        if (input == null) return null;
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.getStatus().equalsIgnoreCase(input)) return status;
        }
        return null;
    }

    public static boolean isValidOrderStatus(String input) {
        if (input == null || input.isEmpty()) return false;
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getStatus().equalsIgnoreCase(input)) return true;
        }
        return false;
    }

    public static OrderStatus getOrderStatusFromString(String input) {
        if (input == null) return null;
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getStatus().equalsIgnoreCase(input)) return status;
        }
        return null;
    }

    public static boolean isValidInventoryStatus(String input) {
        if (input == null || input.isEmpty()) return false;
        for (InventoryStatus status : InventoryStatus.values()) {
            if (status.getStatus().equalsIgnoreCase(input)) return true;
        }
        return false;
    }

    public static InventoryStatus getInventoryStatusFromString(String input) {
        if (input == null) return null;
        for (InventoryStatus status : InventoryStatus.values()) {
            if (status.getStatus().equalsIgnoreCase(input)) return status;
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