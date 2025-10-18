package za.co.admatech.factory;
/**
 * CustomerFactory.java
 * Factory class for Customer
 *
 * Author: Rorisang Makgana (230602363)
 */
import za.co.admatech.domain.*;
import za.co.admatech.util.Helper;

public class CustomerFactory {

    public static Customer createCustomer(
            String firstName,
            String lastName,
            String email,
            String password,
            Address address) {

        // 🔹 Validate required fields
        if (Helper.isNullOrEmpty(firstName) ||
                Helper.isNullOrEmpty(lastName) ||
                Helper.isNullOrEmpty(email) ||
                Helper.isNullOrEmpty(password)) {
            return null; // invalid input
        }

        // 🔹 Validate email format
        if (!Helper.isValidEmail(email)) {
            return null; // invalid email
        }

        // 🔹 Build and return Customer (password will be hashed in builder)
        return new Customer.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)       // ✅ email is PK
                .setPassword(password) // ✅ hashes password
                .setAddress(address)
                .build();
    }
}
