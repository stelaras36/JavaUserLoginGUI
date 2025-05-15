package loginapp;

import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> users;

    public UserManager() {
        users = new ArrayList<>();
    }

    // Εγγραφή χρήστη (επιστρέφει true αν πετύχει)
    public boolean register(User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                return false; // Ήδη υπάρχει το username
            }
        }
        users.add(user);
        return true;
    }

    // Login χρήστη
    public boolean login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return true; // Επιτυχές login
            }
        }
        return false; // Λάθος username ή password
    }
}
