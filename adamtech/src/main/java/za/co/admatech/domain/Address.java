package za.co.admatech.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @Column(nullable = false)
    private Long addressId;

    @Column(nullable = false)
    private String streetNumber;

    @Column(nullable = false)
    private String streetName;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String houseNumber;

    // Public no-arg constructor
    public Address() {}


    private Address(Builder builder) {
        this.addressId = builder.addressId;
        this.streetNumber = builder.streetNumber;
        this.streetName = builder.streetName;
        this.city = builder.city;
        this.province = builder.province;
        this.postalCode = builder.postalCode;
        this.houseNumber = builder.houseNumber;
    }

    public static class Builder {
        private Long addressId;
        private String streetNumber;
        private String streetName;
        private String city;
        private String province;
        private String postalCode;
        private String houseNumber;

        public Builder addressId(Long addressId) {
            this.addressId = addressId;
            return this;
        }

        public Builder streetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
            return this;
        }

        public Builder streetName(String streetName) {
            this.streetName = streetName;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder province(String province) {
            this.province = province;
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder houseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
        public Builder copy(Address address) {
            this.addressId = address.getAddressId();
            this.houseNumber = address.getHouseNumber();
            this.streetName = address.getStreetName();

            this.city = address.getCity();
            this.province = address.getProvince();
            this.postalCode = address.getPostalCode();
            return this;
        }

    }

    public Address copy() {
        return new Builder()
                .addressId(this.addressId)
                .streetNumber(this.streetNumber)
                .streetName(this.streetName)
                .city(this.city)
                .province(this.province)
                .postalCode(this.postalCode)
                .houseNumber(this.houseNumber)
                .build();
    }

    // Getters and setters
    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }
    public String getStreetNumber() { return streetNumber; }
    public void setStreetNumber(String streetNumber) { this.streetNumber = streetNumber; }
    public String getStreetName() { return streetName; }
    public void setStreetName(String streetName) { this.streetName = streetName; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getHouseNumber() { return houseNumber; }
    public void setHouseNumber(String houseNumber) { this.houseNumber = houseNumber; }
}