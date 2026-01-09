/*
Manager class, with wave enemy functions and spawn timers
*/
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;


public class EnemyManager {
    private ArrayList<Point2D> path;
    private int currentWave;
    private ArrayList<Enemy> enemies;
    private Timer enemySpawnTimer;
    private int health = 100;

    // constructor for enemy manager
    public EnemyManager(ArrayList<Point2D> path) {
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

    // method creates waves of enemies
    private Enemy createEnemyForWave(int wave, int index) {
        // wave 1-2: Bluejays (fast, low hp)
        if (wave <= 2) {
            return new Bluejay(40, 5, 3, path);
        }
        // wave 3-4: mix Bluejays and Robins (medium)
        if (wave <= 4) {
            if (index % 3 == 0) return new Robin(80, 10, 2, path);
            return new Bluejay(50, 6, 3, path);
        }
        // wave 5+: introduce stronger Cardinals
        if (wave <= 6) {
            if (index % 6 == 0) return new Cardinal(200, 25, 2, path);
            if (index % 2 == 0) return new Robin(90, 12, 2, path);
            return new Bluejay(60, 8, 3, path);
        }
        // wave 7+: use Sparrows
        if (wave <= 10) {   
            if (index % 6 == 0) return new Cardinal(300, 25, 2, path);
            if (index % 6 == 0) return new Cardinal(200, 25, 2, path);
            if (index % 2 == 0) return new Robin(90, 12, 2, path);
            if (index % 7 == 0) return new Sparrow(500, 50, 2, path);
            return new Bluejay(60, 8, 3, path);
        }
        // wave 11+: more Sparrows and White
        else {
            if (index % 5 == 0) return new Sparrow(500, 50, 2, path);
            if (index % 3 == 0) return new Cardinal(300, 25, 2, path);
            if (index % 7 == 0) return new White(800, 100, 2, path);
            if (index % 2 == 0) return new Robin(150, 12, 2, path);
            return new Bluejay(60, 8, 3, path);           
        }
    }

    // removes dead enemies and enemies that have reached the end of the path
    public void updateEnemies(TowerManager TowerManager) {
        if (enemies == null || enemies.isEmpty()) return;

        java.util.Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            // move the enemy along the path
            enemy.move();

            // if enemy died, award money and remove
            if (!enemy.isAlive()) {
                TowerManager.addMoneyFromEnemy(enemy);
                it.remove();
                continue;
            }

            // if enemy reached the end, remove (no reward)
            if (enemyReachedEnd(enemy)) {
                health-=100;
                System.out.println(health);
                if(health==0) {
                    System.out.println("Game Over! You've run out of health.");
                    int playAgain = JOptionPane.showConfirmDialog(null, "Game Over! You've run out of health. Play Again?", "Game Over", JOptionPane.YES_NO_OPTION);
                    if (playAgain == JOptionPane.YES_OPTION) {
                        // Restart the game
                        KingTowerDefense3000 newGame = new KingTowerDefense3000();
                    } else {
                        // Exit the game
                        System.exit(0);
                    }
                }
                it.remove();
            }
        }
    }

    // returns true if the enemy has reached the end of the path
    private boolean enemyReachedEnd(Enemy enemy) {
        ArrayList<Point2D> pathPoints = enemy.getPath();
        Point2D lastPoint = pathPoints.get(pathPoints.size() - 1);
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

    // returns current health`
    public int getHealth() {
        return health;
    }

}
