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

        //Validating the fields inside the Customer domain
        //Validating the customerID
        if (Helper.isNullOrEmpty(lastName)) {

        }

        //Validating the customers first name
        if (Helper.isNullOrEmpty(firstName)) {

        }
        //Validating the customer last name
        if (Helper.isNullOrEmpty(lastName)) {

        }
        //Validating the customers email address using regex
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
