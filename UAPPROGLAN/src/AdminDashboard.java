import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AdminDashboard extends JFrame {
    private JPanel fishPanel;

    public AdminDashboard(String username) {
        setTitle("Admin Dashboard - Welcome, " + username);
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama dengan tata letak BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        // Header dengan warna biru ala Facebook
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(66, 103, 178));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));

        JLabel titleLabel = new JLabel("Admin Dashboard", JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Panel ikan
        fishPanel = new JPanel();
        fishPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        fishPanel.setBackground(new Color(255, 255, 255));
        JScrollPane scrollPane = new JScrollPane(fishPanel);

        // Panel tombol di bagian bawah
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton addFishButton = createStyledButton("Add Fish", "src/icons/add.png");
        JButton editFishButton = createStyledButton("Edit Fish", "src/icons/edit.png");
        JButton deleteFishButton = createStyledButton("Delete All Fish", "src/icons/delete.png");
        JButton logoutButton = createStyledButton("Logout", "src/icons/logout.png");

        addFishButton.addActionListener(e -> addFish());
        editFishButton.addActionListener(e -> editFish());
        deleteFishButton.addActionListener(e -> deleteAllFish());
        logoutButton.addActionListener(e -> {
            dispose();
            new MainApp();
        });

        buttonPanel.add(addFishButton);
        buttonPanel.add(editFishButton);
        buttonPanel.add(deleteFishButton);
        buttonPanel.add(logoutButton);

        // Menambahkan komponen ke panel utama
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        try {
            ImageIcon icon = new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            button.setIcon(icon);
        } catch (Exception e) {
            // Jika ikon tidak ditemukan, tombol tetap dibuat tanpa ikon
        }
        button.setFocusPainted(false);
        button.setBackground(new Color(66, 103, 178));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    private void addFish() {
        String name = JOptionPane.showInputDialog(this, "Enter Fish Name:");
        String species = JOptionPane.showInputDialog(this, "Enter Fish Species:");
        int age = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Fish Age(Month):"));
        double price = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Fish Price:"));
        int stock = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Fish Stock:"));

        String imageFileName = JOptionPane.showInputDialog(this, "Enter Image File Name (e.g., cupang1.jpg):");
        String imagePath = "images/" + imageFileName;
        File imageFile = new File(imagePath);

        if (!imageFile.exists()) {
            JOptionPane.showMessageDialog(this, "Image file not found in 'images/' directory.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(scaledIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel infoLabel = new JLabel("<html>Name: " + name + "<br>Species: " + species + "<br>Age: " + age + "<br>Price: " + price + "<br>Stock: " + stock + "</html>");
        infoLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel fishContainer = new JPanel(new BorderLayout());
        fishContainer.setPreferredSize(new Dimension(200, 250));
        fishContainer.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        fishContainer.setBackground(Color.WHITE);
        fishContainer.add(imageLabel, BorderLayout.CENTER);
        fishContainer.add(infoLabel, BorderLayout.SOUTH);

        fishPanel.add(fishContainer);
        fishPanel.revalidate();
        fishPanel.repaint();

        BettaFish fish = new BettaFish(name, species, age, price, stock, imagePath);
        Data.fishList.add(fish);
    }

    private void editFish() {
        if (Data.fishList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No fish available to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fishName = JOptionPane.showInputDialog(this, "Enter the name of the fish to edit:");
        BettaFish fishToEdit = null;

        for (BettaFish fish : Data.fishList) {
            if (fish.getName().equalsIgnoreCase(fishName)) {
                fishToEdit = fish;
                break;
            }
        }

        if (fishToEdit == null) {
            JOptionPane.showMessageDialog(this, "Fish not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newName = JOptionPane.showInputDialog(this, "Enter new name:", fishToEdit.getName());
        String newSpecies = JOptionPane.showInputDialog(this, "Enter new species:", fishToEdit.getSpecies());
        int newAge = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter new age:", fishToEdit.getAge()));
        double newPrice = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter new price:", fishToEdit.getPrice()));
        int newStock = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter new stock:", fishToEdit.getStock()));
        String newImageFileName = JOptionPane.showInputDialog(this, "Enter new image file name:", fishToEdit.getImagePath().substring(fishToEdit.getImagePath().lastIndexOf('/') + 1));

        String newImagePath = "images/" + newImageFileName;
        File newImageFile = new File(newImagePath);

        if (!newImageFile.exists()) {
            JOptionPane.showMessageDialog(this, "Image file not found in 'images/' directory.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        fishToEdit.setName(newName);
        fishToEdit.setSpecies(newSpecies);
        fishToEdit.setAge(newAge);
        fishToEdit.setPrice(newPrice);
        fishToEdit.setStock(newStock);
        fishToEdit.setImagePath(newImagePath);

        JOptionPane.showMessageDialog(this, "Fish details updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        refreshFishPanel();
    }

    private void refreshFishPanel() {
        fishPanel.removeAll();
        for (BettaFish fish : Data.fishList) {
            ImageIcon originalIcon = new ImageIcon(fish.getImagePath());
            Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JLabel imageLabel = new JLabel();
            imageLabel.setIcon(scaledIcon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel infoLabel = new JLabel("<html>Name: " + fish.getName() + "<br>Species: " + fish.getSpecies() + "<br>Age: (Month)" + fish.getAge() + "<br>Price: " + fish.getPrice() + "<br>Stock: " + fish.getStock() + "</html>");
            infoLabel.setHorizontalAlignment(JLabel.CENTER);

            JPanel fishContainer = new JPanel(new BorderLayout());
            fishContainer.setPreferredSize(new Dimension(200, 250));
            fishContainer.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            fishContainer.setBackground(Color.WHITE);
            fishContainer.add(imageLabel, BorderLayout.CENTER);
            fishContainer.add(infoLabel, BorderLayout.SOUTH);

            fishPanel.add(fishContainer);
        }
        fishPanel.revalidate();
        fishPanel.repaint();
    }

    private void deleteAllFish() {
        Data.fishList.clear();
        fishPanel.removeAll();
        fishPanel.revalidate();
        fishPanel.repaint();
    }
}
