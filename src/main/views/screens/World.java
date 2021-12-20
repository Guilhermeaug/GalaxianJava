package main.views.screens;

import main.constants.Configuration;
import main.enums.Direction;
import main.items.DoubleShot;
import main.items.Item;
import main.items.TeiaAranha;
import main.models.Bullet;
import main.models.Enemy;
import main.models.Player;
import main.observers.MovementObserver;
import main.observers.ShotFiredByEnemyObserver;
import main.observers.ShotFiredByPlayerObserver;
import main.utils.KeyboardListener;
import main.views.helpers.image.Spritesheet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Principal classe do jogo, que lida com os eventos disparados e atualiza a tela.
 */
public class World extends JPanel {
    Game game;

    // Controlam a situação atual do jogo
    private boolean pauseControl;
    private boolean gameRunning;

    // Objetos do jogo
    private final List<Enemy> enemies;
    private final List<Item> items;
    private final Player player;

    // Pontuação e vidas
    private double score;
    private int lifes;

    /**
     * O jogo utiliza o padrão Observer para notificar as classes responsáveis quando
     * determinados eventos acontecem. Por isso existem estas três interfaces abaixo.
     * Com elas é possível avisar à classe Player quando o jogador dispara um tiro ou uma tecla de movimento é pressionada.
     * Além disso, é possível indicar para a classe Enemy quando um tiro deve ser instanciado.
     * mais informações: https://www.devmedia.com.br/o-padrao-observer-e-swing/5719
     */
    private MovementObserver movementObserver;
    private ShotFiredByPlayerObserver shotFiredByPlayerObserver;
    private ShotFiredByEnemyObserver shotFiredByEnemyObserver;

    public boolean isPauseControl() {
        return pauseControl;
    }

    public void setPauseControl(boolean pauseControl) {
        this.pauseControl = pauseControl;
    }

    public World(Game game) {  // Recebe a instância da janela atual para poder exclui-la quando o jogo terminar
        this.game = game;

        // Inicializa os objetos do jogo
        pauseControl = false;
        gameRunning = true;
        enemies = new ArrayList<>();
        items = new ArrayList<>();
        score = 0;
        lifes = 3;
        player = new Player(Configuration.WIDTH / 2, 580, this);

        initSwing();
        startGame();
        new Thread(() -> {
            // Garantindo que o jogo será executado em 60 frames por segundo ou o determinado na classe de Configuração
            double nextGameTick = System.currentTimeMillis();

            while (gameRunning) {
                while (System.currentTimeMillis() > nextGameTick && gameRunning) {
                    update();
                    nextGameTick += 1000.0 / Configuration.FPS;
                }
            }
        }).start();
    }

