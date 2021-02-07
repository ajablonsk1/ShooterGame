package main;
import javax.swing.JFrame;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Window extends JFrame{

    public Window() throws IOException, FontFormatException {
        setTitle("CS 2Defensive");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel gamePanel = new GamePanel(1280, 768);
        gamePanel.addFont();
        setContentPane(gamePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
