package loginapp;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoginForm extends JFrame {
	private static final long serialVersionUID = 1L;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton goToRegisterButton;
    private JCheckBox darkModeToggle;


    public LoginForm() {
        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10)); // 5 γραμμές, 2 στήλες, spacing

        // Username
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        // Password
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        // Login Button
        loginButton = new JButton("Login");
        add(loginButton);

        // Go to Register Button (αρχικά κρυφό)
        goToRegisterButton = new JButton("Go to Register");
        goToRegisterButton.setVisible(false);
        add(goToRegisterButton);

        // Login action
        loginButton.addActionListener(e -> loginUser());

        // Go to Register action
        goToRegisterButton.addActionListener(e -> {
            dispose(); // Κλείνει το login
            new RegisterForm();
 // Ανοίγει το register
        });
     // Dark Mode Toggle
        darkModeToggle = new JCheckBox("Dark Mode");
        darkModeToggle.addActionListener(e -> {
            boolean enabled = darkModeToggle.isSelected();
            Utils.toggleDarkMode(enabled);
        });
        add(new JLabel()); // Κενή θέση για σωστή ευθυγράμμιση
        add(darkModeToggle);


        setLocationRelativeTo(null); // Κέντρο οθόνης
        setVisible(true);
    }

    private void loginUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String hashedInputPassword = Utils.hashPassword(password);

        try (BufferedReader reader = Files.newBufferedReader(Paths.get("users.txt"), StandardCharsets.UTF_8)) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String storedUsername = parts[0];
                    String storedHashedPassword = parts[2];

                    if (username.equals(storedUsername) && hashedInputPassword.equals(storedHashedPassword)) {
                        found = true;
                        break;
                    }
                }
            }

            if (found) {
            	dispose(); // Κλείνει το login
            	new WelcomeFrame(username); // Ανοίγει το welcome

            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                goToRegisterButton.setVisible(true);
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading users file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