    /*
        Métodos dos observadores, respetivamente para notificar e adicionar cada um
     */

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
        addKeyListener(new KeyboardListener(this)); // Adiciona o listener de teclado
    }

    /**
     * Organiza a criação dos inimigos ao redor da janela do jogo, mantendo-os a uma distância fixa.
     */
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

    /**
     * Retorna um inimigo aleatório da lista de inimigos.
     */
    public Enemy getRandomEnemy() {
        Random random = new Random();
        int randomEnemy = random.nextInt(enemies.size());

        return enemies.get(randomEnemy);
    }

    /**
     * Inicializa o jogo e também serve para reiniciar quando necessário.
     */
    public void startGame() {
        // Limpa todas as listas de objetos
        enemies.clear();
        items.clear();
        Enemy.bullets.clear();

        createEnemies(); // Cria os inimigos

        // Reseta o score e as vidas
        lifes = 3;
        score = 0;
    }

    /**
     * Retorna um número entre 1 e 2, que representa o tipo de item a ser criado.
     * 1 -- Item da aranha
     * 2 -- Item do tiro duplo
     */
    public int generateRandomItem() {
        Random random = new Random();
        return random.nextInt(3);
    }

    /**
     * Finaliza toda a janela atual e inicia uma nova.
     * @param victory indica se o jogo acabou com vitória ou não
     */
    public void endGame(boolean victory) {
        game.getBackgroundMusic().stop(); // Para a música de fundo

        gameRunning = false;
        game.setVisible(false);
        game.dispose();
        new EndScreen(victory); // Mostra a tela de fim de jogo
    }

    /**
     * Função mais importante.
     * Responsável por atualizar o estado do jogo a cada frame.
     */
    private void update() {
        if (!pauseControl) { // Se o jogo não estiver pausado

            // Caso não haja mais vidas ou todos os inimigos tenham morrido, o jogo acaba
            if(lifes == 0){
                endGame(false); // perde o jogo caso não haja vidas
            }

            if(enemies.isEmpty()){
                endGame(true); // ganha o jogo caso todos os inimigos tenham sido derrotados
            }

            List<Bullet> playerBullets = player.getBullets();

            // Chama todas as funções de atualização de cada objeto
            player.animation.updateAnimationFrames();
            playerBullets.forEach(Bullet::update);
            Enemy.bullets.forEach(Bullet::update);
            enemies.forEach(Enemy::update);
            items.forEach(Item::update);

            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                // Verifica se o jogador colide com algum item.
                if (player.intersects(item)) { // em caso positivo
                    item.ativaItem(); //ativa o item
                    items.remove(i); // remove o item da lista
                }
            }

            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);

                for (int j = 0; j < playerBullets.size(); j++) {
                    Bullet bullet = playerBullets.get(j);

                    // verifica se algum inimigo colide com alguma bala do jogador
                    if (enemy.intersects(bullet)) { // em caso positivo
                        score += (float) enemy.y / 10.0; // aumenta o score, baseado na distancia em relação ao eixo y

                        // ao abater um inimigo, existe uma chance de 20% de criar um item
                        if (Math.random() < 0.2) {
                            int item = generateRandomItem(); // escolhe um item aleatório entre os dois disponíveis
                            switch (item) { // cria o item sorteado na posição atual do inimigo abatido
                                case Configuration.SPIDER_ID: {
                                    items.add(new TeiaAranha(enemy.x, enemy.y, Configuration.ENEMY_SIZE, Configuration.ENEMY_SIZE));
                                }
                                break;
                                case Configuration.DOUBLE_SHOT_ID: {
                                    items.add(new DoubleShot(Configuration.DOUBLE_SHOT_ID, enemy.x, enemy.y, Configuration.ENEMY_SIZE, Configuration.ENEMY_SIZE));
                                }
                                break;
                            }
                        }

                        enemies.remove(i); // remove o inimigo atingido da lista
                        playerBullets.remove(j); // remove a bala que atingiu o inimigo da lista
                    }
                }

                // se um inimigo colide com o jogador, é fim de jogo.
                // isso ocorre porque caso isso aconteça, o inimigo já estará baixo suficiente na tela
                if (enemy.intersects(player)) {
                    endGame(false); // game over
                }
            }

            // itera sobre as balas dos inimigos
            for (int i = 0; i < Enemy.bullets.size(); i++) {
                Bullet bullet = Enemy.bullets.get(i);
                // se o jogador colidir com um disparo inimigo
                if (player.intersects(bullet)) { // em caso positivo
                    Enemy.bullets.remove(i);
                    lifes--; // perde uma vida
                }
            }

            repaint(); // chama o método paintComponent() para redesenhar o jogo
        }
    }

    /**
     * Método que desenha todos os objetos do jogo.
     * É herdado da classe JPanel, que representa uma área de desenho.
     * @param g o objeto Graphics que será usado para desenhar o jogo
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // desenha a imagem de fundo e adiciona um observador para continuar atualizando a animação
        g.drawImage(Spritesheet.backgroundImage, 0, 0, Configuration.WIDTH, Configuration.HEIGHT, this);

        // chama o método de desenho do jogador
        player.draw(g);

        // para cada disparo do jogador, chama o método de desenho
        player.getBullets().forEach(bullet -> bullet.draw(g, Color.RED));

        // para cada disparo dos inimigos, chama o método de desenho
        Enemy.bullets.forEach(bullet -> bullet.draw(g, Color.CYAN));

        // para cada inimigo, chama o método de desenho
        enemies.forEach(enemy -> enemy.draw(g));

        // para cada item, chama o método de desenho
        items.forEach(item -> item.draw(g, item));

        // desenha as vidas disponíveis na tela
        for (int i = 0; i < lifes; i++) {
            g.setColor(Color.yellow);
            g.fillOval(800 + (i * 20), 650, 20, 20);
        }

        // representa a hud que mostra a pontuação do jogo
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(Color.white);
        g.drawString(String.valueOf(score), 620, 80);
    }
}