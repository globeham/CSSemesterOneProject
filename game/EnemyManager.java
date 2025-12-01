import java.awt.*;
import java.util.ArrayList;

public class EnemyManager{
    private ArrayList<point2D> path;
    private int currentWave;
    private ArrayList<Enemy> enemies;

    public EnemyManager(ArrayList<point2D> path) {
        this.path = path;
        this.currentWave = 0;
        this.enemies = new ArrayList<Enemy>();
    }

    public void spawnWave() {
        currentWave++;
        for (int i = 0; i < currentWave * 5; i++) {
            enemies.add(new Enemy(100, 10, 2, path, Color.RED));
        }
    }

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

    private boolean enemyReachedEnd(Enemy enemy) {
        ArrayList<point2D> pathPoints = enemy.getPath();
        point2D lastPoint = pathPoints.get(pathPoints.size() - 1);
        return enemy.getX() == lastPoint.getX() && enemy.getY() == lastPoint.getY();
    }

    public void drawEnemies(Graphics g) {
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

}
