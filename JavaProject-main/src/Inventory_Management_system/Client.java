package Inventory_Management_system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;

public class Client extends javax.swing.JFrame {

    private JTextField emailToField;
    private String clientName;
    private String clientEmail;
    private AdminModule adminModule;
    private AdminModuleGUI ssss;
    // Constructor to initialize with client data
    public Client(String name, String email) {
        this.clientName = name;
        this.clientEmail = email;
        initComponents();
        adminModule = new AdminModule();
        ssss = new AdminModuleGUI(); 
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        JTabbedPane tabbedPane = new JTabbedPane();
        
        JPanel AdminPanel = new JPanel();
        
        JLabel ID = new JLabel("ID:");
        JTextField ID2 = new JTextField(20);
        
        JLabel pass = new JLabel("Password:");
        JPasswordField pass2 = new JPasswordField(20);
        
        JButton login = new JButton("LOGIN");
        AdminPanel.add(ID);
        AdminPanel.add(ID2);
        AdminPanel.add(pass);
        AdminPanel.add(pass2);
        AdminPanel.add(login);

        // Order Button Logic
        login.addActionListener(e -> {
            String id = ID2.getText();
            String password = new String(pass2.getPassword());
            if (adminModule.login(id, password)) {
                dispose();
                ssss.showMainMenu();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        tabbedPane.add("Admin", AdminPanel);
        
        
        
        //////////////////////////////////////////////////////////////////
        // Tab 2: Request Purchase Order
        JPanel orderPanel = new JPanel();
        JLabel productLabel = new JLabel("Product:");
        JTextField productField = new JTextField(20);
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField(5);
        JButton orderButton = new JButton("Request Order");
        orderPanel.add(productLabel);
        orderPanel.add(productField);
        orderPanel.add(quantityLabel);
        orderPanel.add(quantityField);
        orderPanel.add(orderButton);

        // Order Button Logic
        orderButton.addActionListener(e -> placeOrder(productField.getText(), Integer.parseInt(quantityField.getText())));

        tabbedPane.add("Request Purchase Order", orderPanel);

        // Tab 3: Generate Order Reports
        JPanel reportPanel = new JPanel();
        JTextArea reportArea = new JTextArea(15, 30);
        JButton generateReportButton = new JButton("Generate Report");
        reportPanel.add(new JScrollPane(reportArea));
        reportArea.setEnabled(false);
        reportPanel.add(generateReportButton);

        // Report Button Logic
        generateReportButton.addActionListener(e -> generateReport(reportArea));

        tabbedPane.add("Generate Order Report", reportPanel);

        // Tab 4: Email Notification
        JPanel emailPanel = new JPanel();
        JLabel emailToLabel = new JLabel("Email to:");
        emailToField = new JTextField(20);
        JButton sendEmailButton = new JButton("Send Notification");
        emailPanel.add(emailToLabel);
        emailPanel.add(emailToField);
        emailPanel.add(sendEmailButton);

        // Email Button Logic
        sendEmailButton.addActionListener(e -> sendNotification());

        tabbedPane.add("Email Notification", emailPanel);

        // Set up the JFrame
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(tabbedPane);
        pack();
        setTitle("Client Management System");
        setSize(1000, 500);
        setVisible(true);

        // Display welcome message with client information
        JOptionPane.showMessageDialog(this, 
            "Welcome , " + clientName ,
            "Client Registered",
            JOptionPane.INFORMATION_MESSAGE);
    }///////

    
    
    // Method to handle order placement
    private void placeOrder(String product, int quantity) {
    File file = new File("products.txt");
    double soldPrice = 0;
    boolean found = false; // Flag to check if the product is found

    // Validate inputs
    if (product.isEmpty() || quantity <= 0) {
        JOptionPane.showMessageDialog(null, "Please fill in all fields with valid values!");
        return;
    }

    if (file.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7 && parts[0].equalsIgnoreCase(product)) {
                    String name = parts[0];
                    LocalDate productionDate = LocalDate.parse(parts[1]);
                    LocalDate expirationDate = LocalDate.parse(parts[2]);
                    String category = parts[3];
                    int quantityy = Integer.parseInt(parts[4]);
                    double boughtPrice = Double.parseDouble(parts[5]);
                    double soldPricee = Double.parseDouble(parts[6]);
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("products.txt"))) {
        
            writer.write(name + "," +
                    productionDate + "," +
                    expirationDate + "," +
                    category + "," +
                    (quantityy - quantity) + "," +
                    boughtPrice + "," +
                    soldPrice);
    } catch (IOException e) {
        e.printStackTrace();
    }
                    found = true; 
                    int quantityAvailable = Integer.parseInt(parts[4]);
                    
                    if (quantityAvailable >= quantity) { 
                        soldPrice = Double.parseDouble(parts[6]);
                    } else {
                        JOptionPane.showMessageDialog(null, "Stock not enough!");
                        return;
                    }
                    break; // Exit the loop once the product is found and processed
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reading product file!");
            return;
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "Product not found!");
            return;
        }
    } else {
        JOptionPane.showMessageDialog(null, "Product file does not exist!");
        return;
    }

    // Save the order
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt", true))) {
        writer.write(product + "," + quantity + "," + (quantity * soldPrice));
        writer.newLine();
        JOptionPane.showMessageDialog(null, "Order placed successfully!");
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(null, "Error saving order!");
    }
}


    // Method to handle report generation
    private void generateReport(JTextArea reportArea) {
        try (BufferedReader reader = new BufferedReader(new FileReader("orders.txt"))) {
            StringBuilder report = new StringBuilder("Order Report:\n");
            String line;
            while ((line = reader.readLine()) != null) {
                report.append(line).append("\n");
            }
            reportArea.setText(report.toString());
            reportArea.setForeground(Color.BLACK);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error generating report!");
        }
    }

    // Method to handle email notifications
    private void sendNotification() {
        String emailTo = emailToField.getText();
        if (emailTo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please provide an email address.!");
        } else {
            JOptionPane.showMessageDialog(null, "Email notification sent to " + emailTo);
        }
    }

    public static void main(String[] args) {
        // Launch RegisterWindow first
        java.awt.EventQueue.invokeLater(() -> new RegisterWindow().setVisible(true));
    }
}

class RegisterWindow extends JFrame {

    private JTextField nameField;
    private JTextField emailField;

    public RegisterWindow() {
        setTitle("Register Client");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel("Name: ");
        nameField = new JTextField(20);
        
        JLabel emailLabel = new JLabel("Email: ");
        emailField = new JTextField(20);
        
        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(100, 40));
        
        nameField.setPreferredSize(new Dimension(10, 10));
        emailField.setPreferredSize(new Dimension(10, 10));
        

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();

                if (name.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields!");
                } else {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("clients.txt", true))) {
                        writer.write("Name: " + name + "\n" + "Email: " + email + "\n" + "--------------------------------------" + "\n" );
                        writer.newLine();
                        JOptionPane.showMessageDialog(null, "Client registered successfully!");

                        // Launch Client Management Window
                        new Client(name, email).setVisible(true);
                        dispose(); // Close the Register window
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error saving client data!");
                    }
                }
            }
        });

        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(registerButton);
    }
}
