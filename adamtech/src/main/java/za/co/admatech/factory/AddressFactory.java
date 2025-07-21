/*





AddressFactory.java



Author: Rorisang Makgana (230602363) */ package za.co.admatech.factory;

import za.co.admatech.domain.Address; import za.co.admatech.util.Helper;

public class AddressFactory { public static Address createAddress(Long id, short streetNumber, String streetName, String suburb, String city, String province, short postalCode) { if (Helper.isNullOrEmpty(streetName) || Helper.isNullOrEmpty(suburb) || Helper.isNullOrEmpty(city) || Helper.isNullOrEmpty(province)) { throw new IllegalArgumentException("Street name, suburb, city, and province must not be empty"); } if (!Helper.isValidPostalCode(postalCode) || !Helper.isValidStreetNumber(streetNumber)) { throw new IllegalArgumentException("Invalid postal code or street number"); }

    return new Address.Builder()
            .setAddressID(id)
            .setStreetNumber(streetNumber)
            .setStreetName(streetName)
            .setSuburb(suburb)
            .setCity(city)
            .setProvince(province)
            .setPostalCode(postalCode)
            .build();
}

}