import java.awt.*;
import java.util.ArrayList;

/*
Tower superclass for all towers in the game
 */
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
    private java.awt.image.BufferedImage projectileImage;

    // constructor for tower
    public Tower(int speed, int radius, int damage, int cost) {
        this.shootingSpeed = speed;
        this.radius = radius;
        this.damage = damage;
        this.cost = cost;
        this.shotCooldown = 0;
    }

    public void update(ArrayList<Enemy> enemies, TowerManager towerManager) {
        // Tower logic to target and shoot enemies within radius
        if (shotCooldown > 0) {
            shotCooldown--;
            return;
        }
        for (Enemy enemy : enemies) {
            double distance = Math.sqrt(Math.pow(enemy.getX() - x, 2) + Math.pow(enemy.getY() - y, 2));
            if (distance <= radius) {
                
                // Create projectile instead of instant damage
                Projectile projectile = new Projectile(x, y, enemy, 8, damage, projectileImage);
                towerManager.addProjectile(projectile);
                
                shotCooldown = shootingSpeed;
                pulse = 30;
                break;
            }

            if (pulse > 0) {
                pulse--;
            }
        }
    }

    // setter
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // upgrade methods
    public void upgradeSpeed(int increment) {
        this.shootingSpeed += increment;
    }

    public void upgradeRadius(int increment) {
        this.radius += increment;
    }

    public void upgradeDamage(int increment) {
        this.damage += increment;
    }

    // basic draw method
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

    // sets image
    public void setImage(java.awt.image.BufferedImage img) {

        this.image = img;
    }

    public void setProjectileImage(java.awt.image.BufferedImage img) {

        this.projectileImage = img;
    }

    // getters
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