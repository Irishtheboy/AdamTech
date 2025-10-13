-- Fix AdamTech Database Schema Issues
-- Run this script to fix the database schema problems identified in tests

USE adamtechdb;

-- Fix Product table - change category_id from INT to VARCHAR
ALTER TABLE product MODIFY COLUMN category_id VARCHAR(255);

-- Fix Address table - ensure address_id has AUTO_INCREMENT
ALTER TABLE address MODIFY COLUMN address_id BIGINT AUTO_INCREMENT;

-- Fix Cart table - ensure id has AUTO_INCREMENT and is PRIMARY KEY
ALTER TABLE cart MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY;

-- Fix Payment table - ensure payment_id has AUTO_INCREMENT
ALTER TABLE payment MODIFY COLUMN payment_id BIGINT AUTO_INCREMENT;

-- Fix CartItem table - ensure cart_item_id has AUTO_INCREMENT
ALTER TABLE cart_item MODIFY COLUMN cart_item_id BIGINT AUTO_INCREMENT;

-- Show table structures to verify fixes
DESCRIBE product;
DESCRIBE address;  
DESCRIBE cart;
DESCRIBE payment;
DESCRIBE cart_item;
