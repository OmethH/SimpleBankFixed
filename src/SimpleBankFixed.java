import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleBankFixed extends JFrame {
    private double balance = 1000.0;
    private JLabel balanceLabel;
    private JTextField amountField;
    private JTextArea historyArea;

    public SimpleBankFixed() {
        setTitle("🏦 Simple Bank Account");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setupUI();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupUI() {
        // Panel for balance and controls
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        // Row 1: Balance
        balanceLabel = new JLabel("Balance: $" + formatAmount(balance));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balanceLabel.setForeground(Color.GREEN);
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Row 2: Amount input
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel amountLabel = new JLabel("Amount: $");
        amountField = new JTextField(10);
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);

        // Row 3: Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton depositButton = new JButton("💰 Deposit");
        depositButton.setBackground(Color.LIGHT_GRAY);
        depositButton.addActionListener(e -> handleDeposit());

        JButton withdrawButton = new JButton("💸 Withdraw");
        withdrawButton.setBackground(Color.LIGHT_GRAY);
        withdrawButton.addActionListener(e -> handleWithdraw());

        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);

        // Add all to controlPanel
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(balanceLabel);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(inputPanel);
        controlPanel.add(buttonPanel);

        // History area
        historyArea = new JTextArea(10, 40);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(historyArea);

        // Add to main frame
        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        logHistory("🟢 Account opened with balance: $" + formatAmount(balance));
    }

    private void handleDeposit() {
        double amount = parseAmount();
        if (amount > 0) {
            balance += amount;
            updateBalance();
            amountField.setText("");
            String message = "✅ Deposited $" + formatAmount(amount);
            showMessage(message);
            logHistory("💰 " + message);
        } else {
            showMessage("❌ Enter a positive amount.");
        }
    }

    private void handleWithdraw() {
        double amount = parseAmount();
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            updateBalance();
            amountField.setText("");
            String message = "✅ Withdrew $" + formatAmount(amount);
            showMessage(message);
            logHistory("💸 " + message);
        } else if (amount > balance) {
            showMessage("❌ Insufficient funds.");
            logHistory("❌ Withdraw failed — insufficient funds.");
        } else {
            showMessage("❌ Enter a positive amount.");
        }
    }

    private double parseAmount() {
        try {
            return Double.parseDouble(amountField.getText());
        } catch (Exception e) {
            showMessage("❌ Enter a valid number.");
            return -1;
        }
    }

    private String formatAmount(double amount) {
        return String.format("%.2f", amount);
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: $" + formatAmount(balance));
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void logHistory(String entry) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        historyArea.append("[" + timestamp + "] " + entry + " | New Balance: $" + formatAmount(balance) + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleBankFixed::new);
    }
}
