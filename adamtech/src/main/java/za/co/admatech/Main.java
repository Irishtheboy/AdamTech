package za.co.admatech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import za.co.admatech.views.AdminProductSwing;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        // Start Spring Boot in a separate thread
        Thread springThread = new Thread(() -> SpringApplication.run(Main.class, args));
        springThread.start();

//        // Start Swing in the Event Dispatch Thread
//        SwingUtilities.invokeLater(() -> {
//            try {
//                new AdminProductSwing().setVisible(true);
//            } catch (HeadlessException e) {
//                System.err.println("GUI not supported in this environment.");
//                e.printStackTrace();
//            }
//        });
    }
}
