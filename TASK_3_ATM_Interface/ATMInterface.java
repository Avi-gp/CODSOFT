package TASK_3_ATM_Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;
    private String pin;

    public BankAccount(double initialBalance, String pin) {
        this.balance = initialBalance;
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance - amount >= 1000) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyPin(String enteredPin) {
        return pin.equals(enteredPin);
    }
}

class ATM {
    private BankAccount bankAccount;
    private String enteredPin;

    public ATM(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void performWithdrawal() {
        if (isPinValid()) {
            String amountStr = JOptionPane.showInputDialog(null, "Enter withdrawal amount (Min: $100, Max: $30,000):", "Withdrawal", JOptionPane.PLAIN_MESSAGE);
            if (amountStr != null && !amountStr.isEmpty()) {
                double amount = Double.parseDouble(amountStr);
                if (amount >= 100 && amount <= 30000) {
                    if (bankAccount.withdraw(amount)) {
                        showMessage("Withdrawal successful. Current balance: $" + bankAccount.getBalance());
                    } else {
                        showMessage("Insufficient funds. Minimum balance should be maintained.");
                    }
                } else {
                    showMessage("Invalid withdrawal amount. Please enter an amount between $100 and $30,000.");
                }
            }
        }
    }

    public void performDeposit() {
        if (isPinValid()) {
            String amountStr = JOptionPane.showInputDialog(null, "Enter deposit amount (Min: $100):", "Deposit", JOptionPane.PLAIN_MESSAGE);
            if (amountStr != null && !amountStr.isEmpty()) {
                double amount = Double.parseDouble(amountStr);
                if (amount >= 100) {
                    if (amount > 50000) {
                        String panCardInput = JOptionPane.showInputDialog(null, "Deposit amount exceeds $50,000. Please enter PAN Card Number for proof:", "PAN Card Number", JOptionPane.PLAIN_MESSAGE);
                        if (panCardInput != null && !panCardInput.isEmpty()) {
                            showMessage("PAN Card Number: " + panCardInput);
                            bankAccount.deposit(amount);
                            showMessage("Deposit successful. Current balance: $" + bankAccount.getBalance());
                        } else {
                            showMessage("Deposit aborted. PAN Card Number is required for deposits exceeding $50,000.");
                        }
                    } else {
                        bankAccount.deposit(amount);
                        showMessage("Deposit successful. Current balance: $" + bankAccount.getBalance());
                    }
                } else {
                    showMessage("Invalid deposit amount. Please enter an amount of $100 or more.");
                }
            }
        }
    }

    public void checkBalance() {
        if (isPinValid()) {
            showMessage("Current balance: $" + bankAccount.getBalance());
        }
    }

    public void setEnteredPin(String enteredPin) {
        this.enteredPin = enteredPin;
    }

    private boolean isPinValid() {
        if (enteredPin != null && bankAccount.verifyPin(enteredPin)) {
            return true;
        } else {
            showMessage("Incorrect PIN. Please enter a valid PIN.");
            enteredPin = null; // Reset enteredPin
            return false;
        }
    }

    private void showMessage(String message) {
        JTextArea textArea = new JTextArea(message);
        textArea.setFont(new Font("Georgia", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        JOptionPane.showMessageDialog(null, scrollPane, "ATM Message", JOptionPane.INFORMATION_MESSAGE);
    }
}

public class ATMInterface extends JFrame {
    private ATM atm;
    private JTextField pinField;

    public ATMInterface() {
        BankAccount bankAccount = new BankAccount(5000, "1314");
        atm = new ATM(bankAccount);

        setTitle("ATM Interface");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 300));

        JLabel titleLabel = new JLabel("ATM Interface");
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton checkBalanceButton = new JButton("Check Balance");

        Font buttonFont = new Font("Book Antiqua", Font.BOLD, 17);
        withdrawButton.setFont(buttonFont);
        depositButton.setFont(buttonFont);
        checkBalanceButton.setFont(buttonFont);

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPinDialog();
                atm.performWithdrawal();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPinDialog();
                atm.performDeposit();
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPinDialog();
                atm.checkBalance();
            }
        });

        buttonPanel.add(withdrawButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(checkBalanceButton);

        add(buttonPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void showPinDialog() {
        JPanel pinPanel = new JPanel(new FlowLayout());
        pinPanel.add(new JLabel("Enter PIN: "));
        pinField = new JPasswordField(4);
        pinPanel.add(pinField);

        int result = JOptionPane.showConfirmDialog(null, pinPanel, "Enter PIN", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String enteredPin = pinField.getText();
            atm.setEnteredPin(enteredPin);
        } else {
            atm.setEnteredPin(null);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ATMInterface();
            }
        });
    }
}
