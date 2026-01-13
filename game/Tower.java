import java.awt.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Tower {
    private int shootingSpeed;
    private int radius;
    private int damage;
    private int x,y;
    private Color color;
    private int cost;
    private int shotCooldown;
    private int pulse = 0;
    private java.awt.image.BufferedImage image;

    public Tower(int speed, int radius, int damage, int cost) {
        this.shootingSpeed = speed;
        this.radius = radius;
        this.damage = damage;
        this.cost = cost;
        this.shotCooldown = 0;
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
                pulse = 30;
                break;
            }

            if (pulse > 0) {
                pulse--;
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
        if (pulse>0) {
            g.setColor(Color.RED);
            g.drawOval(x - radius - pulse, y - radius - pulse, 2 * (radius + pulse), 2 * (radius + pulse));
        }
        if (image != null) {
            int iw = image.getWidth();
            int ih = image.getHeight();
            g.drawImage(image, x - iw/2, y - ih/2, null);
        } else {
            g.setColor(color == null ? Color.GRAY : color);
            g.fillOval(x - 10, y - 10, 20, 20);
            g.fillOval(x -5, y-5, 10, 10);
        }
    }

    public void setImage(java.awt.image.BufferedImage img) {
        this.image = img;
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