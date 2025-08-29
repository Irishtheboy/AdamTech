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
            Cart cart,
            Address address) {

        //Validating the fields inside the Customer domain
        //Validating the customerID
        if (Helper.isNullOrEmpty(lastName)) {
            return null;
        }

        //Validating the customers first name
        if (Helper.isNullOrEmpty(firstName)) {
            return null;
        }

        //Validating the customer last name
        if (Helper.isNullOrEmpty(lastName)) {
            return null;
        }

        //Validating the customers email address using regex
        if(Helper.isValidEmail(email)){
            return null;
        }

        return new Customer.Builder()
        .setFirstName(firstName)
        .setLastName(lastName)
        .setCart(cart)
        .setAddress(address)
        .build();

    }
}
