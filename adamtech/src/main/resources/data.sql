-- Insert default admin user
-- Password: admin123 (encoded with BCrypt)
INSERT IGNORE INTO users (username, email, password, role, enabled, created_at, updated_at) 
VALUES ('admin', 'admin@adamtech.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'ADMIN', true, NOW(), NOW());

-- Insert default regular user
-- Password: user123 (encoded with BCrypt)
INSERT IGNORE INTO users (username, email, password, role, enabled, created_at, updated_at) 
VALUES ('user', 'user@adamtech.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'USER', true, NOW(), NOW());

-- Note: The password hash above is for "secret" - in production, generate proper BCrypt hashes
-- You can generate BCrypt hashes using online tools or programmatically
