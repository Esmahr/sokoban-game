package Codes;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;

public class Board extends JPanel {

    private final int OFFSET = 30;
    private final int SPACE = 20;
    private final int LEFT_COLLISION = 1;
    private final int RIGHT_COLLISION = 2;
    private final int TOP_COLLISION = 3;
    private final int BOTTOM_COLLISION = 4;

    private Image backgroundImage;

    private ArrayList<Wall> walls;
    private ArrayList<Baggage> baggs;
    private ArrayList<Area> areas;

    private Player soko;
    private Player soko1;

    private int w = 0;
    private int h = 0;
    private int currentLevel;

    private boolean isCompleted = false;
    public static boolean isLevel1Button = true;
    public static boolean isLevel2Button = false;
    public static boolean isLevel3Button = false;
    public static boolean isLevel4Button = false;
    public static boolean isLevel5Button = false;


    private String level
            = "###################################\n"
            + "######                      #######\n"
            + "######     #############    #######\n"
            + "#####  $              $          ##\n"
            + "###                              ##\n"
            + "###        $       #             ##\n"
            + "###        ####                  ##\n"
            + "###     ##      $       $        ##\n"
            + "###                              ##\n"
            + "###                              ##\n"
            + "###         ###     ####         ##\n"
            + "###                              ##\n"
            + "###                              ##\n"
            + "###     #######      #####       ##\n"
            + "###     $     *                ..##\n"
            + "###                            ..##\n"
            + "#########@#####################..##\n"
            + "###################################\n";

    private String level1
            = "###################################\n"
            + "################  #################\n"
            + "########         $     ############\n"
            + "######                 ############\n"
            + "#####   $   ####  $            ####\n"
            + "###    ###########   ###     ######\n"
            + "###                    #### #######\n"
            + "######     *               $ ######\n"
            + "########                     ######\n"
            + "###         #     #          ..####\n"
            + "###    $###3       $          ..####\n"
            + "###         ######  ####@### ..####\n"
            + "###################################\n"
            + "###################################\n";

    private String level2
            = "######################\n"
            + "####             #####\n"
            + "###   ##$#     #   ###\n"
            + "##         # $ #$  ###\n"
            + "###  $  # ###    #####\n"
            + "#####      #  ## #####\n"
            + "##         #     # ..#\n"
            + "##    $       *    ..#\n"
            + "###$#### @###      ..#\n"
            + "###      #############\n"
            + "######################\n";

    private String level3
            = "########################\n"
            + "####################  ##\n"
            + "##       #   *       $##\n"
            + "## $    $#      ##### ##\n"
            + "###  #   #    $   ### ##\n"
            + "### ###  #  ######### ##\n"
            + "##       #      #  ## ##\n"
            + "##          $   #  ## ##\n"
            + "###    $    #        ..#\n"
            + "###   ##   ####      ..#\n"
            + "#########@######    #..#\n"
            + "########################\n";

    private String level4
            = "    ######                        \n"
            + "    #    #########################\n"
            + "    #    $             ####      #\n"
            + "   ##$    ##   ###        #      #\n"
            + "  ##   #   ##       #####  $    ##\n"
            + "  ##   #   ###$     $      ###   #\n"
            + " ##    $    ##   ######      #   #\n"
            + "##  ## # ## ######  ..# $    #   #\n"
            + "#   #  #  #   #*##  ..#  ### # ###\n"
            + "#                   ..#  #   #   #\n"
            + "# $ ### ###         ..#  $   #   #\n"
            + "##  # # # #   #@##  ..#      #   #\n"
            + " ## $       ######  ..########$  #\n"
            + "  # ### ### ##   ######  $       #\n"
            + "  # #     #    #            ###  #\n"
            + "  ################################\n";

    public Board(int levelNum) {
        currentLevel = levelNum;
        switch (levelNum) {
            case 1:
                this.level = level;
                break;
            case 2:
                this.level = level1;
                break;
            case 3:
                this.level = level2;
                break;
            case 4:
                this.level = level3;
                break;
            case 5:
                this.level = level4;
                break;
            default:
                break;
        }
        initBoard();
    }
    private void loadImage() {
        ImageIcon ii = new ImageIcon("src/resources/background.png"); // Resmin yolu
        backgroundImage = ii.getImage();
    }


