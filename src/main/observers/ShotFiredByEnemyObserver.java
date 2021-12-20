package main.observers;

/**
 * Interface para os observadores de tiros disparados pelos inimigos.
 */
public interface ShotFiredByEnemyObserver {
    void shotFiredByEnemy(int xEnemy, int yEnemy);
}