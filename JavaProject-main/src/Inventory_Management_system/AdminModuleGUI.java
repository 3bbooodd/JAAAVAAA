package Inventory_Management_system;


import javax.swing.*;
import java.awt.*;


import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.time.*;
import java.time.format.DateTimeFormatter;


public class AdminModuleGUI {
    private static AdminModule adminModule = new AdminModule();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminModuleGUI().showLoginScreen());
    }

    private void showLoginScreen() {
        JFrame frame = new JFrame("Admin Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(null);

        JLabel idLabel = new JLabel("Enter ID:");
        idLabel.setBounds(20, 20, 80, 25);
        JTextField idField = new JTextField();
        idField.setBounds(120, 20, 150, 25);

        JLabel passwordLabel = new JLabel("Enter Password:");
        passwordLabel.setBounds(20, 60, 100, 25);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(120, 60, 150, 25);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 110, 100, 25);

        frame.add(idLabel);
        frame.add(idField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(loginButton);

        loginButton.addActionListener(e -> {
            String id = idField.getText();
            String password = new String(passwordField.getPassword());
            if (adminModule.login(id, password)) {
                frame.dispose();
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    public void showMainMenu() {
        JFrame frame = new JFrame("Admin Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 450);
        frame.setLayout(null);

        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(50, 30, 200, 30);

        JButton manageSuppliersButton = new JButton("Manage Suppliers");
        manageSuppliersButton.setBounds(50, 80, 200, 30);
        
        JButton manageProductsButton = new JButton("Manage Products");
        manageProductsButton.setBounds(50, 280, 200, 30);

        JButton generateReportsButton = new JButton("Generate Reports");
        generateReportsButton.setBounds(50, 130, 200, 30);

        JButton addOffersButton = new JButton("Add Offers");
        addOffersButton.setBounds(50, 180, 200, 30);

        JButton manageCategoriesButton = new JButton("Manage Categories");
        manageCategoriesButton.setBounds(50, 230, 200, 30);

//        JButton exitButton = new JButton("Exit");
//        exitButton.setBounds(50, 280, 200, 30);

        frame.add(changePasswordButton);
        frame.add(manageSuppliersButton);
        frame.add(manageProductsButton);
        frame.add(generateReportsButton);
        frame.add(addOffersButton);
        frame.add(manageCategoriesButton);
//        frame.add(exitButton);
        

        changePasswordButton.addActionListener(e -> showChangePasswordScreen(frame));
        manageSuppliersButton.addActionListener(e -> showManageSuppliersScreen());
        manageProductsButton.addActionListener(e -> showManageProductsScreen());
        generateReportsButton.addActionListener(e -> showGenerateReportsScreen());
        addOffersButton.addActionListener(e -> showAddOffersScreen());
        manageCategoriesButton.addActionListener(e -> showManageCategoriesScreen());
//        exitButton.addActionListener(e -> System.exit(0));
        
        
        frame.setVisible(true);
    }
    
    private void showManageProductsScreen() {
        ProductManagementSystem system = new ProductManagementSystem();
        
        system.saveProducts();
        system.startGUI();
    }

    private void showChangePasswordScreen(JFrame parentFrame) {
        String id = JOptionPane.showInputDialog(parentFrame, "Enter Admin ID:");
        if (id == null) return;
        String newPassword = JOptionPane.showInputDialog(parentFrame, "Enter New Password:");
        if (newPassword == null) return;

        adminModule.changeAdminPassword(id, newPassword);
    }

    private void showManageSuppliersScreen() {
        JFrame frame = new JFrame("Manage Suppliers");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(null);

        JButton addSupplierButton = new JButton("Add Supplier");
        addSupplierButton.setBounds(50, 30, 200, 30);

        JButton updateSupplierButton = new JButton("Update Supplier");
        updateSupplierButton.setBounds(50, 80, 200, 30);

        JButton deleteSupplierButton = new JButton("Delete Supplier");
        deleteSupplierButton.setBounds(50, 130, 200, 30);

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 180, 200, 30);

        frame.add(addSupplierButton);
        frame.add(updateSupplierButton);
        frame.add(deleteSupplierButton);
        frame.add(backButton);

        addSupplierButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "Enter Supplier ID:");
            String name = JOptionPane.showInputDialog(frame, "Enter Supplier Name:");
            String contractDateStr = JOptionPane.showInputDialog(frame, "Enter Contract Date (YYYY-MM-DD):");
            try {
                Date contractDate = new SimpleDateFormat("yyyy-MM-dd").parse(contractDateStr);
                adminModule.addSupplier(id, name, contractDate);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateSupplierButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "Enter Supplier ID to Update:");
            String newName = JOptionPane.showInputDialog(frame, "Enter New Name:");
            String newContractDateStr = JOptionPane.showInputDialog(frame, "Enter New Contract Date (YYYY-MM-DD):");
            try {
                Date newContractDate = new SimpleDateFormat("yyyy-MM-dd").parse(newContractDateStr);
                adminModule.updateSupplier(id, newName, newContractDate);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteSupplierButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "Enter Supplier ID to Delete:");
            adminModule.deleteSupplier(id);
        });

        backButton.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }

    private void showGenerateReportsScreen() {
        JFrame frame = new JFrame("Generate Reports");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(null);

        JButton productReportButton = new JButton("Products Report");
        productReportButton.setBounds(50, 30, 200, 30);

        JButton categoryStatisticsButton = new JButton("Category Statistics");
        categoryStatisticsButton.setBounds(50, 80, 200, 30);

        JButton profitReportButton = new JButton("Profit Report");
        profitReportButton.setBounds(50, 130, 200, 30);

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 180, 200, 30);

        frame.add(productReportButton);
        frame.add(categoryStatisticsButton);
        frame.add(profitReportButton);
        frame.add(backButton);

        productReportButton.addActionListener(e -> adminModule.generateProductReport());
        categoryStatisticsButton.addActionListener(e -> adminModule.generateCategoryStatistics());
        profitReportButton.addActionListener(e -> {
            try {
                adminModule.generateProfitReport();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error generating report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }

    private void showAddOffersScreen() {
        JFrame frame = new JFrame("Add Offers");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);

        JLabel productNameLabel = new JLabel("Product Name:");
        productNameLabel.setBounds(20, 30, 120, 25);
        JTextField productNameField = new JTextField();
        productNameField.setBounds(150, 30, 200, 25);

        JLabel offerLabel = new JLabel("Offer Description:");
        offerLabel.setBounds(20, 70, 120, 25);
        JTextField offerField = new JTextField();
        offerField.setBounds(150, 70, 200, 25);

        JLabel startDateLabel = new JLabel("Start Date (YYYY-MM-DD):");
        startDateLabel.setBounds(20, 110, 200, 25);
        JTextField startDateField = new JTextField();
        startDateField.setBounds(150, 110, 200, 25);

        JLabel endDateLabel = new JLabel("End Date (YYYY-MM-DD):");
        endDateLabel.setBounds(20, 150, 200, 25);
        JTextField endDateField = new JTextField();
        endDateField.setBounds(150, 150, 200, 25);

        JButton addOfferButton = new JButton("Add Offer");
        addOfferButton.setBounds(150, 200, 100, 30);

        frame.add(productNameLabel);
        frame.add(productNameField);
        frame.add(offerLabel);
        frame.add(offerField);
        frame.add(startDateLabel);
        frame.add(startDateField);
        frame.add(endDateLabel);
        frame.add(endDateField);
        frame.add(addOfferButton);

        addOfferButton.addActionListener(e -> {
            String productName = productNameField.getText();
            int offerDescription;
            try {
                offerDescription = Integer.parseInt(offerField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid offer description!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String startDate = startDateField.getText();
            String endDate = endDateField.getText();
            adminModule.addOfferForProduct(productName, offerDescription, startDate, endDate);
        });

        frame.setVisible(true);
    }

    private void showManageCategoriesScreen() {
        JFrame frame = new JFrame("Manage Categories");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(null);

        JButton addCategoryButton = new JButton("Add Category");
        addCategoryButton.setBounds(50, 30, 200, 30);

        JButton updateCategoryButton = new JButton("Update Category");
        updateCategoryButton.setBounds(50, 80, 200, 30);

        JButton deleteCategoryButton = new JButton("Delete Category");
        deleteCategoryButton.setBounds(50, 130, 200, 30);

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 180, 200, 30);

        frame.add(addCategoryButton);
        frame.add(updateCategoryButton);
        frame.add(deleteCategoryButton);
        frame.add(backButton);

        addCategoryButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "Enter Category ID:");
            String name = JOptionPane.showInputDialog(frame, "Enter Category Name:");
            int quantity;
            try {
                quantity = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Quantity:"));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid quantity!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                adminModule.addcategory(id, name, quantity);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error adding category: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateCategoryButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "Enter Category ID to Update:");
            String newName = JOptionPane.showInputDialog(frame, "Enter New Category Name:");
            int newQuantity;
            try {
                newQuantity = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter New Quantity:"));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid quantity!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            adminModule.updateCategory(id, newName, newQuantity);
        });

        deleteCategoryButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "Enter Category ID to Delete:");
            adminModule.deleteCategory(id);
        });

        backButton.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }
}

 class AdminModule extends Interface {

    private static final String ADMIN_FILE = "adminAccount.txt";
    private static final String SUPPLIER_FILE = "supplier.txt";
    private static final String PRODUCT_FILE = "products.txt";
    private static final String FILE_PATH = "warehouse.txt";
    private static final String SALES_FILE_PATH = "orders.txt";

    public AdminModule() {
        super();
    }

    @Override
    public boolean login(String ID,String pass) {
        try (BufferedReader br = new BufferedReader(new FileReader(ADMIN_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2 && credentials[0].equals(ID) && credentials[1].equals(pass)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading admin file: " + e.getMessage());
        }
        return false;    }

    public void changeAdminPassword(String ID, String newPass) {
        File inputFile = new File(ADMIN_FILE);
        File tempFile = new File("temp.txt");

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile)); BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials[0].equals(ID) && !(credentials[1].equals(newPass))) {
                    // Write updated password for the username
                    bw.write(ID + "," + newPass + "\n");
                    found = true;
                } else {
                    // Write the same line for other users
                    bw.write(line + "\n");
                }
            }

            if (!found) {
                System.out.println("New password can't be the same as the old password.");
            } else {
                System.out.println("Password updated successfully!");
            }
        } catch (IOException e) {
            System.out.println("Error updating password: " + e.getMessage());
        }

        // Replace the original file with the updated one
        if (found && inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Error renaming temp file to admins.txt.");
            }
        } else if (tempFile.exists()) {
            tempFile.delete(); // Clean up temp file if update failed
        }

    }

    public void addSupplier(String id, String name, Date contract) {
        if (isSupplierIdExists(id)) {
            System.out.println("Error: Supplier ID already exists.");
            return;
        }

        try (FileWriter fw = new FileWriter(SUPPLIER_FILE, true); // Enable append mode
                 BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(id + "," + name + "," + contract + "\n");
            System.out.println("Supplier added successfully!");
        } catch (IOException e) {
            System.out.println("Error adding supplier: " + e.getMessage());
        }
    }

    public boolean isSupplierIdExists(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(SUPPLIER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking supplier ID: " + e.getMessage());
        }
        return false;
    }

    public void updateSupplier(String id, String newName, Date newContract) {

        ArrayList<String> suppliers = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(SUPPLIER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id)) {
                    suppliers.add(id + "," + newName + "," + newContract);
                    updated = true;
                } else {
                    suppliers.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        try (FileWriter fw = new FileWriter(SUPPLIER_FILE, false); BufferedWriter bw = new BufferedWriter(fw)) {
            for (int i = 0; i < suppliers.size(); i++) {
                    bw.write(suppliers.get(i) + "\n");
                }
            if (updated) {
                System.out.println("Supplier updated successfully!");
            } else {
                System.out.println("Supplier not found.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    
    public void deleteSupplier(String id) {
    ArrayList<String> suppliers = new ArrayList<>();
    boolean deleted = false;

    try (BufferedReader br = new BufferedReader(new FileReader(SUPPLIER_FILE))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (!parts[0].equals(id)) {
                suppliers.add(line);
            } else {
                deleted = true;
            }
        }
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
        return;
    }

    try (FileWriter fw = new FileWriter(SUPPLIER_FILE, false); BufferedWriter bw = new BufferedWriter(fw)) {
        for (int i = 0; i < suppliers.size(); i++) {
            bw.write(suppliers.get(i) + "\n");
        }
        if (deleted) {
            System.out.println("Supplier deleted successfully!");
        } else {
            System.out.println("Supplier not found.");
        }
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
    
    public void generateProductReport() {
        File file = new File(PRODUCT_FILE);
        if (!file.exists() || file.length() == 0) {
            System.out.println("Error: No products found to generate a report.\n");
            return;
        }
        System.out.printf("%-15s %-15s %-15s %-10s %-15s\n", "Name", "Category", "Price", "Quantity", "Expiry Date");
        System.out.println("-----------------------------------------------------------------------------------------");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    System.out.printf("%-15s %-15s LE%-15s %-10s %-15s\n", parts[0], parts[3], parts[6], parts[4], parts[2]);
                } else {
                    System.out.println("Invalid data format in products.txt");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error generating product report: " + e.getMessage());
        }
    }

    public void generateCategoryStatistics() {
    File file = new File(PRODUCT_FILE);

    if (!file.exists() || file.length() == 0) {
        System.out.println("Error: No products found to generate category statistics.\n");
        return;
    }

    HashMap<String, Integer> categoryCounts = new HashMap<>();

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                String category = parts[3].trim();
                categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
            } else {
                System.out.println("Invalid data format in products.txt");
            }
        }

        System.out.println("\nCategory Statistics:");
        System.out.printf("%-20s %-10s\n", "Category", "Count");
        System.out.println("----------------------------------");

        for (Map.Entry<String, Integer> entry : categoryCounts.entrySet()) {
            System.out.printf("%-20s %-10d\n", entry.getKey(), entry.getValue());
        }

    } catch (IOException e) {
        System.out.println("Error generating category statistics: " + e.getMessage());
    }
}

public void addOfferForProduct(String productName, int offerDescription, String startDate, String endDate) {
    if (!isProductExists(productName)) {
        System.out.println("Error: Product with ID " + productName + " does not exist.");
        return;
    }

    // Append the offer to the offers.txt file
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("offers.txt", true))) {
        bw.write(productName + "," + offerDescription + "," + startDate + "," + endDate + "\n");
        System.out.println("Offer added successfully for product ID: " + productName);
    } catch (IOException e) {
        System.out.println("Error adding offer: " + e.getMessage());
    }
}

