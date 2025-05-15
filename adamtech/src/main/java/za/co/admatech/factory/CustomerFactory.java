package za.co.admatech.factory;

import za.co.admatech.domain.*;
import za.co.admatech.util.Helper;

public class CustomerFactory {
    public static Customer createCustomer(
            String customerID,
            String firstName,
            String lastName,
            String email,
            Cart cartID,
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

        if(Helper.isValidCartID(cartID)){
            return null;
        }
        return new Customer.Builder()
        .setCartID(cartID)
        .setCustomerID(customerID)
        .setFirstName(firstName)
        .setLastName(lastName)
        .setAddress(address)
        .build();

    }
}
