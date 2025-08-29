/*
 * Address.java
 * Address Class
 * Author: Rorisang Makgana (230602363)
 * Date: 11 May 2025
 */
package za.co.admatech.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "street_number")
    private short streetNumber;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "suburb")
    private String suburb;

    @Column(name = "city")
    private String city;

    @Column(name = "province")    
    private String province;

    @Column(name = "postal_code")
    private short postalCode;

    public Address() {
    }

    public Long getAddressId() {
        return addressId;
    }

    public short getStreetNumber() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public short getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", streetNumber=" + streetNumber +
                ", streetName='" + streetName + '\'' +
                ", suburb='" + suburb + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", postalCode=" + postalCode +
                '}';
    }

    protected Address(Builder builder) {
        this.addressId = builder.addressId;
        this.streetNumber = builder.streetNumber;
        this.streetName = builder.streetName;
        this.suburb = builder.suburb;
        this.city = builder.city;
        this.province = builder.province;
        this.postalCode = builder.postalCode;
    }

    public static class Builder {
        private Long addressId;
        private short streetNumber;
        private String streetName;
        private String suburb;
        private String city;
        private String province;
        private short postalCode;

        public Builder setAddressId(Long addressId) {
            this.addressId = addressId;
            return this;
        }

        public Builder setStreetNumber(short streetNumber) {
            this.streetNumber = streetNumber;
            return this;
        }

        public Builder setStreetName(String streetName) {
            this.streetName = streetName;
            return this;
        }

        public Builder setSuburb(String suburb) {
            this.suburb = suburb;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setProvince(String province) {
            this.province = province;
            return this;
        }

        public Builder setPostalCode(short postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder copy(Address address) {
            this.addressId = address.addressId;
            this.streetNumber = address.streetNumber;
            this.streetName = address.streetName;
            this.suburb = address.suburb;
            this.city = address.city;
            this.province = address.province;
            this.postalCode = address.postalCode;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}