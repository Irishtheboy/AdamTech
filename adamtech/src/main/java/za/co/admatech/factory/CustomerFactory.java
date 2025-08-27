package za.co.admatech.factory;
/**
 * CustomerFactory.java
 * CustomerFactory Factory class
 *
 * Author: Rorisang Makgana(230602363)
 */
import za.co.admatech.domain.*;
import za.co.admatech.util.Helper;

public class CustomerFactory {
    public static Customer createCustomer(
            String firstName,
            String lastName,
            String email,
            Address address) {

        //Validating the fields inside the Customer domain
        if (Helper.isNullOrEmpty(firstName) || Helper.isNullOrEmpty(lastName)) {
            return null;
        }

        //Validating the customers email address using regex
        if(!Helper.isValidEmail(email)){
            return null;
        }

        return new Customer.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setAddress(address)
                .build();
    }

    public static Customer buildCustomer(
            Long customerId,
            String firstName,
            String lastName,
            String email,
            Address address,
            Cart cart,
            String phoneNumber) {

        return new Customer.Builder()
                .setCustomerId(customerId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setAddress(address)
                .setCart(cart)
                .setPhoneNumber(phoneNumber)
                .build();
    }
}
