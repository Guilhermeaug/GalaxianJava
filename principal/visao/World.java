package JogoGalaxian.principal.visao;

import JogoGalaxian.constants.Configuration;
import JogoGalaxian.enums.Direction;
import JogoGalaxian.observers.MovementObserver;
import JogoGalaxian.observers.ShotFiredByEnemyObserver;
import JogoGalaxian.observers.ShotFiredByPlayerObserver;
import JogoGalaxian.principal.modelo.Bullet;
import JogoGalaxian.principal.modelo.Enemy;
import JogoGalaxian.principal.modelo.Player;
import JogoGalaxian.principal.visao.recursos.Spritesheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World extends JPanel implements KeyListener {
    boolean pauseControl = false;

    private final List<Enemy> enemies = new ArrayList<>();

    private final Player player;

    private MovementObserver movementObserver;
    private ShotFiredByPlayerObserver shotFiredByPlayerObserver;
    private ShotFiredByEnemyObserver shotFiredByEnemyObserver;

    private double score;
    private int life;

    public World() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Configuration.WIDTH, Configuration.HEIGHT));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createMatteBorder(20, 40, 20, 40, Color.ORANGE));

        setFocusable(true);
        requestFocus();
        addKeyListener(this);

        score = 0;
        life = 3;
        player = new Player(Configuration.WIDTH / 2, 580, this);

        startGame();

        new Thread(() -> {
            while (true) {
                update();

                try {
                    Thread.sleep(1000 / Configuration.FPS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void notifyMovementObserver(Direction direction) {
        if (movementObserver != null) {
            movementObserver.updateMovement(direction);
        }
    }

    public void addMovementObserver(MovementObserver observer) {
        movementObserver = observer;
    }

    private void notifyShotPlayerObserver() {
        if (shotFiredByPlayerObserver != null) {
            shotFiredByPlayerObserver.shotFiredByPlayer();
        }
    }

    public void addShotPlayerObserver(ShotFiredByPlayerObserver observer) {
        shotFiredByPlayerObserver = observer;
    }

    private void notifyShotEnemyObserver(int x, int y) {
        if (shotFiredByEnemyObserver != null) {
            shotFiredByEnemyObserver.shotFiredByEnemy(x, y);
        }
    }

    public void addShotEnemyObserver(ShotFiredByEnemyObserver observer) {
        shotFiredByEnemyObserver = observer;
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

    private void startGame() {
        enemies.clear();
        createEnemies();

        life = 3;
        score = 0;
    }

    private void update() {
        if (!pauseControl) {
            if(life == 0){
                System.exit(0);
            }

            List<Bullet> playerBullets = player.getBullets();

            playerBullets.forEach(Bullet::update);
            Enemy.bullets.forEach(Bullet::update);
            enemies.forEach(Enemy::update);

            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);

                for (int j = 0; j < playerBullets.size(); j++) {
                    Bullet bullet = playerBullets.get(j);

                    if (enemy.intersects(bullet)) {
                        //pontuacao baseada na distancia
                        score += (float) enemy.y / 10.0;

                        enemies.remove(i);
                        playerBullets.remove(j);
                    }
                }

                if (enemy.intersects(player)) {
                    System.exit(0);
                }
            }

            for(int i = 0; i < Enemy.bullets.size(); i++) {
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

        g.drawImage(Spritesheet.plater_front, player.x, player.y, player.width, player.height, null);

        player.getBullets().forEach(bullet -> {
            g.setColor(Color.YELLOW);
            g.drawOval(bullet.x, bullet.y, bullet.width, bullet.height);
        });

        Enemy.bullets.forEach(bullet -> {
            g.setColor(Color.CYAN);
            g.drawOval(bullet.x, bullet.y, bullet.width, bullet.height);
        });

        enemies.forEach(enemy -> {
            g.drawImage(Spritesheet.inimigo, enemy.x, enemy.y,
                    Configuration.ENEMY_SIZE, Configuration.ENEMY_SIZE, null);
        });

        g.drawImage(Spritesheet.plater_front, player.x, player.y, 80, 80, null);
        for (int i = 0; i < life; i++) {
            g.setColor(Color.yellow);
            g.fillOval(800 + (i * 20), 650, 20, 20);
        }

        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(Color.white);
        g.drawString(String.valueOf(score), 620, 80);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
            notifyMovementObserver(e.getKeyCode() == KeyEvent.VK_RIGHT ? Direction.RIGHT : Direction.LEFT);
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            notifyShotPlayerObserver();

            Random random = new Random();

            //50% de chance do inimigo disparar quanto o jogador dispara
            if (random.nextInt(100) < 50) {
                int randomEnemy = random.nextInt(enemies.size());
                Enemy enemy = enemies.get(randomEnemy);
                notifyShotEnemyObserver(enemy.x, enemy.y);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_P) {
            pauseControl = !pauseControl;
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            startGame();
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}