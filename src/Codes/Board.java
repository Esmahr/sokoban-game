package Codes;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;

import javax.swing.border.EmptyBorder;

public class Board extends JPanel {

    private Timer timer;
    private int elapsedTime = 0;

    private JLabel timeLabel;
    private JLabel levelLabel;
    private int movesCount = 0;
    private JLabel movesLabel;
    private final int SPACE = 20;
    private final int LEFT_COLLISION = 1;
    private final int RIGHT_COLLISION = 2;
    private final int TOP_COLLISION = 3;
    private final int BOTTOM_COLLISION = 4;

    private Image backgroundImage;

    private Music music = new Music();

    private ArrayList<Wall> walls;
    private ArrayList<Baggage> baggs;
    private ArrayList<Area> areas;

    private Player soko;

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
            + "######     #### ######## $  #######\n"
            + "#####       $   #                ##\n"
            + "###        ######                ##\n"
            + "###                              ##\n"
            + "###        ####    ####          ##\n"
            + "###     #####  $     #####       ##\n"
            + "###                              ##\n"
            + "###       #####     ####         ##\n"
            + "###   $     #        #           ##\n"
            + "###                     $        ##\n"
            + "###     #######     ######       ##\n"
            + "###     $                      ..##\n"
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
            + "######                     $ ######\n"
            + "########                    #######\n"
            + "###         #     #          ..####\n"
            + "###    $###       $          ..####\n"
            + "###         ######  ### @ ## ..####\n"
            + "######################## ##########\n"
            + "###################################\n";

    private String level2
            = "##################################\n"
            + "####             #################\n"
            + "####                         #####\n"
            + "###      ##$#          #       ###\n"
            + "##           # $       #$      ###\n"
            + "###   $  # ###     ####      #####\n"
            + "####                         #####\n"
            + "#####      #  ##  ##         #####\n"
            + "##         #                 #####\n"
            + "##                           # ..#\n"
            + "##    $                        ..#\n"
            + "#############$#### @ ##        ..#\n"
            + "###                  #############\n"
            + "##################################\n";

    private String level3
            = "##################################\n"
            + "##############################  ##\n"
            + "##          #                  $##\n"
            + "## $       $#      #####   #### ##\n"
            + "###   #     #    $              ##\n"
            + "###  ###    #  #########   #### ##\n"
            + "##          #      #  ##   #### ##\n"
            + "##             $   #  ##   #### ##\n"
            + "###    $         #             ..#\n"
            + "###   ##        ### @          ..#\n"
            + "##################   #####    #..#\n"
            + "##################################\n";

