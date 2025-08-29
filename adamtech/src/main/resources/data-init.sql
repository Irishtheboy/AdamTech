-- AdamTech Database Sample Data
-- Insert default admin user
-- Password: admin123 (encoded with BCrypt)
INSERT IGNORE INTO users (id, username, email, password, role, enabled, created_at, updated_at) 
VALUES (1, 'admin', 'admin@adamtech.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'ADMIN', true, NOW(), NOW());

-- Insert default regular user
-- Password: user123 (encoded with BCrypt)
INSERT IGNORE INTO users (id, username, email, password, role, enabled, created_at, updated_at) 
VALUES (2, 'user', 'user@adamtech.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'USER', true, NOW(), NOW());

-- Insert sample categories
INSERT IGNORE INTO categories (id, name, description, active, created_at) VALUES
(1, 'Electronics', 'Electronic devices and gadgets', true, NOW()),
(2, 'Clothing', 'Fashion and apparel', true, NOW()),
(3, 'Home & Garden', 'Home improvement and garden supplies', true, NOW()),
(4, 'Books', 'Books and educational materials', true, NOW()),
(5, 'Sports', 'Sports equipment and accessories', true, NOW());

-- Insert sample products
INSERT IGNORE INTO products (id, name, description, sku, price, cost_price, category_id, product_type, active, created_at) VALUES
(1, 'Smartphone Pro X', 'Latest flagship smartphone with advanced features', 'SPX-001', 12999.99, 8000.00, 1, 'PHYSICAL', true, NOW()),
(2, 'Wireless Earbuds', 'Premium wireless earbuds with noise cancellation', 'WEB-002', 2499.99, 1500.00, 1, 'PHYSICAL', true, NOW()),
(3, 'Cotton T-Shirt', 'Comfortable 100% cotton t-shirt', 'CTS-003', 299.99, 150.00, 2, 'PHYSICAL', true, NOW()),
(4, 'Running Shoes', 'Professional running shoes for athletes', 'RS-004', 1899.99, 1200.00, 5, 'PHYSICAL', true, NOW()),
(5, 'Programming Book', 'Complete guide to modern programming', 'PB-005', 799.99, 400.00, 4, 'PHYSICAL', true, NOW());

-- Insert inventory for products
INSERT IGNORE INTO inventory (id, product_id, quantity_available, quantity_reserved, reorder_level, status, created_at) VALUES
(1, 1, 50, 5, 10, 'IN_STOCK', NOW()),
(2, 2, 100, 10, 20, 'IN_STOCK', NOW()),
(3, 3, 200, 0, 50, 'IN_STOCK', NOW()),
(4, 4, 30, 2, 15, 'IN_STOCK', NOW()),
(5, 5, 75, 5, 25, 'IN_STOCK', NOW());

-- Insert sample customer
INSERT IGNORE INTO customers (id, user_id, first_name, last_name, email, phone, loyalty_points, created_at) VALUES
(1, 2, 'John', 'Doe', 'user@adamtech.com', '+27123456789', 100, NOW());

-- Insert sample address
INSERT IGNORE INTO addresses (id, customer_id, address_type, street_address, city, state_province, postal_code, country, is_default, created_at) VALUES
(1, 1, 'BOTH', '123 Main Street', 'Cape Town', 'Western Cape', '8001', 'South Africa', true, NOW());

-- Insert sample cart
INSERT IGNORE INTO carts (id, customer_id, total_amount, currency, created_at) VALUES
(1, 1, 3799.98, 'ZAR', NOW());

-- Insert sample cart items
INSERT IGNORE INTO cart_items (id, cart_id, product_id, quantity, unit_price, total_price, added_at) VALUES
(1, 1, 2, 1, 2499.99, 2499.99, NOW()),
(2, 1, 3, 4, 299.99, 1199.96, NOW());

-- Insert sample order
INSERT IGNORE INTO orders (id, order_number, customer_id, billing_address_id, shipping_address_id, subtotal, tax_amount, total_amount, status, payment_status, order_date) VALUES
(1, 'ORD-2024-001', 1, 1, 1, 2499.99, 375.00, 2874.99, 'DELIVERED', 'PAID', NOW());

-- Insert sample order items
INSERT IGNORE INTO order_items (id, order_id, product_id, product_name, product_sku, quantity, unit_price, total_price) VALUES
(1, 1, 2, 'Wireless Earbuds', 'WEB-002', 1, 2499.99, 2499.99);

-- Insert sample payment
INSERT IGNORE INTO payments (id, order_id, payment_method, amount, status, processed_at) VALUES
(1, 1, 'CREDIT_CARD', 2874.99, 'COMPLETED', NOW());

-- Note: Password hashes are for demonstration purposes using BCrypt encoding
