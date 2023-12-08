import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGameGUI extends JFrame {
    private int lowerBound = 1;
    private int upperBound = 100;
    private int generatedNumber;
    private int attemptsLimit = 10;
    private int attemptsLeft;
    private int score = 0;

    private JLabel instructionsLabel;
    private JTextField guessTextField;
    private JButton guessButton;
    private JButton playAgainButton;
    private JLabel scoreLabel;

    public NumberGameGUI() {
        setTitle("Number Guessing Game");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        generateNumber();
        updateInstructions();

        setLayout(new GridLayout(5, 1));
        add(createCenteredLabel(instructionsLabel));
        add(createBoldTextField(guessTextField));
        add(createCenteredButtonPanel(guessButton));
        add(createCenteredLabel(scoreLabel));
        add(playAgainButton);

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateNumber();
                updateInstructions();
            }
        });
    }

    private void initializeComponents() {
        instructionsLabel = new JLabel("Guess the number between " + lowerBound + " and " + upperBound);
        guessTextField = new JTextField();
        guessButton = new JButton("Guess");
        playAgainButton = new JButton("Play Again");
        scoreLabel = new JLabel("Score: " + score);
        playAgainButton.setEnabled(false);

        Font boldFont = new Font("Times New Roman", Font.BOLD, 14);

        instructionsLabel.setFont(boldFont);
        guessTextField.setFont(boldFont);
        guessButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        playAgainButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        scoreLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
    }

    private JTextField createBoldTextField(JTextField textField) {
        textField.setFont(textField.getFont().deriveFont(Font.BOLD));
        return textField;
    }

    private JPanel createCenteredButtonPanel(JButton button) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(button);
        return panel;
    }

    private JLabel createCenteredLabel(JLabel label) {
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private void generateNumber() {
        Random random = new Random();
        generatedNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
        attemptsLeft = attemptsLimit;
        System.out.println(generatedNumber);
    }

    private void updateInstructions() {
        instructionsLabel.setText("Guess the number between " + lowerBound + " and " + upperBound);
        guessTextField.setText("");
        guessButton.setEnabled(true);
        playAgainButton.setEnabled(false);
        scoreLabel.setText("Score: " + score);
        attemptsLeft = attemptsLimit;
    }

    private void checkGuess() {
        try {
            int userGuess = Integer.parseInt(guessTextField.getText());

            if (userGuess == generatedNumber) {
                JOptionPane.showMessageDialog(this, "Congratulations! You guessed the number!");
                guessButton.setEnabled(false);
                playAgainButton.setEnabled(true);
                score += attemptsLimit - attemptsLeft + 1;
            } else {
                String message;
                if (userGuess < generatedNumber) {
                    message = "Too low! Attempts left: " + (--attemptsLeft);
                } else {
                    message = "Too high! Attempts left: " + (--attemptsLeft);
                }

                if (attemptsLeft == 0) {
                    message += "\nOut of attempts.The correct number was: " + generatedNumber;
                    guessButton.setEnabled(false);
                    playAgainButton.setEnabled(true);
                }

                JOptionPane.showMessageDialog(this, message, "Guess Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NumberGameGUI().setVisible(true);
            }
        });
    }
}
