package za.co.admatech.views;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import okhttp3.*;

public class AdminProductSwing extends JFrame {
    private JTextField nameField, descriptionField, skuField, amountField, currencyField, categoryField;
    private JLabel imageLabel;
    private File selectedImage;

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build();

    private final String BACKEND_URL = "http://localhost:8080/adamtech/products/create";
    private final Gson gson = new Gson();

    public AdminProductSwing() {
        setTitle("Admin - Add Product");
        setSize(400, 500);
        setLayout(new GridLayout(9, 2, 5, 5));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        nameField = new JTextField();
        descriptionField = new JTextField();
        skuField = new JTextField();
        amountField = new JTextField();
        currencyField = new JTextField("USD");
        categoryField = new JTextField();

        JButton imageButton = new JButton("Choose Image");
        imageLabel = new JLabel("No image selected");

        JButton submitButton = new JButton("Add Product");

        add(new JLabel("Name:")); add(nameField);
        add(new JLabel("Description:")); add(descriptionField);
        add(new JLabel("SKU:")); add(skuField);
        add(new JLabel("Price:")); add(amountField);
        add(new JLabel("Currency:")); add(currencyField);
        add(new JLabel("Category:")); add(categoryField);
        add(imageButton); add(imageLabel);
        add(submitButton);

        // Choose image
        imageButton.addActionListener(e -> chooseImage());

        // Submit product in background
        submitButton.addActionListener(e -> new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    addProduct();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(AdminProductSwing.this,
                                    "Error adding product: " + ex.getMessage()));
                }
                return null;
            }
        }.execute());
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            selectedImage = fileChooser.getSelectedFile();
            imageLabel.setText(selectedImage.getName());
        }
    }

    private void addProduct() throws IOException {
        String base64Image = null;
        if (selectedImage != null) {
            byte[] imageBytes = Files.readAllBytes(selectedImage.toPath());
            base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);
        }

        // Build Product object
        Product product = new Product();
        product.setName(nameField.getText());
        product.setDescription(descriptionField.getText());
        product.setSku(skuField.getText());
        product.setPrice(new Money(Double.parseDouble(amountField.getText()), currencyField.getText()));
        product.setCategoryId(categoryField.getText());
        product.setImageData(base64Image);

        // Serialize with Gson
        String json = gson.toJson(product);

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(BACKEND_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this, "Product added successfully!"));
            } else {
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this, "Failed: " + response.code() + " " + response.message()));
            }
        }
    }

    // Inner classes for serialization
    static class Product {
        private String name;
        private String description;
        private String sku;
        private Money price;
        private String categoryId;
        @SerializedName("imageData")
        private String imageData;

        // setters
        public void setName(String name) { this.name = name; }
        public void setDescription(String description) { this.description = description; }
        public void setSku(String sku) { this.sku = sku; }
        public void setPrice(Money price) { this.price = price; }
        public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
        public void setImageData(String imageData) { this.imageData = imageData; }
    }

    static class Money {
        private double amount;
        private String currency;

        public Money(double amount, String currency) {
            this.amount = amount;
            this.currency = currency;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminProductSwing().setVisible(true));
    }
}
