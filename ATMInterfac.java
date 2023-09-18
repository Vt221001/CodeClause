import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATMInterfac {
    private JFrame frame;
    private JTextField cardNumberField;
    private JPasswordField pinField;
    private JButton loginButton;
    private JButton withdrawButton;
    private JButton depositButton;
    private JButton balanceButton;

    private double balance;

    public ATMInterfac() {
        frame = new JFrame("ATM Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(400, 200);

        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        cardNumberField = new JTextField();
        pinField = new JPasswordField();
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        loginPanel.add(new JLabel("Card Number:"));
        loginPanel.add(cardNumberField);
        loginPanel.add(new JLabel("PIN:"));
        loginPanel.add(pinField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        JPanel transactionPanel = new JPanel(new GridLayout(4, 1));
        withdrawButton = new JButton("Withdraw");
        depositButton = new JButton("Deposit");
        balanceButton = new JButton("Check Balance");

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });

        balanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });

        transactionPanel.add(withdrawButton);
        transactionPanel.add(depositButton);
        transactionPanel.add(balanceButton);

        frame.add(loginPanel, BorderLayout.NORTH);
        frame.add(transactionPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void login() {
        
        String cardNumber = cardNumberField.getText();
        String enteredPin = String.valueOf(pinField.getPassword());

        if (cardNumber.equals("123456") && enteredPin.equals("1234")) {
            JOptionPane.showMessageDialog(frame, "Login successful!");
            cardNumberField.setEditable(false);
            pinField.setEditable(false);
            loginButton.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(frame, "Login failed. Please check your card number and PIN.");
        }
    }

    private void withdraw() {
        if (cardNumberField.isEditable()) {
            JOptionPane.showMessageDialog(frame, "Please login first.");
            return;
        }

        String amountStr = JOptionPane.showInputDialog(frame, "Enter withdrawal amount:");
        if (amountStr != null) {
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount > balance) {
                    JOptionPane.showMessageDialog(frame, "Insufficient balance.");
                } else {
                    balance -= amount;
                    JOptionPane.showMessageDialog(frame, "Withdrawal successful. New balance: $" + balance);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid amount.");
            }
        }
    }

    private void deposit() {
        if (cardNumberField.isEditable()) {
            JOptionPane.showMessageDialog(frame, "Please login first.");
            return;
        }

        String amountStr = JOptionPane.showInputDialog(frame, "Enter deposit amount:");
        if (amountStr != null) {
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(frame, "Invalid amount.");
                } else {
                    balance += amount;
                    JOptionPane.showMessageDialog(frame, "Deposit successful. New balance: $" + balance);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid amount.");
            }
        }
    }

    private void checkBalance() {
        if (cardNumberField.isEditable()) {
            JOptionPane.showMessageDialog(frame, "Please login first.");
        } else {
            JOptionPane.showMessageDialog(frame, "Your balance: $" + balance);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ATMInterfac();
            }
        });
    }

    
    private void initializeBalance() {
        balance = 1000.0; // Starting balance
    }
}
