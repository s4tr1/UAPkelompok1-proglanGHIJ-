// BuyerDashboard.java
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BuyerDashboard extends JFrame {
    private JPanel fishPanel;
    private JLabel balanceLabel;
    private double balance = 0.0;
    private ArrayList<BettaFish> purchasedFish = new ArrayList<>();

    public BuyerDashboard(String username) {
        setTitle("Buyer Dashboard - Welcome, " + username);
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Mengatur panel utama
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        // Header dengan informasi saldo
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(59, 89, 152));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));

        JLabel titleLabel = new JLabel("Buyer Dashboard", JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        balanceLabel = new JLabel("Balance: $0.00", JLabel.RIGHT);
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(balanceLabel, BorderLayout.EAST);

        // Panel tombol di bagian atas
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topButtonPanel.setBackground(new Color(240, 240, 240));

        JButton addBalanceButton = createStyledButton("Add Balance");
        JButton viewCollectionButton = createStyledButton("View Collection");
        JButton logoutButton = createStyledButton("Logout");

        addBalanceButton.addActionListener(e -> addBalance());
        viewCollectionButton.addActionListener(e -> viewCollection());
        logoutButton.addActionListener(e -> {
            dispose();
            new MainApp();
        });

        topButtonPanel.add(addBalanceButton);
        topButtonPanel.add(viewCollectionButton);
        topButtonPanel.add(logoutButton);

        // Panel ikan
        fishPanel = new JPanel();
        fishPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        fishPanel.setBackground(new Color(240, 240, 240));
        JScrollPane scrollPane = new JScrollPane(fishPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Menambahkan semua komponen ke panel utama
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(topButtonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);
        loadFishData();
        setVisible(true);
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

    private void loadFishData() {
        fishPanel.removeAll();
        for (BettaFish fish : Data.fishList) {
            if (fish.getStock() <= 0) {
                continue; // Skip fish with no stock
            }

            ImageIcon originalIcon = new ImageIcon(fish.getImagePath());
            Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JLabel imageLabel = new JLabel();
            imageLabel.setIcon(scaledIcon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            JLabel infoLabel = new JLabel("<html>Name: " + fish.getName() + "<br>Species: " + fish.getSpecies() + "<br>Age: " + fish.getAge() + "<br>Price: " + fish.getPrice() + "<br>Stock: " + fish.getStock() + "</html>");
            infoLabel.setHorizontalAlignment(JLabel.CENTER);

            JButton buyButton = new JButton("Buy");
            buyButton.setFocusPainted(false);
            buyButton.setBackground(new Color(66, 183, 42));
            buyButton.setForeground(Color.WHITE);
            buyButton.setFont(new Font("Arial", Font.BOLD, 14));
            buyButton.addActionListener(e -> buyFish(fish));

            JPanel fishContainer = new JPanel(new BorderLayout());
            fishContainer.setPreferredSize(new Dimension(220, 330));
            fishContainer.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            fishContainer.setBackground(Color.WHITE);
            fishContainer.add(imageLabel, BorderLayout.CENTER);
            fishContainer.add(infoLabel, BorderLayout.NORTH);
            fishContainer.add(buyButton, BorderLayout.SOUTH);

            fishPanel.add(fishContainer);
        }

        fishPanel.revalidate();
        fishPanel.repaint();
    }

    private void addBalance() {
        String input = JOptionPane.showInputDialog(this, "Enter amount to add:");
        try {
            double amount = Double.parseDouble(input);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than 0.");
            } else {
                balance += amount;
                updateBalanceLabel();
                JOptionPane.showMessageDialog(this, "Balance updated! Current balance: $" + balance);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount!");
        }
    }

    private void buyFish(BettaFish fish) {
        if (fish.getStock() <= 0) {
            JOptionPane.showMessageDialog(this, "This fish is out of stock.");
            return;
        }

        if (balance < fish.getPrice()) {
            JOptionPane.showMessageDialog(this, "Insufficient balance.");
            return;
        }

        balance -= fish.getPrice();
        fish.setStock(fish.getStock() - 1);
        purchasedFish.add(fish);
        updateBalanceLabel();
        JOptionPane.showMessageDialog(this, "You have purchased: " + fish.getName());
        loadFishData();
    }

    private void viewCollection() {
        JDialog collectionDialog = new JDialog(this, "My Collection", true);
        collectionDialog.setSize(800, 600);
        collectionDialog.setLocationRelativeTo(this);

        JPanel collectionPanel = new JPanel();
        collectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        collectionPanel.setBackground(new Color(240, 240, 240));

        for (BettaFish fish : purchasedFish) {
            ImageIcon originalIcon = new ImageIcon(fish.getImagePath());
            Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JLabel imageLabel = new JLabel();
            imageLabel.setIcon(scaledIcon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            JLabel infoLabel = new JLabel("<html>Name: " + fish.getName() + "<br>Species: " + fish.getSpecies() + "<br>Age: " + fish.getAge() + "<br>Price: " + fish.getPrice() + "</html>");
            infoLabel.setHorizontalAlignment(JLabel.CENTER);

            JPanel fishContainer = new JPanel(new BorderLayout());
            fishContainer.setPreferredSize(new Dimension(220, 300));
            fishContainer.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            fishContainer.setBackground(Color.WHITE);
            fishContainer.add(imageLabel, BorderLayout.CENTER);
            fishContainer.add(infoLabel, BorderLayout.SOUTH);

            collectionPanel.add(fishContainer);
        }

        JScrollPane scrollPane = new JScrollPane(collectionPanel);
        collectionDialog.add(scrollPane);
        collectionDialog.setVisible(true);
    }

    private void updateBalanceLabel() {
        balanceLabel.setText(String.format("Balance: $%.2f", balance));
    }

    // Tambahkan metode untuk mendapatkan nilai tukar mata uang
    public static double convertCurrency(String fromCurrency, String toCurrency, double amount) {
        double convertedAmount = 0.0;
        try {
            // API Endpoint
            String endpoint = "https://api.exchangerate-api.com/v4/latest/" + fromCurrency;

            // Membuka koneksi ke API
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Membaca respon dari API
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parsing manual respon untuk mendapatkan nilai tukar
            String responseStr = response.toString();
            String searchKey = "\"" + toCurrency + "\":";
            int startIndex = responseStr.indexOf(searchKey) + searchKey.length();
            int endIndex = responseStr.indexOf(",", startIndex);
            if (endIndex == -1) {
                endIndex = responseStr.indexOf("}", startIndex);
            }

            String rateStr = responseStr.substring(startIndex, endIndex).trim();
            double rate = Double.parseDouble(rateStr);

            // Menghitung hasil konversi
            convertedAmount = amount * rate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertedAmount;
    }
}
