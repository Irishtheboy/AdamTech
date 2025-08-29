/*
 * Customer.java
 * Customer Class
 * Author: Rorisang Makgana (230602363)
 * Date: 11 May 2025
 */
package za.co.admatech.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@Table(name = "customer")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
 property = "cartItemId")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    //@JsonIgnore
    /*Removed the @JsonIgnore annotation as we have to persist the information of the cart to our customer
     * details, this ensures that when we fetch a customer, we also get their associated cart(its been serialized properly).
     */
    private Cart cart;

    @Column(name = "phone_number")
    private String phoneNumber;

    public Customer() {
    }

    protected Customer(Builder builder) {
        this.customerId = builder.customerId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.address = builder.address;
        this.cart = builder.cart;
        this.phoneNumber = builder.phoneNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
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

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", cart=" + cart +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public static class Builder {
        private Long customerId;
        private String firstName;
        private String lastName;
        private String email;
        private Address address;
        private Cart cart;
        private String phoneNumber;

        public Builder setCustomerId(Long customerId) {
            this.customerId = customerId;
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

        public Builder setEmail(String email) {
            this.email = email;
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
            this.customerId = customer.customerId;
            this.firstName = customer.firstName;
            this.lastName = customer.lastName;
            this.email = customer.email;
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