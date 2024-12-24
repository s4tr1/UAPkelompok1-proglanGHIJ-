import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class MainApp extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<BettaFish> bettaFishCollection;
    private String currentUser;
    private String currentRole;

    public MainApp() {
        // Show Login Form on start
        showLoginForm();
    }

    private void showLoginForm() {
        JFrame loginFrame = new JFrame("Login Form");
        loginFrame.setSize(500, 400);
        loginFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(59, 89, 152));
        JLabel headerLabel = new JLabel("Login", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel roleLabel = new JLabel("Login As:");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JRadioButton adminButton = new JRadioButton("Admin");
        JRadioButton buyerButton = new JRadioButton("Buyer");
        adminButton.setBackground(new Color(240, 240, 240));
        buyerButton.setBackground(new Color(240, 240, 240));

        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(adminButton);
        roleGroup.add(buyerButton);

        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rolePanel.setBackground(new Color(240, 240, 240));
        rolePanel.add(adminButton);
        rolePanel.add(buyerButton);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JPasswordField passwordField = new JPasswordField();

        formPanel.add(roleLabel);
        formPanel.add(rolePanel);
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton loginButton = createStyledButton("Login");
        JButton registerButton = createStyledButton("Register");

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = adminButton.isSelected() ? "admin" : buyerButton.isSelected() ? "buyer" : null;

            if (role == null) {
                JOptionPane.showMessageDialog(loginFrame, "Please select a role (Admin or Buyer).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (isValidLogin(username, password, role)) {
                JOptionPane.showMessageDialog(loginFrame, "Welcome, " + username + " as " + role + "!");
                currentUser = username;
                currentRole = role;
                loginFrame.dispose();
                if ("admin".equals(role)) {
                    showAdminDashboard();
                } else {
                    showBuyerDashboard();
                }
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            loginFrame.dispose();
            showRegistrationForm();
        });

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        loginFrame.add(mainPanel);
        loginFrame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(59, 89, 152));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 40));
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    public static boolean isValidLogin(String username, String password, String role) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 3 && userData[0].equals(username) && userData[1].equals(password)
                        && userData[2].equals(role)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showRegistrationForm() {
        new RegistrationForm();
    }

    private void showAdminDashboard() {
        new AdminDashboard(currentUser);
    }

    private void showBuyerDashboard() {
        new BuyerDashboard(currentUser);
    }

    public static void main(String[] args) {
        new MainApp();
    }
}
