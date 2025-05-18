package za.co.admatech.util;

import org.apache.commons.validator.routines.EmailValidator;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.enums.InventoryStatus;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.domain.enums.PaymentStatus;

import java.time.LocalDate;

import java.util.UUID;

public class Helper {
    public static boolean isNullOrEmpty(String s) {
        if (s.isEmpty() || s == null)
            return true;
        return false;


    }

    public static String generateId() {
        return UUID.randomUUID().toString();

    }
//    public static String generateId() {
//        return java.util.UUID.randomUUID().toString();
//    }


    public static boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        if (validator.isValid(email)) {
            return true;
        }else {
            return false;
        }
    }
    // Todo: isValidPostalCode method - 4 digits with range 1000 to 9999
    public static boolean isValidPostalCode(short postalCode) {
        if (postalCode < 1000 || postalCode > 9999) {
            return false;
        }
        return true;
    }

    // Todo: isValidStreetNumber method - 1 to 5 digits with range 1 to 99999
    public static boolean isValidStreetNumber(short streetNumber) {
        if (streetNumber < 1 || streetNumber > 99999) {
            return false;
        }
        return true;
    }

    public static boolean isValidLocalDate(LocalDate date) {
        if (date == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return (!date.isAfter(today)) && date.getYear() >= 1900;
    }


    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            return false;
        }
        return true;
    }

    // PaymentStatus
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

    // OrderStatus
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

    // InventoryStatus
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

    // inventoryStatus
    public static boolean isValidInventoryStatus(InventoryStatus inventoryStatus) {
        if (inventoryStatus == null) return false;
        for (InventoryStatus status : InventoryStatus.values()) {
            if (status.equals(inventoryStatus)) return true;
        }
        return false;
    }
    public static InventoryStatus getInventoryStatusFromString(InventoryStatus inventoryStatus) {
        if (inventoryStatus == null) return null;
        for (InventoryStatus status : InventoryStatus.values()) {
            if (status.equals(inventoryStatus)) return status;
        }
        return null;
    }


    //Validation methods to validate the customer fields: the customer address
    //1. Validating whether the customer's cart ID matches the initial card ID issued
    public static Cart isValidCartID(Cart cartID){
        if(cartID == null){
            return null;
        }

        return cartID;
    }
    //Validating the 
}



