import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GuessTheNumberGameGUI extends JFrame {
    private int numberToGuess;
    private int attemptsLeft;
    private int totalRounds = 3;
    private int currentRound = 1;
    private int score = 0;
    private final int maxAttempts = 5;

    private JLabel roundLabel, promptLabel, feedbackLabel, scoreLabel;
    private JTextField guessField;
    private JButton guessButton;

    public GuessTheNumberGameGUI() {
        setTitle("Guess the Number Game");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1));

        roundLabel = new JLabel("Round: 1 / " + totalRounds, SwingConstants.CENTER);
        promptLabel = new JLabel("Guess a number between 1 and 100", SwingConstants.CENTER);
        feedbackLabel = new JLabel("Attempts left: " + maxAttempts, SwingConstants.CENTER);
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);

        guessField = new JTextField();
        guessButton = new JButton("Guess");

        add(roundLabel);
        add(promptLabel);
        add(guessField);
        add(guessButton);
        add(feedbackLabel);
        add(scoreLabel);

        guessButton.addActionListener(e -> handleGuess());

        startNewRound();
        setVisible(true);
    }

    private void startNewRound() {
        if (currentRound > totalRounds) {
            JOptionPane.showMessageDialog(this, "Game Over! Your final score is: " + score);
            System.exit(0);
        }
        numberToGuess = new Random().nextInt(100) + 1;
        attemptsLeft = maxAttempts;
        roundLabel.setText("Round: " + currentRound + " / " + totalRounds);
        feedbackLabel.setText("Attempts left: " + attemptsLeft);
        guessField.setText("");
        feedbackLabel.setForeground(Color.BLACK);
    }

    private void handleGuess() {
        String input = guessField.getText();
        try {
            int guess = Integer.parseInt(input);
            attemptsLeft--;

            if (guess == numberToGuess) {
                int points = (maxAttempts - attemptsLeft) * 10;
                score += (maxAttempts - attemptsLeft + 1) * 10;
                JOptionPane.showMessageDialog(this, "Correct! You earned " + points + " points.");
                currentRound++;
                scoreLabel.setText("Score: " + score);
                startNewRound();
                return;
            } else if (guess < numberToGuess) {
                feedbackLabel.setText("Too low. Attempts left: " + attemptsLeft);
                feedbackLabel.setForeground(Color.BLUE);
            } else {
                feedbackLabel.setText("Too high. Attempts left: " + attemptsLeft);
                feedbackLabel.setForeground(Color.RED);
            }

            if (attemptsLeft <= 0) {
                JOptionPane.showMessageDialog(this, "Out of attempts! The correct number was: " + numberToGuess);
                currentRound++;
                startNewRound();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuessTheNumberGameGUI::new);
    }
}
