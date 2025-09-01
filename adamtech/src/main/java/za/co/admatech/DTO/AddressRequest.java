package za.co.admatech.DTO;

public class AddressRequest {
    private String street;
    private String city;
    private String province;
    private String zipCode;
    private String postalCode; // Alternative field name that frontend might use

    // Getters and Setters
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZipCode() {
        return zipCode != null ? zipCode : postalCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPostalCode() {
        return postalCode != null ? postalCode : zipCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
