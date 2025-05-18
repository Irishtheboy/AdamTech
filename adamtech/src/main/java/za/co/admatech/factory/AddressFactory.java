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
    public static Address createAddress(
        Long addressID,
        short streetNumber,
        String streetName,
        String suburb,
        String city,
        String province,
        short postalCode
    ){
        if(Helper.isNullOrEmpty(suburb)){
            return null;
        }

        return new Address.Builder()
        .setAddressID(addressID)
        .setStreetName(streetName)
        .setStreetNumber(streetNumber)
        .setSuburb(suburb)
        .setCity(city)
        .setProvince(province)
        .setPostalCode(postalCode)
        .build();
    }   
}
