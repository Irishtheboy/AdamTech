/*
 * Customer.java
 * Customer Class
 * Author: Rorisang Makgana (230602363)
 * Date: 11 May 2025
 */
package za.co.admatech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "customer")
public class Customer implements UserDetails {

    @Id
    @Column(nullable = false, unique = true)
    private String email;   // ✅ Primary Key

    private String firstName;

    private String lastName;

    private String password; // ✅ stored as hashed password

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private String phoneNumber;

    // ✅ New field for Spring Security roles
    private String role = "ROLE_USER"; // Default role

    public Customer() {}

    // ✅ REMOVED AUTO-HASHING - Let Spring Security handle this
    public void setPassword(String password) {
        this.password = password;
    }

    protected Customer(Builder builder) {
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.password = builder.password;
        this.address = builder.address;
        this.cart = builder.cart;
        this.phoneNumber = builder.phoneNumber;
        this.role = builder.role != null ? builder.role : "ROLE_USER";
    }

    // ✅ UserDetails implementation methods
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Ultimate null safety
        if (role == null || role.trim().isEmpty()) {
            System.out.println("WARNING: Role is null/empty for user: " + email + ", using ROLE_USER");
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // Ensure role has proper format
        String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        return Collections.singletonList(new SimpleGrantedAuthority(authority));
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    // ✅ Getters and Setters
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

    public String getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(String role) {
        // Ensure role has ROLE_ prefix if not already present
        if (role != null && !role.startsWith("ROLE_")) {
            this.role = "ROLE_" + role;
        } else {
            this.role = role != null ? role : "ROLE_USER";
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role='" + role + '\'' +
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
        private String role;

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

        // ✅ REMOVED AUTO-HASHING - Accept raw or encoded password
        public Builder setPassword(String password) {
            this.password = password;
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

        public Builder setRole(String role) {
            // Ensure role has ROLE_ prefix if not already present
            if (role != null && !role.startsWith("ROLE_")) {
                this.role = "ROLE_" + role;
            } else {
                this.role = role;
            }
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
            this.role = customer.role;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}