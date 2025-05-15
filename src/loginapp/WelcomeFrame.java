package loginapp;

import javax.swing.*;
import java.awt.*;

public class WelcomeFrame extends JFrame {
	private static final long serialVersionUID = 1L;
    public WelcomeFrame(String username) {
        setTitle("Welcome");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(welcomeLabel, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose(); // Κλείνει το Welcome
            new LoginForm(); // Ξανανοίγει το Login
        });
        add(logoutButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
