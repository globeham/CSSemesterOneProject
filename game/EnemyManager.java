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
    private boolean gameOver = false;
    private int waveAtGameOver = 0;

    private int health = 100;

    // constructor for enemy manager
    public EnemyManager(ArrayList<Point2D> path) {
        this.path = path;
        this.currentWave = 0;
        this.enemies = new ArrayList<Enemy>();
    }

    // allow updating the active display path (called from GamePanel after scaling)
    public void setPath(ArrayList<Point2D> newPath) {
        this.path = newPath;
    }

    // spawns wave of enemies
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
        Enemy enemy;
        // wave 1-2: Bluejays (fast, low hp)
        if (wave <= 2) {
            enemy = new Bluejay(22, 5, 3, path);
        }
        // wave 3-4: mix Bluejays and Robins (medium)
        else if (wave <= 4) {
            if (index % 3 == 0) enemy = new Robin(40, 10, 2, path);
            else enemy = new Bluejay(28, 6, 3, path);
        }
        // wave 5+: introduce stronger Cardinals
        else if (wave <= 6) {
            if (index % 6 == 0) enemy = new Cardinal(70, 25, 2, path);
            else if (index % 2 == 0) enemy = new Robin(45, 12, 2, path);
            else enemy = new Bluejay(32, 8, 3, path);
        }
        // wave 7+: use Sparrows
        else if (wave <= 10) {   
            if (index % 6 == 0) enemy = new Cardinal(85, 25, 2, path);
            else if (index % 7 == 0) enemy = new Sparrow(100, 50, 2, path);
            else if (index % 2 == 0) enemy = new Robin(55, 12, 2, path);
            else enemy = new Bluejay(40, 8, 3, path);
        }
        // wave 11+: more Sparrows and White
        else {
            if (index % 5 == 0) enemy = new Sparrow(110, 50, 2, path);
            else if (index % 3 == 0) enemy = new Cardinal(95, 25, 2, path);
            else if (index % 7 == 0) enemy = new White(150, 100, 2, path);
            else if (index % 2 == 0) enemy = new Robin(65, 12, 2, path);
            else enemy = new Bluejay(45, 8, 3, path);           
        }
        // Increase health progressively with wave
        enemy.setHealth(enemy.getHealth() + wave * 5);
        return enemy;
    }

    public boolean isGameOver() {
        return gameOver;
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

            // if enemy reached the end, remove
            if (enemyReachedEnd(enemy)) {
                health -= 5;
                System.out.println("Enemy reached end! Health: " + health);
                if(health <= 0) {
                    gameOver = true;
                    waveAtGameOver = currentWave;
                    

                
                }
                it.remove();
            }
        }
    }

    // returns true if the enemy has reached the end of the path
    private boolean enemyReachedEnd(Enemy enemy) {
        ArrayList<Point2D> pathPoints = enemy.getPath();
        Point2D lastPoint = pathPoints.get(pathPoints.size() - 1);
        double distance = Math.sqrt(Math.pow(enemy.getX() - lastPoint.getX(), 2) + 
                                     Math.pow(enemy.getY() - lastPoint.getY(), 2));
        return distance < 25;
    }

    // draws all enemies on the screen
    public void drawEnemies(Graphics g) {
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
    }


    // returns wave at game over
    public int waveAtGameOver() {
        return waveAtGameOver;
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

    // returns current health
    public int getHealth() {
        return health;
    }

}
