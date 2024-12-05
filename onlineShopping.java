import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

//Product class to represent individual products
class Product {
    private String modelName;
    private double price;
    private String description;
    private String category;

    public Product(String modelName, double price, String description, String category) {
        this.modelName = modelName;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    // Getters
    public String getModelName() { return modelName; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
}

// User Authentication Class
class UserAuthentication extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public UserAuthentication() {
        setTitle("Login");
        setSize(600, 500); // Set the size to 600x500
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel with a background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("Shopping_Cart.jpeg");
                Image image = backgroundImage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        // Create a panel for login components
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2, 10, 10));
        loginPanel.setOpaque(false); // Make panel transparent

        // Add login components with larger fonts and sizes
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        loginPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setPreferredSize(new Dimension(200, 30));
        loginPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        loginPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setPreferredSize(new Dimension(200, 30));
        loginPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("admin") && password.equals("admin123")) {
                    new AdminPage();
                    dispose();
                } else if (username.equals("user") && password.equals("user123")) {
                    new UserPage();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Credentials!");
                }
            }
        });
        loginPanel.add(loginButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        loginPanel.add(exitButton);

        // Center the login panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(loginPanel, gbc);

        // Add the background panel to the frame
        add(backgroundPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}

// Admin Page Class
class AdminPage extends JFrame {
    private JTextField modelNameField, priceField, descriptionField, categoryField;
    private JButton addProductButton, deleteProductButton;

    public AdminPage() {
        setTitle("Admin - Add Products");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

        // Create labels with increased font size and center alignment
        JLabel modelNameLabel = new JLabel("Model Name:", SwingConstants.CENTER);
        modelNameLabel.setOpaque(true);
        modelNameLabel.setBackground(Color.CYAN);
        modelNameLabel.setForeground(Color.BLACK);
        modelNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(modelNameLabel);
        modelNameField = new JTextField();
        modelNameField.setPreferredSize(new Dimension(150, 30));
        inputPanel.add(modelNameField);

        JLabel priceLabel = new JLabel("Price:", SwingConstants.CENTER);
        priceLabel.setOpaque(true);
        priceLabel.setBackground(Color.PINK);
        priceLabel.setForeground(Color.BLACK);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(priceLabel);
        priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(150, 30));
        inputPanel.add(priceField);

        JLabel descriptionLabel = new JLabel("Description:", SwingConstants.CENTER);
        descriptionLabel.setOpaque(true);
        descriptionLabel.setBackground(Color.LIGHT_GRAY);
        descriptionLabel.setForeground(Color.BLACK);
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(descriptionLabel);
        descriptionField = new JTextField();
        descriptionField.setPreferredSize(new Dimension(150, 30));
        inputPanel.add(descriptionField);

        // Adjust categoryLabel to ensure full text visibility
        JLabel categoryLabel = new JLabel("Category (Phone/Laptop/Tablet):", SwingConstants.CENTER);
        categoryLabel.setOpaque(true);
        categoryLabel.setBackground(Color.ORANGE);
        categoryLabel.setForeground(Color.BLACK);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        categoryLabel.setPreferredSize(new Dimension(250, 30)); // Adjust width to fit text
        inputPanel.add(categoryLabel);
        categoryField = new JTextField();
        categoryField.setPreferredSize(new Dimension(150, 30));
        inputPanel.add(categoryField);

        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Add Product Button
        addProductButton = new JButton("Add Product");
        addProductButton.setBackground(Color.GREEN);
        addProductButton.setFont(new Font("Arial", Font.BOLD, 14));
        addProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addProductToFile();
            }
        });
        buttonPanel.add(addProductButton);

        // Delete Product Button
        deleteProductButton = new JButton("Delete Product");
        deleteProductButton.setBackground(Color.YELLOW);
        deleteProductButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AdminProductTable();
            }
        });
        buttonPanel.add(deleteProductButton);

        add(buttonPanel, BorderLayout.NORTH);

        // Logout Button with increased height
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setPreferredSize(new Dimension(getWidth(), 50)); // Increase height
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UserAuthentication();
                dispose();
            }
        });
        add(logoutButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addProductToFile() {
        // Input validation
        if (modelNameField.getText().isEmpty() || priceField.getText().isEmpty() || 
            descriptionField.getText().isEmpty() || categoryField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all fields!");
            return;
        }

        try {
            // Validate price
            double price = Double.parseDouble(priceField.getText());
            
            try (FileWriter fw = new FileWriter("Products.txt", true);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                
                String productLine = modelNameField.getText() + "," + 
                                     price + "," + 
                                     descriptionField.getText() + "," + 
                                     categoryField.getText() + "\n";
                
                bw.write(productLine);
                JOptionPane.showMessageDialog(null, "Product Added Successfully!");
                
                // Clear fields after adding
                modelNameField.setText("");
                priceField.setText("");
                descriptionField.setText("");
                categoryField.setText("");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Price! Please enter a number.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error adding product: " + ex.getMessage());
        }
    }
}

class AdminProductTable extends JFrame {
    private JTable productTable;
    private DefaultTableModel tableModel;
    private List<Product> products = new ArrayList<>();

    public AdminProductTable() {
        setTitle("Admin - Manage Products");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        loadProducts();

        String[] columnNames = {"Model Name", "Price", "Description", "Category"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(255,182,193)); // Pink color
                }
                c.setForeground(Color.BLACK);
                return c;
            }
        };

        // Increase row height and font size
        productTable.setRowHeight(30); // Increase row height
        productTable.setFont(new Font("Arial", Font.PLAIN, 16)); // Increase font size

        // Increase header font size and height
        JTableHeader header = productTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        // Adjust column widths
        productTable.getColumnModel().getColumn(1).setPreferredWidth(50); // Price column
        productTable.getColumnModel().getColumn(2).setPreferredWidth(300); // Description column

        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton deleteButton = new JButton("Delete Selected Product");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    products.remove(selectedRow);
                    saveProducts();
                    updateTableModel();
                    JOptionPane.showMessageDialog(null, "Product deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a product to delete.");
                }
            }
        });
        add(deleteButton, BorderLayout.SOUTH);

        updateTableModel(); // Ensure the table model is updated with loaded products

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadProducts() {
        products.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("Products.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    products.add(new Product(parts[0], 
                                             Double.parseDouble(parts[1]), 
                                             parts[2], 
                                             parts[3]));
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error loading products: " + ex.getMessage());
        }
    }

    private void saveProducts() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Products.txt"))) {
            for (Product p : products) {
                bw.write(p.getModelName() + "," + p.getPrice() + "," + p.getDescription() + "," + p.getCategory() + "\n");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving products: " + ex.getMessage());
        }
    }

    private void updateTableModel() {
        tableModel.setRowCount(0); // Clear existing rows
        for (Product p : products) {
            tableModel.addRow(new Object[]{p.getModelName(), p.getPrice(), p.getDescription(), p.getCategory()});
        }
    }
}

