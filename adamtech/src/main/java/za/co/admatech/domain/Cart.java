package za.co.admatech.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cart {
    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String customerId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items;

    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "customerId", insertable = false, updatable = false)
    private Customer customer;

    // Public no-arg constructor
    public Cart() {}



    private Cart(Builder builder) {
        this.id = builder.id;
        this.customerId = builder.customerId;
        this.items = builder.items;
        this.customer = builder.customer;
    }

    public static class Builder {
        private String id;
        private String customerId;
        private List<CartItem> items;
        private Customer customer;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder items(List<CartItem> items) {
            this.items = items;
            return this;
        }

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Cart build() {
            return new Cart(this);
        }
    }

    public Cart copy() {
        return new Builder()
                .id(this.id)
                .customerId(this.customerId)
                .items(this.items)
                .customer(this.customer)
                .build();
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
}