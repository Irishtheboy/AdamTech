-- AdamTech Test Data Initialization
-- This script populates the database with test data for hydrating tests

-- Insert test categories (these can be referenced by category_id in products)
-- Note: We'll use string category IDs to match the test data

-- Insert test addresses
INSERT INTO address (street_number, street_name, suburb, city, province, postal_code) VALUES
(123, 'Main Street', 'Suburb1', 'Johannesburg', 'Gauteng', 2000),
(456, 'Oak Avenue', 'Suburb2', 'Cape Town', 'Western Cape', 8000),
(789, 'Pine Road', 'Suburb3', 'Durban', 'KwaZulu-Natal', 4000);

-- Insert test customers (email is the primary key)
INSERT INTO customer (email, first_name, last_name, phone_number, password, address_id) VALUES
('john.doe@example.com', 'John', 'Doe', '0821234567', 'password123', 1),
('jane.smith@example.com', 'Jane', 'Smith', '0827654321', 'password456', 2),
('mike.johnson@example.com', 'Mike', 'Johnson', '0829876543', 'password789', 3);

-- Insert test carts for customers
INSERT INTO cart (customer_id) VALUES
('john.doe@example.com'),
('jane.smith@example.com'), 
('mike.johnson@example.com');

-- Insert test products with proper category_id as strings
INSERT INTO product (name, description, sku, category_id, amount, currency) VALUES
('Gaming Laptop', 'High-performance gaming laptop', 'LAPTOP001', 'LAPTOPS', 15000.00, 'ZAR'),
('Wireless Mouse', 'Ergonomic wireless mouse', 'MOUSE001', 'PERIPHERALS', 350.00, 'ZAR'),
('Mechanical Keyboard', 'RGB mechanical keyboard', 'KB001', 'PERIPHERALS', 1200.00, 'ZAR'),
('Monitor', '27-inch 4K monitor', 'MON001', 'DISPLAYS', 8000.00, 'ZAR'),
('USB Headset', 'Professional USB headset', 'HEAD001', 'PERIPHERALS', 450.00, 'ZAR');

-- Insert test inventory
INSERT INTO inventory (product_id, quantity_in_stock, reorder_level) VALUES
(1, 50, 10),
(2, 100, 20),
(3, 75, 15),
(4, 30, 5),
(5, 80, 15);

-- Note: Orders, order items, payments, wishlists and cart items will be created by tests
-- as they require more complex relationships and business logic
