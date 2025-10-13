package za.co.admatech.views;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import okhttp3.*;
import za.co.admatech.domain.enums.ProductType;

public class AdminProductSwing extends JFrame {

    private JTextField nameField, descriptionField, skuField, amountField, currencyField;
    private JComboBox<ProductType> categoryCombo;
    private JLabel imageLabel;
    private File selectedImage;
    private JButton submitButton;
    private JTable productTable;
    private ProductTableModel tableModel;

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build();

    private final Gson gson = new Gson();
    private final String BASE_URL = "http://localhost:8080/adamtech/products";

    private Long updateProductId = null; // null = add, otherwise update

    public AdminProductSwing() {
        setTitle("Admin - Product Manager");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Add / Update Product", initAddUpdatePanel());
        tabs.addTab("View / Delete Products", initViewPanel());
        add(tabs);

        loadProducts(); // load table on start
    }

    /** ------------------ Add / Update Panel ------------------ */
    private JPanel initAddUpdatePanel() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));

        nameField = new JTextField();
        descriptionField = new JTextField();
        skuField = new JTextField();
        amountField = new JTextField();
        currencyField = new JTextField("USD");
        categoryCombo = new JComboBox<>(ProductType.values());
        imageLabel = new JLabel("No image selected");

        JButton imageButton = new JButton("Choose Image");
        imageButton.addActionListener(e -> chooseImage());

        submitButton = new JButton("Add Product");
        submitButton.addActionListener(e -> {
            if (updateProductId == null) addProduct();
            else updateProduct(updateProductId);
        });

        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Description:")); panel.add(descriptionField);
        panel.add(new JLabel("SKU:")); panel.add(skuField);
        panel.add(new JLabel("Price:")); panel.add(amountField);
        panel.add(new JLabel("Currency:")); panel.add(currencyField);
        panel.add(new JLabel("Category:")); panel.add(categoryCombo);
        panel.add(imageButton); panel.add(imageLabel);
        panel.add(submitButton);

        return panel;
    }

    /** ------------------ View / Delete Panel ------------------ */
    private JPanel initViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        tableModel = new ProductTableModel();
        productTable = new JTable(tableModel);
        panel.add(new JScrollPane(productTable), BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton deleteButton = new JButton("Delete Selected");
        JButton editButton = new JButton("Edit Selected");
        buttons.add(editButton); buttons.add(deleteButton);
        panel.add(buttons, BorderLayout.SOUTH);

        deleteButton.addActionListener(e -> deleteSelected());
        editButton.addActionListener(e -> editSelected());

        return panel;
    }

    /** ------------------ CRUD Methods ------------------ */

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            selectedImage = fileChooser.getSelectedFile();
            imageLabel.setText(selectedImage.getName());
        }
    }

    private void addProduct() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    sendProductRequest("create");
                } catch (IOException e) {
                    showError("Error adding product: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                JOptionPane.showMessageDialog(AdminProductSwing.this, "Product added!");
                clearForm();
                loadProducts();
            }
        }.execute();
    }

    private void updateProduct(Long id) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    sendProductRequest("update/" + id);
                } catch (IOException e) {
                    showError("Error updating product: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                JOptionPane.showMessageDialog(AdminProductSwing.this, "Product updated!");
                clearForm();
                submitButton.setText("Add Product");
                updateProductId = null;
                loadProducts();
            }
        }.execute();
    }

    private void deleteSelected() {
        int row = productTable.getSelectedRow();
        if (row < 0) return;

        Long id = tableModel.getProductAt(row).getId();

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    String url = BASE_URL + "/delete/" + id;
                    System.out.println("➡ DELETE " + url);

                    Request request = new Request.Builder()
                            .url(url)
                            .delete()
                            .build();

                    try (Response response = client.newCall(request).execute()) {
                        System.out.println("⬅ " + response.code() + " " + response.message());
                        if (!response.isSuccessful()) {
                            showError("Delete failed: " + response.code() + " " + response.message());
                        }
                    }
                } catch (IOException e) {
                    showError("Error deleting product: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                loadProducts();
            }
        }.execute();
    }

    private void editSelected() {
        int row = productTable.getSelectedRow();
        if (row < 0) return;

        Product selected = tableModel.getProductAt(row);
        updateProductId = selected.getId();

        nameField.setText(selected.getName());
        descriptionField.setText(selected.getDescription());
        skuField.setText(selected.getSku());
        amountField.setText(String.valueOf(selected.getPrice().getAmount()));
        currencyField.setText(selected.getPrice().getCurrency());
        categoryCombo.setSelectedItem(ProductType.valueOf(selected.getCategoryId()));
        submitButton.setText("Update Product");
    }

    private void clearForm() {
        nameField.setText("");
        descriptionField.setText("");
        skuField.setText("");
        amountField.setText("");
        currencyField.setText("USD");
        categoryCombo.setSelectedIndex(0);
        imageLabel.setText("No image selected");
        selectedImage = null;
    }

    private void loadProducts() {
        new SwingWorker<List<Product>, Void>() {
            @Override
            protected List<Product> doInBackground() {
                try {
                    Request request = new Request.Builder()
                            .url(BASE_URL + "/getAll")
                            .get()
                            .build();
                    try (Response response = client.newCall(request).execute()) {
                        if (!response.isSuccessful()) return List.of();
                        String json = response.body().string();
                        Product[] arr = gson.fromJson(json, Product[].class);
                        return Arrays.asList(arr);
                    }
                } catch (IOException e) {
                    showError("Failed to load products: " + e.getMessage());
                    return List.of();
                }
            }

            @Override
            protected void done() {
                try {
                    tableModel.setProducts(get());
                } catch (Exception ignored) {}
            }
        }.execute();
    }

    private void sendProductRequest(String endpoint) throws IOException {
        String base64Image = null;
        if (selectedImage != null) {
            byte[] imageBytes = Files.readAllBytes(selectedImage.toPath());
            base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);
        }

        Product product = new Product();
        product.setName(nameField.getText());
        product.setDescription(descriptionField.getText());
        product.setSku(skuField.getText());
        product.setPrice(new Money(Double.parseDouble(amountField.getText()), currencyField.getText()));
        product.setCategoryId(categoryCombo.getSelectedItem().toString());
        product.setImageData(base64Image);

        String json = gson.toJson(product);
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + endpoint)
                .method(endpoint.startsWith("update") ? "PUT" : "POST", body)
                .build();

        client.newCall(request).execute();
    }

    private void showError(String message) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE));
    }

    /** ------------------ Table Model ------------------ */
    class ProductTableModel extends AbstractTableModel {
        private final String[] columns = {"ID", "Name", "SKU", "Price", "Category", "Image"};
        private List<Product> products = List.of();

        public void setProducts(List<Product> products) {
            this.products = products;
            fireTableDataChanged();
        }

        @Override public int getRowCount() { return products.size(); }
        @Override public int getColumnCount() { return columns.length; }
        @Override public String getColumnName(int col) { return columns[col]; }

        @Override
        public Class<?> getColumnClass(int col) {
            if (col == 5) return Icon.class; // image column
            return String.class;
        }

        @Override
        public Object getValueAt(int row, int col) {
            Product p = products.get(row);
            return switch (col) {
                case 0 -> p.getId();
                case 1 -> p.getName();
                case 2 -> p.getSku();
                case 3 -> p.getPrice().getAmount() + " " + p.getPrice().getCurrency();
                case 4 -> p.getCategoryId();
                case 5 -> {
                    if (p.imageData != null) {
                        byte[] imgBytes = java.util.Base64.getDecoder().decode(p.imageData);
                        yield new ImageIcon(new ImageIcon(imgBytes)
                                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                    } else yield null;
                }
                default -> null;
            };
        }

        public Product getProductAt(int row) { return products.get(row); }
    }

    /** ------------------ Product & Money ------------------ */
    static class Product {
        @SerializedName("productId")
        private Long id;
        private String name;
        private String description;
        private String sku;
        private Money price;
        private String categoryId;
        @SerializedName("imageData")
        private String imageData;

        public Long getId() { return id; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public String getSku() { return sku; }
        public Money getPrice() { return price; }
        public String getCategoryId() { return categoryId; }
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
        public Money(double amount, String currency) { this.amount = amount; this.currency = currency; }
        public double getAmount() { return amount; }
        public String getCurrency() { return currency; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminProductSwing().setVisible(true));
    }
}
