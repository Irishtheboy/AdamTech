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
        return new Address.Builder()
                .setStreetNumber(streetNumber)
                .setStreetName(streetName)
                .setSuburb(suburb)
                .setCity(city)
                .setProvince(province)
                .setPostalCode(postalCode)
                .build();
    }

    public static Address buildAddress(int streetNumber, String streetName,
                                      String suburb, String city,
                                      String province, short postalCode) {
        return new Address.Builder()
                .setStreetNumber((short) streetNumber)
                .setStreetName(streetName)
                .setSuburb(suburb)
                .setCity(city)
                .setProvince(province)
                .setPostalCode(postalCode)
                .build();
    }

}
