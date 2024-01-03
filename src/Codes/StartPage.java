package Codes;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class StartPage extends JPanel {

    private Image backgroundImage;
    private JButton myButton;
    private final int buttonOriginalWidth = 155;
    private final int buttonOriginalHeight = 40;
    private final int buttonGrowWidth = 165;
    private final int buttonGrowHeight = 45;
    private final Font originalFont = new Font("SansSerif", Font.BOLD, 15); // Orijinal font boyutu
    private final Font enlargedFont = new Font("SansSerif", Font.BOLD, 17); // Büyütülmüş font boyutu
    private JFrame frame;
    private Music music;
    private Music buttonHoverSound;

    public StartPage(JFrame frame) {
        music = new Music();
        music.playBackgroundMusic("resources/startpage.wav");

        buttonHoverSound = new Music();

        this.frame = frame;
        try {
            backgroundImage = new ImageIcon("src/resources/welcome1.png").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initializeButton();
        setLayout(null);
    }

    private void initializeButton() {
        myButton = new JButton("Oyuna Başla") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color color2 = new Color(2, 54, 125);
                Color color1 = new Color(41, 125, 224);
                float[] fractions = {0.0f, 1.0f};
                Color[] colors = {color1, color2};

                LinearGradientPaint paint = new LinearGradientPaint(
                        new Point(0, 0),
                        new Point(0, getHeight()),
                        fractions,
                        colors
                );

                myButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        music.stopMusic();
                        openLevelsPage();
                    }
                });

                g2d.setPaint(paint);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(),
                        ((RoundedBorder) getBorder()).getArcWidth(),
                        ((RoundedBorder) getBorder()).getArcHeight()));

                g2d.dispose();
                super.paintComponent(g);
            }
        };

        myButton.setForeground(Color.WHITE);
        myButton.setBorder((Border) new RoundedBorder(30)); // 10 piksel yuvarlaklık
        myButton.setOpaque(false);
        myButton.setContentAreaFilled(false);
        myButton.setFocusPainted(false);
        myButton.setPreferredSize(new Dimension(buttonOriginalWidth, buttonOriginalHeight));
        myButton.setBounds(750, 495, buttonOriginalWidth, buttonOriginalHeight);
        myButton.setFont(new Font("SansSerif", Font.BOLD, 15));

        myButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                music.setVolume(-40.0f);
                music.playSoundEffect("resources/click-button.wav");

                myButton.setFont(enlargedFont);
                int newX = myButton.getX() - (buttonGrowWidth - buttonOriginalWidth) / 2;
                int newY = myButton.getY() - (buttonGrowHeight - buttonOriginalHeight) / 2;
                myButton.setBounds(newX, newY, buttonGrowWidth, buttonGrowHeight);
            }

            public void mouseExited(MouseEvent evt) {
                myButton.setFont(originalFont);
                int newX = myButton.getX() + (buttonGrowWidth - buttonOriginalWidth) / 2;
                int newY = myButton.getY() + (buttonGrowHeight - buttonOriginalHeight) / 2;
                myButton.setBounds(newX, newY, buttonOriginalWidth, buttonOriginalHeight);
            }
        });
        add(myButton);
    }

    private void openLevelsPage() {
        Levels levelsPanel = new Levels(frame);
        frame.setContentPane(levelsPanel);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    class RoundedBorder implements Border {
        private final int arcWidth;
        private final int arcHeight;

        public RoundedBorder(int radius) {
            this.arcWidth = radius;
            this.arcHeight = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.arcHeight, this.arcWidth, this.arcHeight, this.arcWidth);
        }

        public boolean isBorderOpaque() {
            return false;
        }

        public int getArcWidth() {
            return arcWidth;
        }

        public int getArcHeight() {
            return arcHeight;
        }
    }
}
