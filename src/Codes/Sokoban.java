package Codes;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Sokoban extends JFrame {

    public Sokoban() {
        initUI();
    }

    private void initUI() {

        StartPage startScreen = new StartPage(this);
        add(startScreen);

        setTitle("Sokoban");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Sokoban game = new Sokoban();
            game.setVisible(true);
        });
    }
}
