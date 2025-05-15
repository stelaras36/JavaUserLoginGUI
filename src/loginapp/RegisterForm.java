package loginapp;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;


public class RegisterForm extends JFrame {
	private static final long serialVersionUID = 1L;
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JCheckBox darkModeToggle;

    public RegisterForm() {
        setTitle("Register");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        registerButton = new JButton("Register");
        add(registerButton);

        registerButton.addActionListener(e -> registerUser());

        // 🔘 Dark Mode toggle
        darkModeToggle = new JCheckBox("Dark Mode");
        darkModeToggle.addActionListener(e -> {
            boolean enabled = darkModeToggle.isSelected();
            Utils.toggleDarkMode(enabled);
        });
        add(new JLabel());          // Κενή θέση για στοίχιση
        add(darkModeToggle);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void registerUser() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Έλεγχος: Μόνο αγγλικοί χαρακτήρες & αριθμοί
        if (!Pattern.matches("[a-zA-Z0-9]+", username)) {
            JOptionPane.showMessageDialog(this,
                "Only English letters and numbers allowed in username.",
                "Invalid Username", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Έλεγχος: Email να είναι από συγκεκριμένα domains
        if (!email.matches("^[a-zA-Z0-9._%+-]+@(gmail\\.com|yahoomail\\.com|hotmail\\.com|outlook\\.com)$")) {
            JOptionPane.showMessageDialog(this,
                "Only emails from gmail.com, yahoomail.com, hotmail.com or outlook.com are allowed.",
                "Invalid Email", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Έλεγχος: Password ελάχιστου μήκους
        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this,
                "Password must be at least 4 characters.",
                "Invalid Password", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
     // Έλεγχος αν το username υπάρχει ήδη στο αρχείο
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equalsIgnoreCase(username)) {
                    JOptionPane.showMessageDialog(this,
                        "Username already exists. Please choose another.",
                        "Duplicate Username", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (parts[1].equalsIgnoreCase(email)) {
                    JOptionPane.showMessageDialog(this,
                        "Email already registered. Use a different one.",
                        "Duplicate Email", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        // Hash του κωδικού
        String hashedPassword = Utils.hashPassword(password);

        // Αποθήκευση στο αρχείο
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(username + "," + email + "," + hashedPassword);
            writer.newLine();

            JOptionPane.showMessageDialog(this,
                "Registration successful!",
                "Success", JOptionPane.INFORMATION_MESSAGE);

            // Καθαρισμός πεδίων
            usernameField.setText("");
            emailField.setText("");
            passwordField.setText("");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Error saving user.",
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
