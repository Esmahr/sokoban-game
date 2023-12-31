package Codes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Levels extends JPanel {

    private JFrame frame;
    private Image backgroundImage;
    private static final Dimension BUTTON_SIZE = new Dimension(100, 50);
    private static final Color BUTTON_COLOR = new Color(245, 6, 254);

    public Levels(JFrame frame) {
        this.frame = frame;
        initUI();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Arka plan resmini çiz
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false); // Paneli şeffaf yap

        topPanel.add(Box.createVerticalStrut(50));

        JLabel welcomeLabel = new JLabel("Sokoban Oyununa Hoşgeldiniz!", JLabel.CENTER);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/PixelifySans-VariableFont_wght.ttf")).deriveFont(Font.BOLD,38f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            welcomeLabel.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        topPanel.add(welcomeLabel);
        topPanel.add(Box.createVerticalStrut(20));

        add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false); // Paneli şeffaf yap

        for (int i = 1; i <= 5; i++) {
            JButton button = createLevelButton("Level " + i, i);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createLevelButton(String text, int level) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color shadow = new Color(199, 47, 248);
                g2.setColor(shadow);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 5, getHeight() - 5, 30, 30);

                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Border'ı iptal etmek için bu metod boş bırakıldı.
            }
        };

        styleButton(button);
        button.addActionListener(e -> loadLevel(level));

        return button;
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(BUTTON_SIZE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBackground(BUTTON_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder());
    }

    private void loadLevel(int level) {
        frame.getContentPane().removeAll();

        Board board = new Board(level);
        frame.add(board);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.validate();
        frame.repaint();

        board.startGame();
    }
}
