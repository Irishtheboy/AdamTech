/* Helper.java Author: Various */ package za.co.admatech.util;

import org.apache.commons.validator.routines.EmailValidator; import za.co.admatech.domain.*; import za.co.admatech.domain.enums.InventoryStatus; import za.co.admatech.domain.enums.OrderStatus; import za.co.admatech.domain.enums.PaymentStatus;

import java.time.LocalDate; import java.util.UUID;

public class Helper { /* METHODS FOR VALIDATING THE CUSTOMER DOMAIN */ public static boolean isValidEmail(String email) { EmailValidator validator = EmailValidator.getInstance(); return validator.isValid(email); }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10}");
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isValidCart(Cart cart) {
        return cart != null;
    }

    public static boolean isValidCustomer(Customer customer) {
        return customer != null &&
                !isNullOrEmpty(customer.getFirstName()) &&
                !isNullOrEmpty(customer.getLastName()) &&
                isValidEmail(customer.getEmail()) &&
                isValidPhoneNumber(customer.getPhoneNumber());
    }

    /* METHODS FOR VALIDATING THE ADDRESS DOMAIN */
    public static boolean isValidPostalCode(short postalCode) {
        return postalCode >= 1000 && postalCode <= 9999;
    }

    public static boolean isValidStreetNumber(short streetNumber) {
        return streetNumber >= 1 && streetNumber <= 99999;
    }

    public static boolean isValidAddress(Address address) {
        return address != null &&
                isValidStreetNumber(address.getStreetNumber()) &&
                !isNullOrEmpty(address.getStreetName()) &&
                !isNullOrEmpty(address.getCity()) &&
                isValidPostalCode(address.getPostalCode());
    }

    public static boolean isValidPostalCodeRegex(String postalCode) {
        return postalCode != null && postalCode.matches("^[1-9]\\d{3}$");
    }

    /* METHODS FOR VALIDATING THE INVENTORY DOMAIN */
    public static boolean isValidInventoryStatus(InventoryStatus status) {
        return status != null;
    }

    public static boolean isValidInventory(Long inventoryId, InventoryStatus status) {
        return inventoryId != null && inventoryId >= 0 && status != null;
    }

    /* METHODS FOR VALIDATING THE ORDER DOMAIN */
    public static boolean isValidLocalDate(LocalDate date) {
        if (date == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return !date.isAfter(today) && date.getYear() >= 1900;
    }

    public static boolean isValidOrderStatus(OrderStatus status) {
        return status != null;
    }

    public static boolean isValidOrder(Order order) {
        return order != null &&
                isValidLocalDate(order.getOrderDate()) &&
                order.getCustomer() != null &&
                order.getOrderItems() != null &&
                isValidOrderStatus(order.getOrderStatus());
    }

    /* METHODS FOR VALIDATING THE PAYMENT DOMAIN */
    public static boolean isValidPaymentStatus(PaymentStatus status) {
        return status != null;
    }

    public static boolean isValidPayment(Payment payment) {
        return payment != null &&
                isValidLocalDate(payment.getPaymentDate()) &&
                payment.getAmount() != null &&
                isValidPaymentStatus(payment.getPaymentStatus());
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    /* METHODS FOR VALIDATING THE PRODUCT DOMAIN */
    public static boolean isValidProductName(String name) {
        return name != null && name.matches("^[A-Za-z0-9\\s\\-_,\\.]{2,50}$");
    }

    public static boolean isValidProduct(Product product) {
        return product != null && !isNullOrEmpty(product.getProductName()) && product.getProductPriceAmount() != null;
    }

}