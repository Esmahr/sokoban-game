package Codes;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class Levels extends JPanel {

    private JFrame frame;
    private Image backgroundImage;
    private static final Dimension BUTTON_SIZE = new Dimension(100, 50);
    private static final Color BUTTON_COLOR = new Color(245, 6, 254);
    private static final Color SHADOW_COLOR = new Color(199, 47, 248);
    private static final int BUTTON_ROUNDNESS = 30;

    public Levels(JFrame frame) {
        this.frame = frame;
        try {
            backgroundImage = new ImageIcon("src/resources/background.png").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        topPanel.add(Box.createVerticalStrut(200));

        JLabel welcomeLabel = new JLabel("Oyuna başla", JLabel.CENTER);

        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        setLabelFont(welcomeLabel);

        topPanel.add(welcomeLabel);
        topPanel.add(Box.createVerticalStrut(20));

        add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 100));
        buttonPanel.setOpaque(false);

        for (int i = 1; i <= 5; i++) {
            JButton button = createLevelButton("Level " + i, i);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createLevelButton(String text, int level) {
        JButton button = new JButton(text);
        styleButton(button);
        button.addActionListener(e -> loadLevel(level));

        // MouseListener ile görsel efektleri uygula
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.YELLOW); // Font rengini değiştir
                button.setFont(new Font("Arial", Font.BOLD, 16)); // Font stilini kalın yap
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.BLACK); // Orijinal font rengine dön
                button.setFont(new Font("Arial", Font.PLAIN, 16)); // Orijinal font stilini uygula
            }
        });

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
        button.setUI(new StyledButtonUI());
    }

    private void setLabelFont(JLabel label) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/PixelifySans-VariableFont_wght.ttf")).deriveFont(Font.BOLD, 78f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            label.setFont(customFont);
            label.setForeground(Color.WHITE);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            label.setFont(new Font("Arial", Font.BOLD, 58)); // Use default font if custom font fails
        }
    }

    private void loadLevel(int level) {
        frame.getContentPane().removeAll();
        Board board = new Board(level);
        boolean isAllowedToPlay = false;
        switch(level) {
            case 1:
                isAllowedToPlay = true; // 1. seviye her zaman oynanabilir
                break;
            case 2:
                isAllowedToPlay = board.isLevel2Button; // 2. seviye için kontrol
                break;
            case 3:
                isAllowedToPlay = board.isLevel3Button; // 3. seviye için kontrol
                break;
            case 4:
                isAllowedToPlay = board.isLevel4Button; // 4. seviye için kontrol
                break;
            case 5:
                isAllowedToPlay = board.isLevel5Button; // 5. seviye için kontrol
                break;
        }

        if (isAllowedToPlay) {
            frame.add(board);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.validate();
            frame.repaint();
            board.startGame();
        } else {
            JOptionPane.showMessageDialog(frame, "Kilitli!!");
            initUI();
            frame.validate();
            frame.repaint();
        }

    }

    // Inner class for button UI styling
    private static class StyledButtonUI extends BasicButtonUI {
        @Override
        public void paint(Graphics g, JComponent c) {
            super.paint(g, c);
            if (c instanceof JButton) {
                JButton button = (JButton) c;
                paintButtonAppearance(button, g, SHADOW_COLOR, BUTTON_COLOR);
            }
        }

        private void paintButtonAppearance(JButton button, Graphics g, Color shadowColor, Color backgroundColor) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Draw the shadow
            g2.setColor(shadowColor);
            g2.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), BUTTON_ROUNDNESS, BUTTON_ROUNDNESS);
            // Draw the button background
            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, button.getWidth() - 5, button.getHeight() - 5, BUTTON_ROUNDNESS, BUTTON_ROUNDNESS);
            super.paint(g2, button);
            g2.dispose();
        }
    }
}
