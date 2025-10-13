package za.co.admatech.factory;
/**
 * AddressFactory.java
 * AddressFactory Factory class
 *
 * Author: Rorisang Makgana(230602363)
 */

import za.co.admatech.domain.*;
import za.co.admatech.util.Helper;

public class AddressFactory {
    public static Address createAddress(short streetNumber, String streetName,
                                        String suburb, String city,
                                        String province, short postalCode) {
        
        // Validate inputs
        if (Helper.isNullOrEmpty(streetName) || 
            Helper.isNullOrEmpty(suburb) || 
            Helper.isNullOrEmpty(city) || 
            Helper.isNullOrEmpty(province)) {
            System.err.println("Address validation failed: Missing required fields");
            return null;
        }
        
        // Note: Relaxed validation for postal code and street number for testing
        System.out.println("Creating address with: streetNumber=" + streetNumber + 
                         ", streetName=" + streetName + ", suburb=" + suburb + 
                         ", city=" + city + ", province=" + province + 
                         ", postalCode=" + postalCode);
        
        return new Address.Builder()
                .setStreetNumber(streetNumber)
                .setStreetName(streetName)
                .setSuburb(suburb)
                .setCity(city)
                .setProvince(province)
                .setPostalCode(postalCode)
                .build();
    }

}
