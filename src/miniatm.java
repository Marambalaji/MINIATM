import javax.swing.*;
import java.awt.*;

public class miniatm extends JFrame {
    private static final int ADMIN_PASSWORD = 1234;
    private static final int USER_PASSWORD = 123;
    private static final int MAX_ATTEMPTS = 3;
    private static final int INITIAL_BALANCE = 50000;

    private int currentBalance = INITIAL_BALANCE;
    private int remainingAttempts = MAX_ATTEMPTS;
    private boolean isAdmin = false;
    private boolean isAuthenticated = false;

    private JPanel panel;
    private CardLayout cardLayout;

    public miniatm() {
        setTitle("Mini ATM");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        panel = new JPanel(cardLayout);

        add(panel);
        initializeLoginScreen();
        initializeMenuScreen();
        initializeCheckBalanceScreen();
        initializeWithdrawScreen();
        initializeDepositScreen();

        setVisible(true);
    }

    private void initializeLoginScreen() {
        JPanel loginPanel = new JPanel(new GridLayout(4, 1));
        JLabel userTypeLabel = new JLabel("Enter the choice: 1 for User, 2 for Admin");
        JTextField userTypeField = new JTextField();
        JLabel passwordLabel = new JLabel("Enter password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginPanel.add(userTypeLabel);
        loginPanel.add(userTypeField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        panel.add(loginPanel, "Login");

        loginButton.addActionListener(e -> {
            int choice = Integer.parseInt(userTypeField.getText());
            int inputPassword = Integer.parseInt(new String(passwordField.getPassword()));

            if (choice == 1 && inputPassword == USER_PASSWORD) {
                isAuthenticated = true;
                isAdmin = false;
                cardLayout.show(panel, "Menu");
            } else if (choice == 2 && inputPassword == ADMIN_PASSWORD) {
                isAuthenticated = true;
                isAdmin = true;
                cardLayout.show(panel, "Menu");
            } else {
                remainingAttempts--;
                JOptionPane.showMessageDialog(this, "Invalid password. " + remainingAttempts + " remaining attempts.");
                if (remainingAttempts == 0) {
                    JOptionPane.showMessageDialog(this, "Your account is blocked.");
                    System.exit(0);
                }
            }
        });
    }

    private void initializeMenuScreen() {
        JPanel menuPanel = new JPanel(new GridLayout(5, 1));
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton exitButton = new JButton("Exit");

        menuPanel.add(checkBalanceButton);
        menuPanel.add(withdrawButton);
        menuPanel.add(depositButton);
        menuPanel.add(exitButton);

        panel.add(menuPanel, "Menu");

        checkBalanceButton.addActionListener(e -> cardLayout.show(panel, "CheckBalance"));
        withdrawButton.addActionListener(e -> cardLayout.show(panel, "Withdraw"));
        depositButton.addActionListener(e -> cardLayout.show(panel, "Deposit"));
        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Thank you for using the ATM. Visit again!");
            System.exit(0);
        });
    }

    private void initializeCheckBalanceScreen() {
        JPanel checkBalancePanel = new JPanel(new BorderLayout());
        JLabel balanceLabel = new JLabel("Your balance is: " + currentBalance);
        JButton backButton = new JButton("Back");

        checkBalancePanel.add(balanceLabel, BorderLayout.CENTER);
        checkBalancePanel.add(backButton, BorderLayout.SOUTH);

        panel.add(checkBalancePanel, "CheckBalance");

        backButton.addActionListener(e -> cardLayout.show(panel, "Menu"));
    }

    private void initializeWithdrawScreen() {
        JPanel withdrawPanel = new JPanel(new GridLayout(3, 1));
        JLabel amountLabel = new JLabel("Enter the amount to withdraw:");
        JTextField amountField = new JTextField();
        JButton withdrawButton = new JButton("Withdraw");
        JButton backButton = new JButton("Back");

        withdrawPanel.add(amountLabel);
        withdrawPanel.add(amountField);
        withdrawPanel.add(withdrawButton);
        withdrawPanel.add(backButton);

        panel.add(withdrawPanel, "Withdraw");

        withdrawButton.addActionListener(e -> {
            int amount = Integer.parseInt(amountField.getText());
            if (amount <= currentBalance && amount < 10000) {
                currentBalance -= amount;
                JOptionPane.showMessageDialog(this, "Withdrawal successful. New balance: " + currentBalance);
            } else {
                JOptionPane.showMessageDialog(this, "Transaction cancelled. Insufficient balance or amount exceeds limit.");
            }
        });

        backButton.addActionListener(e -> cardLayout.show(panel, "Menu"));
    }

    private void initializeDepositScreen() {
        JPanel depositPanel = new JPanel(new GridLayout(4, 1));
        JLabel denominationLabel = new JLabel("Enter the denomination: 1 for 500, 2 for 200, 3 for 100");
        JTextField denominationField = new JTextField();
        JLabel notesLabel = new JLabel("Enter the number of notes:");
        JTextField notesField = new JTextField();
        JButton depositButton = new JButton("Deposit");
        JButton backButton = new JButton("Back");

        depositPanel.add(denominationLabel);
        depositPanel.add(denominationField);
        depositPanel.add(notesLabel);
        depositPanel.add(notesField);
        depositPanel.add(depositButton);
        depositPanel.add(backButton);

        panel.add(depositPanel, "Deposit");

        depositButton.addActionListener(e -> {
            int denomination = Integer.parseInt(denominationField.getText());
            int noteValue;
            switch (denomination) {
                case 1:
                    noteValue = 500;
                    break;
                case 2:
                    noteValue = 200;
                    break;
                case 3:
                    noteValue = 100;
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Invalid denomination.");
                    return;
            }

            int notes = Integer.parseInt(notesField.getText());
            double depositAmount = noteValue * notes;
            currentBalance += depositAmount;
            JOptionPane.showMessageDialog(this, "The amount " + depositAmount + " is successfully deposited. New balance: " + currentBalance);
        });

        backButton.addActionListener(e -> cardLayout.show(panel, "Menu"));
    }

    public static void main(String[] args) {


        SwingUtilities.invokeLater(miniatm::new);
    }
}
