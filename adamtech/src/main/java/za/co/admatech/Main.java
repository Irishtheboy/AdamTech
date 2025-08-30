package za.co.admatech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // This will open the React frontend in the default browser after Spring Boot starts
    @EventListener(ApplicationReadyEvent.class)
    public void openFrontend() {
        String url = "http://localhost:3000"; // React dev server URL
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(URI.create(url));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop not supported. Please open " + url + " manually.");
        }
    }
}
