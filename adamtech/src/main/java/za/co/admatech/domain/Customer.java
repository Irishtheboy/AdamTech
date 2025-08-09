/*
 * Customer.java
 * Customer Class
 * Author: Rorisang Makgana (230602363)
 * Date: 11 May 2025
 */
package za.co.admatech.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import za.co.admatech.domain.Cart;
@Entity
public class Customer{
    @Id
    private String customerID;
    private String firstName;
    private String lastName;
    private String email;
    /*@ManyToOne
    @JoinColumn(name = "cart_cart_id")
    private Cart cart;
     */
    @ManyToOne(cascade = jakarta.persistence.CascadeType.ALL)
    @JoinColumn(name = "address_address_id")
    private Address address;
    private String phoneNumber;

    public void setAddress(Address address) {
        this.address = address;
    }


    public String getCustomerID() {
        return customerID;
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
/*
    public Cart getCart() {
        return cart;
    }
 */
    public Address getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID='" + customerID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                //", cartID=" + cartID +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    protected Customer(Builder builder){
        this.customerID = builder.customerID;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        //this.cart = builder.cartID;
        this.address = builder.address;
        this.phoneNumber = builder.phoneNumber;
    }
    protected Customer(){}

    public static class Builder{
        private String customerID;
        private String firstName;
        private String lastName;
        private String email;
        //private Cart cart;
        private Address address;
        private String phoneNumber;


        public Builder setCustomerID(String customerID){
            this.customerID = customerID;
            return this;
        }
        public Builder setFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }
        public Builder setLastName(String lastName){
            this.lastName = lastName;
            return this;
        }
        public Builder setEmail(String email){
            this.email = email;
            return this;
        }
/*
        public Builder setCart(Cart cart){
            this.cart = cart;
            return this;
        }

 */

        public Builder setAddress(Address address){
            this.address = address;
            return this;
        }
        public Builder setPhoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder copy(Customer customer){
            this.customerID = customer.customerID;
            this.firstName = customer.firstName;
            this.lastName = customer.lastName;
            this.email = customer.email;
            //this.cartID = customer.cartID;
            this.address = customer.address;
            this.phoneNumber = customer.phoneNumber;
            return this;
        }

        public Customer build(){
            return new Customer(this);
        }
    }
}