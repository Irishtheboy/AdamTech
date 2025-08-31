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
            String customerID,
            String firstName,
            String lastName,
            String email,
            //Cart cartID,
            Address address) {


        if (Helper.isNullOrEmpty(lastName)) {

        }


        if (Helper.isNullOrEmpty(firstName)) {

        }

        if (Helper.isNullOrEmpty(lastName)) {

        }

        if(Helper.isValidEmail(email)){
            return null;
        }

        return new Customer.Builder()


        .setFirstName(firstName)
        .setLastName(lastName)
        .setAddress(address)
        .build();

    }
}
