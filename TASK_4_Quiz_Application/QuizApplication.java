package TASK_4_Quiz_Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizApplication extends JFrame {

    private JLabel titleLabel, questionLabel, timerLabel;
    private JRadioButton[] options;
    private ButtonGroup buttonGroup;
    private JButton submitButton;
    private Timer timer;
    private int score;
    private int totalTime;
    private int currentIndex;
    private boolean answered;
    

    private String[] questions = {
            "What is the capital of India?",
            "Which river is the longest in India?",
            "Who is known as the 'Father of the Nation' in India?",
            "Which Indian state is known as the 'Land of Five Rivers'?",
            "In which year did India gain independence?"
    };

    private String[][] optionsData = {
            {"New Delhi", "Mumbai", "Kolkata", "Chennai"},
            {"Ganges", "Yamuna", "Brahmaputra", "Godavari"},
            {"Mahatma Gandhi", "Jawaharlal Nehru", "Sardar Patel", "Subhas Chandra Bose"},
            {"Punjab", "Haryana", "Rajasthan", "Uttar Pradesh"},
            {"1947", "1950", "1942", "1930"}
    };

    private int[] correctAnswersData = {0, 0, 0, 0, 0}; // Index of correct options for each question
    private int[] correctAnswers = new int[questions.length];

    public QuizApplication() {
        setTitle("Quiz Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);

        titleLabel = new JLabel("Quiz Application");
        titleLabel.setFont(new Font("Book Antiqua", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
        add(questionLabel, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;

        options = new JRadioButton[4];
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            gbc.gridy++;
            options[i] = new JRadioButton();
            options[i].setFont(new Font("Book Antiqua", Font.PLAIN, 16));
            options[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    answered = true;
                }
            });
            buttonGroup.add(options[i]);
            add(options[i], gbc);
        }

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        timerLabel = new JLabel();
        timerLabel.setFont(new Font("Book Antiqua", Font.BOLD, 17));
        add(timerLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        submitButton = new JButton("Submit");
        submitButton.setBackground(Color.GREEN);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });
        add(submitButton, gbc);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalTime--;
                updateTimerLabel();
                if (totalTime == 0) {
                    showTimeOutPopup();
                    currentIndex++;
                    showNextQuestion();
                }
            }
        });

        startQuiz();
    }

    private void startQuiz() {
        currentIndex = 0;
        showNextQuestion();
    }

    private void showNextQuestion() {
        if (currentIndex < questions.length) {
            questionLabel.setText(questions[currentIndex]);
            for (int i = 0; i < 4; i++) {
                options[i].setText(optionsData[currentIndex][i]);
                options[i].setSelected(false);
            }

            correctAnswersData[currentIndex] = -1;
            answered = false;
            totalTime = 10;
            updateTimerLabel();
            timer.start();
        } else {
            showResult();
        }
    }

    private void updateTimerLabel() {
        timerLabel.setText("Time Left: " + totalTime + " seconds");
        if (totalTime < 4) {
            timerLabel.setForeground(Color.RED);
        } else {
            timerLabel.setForeground(Color.BLACK);
        }
    }
    
    private void checkAnswer() {
        if (answered) {
            timer.stop();
            for (int i = 0; i < 4; i++) {
                if (options[i].isSelected()) {
                    correctAnswersData[currentIndex] = i;
                    break;
                }
            }
    
            if (correctAnswersData[currentIndex] == correctAnswers[currentIndex]) {
                score++;
            } else {
                showIncorrectAnswerPopup();
                return;  // Return here to avoid further processing in case of an incorrect answer
            }
    
            currentIndex++;
            showNextQuestion();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an answer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    
    private void showResult() {
        JOptionPane.showMessageDialog(this, "Quiz Completed!\nYour score: " + score+ " out of " + questions.length,
                "Result", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

     private void showIncorrectAnswerPopup() {
        JOptionPane.showMessageDialog(this, "Incorrect Answer! The correct answer is: " +
                optionsData[currentIndex][correctAnswers[currentIndex]], "Incorrect", JOptionPane.WARNING_MESSAGE);
        currentIndex++;
        showNextQuestion();
    }

    private void showTimeOutPopup() {
        if (correctAnswersData[currentIndex] != -1) {
            JOptionPane.showMessageDialog(this, "Time Out! The correct answer is: " +
                    optionsData[currentIndex][correctAnswersData[currentIndex]], "Time Out", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Time Out! Correct answer not selected.", "Time Out", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuizApplication().setVisible(true);
            }
        });
    }
}
