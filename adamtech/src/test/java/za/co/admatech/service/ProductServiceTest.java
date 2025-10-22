package za.co.admatech.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.admatech.domain.Money;
import za.co.admatech.domain.Product;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceTest {

    @Autowired
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
        // ==== LAPTOPS ====
        productService.create(new Product.Builder()
                .setName("MSI Stealth 15")
                .setDescription("Gaming laptop with Intel Core i7-13700H (5.0GHz), 16GB DDR5 4800MHz RAM, 1TB NVMe SSD, NVIDIA RTX 4070 8GB GDDR6, 15.6\" FHD 144Hz IPS display, Windows 11 Pro")
                .setSku("LAP1001")
                .setPrice(new Money.Builder().setAmount(34999).setCurrency("ZAR").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg1)
                .build());
        productService.create(new Product.Builder()
                .setName("Dell XPS 13")
                .setDescription("Ultrabook with Intel Core i7-1250U (4.7GHz), 16GB LPDDR5 5200MHz RAM, 512GB PCIe 4.0 SSD, Intel Iris Xe Graphics, 13.4\" OLED 3.5K Touch Display, Windows 11 Home")
                .setSku("LAP1002")
                .setPrice(new Money.Builder().setAmount(27999).setCurrency("ZAR").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg2)
                .build());
        productService.create(new Product.Builder()
                .setName("HP Spectre x360")
                .setDescription("Convertible 2-in-1 with Intel Core i7-1260P (4.7GHz), 16GB LPDDR4X 4266MHz RAM, 1TB PCIe NVMe SSD, Intel Iris Xe Graphics, 13.5\" OLED 3K2K Touch, 360° hinge, Windows 11")
                .setSku("LAP1003")
                .setPrice(new Money.Builder().setAmount(25999).setCurrency("ZAR").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg3)
                .build());
        productService.create(new Product.Builder()
                .setName("Lenovo Legion 5")
                .setDescription("Gaming laptop with AMD Ryzen 7 6800H (4.7GHz), 16GB DDR5 4800MHz RAM, 1TB NVMe SSD, NVIDIA RTX 4060 8GB GDDR6, 15.6\" QHD 165Hz IPS, RGB keyboard, Windows 11 Home")
                .setSku("LAP1004")
                .setPrice(new Money.Builder().setAmount(29999).setCurrency("ZAR").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg4)
                .build());
        productService.create(new Product.Builder()
                .setName("Asus ROG Strix")
                .setDescription("RGB gaming laptop with Intel Core i9-13980HX (5.6GHz), 32GB DDR5 5600MHz RAM, 2TB NVMe SSD, NVIDIA RTX 4080 12GB GDDR6X, 16\" QHD+ 240Hz Mini-LED, Per-key RGB, Windows 11")
                .setSku("LAP1005")
                .setPrice(new Money.Builder().setAmount(45999).setCurrency("ZAR").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg5)
                .build());
        productService.create(new Product.Builder()
                .setName("Apple MacBook Pro 14")
                .setDescription("Apple M2 Pro chip (12-core CPU, 19-core GPU), 16GB Unified Memory, 1TB SSD, 14.2\" Liquid Retina XDR display, 120Hz ProMotion, macOS Ventura, 18-hour battery")
                .setSku("LAP1006")
                .setPrice(new Money.Builder().setAmount(42999).setCurrency("ZAR").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg6)
                .build());
        productService.create(new Product.Builder()
                .setName("Acer Predator Helios")
                .setDescription("Gaming laptop with Intel Core i7-12700H (4.7GHz), 16GB DDR5 4800MHz RAM, 1TB NVMe SSD, NVIDIA RTX 4060 8GB GDDR6, 15.6\" FHD 165Hz IPS, RGB 4-zone keyboard, Windows 11")
                .setSku("LAP1007")
                .setPrice(new Money.Builder().setAmount(32999).setCurrency("ZAR").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg7)
                .build());
        productService.create(new Product.Builder()
                .setName("Razer Blade 15")
                .setDescription("Premium gaming ultrabook with Intel Core i7-12800H (4.8GHz), 16GB DDR5 4800MHz RAM, 1TB NVMe SSD, NVIDIA RTX 4070 8GB GDDR6, 15.6\" QHD 240Hz, CNC aluminum, Windows 11")
                .setSku("LAP1008")
                .setPrice(new Money.Builder().setAmount(39999).setCurrency("ZAR").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg8)
                .build());
        productService.create(new Product.Builder()
                .setName("Samsung Galaxy Book2")
                .setDescription("Lightweight laptop with Intel Core i5-1235U (4.4GHz), 8GB LPDDR4X 4267MHz RAM, 256GB NVMe SSD, Intel UHD Graphics, 15.6\" FHD LED, 1.55kg lightweight, Windows 11")
                .setSku("LAP1009")
                .setPrice(new Money.Builder().setAmount(18999).setCurrency("ZAR").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg9)
                .build());
        productService.create(new Product.Builder()
                .setName("Microsoft Surface Laptop 5")
                .setDescription("Premium ultrabook with Intel Core i7-1255U (4.7GHz), 16GB LPDDR5X 5200MHz RAM, 512GB SSD, Intel Iris Xe Graphics, 13.5\" PixelSense Touch, Alcantara keyboard, Windows 11")
                .setSku("LAP1010")
                .setPrice(new Money.Builder().setAmount(31999).setCurrency("ZAR").build())
                .setCategoryId("LAPTOPS")
                .setImageData(laptopImg10)
                .build());

        // ==== PC CASES ====
        productService.create(new Product.Builder()
                .setName("NZXT H510")
                .setDescription("Compact mid-tower ATX case with tempered glass side panel, 2x 120mm fans, USB 3.1 Type-C, cable management system, supports GPUs up to 381mm, CPU coolers up to 165mm")
                .setSku("CAS2001")
                .setPrice(new Money.Builder().setAmount(1499).setCurrency("ZAR").build())
                .setCategoryId("CASES")
                .setImageData(caseImg1)
                .build());
        productService.create(new Product.Builder()
                .setName("Corsair iCUE 5000X")
                .setDescription("Premium RGB full-tower case with tempered glass, 3x SP120 RGB Elite fans, iCUE Lighting Node Core, supports E-ATX, 360mm radiator, 7x expansion slots, front USB-C")
                .setSku("CAS2002")
                .setPrice(new Money.Builder().setAmount(3299).setCurrency("ZAR").build())
                .setCategoryId("CASES")
                .setImageData(caseImg2)
                .build());
        productService.create(new Product.Builder()
                .setName("Fractal Design Meshify C")
                .setDescription("Compact ATX case with mesh front panel, 2x Dynamic X2 GP-12 fans, tempered glass side, high airflow design, supports GPUs up to 315mm, PSU shroud, dust filters")
                .setSku("CAS2003")
                .setPrice(new Money.Builder().setAmount(1799).setCurrency("ZAR").build())
                .setCategoryId("CASES")
                .setImageData(caseImg3)
                .build());
        productService.create(new Product.Builder()
                .setName("Cooler Master MasterBox NR600")
                .setDescription("Airflow optimized mid-tower with mesh front panel, 2x 120mm fans, tempered glass side, supports ATX/micro-ATX/mini-ITX, GPU up to 410mm, CPU cooler up to 166mm")
                .setSku("CAS2004")
                .setPrice(new Money.Builder().setAmount(1299).setCurrency("ZAR").build())
                .setCategoryId("CASES")
                .setImageData(caseImg4)
                .build());
        productService.create(new Product.Builder()
                .setName("Phanteks Eclipse P400A")
                .setDescription("ARGB mesh mid-tower with 3x DRGB fans, tempered glass side, digital RGB controller, high airflow mesh front, supports 360mm radiator, GPU up to 420mm, USB 3.0")
                .setSku("CAS2005")
                .setPrice(new Money.Builder().setAmount(2199).setCurrency("ZAR").build())
                .setCategoryId("CASES")
                .setImageData(caseImg5)
                .build());
        productService.create(new Product.Builder()
                .setName("Lian Li Lancool II")
                .setDescription("High airflow ATX case with mesh front panel, 3x 120mm PWM fans, tempered glass, tool-free design, supports E-ATX, GPU up to 384mm, 7+2 expansion slots, front USB-C")
                .setSku("CAS2006")
                .setPrice(new Money.Builder().setAmount(2499).setCurrency("ZAR").build())
                .setCategoryId("CASES")
                .setImageData(caseImg6)
                .build());
        productService.create(new Product.Builder()
                .setName("be quiet! Pure Base 500")
                .setDescription("Silent mid-tower case with sound damping material, 2x Pure Wings 2 140mm fans, tempered glass side, modular design, supports ATX, GPU up to 369mm, USB 3.0")
                .setSku("CAS2007")
                .setPrice(new Money.Builder().setAmount(1999).setCurrency("ZAR").build())
                .setCategoryId("CASES")
                .setImageData(caseImg7)
                .build());
        productService.create(new Product.Builder()
                .setName("Thermaltake Versa H18")
                .setDescription("Budget mini tower case with tempered glass side, 1x 120mm rear fan, mesh front panel, supports micro-ATX/mini-ITX, GPU up to 350mm, CPU cooler up to 155mm")
                .setSku("CAS2008")
                .setPrice(new Money.Builder().setAmount(899).setCurrency("ZAR").build())
                .setCategoryId("CASES")
                .setImageData(caseImg8)
                .build());
        productService.create(new Product.Builder()
                .setName("Deepcool Matrexx 55")
                .setDescription("Mid-tower ATX case with 4mm tempered glass side panel, 3x 120mm ARGB fans, mesh front panel, supports E-ATX, GPU up to 370mm, PSU cover, RGB controller")
                .setSku("CAS2009")
                .setPrice(new Money.Builder().setAmount(1599).setCurrency("ZAR").build())
                .setCategoryId("CASES")
                .setImageData(caseImg9)
                .build());
        productService.create(new Product.Builder()
                .setName("Antec DF500")
                .setDescription("ARGB gaming mid-tower with 3x 120mm ARGB fans, tempered glass side, mesh front panel, supports ATX/micro-ATX, GPU up to 350mm, 7 expansion slots, RGB controller")
                .setSku("CAS2010")
                .setPrice(new Money.Builder().setAmount(1899).setCurrency("ZAR").build())
                .setCategoryId("CASES")
                .setImageData(caseImg10)
                .build());

        // ==== PERIPHERALS ====
        productService.create(new Product.Builder()
                .setName("Logitech G Pro X")
                .setDescription("Mechanical gaming keyboard with GX Blue Clicky switches (actuation force 50g), detachable USB-C cable, LIGHTSYNC RGB, 87-key tenkeyless design, 1ms report rate")
                .setSku("PER3001")
                .setPrice(new Money.Builder().setAmount(2499).setCurrency("ZAR").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg1)
                .build());
        productService.create(new Product.Builder()
                .setName("Razer DeathAdder V3")
                .setDescription("High precision gaming mouse with Focus Pro 30K optical sensor, 59g lightweight, 8 programmable buttons, 1000Hz polling rate, optical mouse switches, 90-hour battery")
                .setSku("PER3002")
                .setPrice(new Money.Builder().setAmount(1499).setCurrency("ZAR").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg2)
                .build());
        productService.create(new Product.Builder()
                .setName("SteelSeries Arctis 7")
                .setDescription("Wireless gaming headset with 2.4GHz lag-free wireless, 24-hour battery, Discord-certified ClearCast microphone, S1 speaker drivers, DTS Headphone:X v2.0 surround")
                .setSku("PER3003")
                .setPrice(new Money.Builder().setAmount(3499).setCurrency("ZAR").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg3)
                .build());
        productService.create(new Product.Builder()
                .setName("Corsair K95 RGB Platinum")
                .setDescription("Mechanical keyboard with Cherry MX Speed switches (actuation 1.2mm), 6 programmable macro keys, aircraft-grade aluminum frame, per-key RGB, 8MB profile storage")
                .setSku("PER3004")
                .setPrice(new Money.Builder().setAmount(3999).setCurrency("ZAR").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg4)
                .build());
        productService.create(new Product.Builder()
                .setName("Razer BlackWidow V3")
                .setDescription("Mechanical gaming keyboard with Razer Green switches (actuation force 50g), chroma RGB lighting, programmable macros, dedicated media controls, 80 million clicks")
                .setSku("PER3005")
                .setPrice(new Money.Builder().setAmount(2999).setCurrency("ZAR").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg5)
                .build());
        productService.create(new Product.Builder()
                .setName("Logitech MX Master 3")
                .setDescription("Wireless productivity mouse with Darkfield 4000 DPI sensor, 70-day battery, MagSpeed electromagnetic scrolling, 7 buttons, USB-C charging, multi-computer flow")
                .setSku("PER3006")
                .setPrice(new Money.Builder().setAmount(1799).setCurrency("ZAR").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg6)
                .build());
        productService.create(new Product.Builder()
                .setName("HyperX Alloy FPS Pro")
                .setDescription("Compact mechanical keyboard with Cherry MX Red switches (45g actuation), solid steel frame, red LED backlighting, 100% anti-ghosting, detachable cable")
                .setSku("PER3007")
                .setPrice(new Money.Builder().setAmount(2199).setCurrency("ZAR").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg7)
                .build());
        productService.create(new Product.Builder()
                .setName("Razer Naga X")
                .setDescription("MMO gaming mouse with 16 programmable buttons, 5G advanced optical sensor (18000 DPI), 8-foot speedflex cable, mechanical switches, 85g lightweight design")
                .setSku("PER3008")
                .setPrice(new Money.Builder().setAmount(1699).setCurrency("ZAR").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg8)
                .build());
        productService.create(new Product.Builder()
                .setName("Logitech G915 TKL")
                .setDescription("Wireless mechanical keyboard with GL Tactile switches (1.5mm travel), LIGHTSYNC RGB, 40-hour battery, low-profile design, 87-key tenkeyless, 1ms report rate")
                .setSku("PER3009")
                .setPrice(new Money.Builder().setAmount(4299).setCurrency("ZAR").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg9)
                .build());
        productService.create(new Product.Builder()
                .setName("Corsair M65 RGB Elite")
                .setDescription("High-precision gaming mouse with 18000 DPI optical sensor, aluminum frame, 8 programmable buttons, onboard profile storage, adjustable weight system (97g)")
                .setSku("PER3010")
                .setPrice(new Money.Builder().setAmount(1399).setCurrency("ZAR").build())
                .setCategoryId("PERIPHERALS")
                .setImageData(periphImg10)
                .build());

        List<Product> all = productService.getAll();
        assertTrue(all.size() >= 30); // At least 30 products created
    }
}