    private String level4
            = "##################################\n"
            + "#####    #########################\n"
            + "#####    $             ####      #\n"
            + "#####$    ##   ###        #      #\n"
            + "####   #   ##       #####  $    ##\n"
            + "####   #   ###$     $      ###   #\n"
            + "###    $    ##   ######      #   #\n"
            + "##  ## # ## ######  ..# $    #   #\n"
            + "#   #  #  #   ####  ..#  ### # ###\n"
            + "#                   ..#  #   #   #\n"
            + "# $ ### ###         ..#  $   #   #\n"
            + "##  # # # #    @ #  ..#      #   #\n"
            + "### $       ### ##  ..########$  #\n"
            + "### ### ### ##   ######  $       #\n"
            + "### #     #    #            ###  #\n"
            + "##################################\n";

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
        levelLabel = new JLabel("LEVEL " + levelNum);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 20));
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setBounds(720, 45, 120, 30);
        add(levelLabel);
    }

    private void loadImage() {
        ImageIcon background = new ImageIcon("src/resources/background.png");
        backgroundImage = background.getImage();
    }

    private void goBackToLevels() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.getContentPane().removeAll();
        topFrame.add(new Levels(topFrame));
        topFrame.validate();
        topFrame.repaint();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld();
        loadImage();
        ImageIcon backButtonIcon = new ImageIcon("src/resources/1.png");
        JButton backButton = new JButton(backButtonIcon);
        setLayout(null);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setOpaque(false);
        backButton.setBounds(180, 50, 85, 55);
        backButton.addActionListener(e -> goBackToLevels());
        add(backButton);

        movesLabel = new JLabel("Moves: " + movesCount);
        movesLabel.setFont(new Font("Arial", Font.BOLD, 19));
        movesLabel.setForeground(Color.WHITE);
        movesLabel.setBounds(1020, 160, 120, 50);
        add(movesLabel);

        timeLabel = new JLabel("Time: 00:00 sn ");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setBounds(420, 170, 120, 30);
        add(timeLabel);
        timer = new Timer(1000, e -> {
            if (!isCompleted) {
                elapsedTime++;
            }
            updateTimeLabel();
        });
        timer.start();
    }

    private void restartTimer() {
        elapsedTime = 0;
        updateTimeLabel();
        timer.restart();
    }

    private void updateTimeLabel() {
        int minutes = elapsedTime / 60;
        int seconds = elapsedTime % 60;
        String formattedTime = String.format("%02d:%02d sn", minutes, seconds);
        timeLabel.setText("Time: " + formattedTime);
    }

    private void resetTimer() {
        elapsedTime = 0;
        updateTimeLabel();
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

                case ' ':
                    x += SPACE;
                    break;

                default:
                    break;
            }
        }
    }

    private void buildWorld(Graphics g) {

        ArrayList<Actor> world = new ArrayList<>();

        world.addAll(walls);
        world.addAll(areas);
        world.addAll(baggs);
        world.add(soko);

        for (int i = 0; i < world.size(); i++) {

            Actor item = world.get(i);

            if (item instanceof Player || item instanceof Baggage) {

                g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
            } else {

                g.drawImage(item.getImage(), item.x(), item.y(), this);
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
                            LEFT_COLLISION)) {
                        return;
                    }

                    if (checkBagCollision(LEFT_COLLISION)) {
                        return;
                    }

                    soko.move(-SPACE, 0);
                    music.playSoundEffect("resources/adım.wav");
                    movesCount++;
                    break;

                case KeyEvent.VK_RIGHT:

                    if (checkWallCollision(soko, RIGHT_COLLISION)) {
                        return;
                    }

                    if (checkBagCollision(RIGHT_COLLISION)) {
                        return;
                    }

                    soko.move(SPACE, 0);
                    music.playSoundEffect("resources/adım.wav");
                    movesCount++;
                    break;

                case KeyEvent.VK_UP:

                    if (checkWallCollision(soko, TOP_COLLISION)) {
                        return;
                    }

                    if (checkBagCollision(TOP_COLLISION)) {
                        return;
                    }

                    soko.move(0, -SPACE);
                    music.playSoundEffect("resources/adım.wav");
                    movesCount++;
                    break;

                case KeyEvent.VK_DOWN:

                    if (checkWallCollision(soko, BOTTOM_COLLISION)) {
                        return;
                    }

                    if (checkBagCollision(BOTTOM_COLLISION)) {
                        return;
                    }

                    soko.move(0, SPACE);
                    music.playSoundEffect("resources/adım.wav");
                    movesCount++;
                    break;

                case KeyEvent.VK_R:
                    restartLevel();
                    movesCount = 0;
                    break;

                default:
                    break;
            }
            movesLabel.setText("Moves: " + movesCount);
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
                    if (!bag.isOnTarget()) {
                        music.playSoundEffect("resources/success.wav");
                        bag.setOnTarget(true);
                    }
                } else {
                    bag.setOnTarget(false);
                }
            }
        }

        if (finishedBags == nOfBags) {
            isCompleted = true;
            timer.stop();
            showCompletionScreen();
        }
    }

    private void showCompletionScreen() {
        music.setVolume(-40.0f);
        music.playSoundEffect("resources/tada-fanfare-a-6313.wav");
        ImageIcon icon = new ImageIcon("src/resources/tada.png");
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel messageLabel = new JLabel("Tebrikler, seviye Tamamlandı!");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 26));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setBorder(new EmptyBorder(0, 100, 20, 100));

        JLabel iconLabel = new JLabel(scaledIcon);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(iconLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(messageLabel);

        Object[] options = {"Bir Sonraki Seviye"};

        int option = JOptionPane.showOptionDialog(
                this,
                panel,
                "Seviye Tamamlandı",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (option == JOptionPane.YES_OPTION) {
            loadNextLevel();
        }
    }

    private void loadNextLevel() {
        currentLevel++;
        switch (currentLevel) {
            case 1:
                this.level = level;
                levelLabel.setText("LEVEL 1");
                break;
            case 2:
                this.level = level1;
                isLevel2Button = true;
                movesCount = -1;
                resetTimer();
                restartTimer();
                levelLabel.setText("LEVEL 2");
                break;
            case 3:
                this.level = level2;
                isLevel3Button = true;
                movesCount = -1;
                resetTimer();
                restartTimer();
                levelLabel.setText("LEVEL 3");
                break;
            case 4:
                this.level = level3;
                isLevel4Button = true;
                movesCount = -1;
                resetTimer();
                restartTimer();
                levelLabel.setText("LEVEL 4");
                break;
            case 5:
                this.level = level4;
                isLevel5Button = true;
                movesCount = -1;
                resetTimer();
                restartTimer();
                levelLabel.setText("LEVEL 5");
                break;
            default:
                break;
        }
        restartLevel();
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