    private void goBackToStartPage() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.getContentPane().removeAll();
        topFrame.add(new StartPage(topFrame));
        topFrame.validate();
        topFrame.repaint();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld();
        loadImage();
        ImageIcon backButtonIcon = new ImageIcon("src/resources/previous.png");
        JButton backButton = new JButton(backButtonIcon);

        // Buton özelliklerini ayarla
        backButton.setBorderPainted(false); // Kenarlık çizimini kapat
        backButton.setContentAreaFilled(false); // İçerik alanı arka planını kapat
        backButton.setFocusPainted(false); // Focus çizimini kapat
        backButton.setOpaque(false); // Şeffaflığı etkinleştir

        // Eylem dinleyicisi ekle
        backButton.addActionListener(e -> goBackToStartPage());

        // Butonu panele ekle
        add(backButton);
    }

    public void startGame() {
        requestFocusInWindow();
    }

    private void initWorld() {
        walls = new ArrayList<>();
        baggs = new ArrayList<>();
        areas = new ArrayList<>();

        int levelWidth = 1;
        int maxWidth = 1;
        int levelHeight = 1;

        for (int i = 0; i < level.length(); i++) {
            if (level.charAt(i) == '\n') {
                levelHeight++;
                if (maxWidth < levelWidth) {
                    maxWidth = levelWidth;
                }
                levelWidth = 0;
            } else {
                levelWidth++;
            }
        }

        int xStart = 420;
        int yStart = 200;

        int x = xStart;
        int y = yStart;

        Wall wall;
        Baggage b;
        Area a;

        for (int i = 0; i < level.length(); i++) {

            char item = level.charAt(i);

            switch (item) {

                case '\n':
                    y += SPACE;
                    x = xStart;
                    break;

                case '#':
                    wall = new Wall(x, y);
                    walls.add(wall);
                    x += SPACE;
                    break;

                case '$':
                    b = new Baggage(x, y);
                    baggs.add(b);
                    x += SPACE;
                    break;

                case '.':
                    a = new Area(x, y);
                    areas.add(a);
                    x += SPACE;
                    break;

                case '@':
                    soko = new Player(x, y);
                    x += SPACE;
                    break;

                case '*':
                    soko1 = new Player(x, y);
                    x += SPACE;
                    break;

                case ' ':
                    x += SPACE;
                    break;

                default:
                    break;
            }
        }

        h = y;
    }

    private void buildWorld(Graphics g) {

        ArrayList<Actor> world = new ArrayList<>();

        world.addAll(walls);
        world.addAll(areas);
        world.addAll(baggs);
        world.add(soko);
        world.add(soko1);

        for (int i = 0; i < world.size(); i++) {

            Actor item = world.get(i);

            if (item instanceof Player || item instanceof Baggage) {

                g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
            } else {

                g.drawImage(item.getImage(), item.x(), item.y(), this);
            }

            if (isCompleted) {

                g.setColor(new Color(0, 0, 0));
                g.drawString("Completed", 25, 20);
            }

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

        buildWorld(g);
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (isCompleted) {
                return;
            }

            int key = e.getKeyCode();

            switch (key) {

                case KeyEvent.VK_LEFT:

                    if (checkWallCollision(soko,
                            LEFT_COLLISION) || checkPlayerCollision(soko, LEFT_COLLISION)) {
                        return;
                    }

                    if (checkBagCollision(LEFT_COLLISION)) {
                        return;
                    }

                    soko.move(-SPACE, 0);

                    break;

                case KeyEvent.VK_A:

                    if (checkWallCollision(soko1,
                            LEFT_COLLISION) || checkPlayerCollision(soko1, LEFT_COLLISION)) {
                        return;
                    }

                    if (checkBagCollision(LEFT_COLLISION)) {
                        return;
                    }

                    soko1.move(-SPACE, 0);

                    break;

                case KeyEvent.VK_RIGHT:

                    if (checkWallCollision(soko, RIGHT_COLLISION) || checkPlayerCollision(soko, RIGHT_COLLISION)) {
                        return;
                    }

                    if (checkBagCollision(RIGHT_COLLISION)) {
                        return;
                    }

                    soko.move(SPACE, 0);

                    break;

                case KeyEvent.VK_D:

                    if (checkWallCollision(soko1, RIGHT_COLLISION) || checkPlayerCollision(soko1, RIGHT_COLLISION)) {
                        return;
                    }

                    if (checkBagCollision(RIGHT_COLLISION)) {
                        return;
                    }

                    soko1.move(SPACE, 0);

                    break;

                case KeyEvent.VK_UP:

                    if (checkWallCollision(soko, TOP_COLLISION) || checkPlayerCollision(soko, TOP_COLLISION)) {
                        return;
                    }

                    if (checkBagCollision(TOP_COLLISION)) {
                        return;
                    }

                    soko.move(0, -SPACE);

                    break;

                case KeyEvent.VK_W:

                    if (checkWallCollision(soko1, TOP_COLLISION) || checkPlayerCollision(soko1, TOP_COLLISION)) {
                        return;
                    }

                    if (checkBagCollision(TOP_COLLISION)) {
                        return;
                    }

                    soko1.move(0, -SPACE);

                    break;

                case KeyEvent.VK_DOWN:

                    if (checkWallCollision(soko, BOTTOM_COLLISION) || checkPlayerCollision(soko, BOTTOM_COLLISION)) {
                        return;
                    }

                    if (checkBagCollision(BOTTOM_COLLISION)) {
                        return;
                    }

                    soko.move(0, SPACE);

                    break;
                case KeyEvent.VK_S:

                    if (checkWallCollision(soko1, BOTTOM_COLLISION) || checkPlayerCollision(soko, BOTTOM_COLLISION)) {
                        return;
                    }

                    if (checkBagCollision(BOTTOM_COLLISION)) {
                        return;
                    }

                    soko1.move(0, SPACE);

                    break;

                case KeyEvent.VK_R:

                    restartLevel();

                    break;

                default:
                    break;
            }

            repaint();
        }
    }

    private boolean checkWallCollision(Actor actor, int type) {

        switch (type) {

            case LEFT_COLLISION:

                for (int i = 0; i < walls.size(); i++) {

                    Wall wall = walls.get(i);

                    if (actor.isLeftCollision(wall)) {

                        return true;
                    }
                }

                return false;

            case RIGHT_COLLISION:

                for (int i = 0; i < walls.size(); i++) {

                    Wall wall = walls.get(i);

                    if (actor.isRightCollision(wall)) {
                        return true;
                    }
                }

                return false;

            case TOP_COLLISION:

                for (int i = 0; i < walls.size(); i++) {

                    Wall wall = walls.get(i);

                    if (actor.isTopCollision(wall)) {

                        return true;
                    }
                }

                return false;

            case BOTTOM_COLLISION:

                for (int i = 0; i < walls.size(); i++) {

                    Wall wall = walls.get(i);

                    if (actor.isBottomCollision(wall)) {

                        return true;
                    }
                }

                return false;

            default:
                break;
        }

        return false;
    }

    private boolean checkPlayerCollision(Player player, int type) {
        switch (type) {
            case LEFT_COLLISION:
                if (player.isLeftCollision(soko1) || player.isLeftCollision(soko)) {
                    return true;
                }
                break;
            case RIGHT_COLLISION:
                if (player.isRightCollision(soko1) || player.isLeftCollision(soko)) {
                    return true;
                }
                break;
            case TOP_COLLISION:
                if (player.isTopCollision(soko1) || player.isLeftCollision(soko)) {
                    return true;
                }
                break;
            case BOTTOM_COLLISION:
                if (player.isBottomCollision(soko1) || player.isLeftCollision(soko)) {
                    return true;
                }
                break;
        }
        return false;
    }

    private boolean checkBagCollision(int type) {

        switch (type) {

            case LEFT_COLLISION:

                for (int i = 0; i < baggs.size(); i++) {

                    Baggage bag = baggs.get(i);

                    if (soko.isLeftCollision(bag)) {

                        for (int j = 0; j < baggs.size(); j++) {

                            Baggage item = baggs.get(j);

                            if (!bag.equals(item)) {

                                if (bag.isLeftCollision(item)) {
                                    return true;
                                }
                            }

                            if (checkWallCollision(bag, LEFT_COLLISION)) {
                                return true;
                            }
                        }

                        bag.move(-SPACE, 0);
                        isCompleted();
                    }
                }

                return false;

            case RIGHT_COLLISION:

                for (int i = 0; i < baggs.size(); i++) {

                    Baggage bag = baggs.get(i);

                    if (soko.isRightCollision(bag)) {

                        for (int j = 0; j < baggs.size(); j++) {

                            Baggage item = baggs.get(j);

                            if (!bag.equals(item)) {

                                if (bag.isRightCollision(item)) {
                                    return true;
                                }
                            }

                            if (checkWallCollision(bag, RIGHT_COLLISION)) {
                                return true;
                            }
                        }

                        bag.move(SPACE, 0);
                        isCompleted();
                    }
                }
                return false;

            case TOP_COLLISION:

                for (int i = 0; i < baggs.size(); i++) {

                    Baggage bag = baggs.get(i);

                    if (soko.isTopCollision(bag)) {

                        for (int j = 0; j < baggs.size(); j++) {

                            Baggage item = baggs.get(j);

                            if (!bag.equals(item)) {

                                if (bag.isTopCollision(item)) {
                                    return true;
                                }
                            }

                            if (checkWallCollision(bag, TOP_COLLISION)) {
                                return true;
                            }
                        }

                        bag.move(0, -SPACE);
                        isCompleted();
                    }
                }

                return false;

            case BOTTOM_COLLISION:

                for (int i = 0; i < baggs.size(); i++) {

                    Baggage bag = baggs.get(i);

                    if (soko.isBottomCollision(bag)) {

                        for (int j = 0; j < baggs.size(); j++) {

                            Baggage item = baggs.get(j);

                            if (!bag.equals(item)) {

                                if (bag.isBottomCollision(item)) {
                                    return true;
                                }
                            }

                            if (checkWallCollision(bag, BOTTOM_COLLISION)) {

                                return true;
                            }
                        }

                        bag.move(0, SPACE);
                        isCompleted();
                    }
                }

                break;

            default:
                break;
        }

        return false;
    }

    public void isCompleted() {

        int nOfBags = baggs.size();
        int finishedBags = 0;

        for (Baggage bag : baggs) {
            for (Area area : areas) {
                if (bag.x() == area.x() && bag.y() == area.y()) {
                    finishedBags++;
                }
            }
        }

        if (finishedBags == nOfBags) {
            isCompleted = true;
            showCompletionScreen();
        }
    }

    private void showCompletionScreen() {
        int option = JOptionPane.showOptionDialog(this,
                "Tebrikler, kazandınız! Bir sonraki levele geçmek için 'Evet', ana sayfaya dönmek için 'Hayır'ı tıklayınız.",
                "Seviye Tamamlandı",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, null, null);

        if (option == JOptionPane.YES_OPTION) {
            loadNextLevel(); // Sonraki seviyeye geç
        } else {
            goBackToStartPage(); // Ana sayfaya dön
        }
    }

    private void loadNextLevel() {
        // Mevcut seviyeyi artır
        currentLevel++;
        switch (currentLevel) {
            case 1:
                this.level = level;
                break;
            case 2:
                this.level = level1;
                isLevel2Button = true;
                break;
            case 3:
                this.level = level2;
                isLevel3Button = true;
                break;
            case 4:
                this.level = level3;
                isLevel4Button = true;
                break;
            case 5:
                this.level = level4;
                isLevel5Button = true;
                break;
            default:
                break;
        }
        restartLevel(); // Yeni seviyeyi başlat
    }


    private void restartLevel() {

        areas.clear();
        baggs.clear();
        walls.clear();

        initWorld();

        if (isCompleted) {
            isCompleted = false;
        }
    }
}