public boolean isProductExists(String productName) {
        File file = new File(PRODUCT_FILE);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(productName)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking product existence: " + e.getMessage());
        }
        return false;
    }
    
    public void addcategory(String id, String name, int quantity ) throws IOException {
        if(iscategoryExists(id,name)){
            System.out.println("error : category already exist");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(id + "," + name + "," + quantity +"\n");
            System.out.println("category add successfuly");
        } catch (IOException e) {
            System.out.println("error adding category: "+e.getMessage());
        }
    }
    public boolean iscategoryExists(String id,String name) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id)||parts[1].equals(name)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking supplier ID: " + e.getMessage());
        }
        return false;
    }

    // Update an existing product in the file
    public void updateCategory(String categoryId, String newCategoryName, int  newquantity) {

        ArrayList<String> categories = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(categoryId)) {
                    // Update the category
                    categories.add(categoryId + "," + newCategoryName + "," + newquantity);
                    updated = true;
                } else {
                    // Keep the existing line
                    categories.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading categories file: " + e.getMessage());
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (int i = 0; i < categories.size(); i++) {
                bw.write(categories.get(i) + "\n");

            }
            if (updated) {
                System.out.println("Category updated successfully!");
            } else {
                System.out.println("Category not found.");
            }
        } catch (IOException e) {
            System.out.println("Error writing to categories file: " + e.getMessage());
        }
    }

    // Delete a product from the file
    public void deleteCategory(String id) {

        ArrayList<String> category= new ArrayList<>();
        boolean deleted = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (!parts[0].equals(id)) {
                    category.add(line);
                } else {
                    deleted = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        try (FileWriter fw = new FileWriter(FILE_PATH, false); BufferedWriter bw = new BufferedWriter(fw)) {
            for (int i = 0; i < category.size(); i++) {
                bw.write(category.get(i) + "\n");
            }
            if (deleted) {
                System.out.println("category deleted successfully!");
            } else {
                System.out.println("category not found.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void generateProfitReport() throws IOException {
        // Read product file
        ArrayList<String> products = readFile(PRODUCT_FILE);
        Map<String, String> salesData = new HashMap<>();


        // Read sales data
        System.out.println("Reading sales data...");
        try (BufferedReader reader = new BufferedReader(new FileReader(SALES_FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {


                String[] parts = line.split(",");
                String name = parts[0];
                salesData.put(name,line);

            }
        } catch (IOException e) {
            System.err.println("Error reading sales file: " + e.getMessage());
            return;
        }

        System.out.println("\nProfit Report:");
        System.out.printf("%-10s %-10s %-10s %-10s\n", "Name", "Quantity", "Revenue", "Profit");

        for (String product : products) {
            String[] parts = product.split(",");

            String name = parts[0];
            if (salesData.containsKey(name)) {
                String[] productParts = salesData.get(name).split(",");
                int q=Integer.parseInt(productParts[1]);
                double boughtPrice = Double.parseDouble(parts[5]);
                double soldPrice = Double.parseDouble(parts[6]);
                double revenue = q * soldPrice;
                double profit = revenue - (q * boughtPrice); 
                System.out.printf("%-10s %-10d %-8.2f %-15.2f\n", name, q, revenue, profit);
            } else {
                System.out.println("Warning: No product data found for " + name);
            }

        }
    }

    // Method to read a file and return its content as a list of lines
    public ArrayList<String> readFile(String filePath) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        System.out.println("Reading file: " + filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            System.out.println("File read successfully: " + filePath);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            throw e;
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            throw e;
        }
        return lines;
    }
}
abstract class Interface {


    public String ID;
    public String pass;
    public String name;
    public String email;
    
    Interface(){
    }
    public String getId() {
        return ID;
    }

    public void setId(String ID) {
        this.ID = ID;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract boolean login(String a,String b);
}

class ProductManagementSystem {

    static class Product {
    String name;
    LocalDate productionDate;
    LocalDate expirationDate;
    String category;
    int quantity;
    double boughtPrice;
    double soldPrice;

    public Product(String name, LocalDate productionDate, LocalDate expirationDate, String category, int quantity, double boughtPrice, double soldPrice) {
        this.name = name;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
        this.category = category;
        this.quantity = quantity;
        this.boughtPrice = boughtPrice;
        this.soldPrice = soldPrice;
    }

    @Override
    public String toString() {
        return name + " - " + category + " - Quantity: " + quantity +
               " - Bought Price: " + boughtPrice + " - Sold Price: " + soldPrice;
    }
}


    private final ArrayList<Product> productList = new ArrayList<>();
    private final String fileName = "products.txt";

    public ProductManagementSystem() {
        loadProducts();
    }

    public void saveProducts() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        for (Product product : productList) {
            writer.write(product.name + "," +
                    product.productionDate + "," +
                    product.expirationDate + "," +
                    product.category + "," +
                    product.quantity + "," +
                    product.boughtPrice + "," +
                    product.soldPrice);
            writer.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    private void loadProducts() {
    File file = new File(fileName);
    if (file.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) { // Update to 7 to account for new fields
                    String name = parts[0];
                    LocalDate productionDate = LocalDate.parse(parts[1]);
                    LocalDate expirationDate = LocalDate.parse(parts[2]);
                    String category = parts[3];
                    int quantity = Integer.parseInt(parts[4]);
                    double boughtPrice = Double.parseDouble(parts[5]);
                    double soldPrice = Double.parseDouble(parts[6]);
                    productList.add(new Product(name, productionDate, expirationDate, category, quantity, boughtPrice, soldPrice));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


    public void startGUI() {
    JFrame frame = new JFrame("Product Management System");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);

    // Create a non-editable table model
    DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // All cells are non-editable
        }
    };

    JTable table = new JTable(model);
    model.addColumn("Name");
    model.addColumn("Production Date");
    model.addColumn("Expiration Date");
    model.addColumn("Category");
    model.addColumn("Quantity");
    model.addColumn("Bought Price");
    model.addColumn("Sold Price");


    updateTable(model);

    JScrollPane scrollPane = new JScrollPane(table);
    frame.add(scrollPane, BorderLayout.CENTER);

    JPanel controlPanel = new JPanel();
    JButton addButton = new JButton("Add");
    JButton updateButton = new JButton("Update");
    JButton deleteButton = new JButton("Delete");
    JButton searchButton = new JButton("Search");
    JButton notifyButton = new JButton("Notifications");

    controlPanel.add(addButton);
    controlPanel.add(updateButton);
    controlPanel.add(deleteButton);
    controlPanel.add(searchButton);
    controlPanel.add(notifyButton);

    frame.add(controlPanel, BorderLayout.SOUTH);

    addButton.addActionListener(e -> addProductDialog(model));
    updateButton.addActionListener(e -> updateProductDialog(model, table));
    deleteButton.addActionListener(e -> deleteProduct(model, table));
    searchButton.addActionListener(e -> searchProductsDialog());
    notifyButton.addActionListener(e -> showNotifications());

    frame.setVisible(true);
}


    private void updateTable(DefaultTableModel model) {
    model.setRowCount(0);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    for (Product product : productList) {
        model.addRow(new Object[]{
                product.name,
                product.productionDate.format(formatter),
                product.expirationDate.format(formatter),
                product.category,
                product.quantity,
                product.boughtPrice,
                product.soldPrice
        });
    }
}
private void addPlaceholderBehavior(JTextField textField, String placeholder) {
    textField.setText(placeholder);
    textField.setForeground(Color.GRAY);

    textField.addFocusListener(new java.awt.event.FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent e) {
            if (textField.getText().equals(placeholder)) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(java.awt.event.FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setText(placeholder);
                textField.setForeground(Color.GRAY);
            }
        }
    });
}


    private void addProductDialog(DefaultTableModel model) {
    JPanel panel = new JPanel(new GridLayout(0, 2));
    JTextField nameField = new JTextField();
    JTextField prodDateField = new JTextField();
    JTextField expDateField = new JTextField();
    JTextField categoryField = new JTextField();
    JTextField quantityField = new JTextField();
    JTextField boughtPriceField = new JTextField();
    JTextField soldPriceField = new JTextField();

    // Add placeholder behavior
    addPlaceholderBehavior(prodDateField, "yyyy-MM-dd");
    addPlaceholderBehavior(expDateField, "yyyy-MM-dd");

    panel.add(new JLabel("Name:"));
    panel.add(nameField);
    panel.add(new JLabel("Production Date:"));
    panel.add(prodDateField);
    panel.add(new JLabel("Expiration Date:"));
    panel.add(expDateField);
    panel.add(new JLabel("Category:"));
    panel.add(categoryField);
    panel.add(new JLabel("Quantity:"));
    panel.add(quantityField);
    panel.add(new JLabel("Bought Price:"));
    panel.add(boughtPriceField);
    panel.add(new JLabel("Sold Price:"));
    panel.add(soldPriceField);

    int result = JOptionPane.showConfirmDialog(null, panel, "Add Product", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
        try {
            String name = nameField.getText().trim();
            LocalDate prodDate = LocalDate.parse(prodDateField.getText().trim());
            LocalDate expDate = LocalDate.parse(expDateField.getText().trim());
            String category = categoryField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());
            double boughtPrice = Double.parseDouble(boughtPriceField.getText().trim());
            double soldPrice = Double.parseDouble(soldPriceField.getText().trim());

            // Check for duplicate product name
            for (Product product : productList) {
                if (product.name.equalsIgnoreCase(name)) {
                    JOptionPane.showMessageDialog(null, "Product with the same name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            productList.add(new Product(name, prodDate, expDate, category, quantity, boughtPrice, soldPrice));
            saveProducts();
            updateTable(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid Input! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}




    private void updateProductDialog(DefaultTableModel model, JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a product to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Product product = productList.get(selectedRow);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        JTextField nameField = new JTextField(product.name);
        JTextField prodDateField = new JTextField(product.productionDate.toString());
        JTextField expDateField = new JTextField(product.expirationDate.toString());
        JTextField categoryField = new JTextField(product.category);
        JTextField quantityField = new JTextField(String.valueOf(product.quantity));

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Production Date:"));
        panel.add(prodDateField);
        panel.add(new JLabel("Expiration Date:"));
        panel.add(expDateField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Update Product", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                product.name = nameField.getText();
                product.productionDate = LocalDate.parse(prodDateField.getText());
                product.expirationDate = LocalDate.parse(expDateField.getText());
                product.category = categoryField.getText();
                product.quantity = Integer.parseInt(quantityField.getText());

                saveProducts();
                updateTable(model);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid Input! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteProduct(DefaultTableModel model, JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a product to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this product?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            productList.remove(selectedRow);
            saveProducts();
            updateTable(model);
        }
    }

   private void searchProductsDialog() {
    JPanel panel = new JPanel(new GridLayout(0, 2));

    JTextField nameField = new JTextField();
    JTextField prodDateField = new JTextField("yyyy-MM-dd");
    JTextField expDateField = new JTextField("yyyy-MM-dd");
    JTextField categoryField = new JTextField();

    addPlaceholderBehavior(prodDateField, "yyyy-MM-dd");
    addPlaceholderBehavior(expDateField, "yyyy-MM-dd");
    
    panel.add(new JLabel("Name:"));
    panel.add(nameField);
    panel.add(new JLabel("Production Date:"));
    panel.add(prodDateField);
    panel.add(new JLabel("Expiration Date:"));
    panel.add(expDateField);
    panel.add(new JLabel("Category:"));
    panel.add(categoryField);

    int result = JOptionPane.showConfirmDialog(null, panel, "Search Products", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
        String name = nameField.getText().trim().toLowerCase();
        String prodDateInput = prodDateField.getText().trim();
        String expDateInput = expDateField.getText().trim();
        String category = categoryField.getText().trim().toLowerCase();

        LocalDate prodDate = null, expDate = null;

        try {
            if (!prodDateInput.isEmpty()) {
                prodDate = LocalDate.parse(prodDateInput);
            }
            if (!expDateInput.isEmpty()) {
                expDate = LocalDate.parse(expDateInput);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid date format! Please use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<Product> results = new ArrayList<>();
        for (Product product : productList) {
            boolean matches = true;

            if (!name.isEmpty() && !product.name.toLowerCase().contains(name)) {
                matches = false;
            }
            if (prodDate != null && !product.productionDate.equals(prodDate)) {
                matches = false;
            }
            if (expDate != null && !product.expirationDate.equals(expDate)) {
                matches = false;
            }
            if (!category.isEmpty() && !product.category.toLowerCase().contains(category)) {
                matches = false;
            }

            if (matches) {
                results.add(product);
            }
        }

        StringBuilder message = new StringBuilder("Search Results:\n");
        for (Product product : results) {
            message.append(product).append("\n");
        }

        if (results.isEmpty()) {
            message.append("No products found.");
        }

        JOptionPane.showMessageDialog(null, message.toString());
    }
}


    private void showNotifications() {
        LocalDate today = LocalDate.now();
        StringBuilder message = new StringBuilder("Notifications:\n");
        for (Product product : productList) {
            if (product.expirationDate.minusDays(7).isBefore(today)) {
                message.append("Near Expiry: ").append(product).append("\n");
            }
            if (product.quantity < 10) {
                message.append("Low Stock: ").append(product).append("\n");
            }
        }

        if (message.toString().equals("Notifications:\n")) {
            message.append("No notifications.");
        }

        JOptionPane.showMessageDialog(null, message.toString());
    }
}
