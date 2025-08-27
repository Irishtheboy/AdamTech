package za.co.admatech;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ApplicationIntegrationTest {

    @Test
    void contextLoads() {
        // This test verifies that the Spring context loads without errors
        // Circular dependency should be resolved with @Lazy annotations
    }
}
