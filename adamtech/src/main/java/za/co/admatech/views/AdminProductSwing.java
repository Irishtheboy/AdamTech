package za.co.admatech.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;

import okhttp3.*;

public class AdminProductSwing extends JFrame {
    private JTextField nameField, descriptionField, skuField, amountField, currencyField, categoryField;
    private JLabel imageLabel;
    private File selectedImage;

    private final OkHttpClient client = new OkHttpClient();
    private final String BACKEND_URL = "http://localhost:8080/admin/products/add";

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

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Description:"));
        add(descriptionField);
        add(new JLabel("SKU:"));
        add(skuField);
        add(new JLabel("Price:"));
        add(amountField);
        add(new JLabel("Currency:"));
        add(currencyField);
        add(new JLabel("Category:"));
        add(categoryField);
        add(imageButton);
        add(imageLabel);
        add(submitButton);

        imageButton.addActionListener(e -> chooseImage());
        submitButton.addActionListener(e -> {
            try {
                addProduct();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding product: " + ex.getMessage());
            }
        });
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if(option == JFileChooser.APPROVE_OPTION){
            selectedImage = fileChooser.getSelectedFile();
            imageLabel.setText(selectedImage.getName());
        }
    }

    private void addProduct() throws IOException {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("name", nameField.getText())
                .addFormDataPart("description", descriptionField.getText())
                .addFormDataPart("sku", skuField.getText())
                .addFormDataPart("amount", amountField.getText())
                .addFormDataPart("currency", currencyField.getText())
                .addFormDataPart("categoryId", categoryField.getText());

        if(selectedImage != null){
            builder.addFormDataPart(
                    "image",
                    selectedImage.getName(),
                    RequestBody.create(selectedImage, MediaType.parse("image/jpeg"))
            );
        }

        Request request = new Request.Builder()
                .url(BACKEND_URL)
                .post(builder.build())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if(response.isSuccessful()){
                JOptionPane.showMessageDialog(this, "Product added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed: " + response.code());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminProductSwing().setVisible(true));
    }
}
