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

    public void spawnWave() {
        currentWave++;
        int enemiesInWave = currentWave * 5;
        int spawnDelayMs = 800; // ms between spawns
        for (int i = 0; i < enemiesInWave; i++) {
            int delay = i * spawnDelayMs;
            int index = i;
            int wave = currentWave;
            Timer t = new Timer(delay, e -> {
                Enemy enemy = createEnemyForWave(wave, index);
                enemies.add(enemy);
            });
            t.setRepeats(false);
            t.start();
        }
    }

    // simple factory - tweak stats and patterns to taste
    private Enemy createEnemyForWave(int wave, int index) {
        // wave 1-2: bluejays (fast, low hp)
        if (wave <= 2) {
            return new bluejay(40, 5, 3, path);
        }
        // wave 3-4: mix bluejays and robins (medium)
        if (wave <= 4) {
            if (index % 3 == 0) return new robin(80, 10, 2, path);
            return new bluejay(50, 6, 3, path);
        }
        // wave 5+: introduce stronger cardinals
        if (index % 6 == 0) return new cardinal(200, 25, 2, path);
        if (index % 2 == 0) return new robin(90, 12, 2, path);
        return new bluejay(60, 8, 3, path);
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
