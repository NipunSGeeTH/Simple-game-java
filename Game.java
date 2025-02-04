import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Game extends JFrame {
    private JButton button, playAgainButton;
    private JLabel scoreLabel, timeLabel, highScoreLabel;
    private int score = 0;
    private int highScore = 0;
    private int timeLeft = 3;
    private Timer gameTimer, moveTimer, colorTimer;
    private Random random = new Random();
    private Color[] buttonColors = {
        new Color(52, 152, 219), // Blue
        new Color(46, 204, 113), // Green
        new Color(241, 196, 15), // Yellow
        new Color(231, 76, 60),  // Red
        new Color(155, 89, 182)  // Purple
    };

    public Game() {
        setupFrame();
        initializeLabels();
        createButton();
        createTimers();
        startGame();
    }

    private void setupFrame() {
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(240, 248, 255), 
                    getWidth(), getHeight(), new Color(173, 216, 230)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        });

        setTitle("Super Clicker Challenge");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        ImageIcon icon = new ImageIcon("logo.png"); // Use the image path
        setIconImage(icon.getImage());
    }

    private void initializeLabels() {
        Font gameFont = new Font("Arial", Font.BOLD, 16);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(gameFont);
        scoreLabel.setForeground(new Color(44, 62, 80));
        scoreLabel.setBounds(20, 20, 150, 30);
        add(scoreLabel);

        highScoreLabel = new JLabel("High Score: 0");
        highScoreLabel.setFont(gameFont);
        highScoreLabel.setForeground(new Color(44, 62, 80));
        highScoreLabel.setBounds(20, 50, 150, 30);
        add(highScoreLabel);

        timeLabel = new JLabel("Time: " + timeLeft);
        timeLabel.setFont(gameFont);
        timeLabel.setForeground(new Color(44, 62, 80));
        timeLabel.setBounds(350, 20, 150, 30);
        add(timeLabel);
    }

    private void createButton() {
        button = new JButton("Click Me!");
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(buttonColors[0]);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBounds(150, 150, 200, 70);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                score++;
                scoreLabel.setText("Score: " + score);
                playClickSound();
                moveButton();
                button.setBackground(buttonColors[random.nextInt(buttonColors.length)]);
            }
        });
        add(button);
    }

    private void createTimers() {
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timeLabel.setText("Time: " + timeLeft);
                if (timeLeft == 0) {
                    endGame();
                }
            }
        });

        moveTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveButton();
            }
        });

        colorTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.setBackground(buttonColors[random.nextInt(buttonColors.length)]);
            }
        });
    }

    private void startGame() {
        gameTimer.start();
        moveTimer.start();
        colorTimer.start();
        button.setEnabled(true);
        
        // Remove play again button if exists
        if (playAgainButton != null) {
            remove(playAgainButton);
        }
    }

    private void endGame() {
        gameTimer.stop();
        moveTimer.stop();
        colorTimer.stop();
        button.setEnabled(false);


        JFrame gameOverFrame = new JFrame("Game Over");
    gameOverFrame.setSize(400, 300);
    gameOverFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    gameOverFrame.setLayout(new GridLayout(3, 1));

    // Display final score and high score
    JLabel gameOverLabel = new JLabel("Game Over!", SwingConstants.CENTER);
    gameOverLabel.setFont(new Font("Arial", Font.BOLD, 20));

    JLabel scoreLabel = new JLabel("Your Score: " + score, SwingConstants.CENTER);
    JLabel highScoreLabel = new JLabel("High Score: " + highScore, SwingConstants.CENTER);
    gameOverFrame.add(gameOverLabel);
    gameOverFrame.add(scoreLabel);
    gameOverFrame.add(highScoreLabel);
    ImageIcon icon1 = new ImageIcon("logo.png"); // Use the image path
        gameOverFrame.setIconImage(icon1.getImage());
  
   
    gameOverFrame.setVisible(true);







        
        // Update high score
        if (score > highScore) {
            highScore = score;
            highScoreLabel.setText("High Score: " + highScore);
        }
        
        // Create Play Again button
        playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 14));
        playAgainButton.setBackground(new Color(46, 204, 113));
        playAgainButton.setForeground(Color.WHITE);
        playAgainButton.setBounds(150, 250, 200, 50);
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        add(playAgainButton);
        
        // Repaint to show new button
        revalidate();
        repaint();
    }

    private void resetGame() {
        // Reset game state
        score = 0;
        timeLeft = 30;
        
        // Update labels
        scoreLabel.setText("Score: 0");
        timeLabel.setText("Time: " + timeLeft);
        playAgainButton.setVisible(false);
        
        
        // Restart game
        startGame();
    }

    private void moveButton() {
        int x = random.nextInt(getWidth() - button.getWidth() - 50);
        int y = random.nextInt(getHeight() - button.getHeight() - 80);
        button.setLocation(x, y);
    }

    private void playClickSound() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource("click.wav")));
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error playing sound: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Game().setVisible(true);
        });
    }
}