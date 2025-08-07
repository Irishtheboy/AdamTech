package za.co.admatech.factory;


import za.co.admatech.domain.Address;
import za.co.admatech.domain.Customer;
import za.co.admatech.util.Helper;

public class CustomerFactory {
    public static Customer createCustomer(String customerId, String firstName, String lastName, String email, Address address) {
        if (Helper.isNullOrEmpty(customerId) || Helper.isNullOrEmpty(firstName) || Helper.isNullOrEmpty(lastName) || !Helper.isValidEmail(email) || address == null) {
            return null;
        }
        return new Customer.Builder()
                .customerId(customerId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .address(address)
                .build();
    }

    public static Customer createCustomer(String firstName, String lastName, String email, Address address) {
        return createCustomer(Helper.generateId(), firstName, lastName, email, address);
    }
}