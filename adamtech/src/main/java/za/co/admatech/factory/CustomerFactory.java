package za.co.admatech.factory;
/**
 * CustomerFactory.java
 * CustomerFactory Factory class
 *
 * Author: Rorisang Makgana(230602363)
 */
import za.co.admatech.domain.*;
import za.co.admatech.util.Helper;

import java.util.List;

public class CustomerFactory {
    public static Customer createCustomer(
            Long customerID,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            Cart cart,
            List<Address> address,
            List<Order> orders) {

        if (Helper.isNullOrEmpty(firstName) || Helper.isNullOrEmpty(lastName) ||
                Helper.isNullOrEmpty(email) || Helper.isNullOrEmpty(phoneNumber)) {
            return null;
        }

        if (!Helper.isValidEmail(email) || !Helper.isValidPhoneNumber(phoneNumber)) {
            return null;
        }

        if (cart == null) {
            return null;
        }

        if (address == null || address.isEmpty()) {
            return null;
        }

        for (Address addr : address) {
            if (!Helper.isValidAddress(addr)) {
                return null;
            }
        }

        // Optional: validate orders
        if (orders != null) {
            for (Order order : orders) {
                if (!Helper.isValidOrder(order)) {
                    return null;
                }
            }
        }

        return new Customer.Builder()
                .setCustomerID(customerID)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .setCart(cart)
                .setAddress(address)
                .setOrders(orders)
                .build();
    }

}
