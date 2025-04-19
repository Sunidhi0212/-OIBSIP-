package Tasks;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ATM_GUI extends JFrame {
    private static final String VALID_USER_ID = "12345";
    private static final String VALID_PIN = "0000";
    private JTextField userIdField;
    private JPasswordField pinField; // Changed to JPasswordField
    private JButton loginButton;
    private JPanel loginPanel, atmPanel;
    private JLabel messageLabel, transactionHistoryLabel;
    private JTextArea transactionHistoryArea;
    private JButton withdrawButton, depositButton, transferButton, quitButton;
    private BankOperations bankOps;

    public ATM_GUI() {
        setTitle("ATM System");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Login Panel Setup
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel userIdLabel = new JLabel("User ID:");
        JLabel pinLabel = new JLabel("PIN:");
        userIdField = new JTextField();
        pinField = new JPasswordField(); // Changed to JPasswordField
        loginButton = new JButton("Login");

        loginPanel.add(userIdLabel);
        loginPanel.add(userIdField);
        loginPanel.add(pinLabel);
        loginPanel.add(pinField);
        loginPanel.add(new JLabel()); // Empty cell for spacing
        loginPanel.add(loginButton);

        add(loginPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> authenticate());

        setVisible(true);
    }

    private void authenticate() {
        String userId = userIdField.getText();
        String pin = String.valueOf(pinField.getPassword()); // Use String.valueOf() to convert to String

        if (VALID_USER_ID.equals(userId) && VALID_PIN.equals(pin)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            loginPanel.setVisible(false);
            bankOps = new BankOperations();  // Create BankOperations instance
            showATMPanel();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid ID or PIN. Please try again.");
        }
    }

    private void showATMPanel() {
        atmPanel = new JPanel();
        atmPanel.setLayout(new BorderLayout());

        // Top Message
        messageLabel = new JLabel("Welcome to the ATM!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Transaction History Area
        transactionHistoryLabel = new JLabel("Transaction History:");
        transactionHistoryArea = new JTextArea(10, 40);
        transactionHistoryArea.setEditable(false);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));

        withdrawButton = new JButton("Withdraw");
        depositButton = new JButton("Deposit");
        transferButton = new JButton("Transfer");
        quitButton = new JButton("Quit");

        buttonPanel.add(withdrawButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(transferButton);
        buttonPanel.add(quitButton);

        atmPanel.add(messageLabel, BorderLayout.NORTH);
        atmPanel.add(transactionHistoryLabel, BorderLayout.CENTER);
        atmPanel.add(new JScrollPane(transactionHistoryArea), BorderLayout.SOUTH);
        atmPanel.add(buttonPanel, BorderLayout.EAST);

        add(atmPanel);

        // Action Listeners for ATM Operations
        withdrawButton.addActionListener(e -> showWithdrawDialog());
        depositButton.addActionListener(e -> showDepositDialog());
        transferButton.addActionListener(e -> showTransferDialog());
        quitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void showWithdrawDialog() {
        String amount = JOptionPane.showInputDialog(this, "Enter amount to withdraw:");
        if (amount != null && !amount.isEmpty()) {
            bankOps.withdraw(Double.parseDouble(amount));
            transactionHistoryArea.setText(bankOps.getTransactionHistory());
        }
    }

    private void showDepositDialog() {
        String amount = JOptionPane.showInputDialog(this, "Enter amount to deposit:");
        if (amount != null && !amount.isEmpty()) {
            bankOps.deposit(Double.parseDouble(amount));
            transactionHistoryArea.setText(bankOps.getTransactionHistory());
        }
    }

    private void showTransferDialog() {
        String toAccount = JOptionPane.showInputDialog(this, "Enter recipient account ID:");
        String amount = JOptionPane.showInputDialog(this, "Enter amount to transfer:");
        if (toAccount != null && amount != null && !amount.isEmpty()) {
            bankOps.transfer(toAccount, Double.parseDouble(amount));
            transactionHistoryArea.setText(bankOps.getTransactionHistory());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ATM_GUI::new);
    }
}