// User Page Class
class UserPage extends JFrame {
    private List<Product> products = new ArrayList<>();
    private List<Product> cartItems = new ArrayList<>(); // List to store cart items
    private JTable productTable;

    public UserPage() {
        setTitle("User - Product Catalog");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        loadProducts();

        // Create table model
        String[] columnNames = {"Model Name", "Price", "Description", "Category"};
        Object[][] data = new Object[products.size()][4];
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            data[i][0] = p.getModelName();
            data[i][1] = p.getPrice();
            data[i][2] = p.getDescription();
            data[i][3] = p.getCategory();
        }

        // Create JTable with custom renderer
        productTable = new JTable(data, columnNames) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(127, 255, 212)); // Aqua color
                }
                c.setForeground(Color.BLACK);
                return c;
            }
        };

        // Increase row height and font size
        productTable.setRowHeight(30); // Increase row height
        productTable.setFont(new Font("Arial", Font.PLAIN, 16)); // Increase font size

        // Increase header font size and height
        JTableHeader header = productTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        // Adjust column widths
        productTable.getColumnModel().getColumn(1).setPreferredWidth(50); // Price column
        productTable.getColumnModel().getColumn(2).setPreferredWidth(300); // Description column

        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addToCartButton = new JButton("Add to Cart");
        JButton viewCartButton = new JButton("View Cart");

        addToCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = productTable.getSelectedRow();
                if (selectedIndex != -1) {
                    Product selectedProduct = products.get(selectedIndex);
                    cartItems.add(selectedProduct); // Add selected product to cart
                    JOptionPane.showMessageDialog(null, "Item added to cart!");
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an item to add to cart.");
                }
            }
        });

        viewCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cartItems.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Your cart is empty!");
                } else {
                    new CartPage(cartItems); // Open CartPage with cart items
                }
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UserAuthentication();
                dispose();
            }
        });

        buttonPanel.add(addToCartButton);
        buttonPanel.add(viewCartButton);
        buttonPanel.add(logoutButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadProducts() {
        products.clear(); // Clear existing products before loading
        try (BufferedReader br = new BufferedReader(new FileReader("Products.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    products.add(new Product(parts[0], 
                                             Double.parseDouble(parts[1]), 
                                             parts[2], 
                                             parts[3]));
                }
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Products file not found. Create products first!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error loading products: " + ex.getMessage());
        }
    }
}

//Cart Page Class
class CartPage extends JFrame {
    private List<Product> cartItems;
    private JTextArea cartDetailsArea;
    private JButton checkoutButton;

    // Inside CartPage class
private JButton clearCartButton;

public CartPage(List<Product> items) {
    cartItems = items;
    setTitle("Shopping Cart");
    setSize(400, 300);
    setLayout(new BorderLayout());

    cartDetailsArea = new JTextArea();
    cartDetailsArea.setEditable(false);
    updateCartDetails();

    JScrollPane scrollPane = new JScrollPane(cartDetailsArea);
    add(scrollPane, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    checkoutButton = new JButton("Proceed to Checkout");
    checkoutButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (cartItems.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Your cart is empty!");
                return;
            }
            double total = cartItems.stream().mapToDouble(Product::getPrice).sum();
            JOptionPane.showMessageDialog(null, 
                String.format("Total Amount: Rupees %.2f\nThank you for shopping!", total));
            dispose();
        }
    });

    clearCartButton = new JButton("Clear Cart");
    clearCartButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            cartItems.clear();
            updateCartDetails();
            JOptionPane.showMessageDialog(null, "All items removed from cart!");
            dispose();
        }
    });

    buttonPanel.add(checkoutButton);
    buttonPanel.add(clearCartButton);
    add(buttonPanel, BorderLayout.SOUTH);

    setLocationRelativeTo(null);
    setVisible(true);
}
   private void updateCartDetails() {
        StringBuilder details = new StringBuilder("Cart Items:\n\n");
        double total = 0;
        for (Product item : cartItems) {
            details.append(String.format("%s - Rupees %.2f\n", item.getModelName(), item.getPrice()));
            total += item.getPrice();
        }
        details.append(String.format("\nTotal: Rupees %.2f", total));
        cartDetailsArea.setText(details.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserAuthentication());
    }
}