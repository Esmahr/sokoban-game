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
    private static final Dimension BUTTON_SIZE = new Dimension(150, 80);
    private Music music;
    private Music buttonHoverSound;

    public Levels(JFrame frame) {
        this.frame = frame;
        music = new Music();
        buttonHoverSound = new Music();

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

            if (Board.isLevel1Button == true) {
                JButton button = createLevelButton("src/resources/levels/1.png", 1);
                buttonPanel.add(button);
            } else {
                JButton button = createLevelButton("Level ",1);
                buttonPanel.add(button);
            }
            if (Board.isLevel2Button == true) {
                JButton button = createLevelButton("src/resources/levels/2.png", 2);
                buttonPanel.add(button);
            } else {
                JButton button = createLevelButton("src/resources/levels/3.png", 2);
                buttonPanel.add(button);
            }
            if (Board.isLevel3Button == true) {
                JButton button = createLevelButton("src/resources/levels/4.png", 3);
                buttonPanel.add(button);
            } else {
                JButton button = createLevelButton("src/resources/levels/5.png",3);
                buttonPanel.add(button);
            }
            if (Board.isLevel4Button == true) {
                JButton button = createLevelButton("src/resources/levels/6.png" , 4);
                buttonPanel.add(button);
            } else {
                JButton button = createLevelButton("src/resources/levels/7.png",4);
                buttonPanel.add(button);
            }
            if (Board.isLevel5Button == true) {
                JButton button = createLevelButton("src/resources/levels/8.png" ,5);
                buttonPanel.add(button);
            } else {
                JButton button = createLevelButton("src/resources/levels/9.png" ,5);
                buttonPanel.add(button);
            }


        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createLevelButton(String imageUrl, int level) {

// Öncelikle ImageIcon oluşturun.
        ImageIcon backButtonIcon = new ImageIcon(imageUrl);

// ImageIcon'dan Image'ı elde edin.
        Image image = backButtonIcon.getImage();

// Image'ı yeniden boyutlandırın.
        Image newimg = image.getScaledInstance(150, 175, java.awt.Image.SCALE_SMOOTH);

// Yeni ImageIcon'ı oluşturun.
        ImageIcon newIcon = new ImageIcon(newimg);

// JButton oluşturun ve ona yeni icon'u ekleyin.
        JButton button = new JButton(newIcon);

        styleButton(button);
        button.addActionListener(e -> loadLevel(level));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setFont(new Font("Arial", Font.BOLD, 18)); // Font stilini kalın yap
                music.setVolume(-40.0f); // Arka plan müziğinin sesini düşür
                music.playSoundEffect("resources/click-button.wav");
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
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
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
        switch (level) {
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

        }
    }
}
