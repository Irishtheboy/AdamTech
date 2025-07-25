/*





Customer.java



Customer Class



Author: Rorisang Makgana (230602363)



Date: 11 May 2025 */ package za.co.admatech.domain;

import jakarta.persistence.*; import jakarta.validation.constraints.Email; import jakarta.validation.constraints.NotNull; import jakarta.validation.constraints.Size; import java.util.List;

@Entity public class Customer { @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long customerID;

    @NotNull
    @Size(min = 1, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 50)
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 10, max = 15)
    private String phoneNumber;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Address> address;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Order> orders;

    public Long getCustomerID() {
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

    public Cart getCart() {
        return cart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Address> getAddress() {
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
                ", address=" + address +
                '}';
    }

    protected Customer(Builder builder) {
        this.customerID = builder.customerID;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.cart = builder.cart;
        this.address = builder.address;
        this.orders = builder.orders;
        this.phoneNumber = builder.phoneNumber;
    }

    protected Customer() {
    }

    public static class Builder {
        private Long customerID;
        private String firstName;
        private String lastName;
        private String email;
        private Cart cart;
        private List<Address> address;
        private List<Order> orders;
        private String phoneNumber;

        public Builder setCustomerID(Long customerID) {
            this.customerID = customerID;
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

        public Builder setCart(Cart cart) {
            this.cart = cart;
            return this;
        }

        public Builder setAddress(List<Address> address) {
            this.address = address;
            return this;
        }

        public Builder setOrders(List<Order> orders) {
            this.orders = orders;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder copy(Customer customer) {
            this.customerID = customer.customerID;
            this.firstName = customer.firstName;
            this.lastName = customer.lastName;
            this.email = customer.email;
            this.cart = customer.cart;
            this.address = customer.address;
            this.orders = customer.orders;
            this.phoneNumber = customer.phoneNumber;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }

}