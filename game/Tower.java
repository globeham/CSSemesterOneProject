import java.awt.*;
import java.util.ArrayList;

public class Tower {
    private int shootingSpeed;
    private int radius;
    private int damage;
    private int x,y;
    private Color color;
    private int cost;
    private int shotCooldown;

    public Tower(int speed, int radius, int damage, int cost, Color color) {
        this.shootingSpeed = speed;
        this.radius = radius;
        this.damage = damage;
        this.color = color;
        this.cost = cost;
    }

    public void update(ArrayList<Enemy> enemies) {
        // Tower logic to target and shoot enemies within radius
        if (shotCooldown > 0) {
            shotCooldown--;
            return;
        }
        for (Enemy enemy : enemies) {
            double distance = Math.sqrt(Math.pow(enemy.getX() - x, 2) + Math.pow(enemy.getY() - y, 2));
            if (distance <= radius) {
                enemy.takeDamage(damage);
                shotCooldown = shootingSpeed;
                break;
            }
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void upgradeSpeed(int increment) {
        this.shootingSpeed += increment;
    }

    public void upgradeRadius(int increment) {
        this.radius += increment;
    }

    public void upgradeDamage(int increment) {
        this.damage += increment;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x - 10, y - 10, 20, 20);
        g.fillOval(x -5, y-5, 10, 10);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public int getCost() {
        return cost;
    }
}