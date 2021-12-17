package principal.views.screens;

import principal.constants.Configuration;
import principal.enums.Direction;
import principal.items.DoubleShot;
import principal.items.Item;
import principal.items.TeiaAranha;
import principal.models.Bullet;
import principal.models.Enemy;
import principal.models.Player;
import principal.observers.MovementObserver;
import principal.observers.ShotFiredByEnemyObserver;
import principal.observers.ShotFiredByPlayerObserver;
import principal.utils.KeyboardListener;
import principal.views.helpers.image.Spritesheet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World extends JPanel {
    Game game;

    private boolean pauseControl;
    private boolean gameRunning;

    private final List<Enemy> enemies;
    private final List<Item> items;
    private final Player player;

    private double score;
    private int life;

    private MovementObserver movementObserver;
    private ShotFiredByPlayerObserver shotFiredByPlayerObserver;
    private ShotFiredByEnemyObserver shotFiredByEnemyObserver;

    public boolean isPauseControl() {
        return pauseControl;
    }

    public void setPauseControl(boolean pauseControl) {
        this.pauseControl = pauseControl;
    }

    public World(Game game) {
        this.game = game;

        pauseControl = false;
        gameRunning = true;
        enemies = new ArrayList<>();
        items = new ArrayList<>();
        score = 0;
        life = 3;
        player = new Player(Configuration.WIDTH / 2, 580, this);

        initSwing();
        startGame();
        new Thread(() -> {
            double nextGameTick = System.currentTimeMillis();

            while (gameRunning) {
                while (System.currentTimeMillis() > nextGameTick && gameRunning) {
                    update();
                    nextGameTick += 1000.0 / Configuration.FPS;
                }
            }
        }).start();
    }

    public void notifyMovementObserver(Direction direction) {
        if (movementObserver != null) {
            movementObserver.updateMovement(direction);
        }
    }

    public void addMovementObserver(MovementObserver observer) {
        movementObserver = observer;
    }

    public void notifyShotPlayerObserver() {
        if (shotFiredByPlayerObserver != null) {
            shotFiredByPlayerObserver.shotFiredByPlayer();
        }
    }

    public void addShotPlayerObserver(ShotFiredByPlayerObserver observer) {
        shotFiredByPlayerObserver = observer;
    }

    public void notifyShotEnemyObserver(int x, int y) {
        if (shotFiredByEnemyObserver != null) {
            shotFiredByEnemyObserver.shotFiredByEnemy(x, y);
        }
    }

    public void addShotEnemyObserver(ShotFiredByEnemyObserver observer) {
        shotFiredByEnemyObserver = observer;
    }

    public void initSwing() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Configuration.WIDTH, Configuration.HEIGHT));
        setBorder(BorderFactory.createMatteBorder(20, 40, 20, 40, Color.ORANGE));

        setFocusable(true);
        requestFocus();
        addKeyListener(new KeyboardListener(this));
    }

    private void createEnemies() {
        int xAxis, yAxis;
        for (int i = 0; i <= Configuration.ENEMY_LINES; i++) {
            yAxis = 200 - (45 * i);
            for (int j = 0; j <= Configuration.ENEMY_COLUMNS; j++) {
                xAxis = 120 + (60 * j);
                enemies.add(new Enemy(xAxis, yAxis, this));
            }
        }
    }

    public Enemy getRandomEnemy() {
        Random random = new Random();
        int randomEnemy = random.nextInt(enemies.size());

        return enemies.get(randomEnemy);
    }

    public void startGame() {
        enemies.clear();
        items.clear();
        Enemy.bullets.clear();

        createEnemies();

        life = 3;
        score = 0;
    }

    public int generateRandomItem() {
        Random random = new Random();
        return random.nextInt(3);
    }

    public void endGame(boolean victory) {
        game.getBackgroundMusic().stop();

        gameRunning = false;
        game.setVisible(false);
        game.dispose();
        new EndScreen(victory);
    }

    private void update() {
        if (!pauseControl) {

            if (life == 0 || enemies.isEmpty()) {
                endGame(false);
            }

            player.animation.updateAnimationFrames();
            List<Bullet> playerBullets = player.getBullets();
            playerBullets.forEach(Bullet::update);
            Enemy.bullets.forEach(Bullet::update);
            enemies.forEach(Enemy::update);
            items.forEach(Item::update);

            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                if (player.intersects(item)) {
                    item.ativaItem();
                    items.remove(i);
                }
            }

            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);

                for (int j = 0; j < playerBullets.size(); j++) {
                    Bullet bullet = playerBullets.get(j);

                    if (enemy.intersects(bullet)) {
                        score += (float) enemy.y / 10.0;

                        if (Math.random() < 0.2) {
                            int item = generateRandomItem();
                            switch (item) {
                                case Configuration.SPIDER_ID: {
                                    items.add(new TeiaAranha(enemy.x, enemy.y,
                                            Configuration.ENEMY_SIZE, Configuration.ENEMY_SIZE));
                                }
                                break;
                                case Configuration.DOUBLE_SHOT_ID: {
                                    items.add(new DoubleShot(Configuration.DOUBLE_SHOT_ID, enemy.x, enemy.y,
                                            Configuration.ENEMY_SIZE, Configuration.ENEMY_SIZE));
                                }
                                break;
                            }
                        }

                        enemies.remove(i);
                        playerBullets.remove(j);
                    }
                }

                if (enemy.intersects(player)) {
                    endGame(false);
                }
            }

            for (int i = 0; i < Enemy.bullets.size(); i++) {
                Bullet bullet = Enemy.bullets.get(i);
                if (player.intersects(bullet)) {
                    Enemy.bullets.remove(i);
                    life--;
                }
            }

            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(Spritesheet.backgroundImage, 0, 0, Configuration.WIDTH, Configuration.HEIGHT, this);

        player.draw(g);

        player.getBullets().forEach(bullet -> bullet.draw(g, Color.RED));

        Enemy.bullets.forEach(bullet -> bullet.draw(g, Color.CYAN));

        enemies.forEach(enemy -> enemy.draw(g));

        items.forEach(item -> item.draw(g, item));

        //items.forEach(item -> g.drawRect(item.x, item.y, item.width, item.height));

        for (int i = 0; i < life; i++) {
            g.setColor(Color.yellow);
            g.fillOval(800 + (i * 20), 650, 20, 20);
        }

        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(Color.white);
        g.drawString(String.valueOf(score), 620, 80);
    }
}