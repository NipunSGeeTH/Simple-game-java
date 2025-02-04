import javax.swing.*;
import java.awt.*;

public class ChangeIconExample {
    public static void main(String[] args) {
        // Create JFrame
        JFrame frame = new JFrame("Custom Icon Example");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load the custom icon
        ImageIcon icon = new ImageIcon("logo.png"); // Use the image path
        frame.setIconImage(icon.getImage()); // Set the window icon

        // Add a button (for example)
        JButton button = new JButton("Click Me!");
        frame.add(button);

        frame.setVisible(true); // Show window
    }
}
