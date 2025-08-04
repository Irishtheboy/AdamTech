package za.co.admatech.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "addressId", referencedColumnName = "addressId")
    private Address address;

    // Public no-arg constructor
    public Customer() {}



    private Customer(Builder builder) {
        this.customerId = builder.customerId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.address = builder.address;
    }

    public static class Builder {
        private String customerId;
        private String firstName;
        private String lastName;
        private String email;
        private Address address;

        public Builder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder address(Address address) {
            this.address = address;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }

    public Customer copy() {
        return new Builder()
                .customerId(this.customerId)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .address(this.address)
                .build();
    }

    // Getters and setters
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
}