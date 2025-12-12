/*
Manager class, with wave enemy functions and spawn timers
*/
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;


public class EnemyManager {
    private ArrayList<point2D> path;
    private int currentWave;
    private ArrayList<Enemy> enemies;
    private Timer enemySpawnTimer;

    // constructor for enemy manager
    public EnemyManager(ArrayList<point2D> path) {
        this.path = path;
        this.currentWave = 0;
        this.enemies = new ArrayList<Enemy>();
    }

    // spawns a wave of enemies
    public void spawnWave() {
        currentWave++;
        for (int i = 0; i < currentWave * 5; i++) {
            Enemy enemy = new Enemy(100, 10, 2, path, Color.RED);
            enemySpawnTimer = new Timer(i * 1000, e -> enemies.add(enemy));
            enemySpawnTimer.setRepeats(false);
            enemySpawnTimer.start();
        }
    }

    // removes dead enemies and enemies that have reached the end of the path
    public void updateEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.move();
            if (!enemy.isAlive() || enemyReachedEnd(enemy)) {
                enemies.remove(i);
                i--;
            }
        }
    }

    // returns true if the enemy has reached the end of the path
    private boolean enemyReachedEnd(Enemy enemy) {
        ArrayList<point2D> pathPoints = enemy.getPath();
        point2D lastPoint = pathPoints.get(pathPoints.size() - 1);
        return enemy.getX() == lastPoint.getX() && enemy.getY() == lastPoint.getY();
    }

    // draws all enemies on the screen
    public void drawEnemies(Graphics g) {
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
    }

    // returns list of enemies
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    // returns true if wave is completed
    public boolean isWaveComplete() {
        return enemies.isEmpty();
    }

    // returns current wave number
    public int getCurrentWave() {
        return currentWave;
    }
}
