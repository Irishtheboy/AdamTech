package za.co.admatech.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@SpringBootTest
class DatabaseFixTest {

    @Autowired
    private DataSource dataSource;

    @Test
    @Transactional
    @Rollback(false) // Don't rollback so the schema changes persist
    void fixCartTableSchema() throws Exception {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Fix Cart table - ensure id has AUTO_INCREMENT and is PRIMARY KEY
            try {
                stmt.execute("ALTER TABLE cart MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY");
                System.out.println("Successfully fixed Cart table AUTO_INCREMENT");
            } catch (Exception e) {
                System.err.println("Error fixing Cart table: " + e.getMessage());
                // Try alternative approach
                try {
                    stmt.execute("ALTER TABLE cart MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT");
                    System.out.println("Successfully set AUTO_INCREMENT on Cart table");
                } catch (Exception e2) {
                    System.err.println("Alternative approach failed: " + e2.getMessage());
                }
            }
        }
    }
}
