/*
 * Customer.java
 * Customer Class
 * Author: Rorisang Makgana (230602363)
 * Date: 11 May 2025
 */
package za.co.admatech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @Column(nullable = false, unique = true)
    private String email;   // ✅ Primary Key

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @JsonIgnore
    @Column(nullable = false)
    private String password; // ✅ stored as hashed password

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private Cart cart;

    @Column(name = "phone_number")
    private String phoneNumber;

    public Customer() {}

    protected Customer(Builder builder) {
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.password = builder.password;
        this.address = builder.address;
        this.cart = builder.cart;
        this.phoneNumber = builder.phoneNumber;
    }

    // Setters for JPA/relationship management (used by services)
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public Address getAddress() {
        return address;
    }

    public Cart getCart() {
        return cart;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address +
                '}';
    }

    public static class Builder {
        private String email;
        private String firstName;
        private String lastName;
        private String password;
        private Address address;
        private Cart cart;
        private String phoneNumber;

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        // ✅ Hash password inside builder too
        public Builder setPassword(String rawPassword) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            this.password = encoder.encode(rawPassword);
            return this;
        }

        public Builder setAddress(Address address) {
            this.address = address;
            return this;
        }

        public Builder setCart(Cart cart) {
            this.cart = cart;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder copy(Customer customer) {
            this.email = customer.email;
            this.firstName = customer.firstName;
            this.lastName = customer.lastName;
            this.password = customer.password;
            this.address = customer.address;
            this.cart = customer.cart;
            this.phoneNumber = customer.phoneNumber;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}
