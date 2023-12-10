package TASK_2_Student_Grade_Calculator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradeCalculatorGUI extends JFrame {
    private JTextField[] subjectFields;
    private JLabel totalMarksLabel, averageLabel, gradeLabel;

    public GradeCalculatorGUI() {
        setTitle("Student Grade Calculator");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1, 10, 5)); // Adjusted vertical gap to reduce space
        setFont(new Font("Times New Roman", Font.PLAIN, 15));

        // Title label
        JLabel titleLabel = new JLabel("Student Grade Calculator");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel);

        subjectFields = new JTextField[5];

        for (int i = 0; i < subjectFields.length; i++) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel label = new JLabel("Subject " + (i + 1) + ":");
            label.setFont(new Font("Times New Roman", Font.BOLD, 15));
            subjectFields[i] = new JTextField();
            subjectFields[i].setFont(new Font("Times New Roman", Font.PLAIN, 15));
            subjectFields[i].setPreferredSize(new Dimension(180, 25)); // Set the width to medium
            panel.add(label);
            panel.add(subjectFields[i]);
            add(panel);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateResults();
            }
        });
        buttonPanel.add(calculateButton);

        add(buttonPanel);

        JPanel resultPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        totalMarksLabel = new JLabel("Total Marks: ");
        totalMarksLabel.setFont(new Font("Times New Roman", Font.BOLD, 17));
        totalMarksLabel.setVisible(false); // Initially set to invisible
        averageLabel = new JLabel("Average Percentage: ");
        averageLabel.setFont(new Font("Times New Roman", Font.BOLD, 17));
        averageLabel.setVisible(false); // Initially set to invisible
        gradeLabel = new JLabel("Grade: ");
        gradeLabel.setFont(new Font("Times New Roman", Font.BOLD, 17));
        gradeLabel.setVisible(false); // Initially set to invisible

        resultPanel.add(totalMarksLabel);
        resultPanel.add(new JLabel()); // Empty label for spacing
        resultPanel.add(averageLabel);
        resultPanel.add(new JLabel()); // Empty label for spacing
        resultPanel.add(gradeLabel);

        JPanel resultContainerPanel = new JPanel(new BorderLayout());
        resultContainerPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 0)); // Set margin
        resultContainerPanel.add(resultPanel, BorderLayout.WEST);
        add(resultContainerPanel);

        setVisible(true);
    }

    private void calculateResults() {
        int totalMarks = 0;
        int subjects = 0;

        for (JTextField field : subjectFields) {
            try {
                int marks = Integer.parseInt(field.getText());
                if (marks > 100) {
                    JOptionPane.showMessageDialog(this, "Subject marks should not exceed 100", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Stop calculation if a subject mark exceeds 100
                }
                totalMarks += marks;
                subjects++;
            } catch (NumberFormatException e) {
                // Ignore non-numeric inputs
            }
        }

        double averagePercentage = subjects > 0 ? (double) totalMarks / subjects : 0;

        totalMarksLabel.setText("Total Marks: " + totalMarks);
        averageLabel.setText("Average Percentage: " + String.format("%.2f", averagePercentage) + "%");
        gradeLabel.setText("Grade: " + calculateGrade(averagePercentage));

        // Set the visibility of labels to true after calculation
        totalMarksLabel.setVisible(true);
        averageLabel.setVisible(true);
        gradeLabel.setVisible(true);
    }

    private String calculateGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return "A";
        } else if (averagePercentage >= 80) {
            return "B";
        } else if (averagePercentage >= 70) {
            return "C";
        } else if (averagePercentage >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GradeCalculatorGUI();
            }
        });
    }
}
