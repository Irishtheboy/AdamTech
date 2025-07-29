package za.co.admatech.factory;

import za.co.admatech.domain.Address;
import za.co.admatech.util.Helper;

public class AddressFactory {

    public static Address createAddress(Long addressId, String streetNumber, String streetName, String city,
                                        String province, String postalCode, String houseNumber) {
        if (addressId == null
                || Helper.isNullOrEmpty(streetNumber)
                || Helper.isNullOrEmpty(streetName)
                || Helper.isNullOrEmpty(city)
                || Helper.isNullOrEmpty(province)
                || Helper.isNullOrEmpty(postalCode)
                || Helper.isNullOrEmpty(houseNumber)) {
            return null;
        }

        try {
            short streetNum = Short.parseShort(streetNumber);
            short postalCodeNum = Short.parseShort(postalCode);

            if (!Helper.isValidStreetNumber(streetNum) || !Helper.isValidPostalCode(postalCodeNum)) {
                return null;
            }
        } catch (NumberFormatException e) {
            return null; // invalid number format
        }

        return new Address.Builder()
                .addressId(addressId)
                .streetNumber(streetNumber)
                .streetName(streetName)
                .city(city)
                .province(province)
                .postalCode(postalCode)
                .houseNumber(houseNumber)
                .build();
    }


    public static Address createAddress(String streetNumber, String streetName, String city,
                                        String province, String postalCode, String houseNumber) {
        return createAddress(System.currentTimeMillis(), streetNumber, streetName, city, province, postalCode, houseNumber);
    }
}
