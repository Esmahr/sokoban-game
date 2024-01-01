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

    public StartPage(JFrame frame) {
        Music music = new Music();
        music.playMusic("resources/Joker-ft.-İnfaz-2DK-official-audio.wav");
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
                myButton.setFont(enlargedFont);
                int newX = myButton.getX() - (buttonGrowWidth - buttonOriginalWidth) / 2;
                int newY = myButton.getY() - (buttonGrowHeight - buttonOriginalHeight) / 2;
                myButton.setBounds(newX, newY, buttonGrowWidth, buttonGrowHeight);
            }

            public void mouseExited(MouseEvent evt) {
                myButton.setFont(originalFont); // Fontu orijinal boyutuna geri döndür
                int newX = myButton.getX() + (buttonGrowWidth - buttonOriginalWidth) / 2;
                int newY = myButton.getY() + (buttonGrowHeight - buttonOriginalHeight) / 2;
                myButton.setBounds(newX, newY, buttonOriginalWidth, buttonOriginalHeight);
            }
        });
        add(myButton);
    }

    private void openLevelsPage() {
        // Levels sayfasını açacak kodlar burada.
        Levels levelsPanel = new Levels(frame); // Levels sınıfından bir örnek oluşturun
        frame.setContentPane(levelsPanel); // Ana frame'in içeriğini Levels paneli ile değiştirin
        frame.revalidate(); // Frame içeriğindeki değişiklikleri uygula
        frame.repaint();  // Frame'i yeniden çiz
    }

    static class LevelsPage extends JPanel {
        public LevelsPage() {
            // Levels sayfasının içeriğini burada oluşturun
            setBackground(Color.BLUE); // Örnek arkaplan rengi
            add(new JLabel("Levels Page")); // Örnek label
        }
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
            // Burada border çizgisi çizilmiyor, bu yüzden boş bırakıldı.
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
