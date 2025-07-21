/*





CustomerFactory.java



Author: Rorisang Makgana (230602363) */ package za.co.admatech.factory;

import za.co.admatech.domain.Address;
import za.co.admatech.domain.Cart;
import za.co.admatech.domain.Customer;
import za.co.admatech.domain.Order;
import za.co.admatech.util.Helper;

import java.util.List;

public class CustomerFactory {
    public static Customer createCustomer(Long id,
                                          String firstName,
                                          String lastName,
                                          String email,
                                          String phoneNumber,
                                          Cart cart, List addresses, List orders) {
        if (Helper.isNullOrEmpty(firstName) ||
                Helper.isNullOrEmpty(lastName) ||
                Helper.isNullOrEmpty(email) ||
                Helper.isNullOrEmpty(phoneNumber))
        { throw new IllegalArgumentException("First name, last name, email, and phone number must not be empty"); }
        if (!Helper.isValidEmail(email) || !Helper.isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid email or phone number"); }
        if (cart == null || !Helper.isValidCart(cart))
        { throw new IllegalArgumentException("Invalid cart"); }
        if (addresses == null || addresses.isEmpty()) {
            throw new IllegalArgumentException("Addresses list cannot be null or empty"); }

        return new Customer.Builder()
                .setCustomerID(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .setCart(cart)
                .setAddress(addresses)
                .setOrders(orders)
                .build(); } }

