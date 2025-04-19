package Tasks;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class User {
    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

class Reservation {
    static int counter = 1000;
    int pnr;
    String name;
    String trainNumber;
    String trainName;
    String classType;
    String dateOfJourney;
    String from;
    String to;

    public Reservation(String name, String trainNumber, String trainName, String classType,
                       String dateOfJourney, String from, String to) {
        this.pnr = counter++;
        this.name = name;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.from = from;
        this.to = to;
    }

    public String toString() {
        return "PNR: " + pnr + ", Name: " + name + ", Train: " + trainNumber + " - " + trainName +
               ", Class: " + classType + ", Date: " + dateOfJourney + ", From: " + from + ", To: " + to;
    }
}

public class OnlineReservationSystem {
    static Map<String, User> users = new HashMap<>();
    static Map<Integer, Reservation> reservations = new HashMap<>();
    static JFrame frame;

    public static void main(String[] args) {
        users.put("Sunidhi", new User("Sunidhi", "Sunidhi123"));
        SwingUtilities.invokeLater(OnlineReservationSystem::createLoginForm);
    }

    private static void createLoginForm() {
        frame = new JFrame("Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(new JLabel());
        panel.add(loginBtn);

        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (users.containsKey(username) && users.get(username).password.equals(password)) {
                frame.dispose();
                createMainMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials.");
            }
        });

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createMainMenu() {
        frame = new JFrame("Online Reservation System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        JButton reserveBtn = new JButton("Make Reservation");
        JButton cancelBtn = new JButton("Cancel Reservation");
        JButton exitBtn = new JButton("Exit");

        reserveBtn.addActionListener(e -> createReservationForm());
        cancelBtn.addActionListener(e -> createCancellationForm());
        exitBtn.addActionListener(e -> System.exit(0));

        panel.add(reserveBtn);
        panel.add(cancelBtn);
        panel.add(exitBtn);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createReservationForm() {
        JFrame reserveFrame = new JFrame("Reservation Form");
        reserveFrame.setSize(400, 400);

        JPanel panel = new JPanel(new GridLayout(8, 2));
        JTextField nameField = new JTextField();
        JTextField trainNumberField = new JTextField();
        JTextField classField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField fromField = new JTextField();
        JTextField toField = new JTextField();

        JButton insertBtn = new JButton("Insert");

        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Train Number:")); panel.add(trainNumberField);
        panel.add(new JLabel("Class Type:")); panel.add(classField);
        panel.add(new JLabel("Date of Journey (YYYY-MM-DD):")); panel.add(dateField);
        panel.add(new JLabel("From:")); panel.add(fromField);
        panel.add(new JLabel("To:")); panel.add(toField);
        panel.add(new JLabel()); panel.add(insertBtn);

        insertBtn.addActionListener(e -> {
            String trainName = "Express Train";
            Reservation r = new Reservation(nameField.getText(), trainNumberField.getText(), trainName,
                    classField.getText(), dateField.getText(), fromField.getText(), toField.getText());
            reservations.put(r.pnr, r);
            JOptionPane.showMessageDialog(reserveFrame, "Reservation successful! PNR: " + r.pnr);
            reserveFrame.dispose();
        });

        reserveFrame.add(panel);
        reserveFrame.setLocationRelativeTo(null);
        reserveFrame.setVisible(true);
    }

    private static void createCancellationForm() {
        JFrame cancelFrame = new JFrame("Cancel Reservation");
        cancelFrame.setSize(300, 200);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        JTextField pnrField = new JTextField();
        JButton submitBtn = new JButton("Submit");

        panel.add(new JLabel("Enter PNR Number:"));
        panel.add(pnrField);
        panel.add(submitBtn);

        submitBtn.addActionListener(e -> {
            try {
                int pnr = Integer.parseInt(pnrField.getText());
                if (reservations.containsKey(pnr)) {
                    int confirm = JOptionPane.showConfirmDialog(cancelFrame,
                            "Reservation Found:\n" + reservations.get(pnr) + "\nConfirm Cancellation?",
                            "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        reservations.remove(pnr);
                        JOptionPane.showMessageDialog(cancelFrame, "Reservation cancelled.");
                        cancelFrame.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(cancelFrame, "No reservation found with PNR: " + pnr);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(cancelFrame, "Invalid PNR number.");
            }
        });

        cancelFrame.add(panel);
        cancelFrame.setLocationRelativeTo(null);
        cancelFrame.setVisible(true);
    }
}
