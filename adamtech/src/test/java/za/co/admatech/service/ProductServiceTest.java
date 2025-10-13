package za.co.admatech.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;
import za.co.admatech.factory.MoneyFactory;
import za.co.admatech.factory.ProductFactory;
import za.co.admatech.repository.ProductRepository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private static byte[] laptopImg1, laptopImg2, laptopImg3, laptopImg4, laptopImg5,
            laptopImg6, laptopImg7, laptopImg8, laptopImg9, laptopImg10;

    private static byte[] caseImg1, caseImg2, caseImg3, caseImg4, caseImg5,
            caseImg6, caseImg7, caseImg8, caseImg9, caseImg10;

    private static byte[] periphImg1, periphImg2, periphImg3, periphImg4, periphImg5,
            periphImg6, periphImg7, periphImg8, periphImg9, periphImg10;

    @BeforeAll
    static void loadImages() throws Exception {
        // Laptops
        laptopImg1 = Files.readAllBytes(Paths.get("src/test/resources/laptops/laptop1.png"));
        laptopImg2 = Files.readAllBytes(Paths.get("src/test/resources/laptops/laptop2.png"));
        laptopImg3 = Files.readAllBytes(Paths.get("src/test/resources/laptops/laptop3.png"));
        laptopImg4 = Files.readAllBytes(Paths.get("src/test/resources/laptops/laptop4.png"));
        laptopImg5 = Files.readAllBytes(Paths.get("src/test/resources/laptops/laptop5.png"));
        laptopImg6 = Files.readAllBytes(Paths.get("src/test/resources/laptops/laptop6.png"));
        laptopImg7 = Files.readAllBytes(Paths.get("src/test/resources/laptops/laptop7.png"));
        laptopImg8 = Files.readAllBytes(Paths.get("src/test/resources/laptops/laptop8.png"));
        laptopImg9 = Files.readAllBytes(Paths.get("src/test/resources/laptops/laptop9.png"));
        laptopImg10 = Files.readAllBytes(Paths.get("src/test/resources/laptops/laptop10.png"));

        // Cases
        caseImg1 = Files.readAllBytes(Paths.get("src/test/resources/cases/case1.png"));
        caseImg2 = Files.readAllBytes(Paths.get("src/test/resources/cases/case2.png"));
        caseImg3 = Files.readAllBytes(Paths.get("src/test/resources/cases/case3.png"));
        caseImg4 = Files.readAllBytes(Paths.get("src/test/resources/cases/case4.png"));
        caseImg5 = Files.readAllBytes(Paths.get("src/test/resources/cases/case5.png"));
        caseImg6 = Files.readAllBytes(Paths.get("src/test/resources/cases/case6.png"));
        caseImg7 = Files.readAllBytes(Paths.get("src/test/resources/cases/case7.png"));
        caseImg8 = Files.readAllBytes(Paths.get("src/test/resources/cases/case8.png"));
        caseImg9 = Files.readAllBytes(Paths.get("src/test/resources/cases/case9.png"));
        caseImg10 = Files.readAllBytes(Paths.get("src/test/resources/cases/case10.png"));

        // Peripherals
        periphImg1 = Files.readAllBytes(Paths.get("src/test/resources/peripherals/periph1.png"));
        periphImg2 = Files.readAllBytes(Paths.get("src/test/resources/peripherals/periph2.png"));
        periphImg3 = Files.readAllBytes(Paths.get("src/test/resources/peripherals/periph3.png"));
        periphImg4 = Files.readAllBytes(Paths.get("src/test/resources/peripherals/periph4.png"));
        periphImg5 = Files.readAllBytes(Paths.get("src/test/resources/peripherals/periph5.png"));
        periphImg6 = Files.readAllBytes(Paths.get("src/test/resources/peripherals/periph6.png"));
        periphImg7 = Files.readAllBytes(Paths.get("src/test/resources/peripherals/periph7.png"));
        periphImg8 = Files.readAllBytes(Paths.get("src/test/resources/peripherals/periph8.png"));
        periphImg9 = Files.readAllBytes(Paths.get("src/test/resources/peripherals/periph9.png"));
        periphImg10 = Files.readAllBytes(Paths.get("src/test/resources/peripherals/periph10.png"));
    }

    @Test
    @Order(1)
    void createProducts() {
        // ==== Laptops ====
        productService.create(new Product.Builder()
                .setName("MSI Stealth 15")
                .setDescription("Gaming laptop with RTX 4070")
                .setSku("LAP1001")
                .setPrice(new Money.Builder().setAmount(1800).setCurrency("USD").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg1)
                .build());
        productService.create(new Product.Builder()
                .setName("Dell XPS 13")
                .setDescription("Ultrabook with OLED display")
                .setSku("LAP1002")
                .setPrice(new Money.Builder().setAmount(1400).setCurrency("USD").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg2)
                .build());
        productService.create(new Product.Builder()
                .setName("HP Spectre x360")
                .setDescription("Convertible 2-in-1 laptop")
                .setSku("LAP1003")
                .setPrice(new Money.Builder().setAmount(1300).setCurrency("USD").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg3)
                .build());
        productService.create(new Product.Builder()
                .setName("Lenovo Legion 5")
                .setDescription("High-performance gaming laptop")
                .setSku("LAP1004")
                .setPrice(new Money.Builder().setAmount(1500).setCurrency("USD").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg4)
                .build());
        productService.create(new Product.Builder()
                .setName("Asus ROG Strix")
                .setDescription("RGB gaming laptop")
                .setSku("LAP1005")
                .setPrice(new Money.Builder().setAmount(1600).setCurrency("USD").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg5)
                .build());
        productService.create(new Product.Builder()
                .setName("Apple MacBook Pro 14")
                .setDescription("M2 Pro chip laptop")
                .setSku("LAP1006")
                .setPrice(new Money.Builder().setAmount(2200).setCurrency("USD").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg6)
                .build());
        productService.create(new Product.Builder()
                .setName("Acer Predator Helios")
                .setDescription("Gaming laptop with RTX 4060")
                .setSku("LAP1007")
                .setPrice(new Money.Builder().setAmount(1700).setCurrency("USD").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg7)
                .build());
        productService.create(new Product.Builder()
                .setName("Razer Blade 15")
                .setDescription("Premium gaming ultrabook")
                .setSku("LAP1008")
                .setPrice(new Money.Builder().setAmount(2100).setCurrency("USD").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg8)
                .build());
        productService.create(new Product.Builder()
                .setName("Samsung Galaxy Book2")
                .setDescription("Lightweight Windows laptop")
                .setSku("LAP1009")
                .setPrice(new Money.Builder().setAmount(1200).setCurrency("USD").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg9)
                .build());
        productService.create(new Product.Builder()
                .setName("Microsoft Surface Laptop 5")
                .setDescription("Premium ultrabook from Microsoft")
                .setSku("LAP1010")
                .setPrice(new Money.Builder().setAmount(1600).setCurrency("USD").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg10)
                .build());

        // ==== Cases ====
        productService.create(new Product.Builder()
                .setName("NZXT H510")
                .setDescription("Compact mid-tower ATX case")
                .setSku("CAS2001")
                .setPrice(new Money.Builder().setAmount(120).setCurrency("USD").build())
                .setCategoryId("CASES")
                .setImageData(caseImg1)
                .build());
        productService.create(new Product.Builder()
                .setName("Corsair iCUE 5000X")
                .setDescription("Premium RGB full-tower case")
                .setSku("CAS2002")
                .setPrice(new Money.Builder().setAmount(250).setCurrency("USD").build())
                .setCategoryId("CASES")
                .setImageData(caseImg2)
                .build());
        productService.create(new Product.Builder()
                .setName("Fractal Design Meshify C")
                .setDescription("Compact ATX case with mesh front")
                .setSku("CAS2003")
                .setPrice(new Money.Builder().setAmount(110).setCurrency("USD").build())
                .setCategoryId("CASES")
                .setImageData(caseImg3)
                .build());
        productService.create(new Product.Builder()
                .setName("Cooler Master MasterBox NR600")
                .setDescription("Airflow optimized case")
                .setSku("CAS2004")
                .setPrice(new Money.Builder().setAmount(100).setCurrency("USD").build())
                .setCategoryId("CASES")
                .setImageData(caseImg4)
                .build());
        productService.create(new Product.Builder()
                .setName("Phanteks Eclipse P400A")
                .setDescription("ARGB mesh case")
                .setSku("CAS2005")
                .setPrice(new Money.Builder().setAmount(130).setCurrency("USD").build())
                .setCategoryId("CASES")
                .setImageData(caseImg5)
                .build());
        productService.create(new Product.Builder()
                .setName("Lian Li Lancool II")
                .setDescription("High airflow ATX case")
                .setSku("CAS2006")
                .setPrice(new Money.Builder().setAmount(140).setCurrency("USD").build())
                .setCategoryId("CASES")
                .setImageData(caseImg6)
                .build());
        productService.create(new Product.Builder()
                .setName("be quiet! Pure Base 500")
                .setDescription("Silent mid-tower case")
                .setSku("CAS2007")
                .setPrice(new Money.Builder().setAmount(115).setCurrency("USD").build())
                .setCategoryId("CASES")
                .setImageData(caseImg7)
                .build());
        productService.create(new Product.Builder()
                .setName("Thermaltake Versa H18")
                .setDescription("Budget mini tower case")
                .setSku("CAS2008")
                .setPrice(new Money.Builder().setAmount(75).setCurrency("USD").build())
                .setCategoryId("CASES")
                .setImageData(caseImg8)
                .build());
        productService.create(new Product.Builder()
                .setName("Deepcool Matrexx 55")
                .setDescription("Mid-tower ATX case with tempered glass")
                .setSku("CAS2009")
                .setPrice(new Money.Builder().setAmount(95).setCurrency("USD").build())
                .setCategoryId("CASES")
                .setImageData(caseImg9)
                .build());
        productService.create(new Product.Builder()
                .setName("Antec DF500")
                .setDescription("ARGB gaming mid-tower")
                .setSku("CAS2010")
                .setPrice(new Money.Builder().setAmount(105).setCurrency("USD").build())
                .setCategoryId("CASES")
                .setImageData(caseImg10)
                .build());

        // ==== Peripherals ====
        productService.create(new Product.Builder()
                .setName("Logitech G Pro X")
                .setDescription("Mechanical gaming keyboard")
                .setSku("PER3001")
                .setPrice(new Money.Builder().setAmount(150).setCurrency("USD").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg1)
                .build());
        productService.create(new Product.Builder()
                .setName("Razer DeathAdder V3")
                .setDescription("High precision gaming mouse")
                .setSku("PER3002")
                .setPrice(new Money.Builder().setAmount(70).setCurrency("USD").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg2)
                .build());
        productService.create(new Product.Builder()
                .setName("SteelSeries Arctis 7")
                .setDescription("Wireless gaming headset")
                .setSku("PER3003")
                .setPrice(new Money.Builder().setAmount(150).setCurrency("USD").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg3)
                .build());
        productService.create(new Product.Builder()
                .setName("Corsair K95 RGB Platinum")
                .setDescription("Mechanical keyboard with macros")
                .setSku("PER3004")
                .setPrice(new Money.Builder().setAmount(200).setCurrency("USD").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg4)
                .build());
        productService.create(new Product.Builder()
                .setName("Razer BlackWidow V3")
                .setDescription("Mechanical gaming keyboard")
                .setSku("PER3005")
                .setPrice(new Money.Builder().setAmount(170).setCurrency("USD").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg5)
                .build());
        productService.create(new Product.Builder()
                .setName("Logitech MX Master 3")
                .setDescription("Wireless productivity mouse")
                .setSku("PER3006")
                .setPrice(new Money.Builder().setAmount(100).setCurrency("USD").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg6)
                .build());
        productService.create(new Product.Builder()
                .setName("HyperX Alloy FPS Pro")
                .setDescription("Compact mechanical keyboard")
                .setSku("PER3007")
                .setPrice(new Money.Builder().setAmount(120).setCurrency("USD").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg7)
                .build());
        productService.create(new Product.Builder()
                .setName("Razer Naga X")
                .setDescription("MMO gaming mouse")
                .setSku("PER3008")
                .setPrice(new Money.Builder().setAmount(90).setCurrency("USD").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg8)
                .build());
        productService.create(new Product.Builder()
                .setName("Logitech G915 TKL")
                .setDescription("Wireless mechanical keyboard")
                .setSku("PER3009")
                .setPrice(new Money.Builder().setAmount(250).setCurrency("USD").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg9)
                .build());
        productService.create(new Product.Builder()
                .setName("Corsair M65 RGB Elite")
                .setDescription("High-precision gaming mouse")
                .setSku("PER3010")
                .setPrice(new Money.Builder().setAmount(80).setCurrency("USD").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg10)
                .build());

        List<Product> all = productService.getAll();
        assertTrue(all.size() >= 30); // At least 30 products created
    }
}
