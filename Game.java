import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;


public class Game extends JFrame {
    private JButton button;
    private JLabel scoreLabel, timeLabel;
    private int score = 0;
    private int timeLeft = 30; // 30 seconds countdown
    private Timer gameTimer, moveTimer;
    private Random random = new Random();

    public Game() {
        setTitle("Click the Button Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Score label
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(20, 20, 100, 20);
        add(scoreLabel);

        // Time label
        timeLabel = new JLabel("Time: " + timeLeft);
        timeLabel.setBounds(300, 20, 100, 20);
        add(timeLabel);

        // Button to click
        button = new JButton("Click Me!");
        button.setBounds(150, 150, 100, 50);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                score++;
                scoreLabel.setText("Score: " + score);
                playClickSound(); // Play sound when clicked
                moveButton(); // Move button to new position
            }
        });
        add(button);

        // Timer for game countdown
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timeLabel.setText("Time: " + timeLeft);
                if (timeLeft == 0) {
                    gameTimer.stop();
                    moveTimer.stop();
                    button.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "Game Over! Your Score: " + score);
                }
            }
        });

        // Timer to move the button every second
        moveTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveButton();
            }
        });

        gameTimer.start();
        moveTimer.start();
    }

    private void moveButton() {
        int x = random.nextInt(getWidth() - button.getWidth() - 50);
        int y = random.nextInt(getHeight() - button.getHeight() - 80);
        button.setLocation(x, y);
    }
 
    

    private void playClickSound() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource("abc.wav")));
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error playing sound: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Game().setVisible(true));
    }
}
