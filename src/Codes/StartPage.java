package Codes;

import javax.swing.*;
import java.awt.*;

public class StartPage extends JPanel {

    private JFrame frame;
    private static final Dimension BUTTON_SIZE = new Dimension(100, 50);
    private static final Color BUTTON_COLOR = new Color(245, 6, 254);
    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 500;

    public StartPage(JFrame frame) {
        this.frame = frame;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        topPanel.add(Box.createVerticalStrut(50));

        JLabel welcomeLabel = new JLabel("Sokoban Oyununa Hoşgeldiniz!", JLabel.CENTER);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        topPanel.add(welcomeLabel);

        // Alt boşluk
        topPanel.add(Box.createVerticalStrut(20)); // 20 piksel yükseklikte dikey boşluk
        add(topPanel, BorderLayout.NORTH); // Üst paneli BorderLayout'un kuzey bölümüne ekle

        // Butonlar için alt panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Butonların arasında 10 piksel boşluk

        topPanel.add(Box.createVerticalStrut(50));

        for (int i = 1; i <= 5; i++) {
            JButton button = createLevelButton("Level " + i, i);
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.CENTER); // Buton panelini BorderLayout'un orta bölümüne ekle

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
        // Burada, 'Board' sınıfı oyun durumunu kontrol edecek ve
        // gerektiğinde önceki durumdan devam edecek şekilde ayarlanmalıdır.

        frame.add(board);
        frame.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        frame.validate();
        frame.repaint();


    }
}
