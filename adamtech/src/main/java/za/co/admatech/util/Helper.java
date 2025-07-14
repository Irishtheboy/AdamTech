package za.co.admatech.util;

import org.apache.commons.validator.routines.EmailValidator;
import za.co.admatech.domain.*;
import za.co.admatech.domain.enums.InventoryStatus;
import za.co.admatech.domain.enums.OrderStatus;
import za.co.admatech.domain.enums.PaymentStatus;

import java.time.LocalDate;

import java.util.UUID;

public class Helper {
    /* METHODS FOR VALIDATING THE CUSTOMER DOMAIN
    */
    public static boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        if (validator.isValid(email)) {
            return true;
        }else {
            return false;
        }
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            return false;
        }
        return true;
    }

    public static boolean isNullOrEmpty(String s) {
        if (s.isEmpty() || s == null)
            return true;
        return false;
    }

    //Validation methods to validate the customer fields: the customer address
    //1. Validating whether the customer's cart ID matches the initial card ID issued
    public static boolean isValidCart(Cart cart){
        if(cart == null){
            return true;
        }
        return false;
    }
    public static boolean isValidCustomer(Customer customer) {
        return customer != null &&
                !isNullOrEmpty(customer.getFirstName()) &&
                !isNullOrEmpty(customer.getLastName()) &&
                isValidEmail(customer.getEmail()) &&
                isValidPhoneNumber(customer.getPhoneNumber());
    }

    /* END OF CHANGES
     */

    /* METHODS FOR VALIDATING THE ADDRESS DOMAIN
    */
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

    /* END OF CHANGES
     */

    /* METHODS FOR VALIDATING THE INVENTORY DOMAIN
     */
    public static boolean isValidInventoryStatus(String input) {
        if (input == null || input.isEmpty()) return false;
        for (InventoryStatus status : InventoryStatus.values()) {
            if (status.getStatus().equalsIgnoreCase(input)) return true;
        }
        return false;
    }
    public static boolean isValidInventoryStatus(InventoryStatus status) {
        return status != null;
    }

    public static InventoryStatus getInventoryStatusFromString(String input) {
        if (input == null) return null;
        for (InventoryStatus status : InventoryStatus.values()) {
            if (status.getStatus().equalsIgnoreCase(input)) return status;
        }
        return null;
    }
    public static boolean isValidInventory(Long inventoryId, InventoryStatus status) {
        return inventoryId != null &&
                inventoryId >= 0 &&
                isValidInventoryStatus(status.getInventoryStatus());
    }

    /* END OF CHANGES
     */

    /* METHODS FOR VALIDATING THE ORDER DOMAIN
     */
    public static boolean isValidLocalDate(LocalDate date) {
        if (date == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return (!date.isAfter(today)) && date.getYear() >= 1900;
    }

    public static OrderStatus getOrderStatusFromString(String input) {
        if (input == null) return null;
        for (OrderStatus status : OrderStatus.values()) {
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
    public static boolean isValidOrder(Order order) {
        return order != null &&
                isValidLocalDate(order.getOrderDate()) &&
                order.getCustomer() != null &&
                order.getOrderItems() != null &&
                isValidOrderStatus(order.getOrderStatus().toString());
    }

    /* END OF CHANGES
     */

    /* METHODS FOR VALIDATING THE PAYMENT DOMAIN
     */
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
    public static boolean isValidPayment(Payment payment) {
        return payment != null &&
                isValidLocalDate(payment.getPaymentDate()) &&
                payment.getAmount() != null &&
                isValidPaymentStatus(payment.getPaymentStatus());
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }
    public static boolean isNullOrEmpty(){

        return false;
    }
    /* END OF CHANGES
     */

    /* METHODS FOR VALIDATING THE PRODUCT DOMAIN
     */
    public static boolean isValidProductName(String name) {
        return name != null && name.matches("^[A-Za-z0-9\\s\\-_,\\.]{2,50}$");
    }

    public static boolean isValidProduct(Product product) {
        if (product == null) return false;
        else return true;
    }
    /* END OF CHANGES
     */
}



