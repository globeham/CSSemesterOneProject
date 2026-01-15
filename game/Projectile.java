import java.awt.*;
import java.awt.image.BufferedImage;

/*
Projectile class for tower attacks
 */
public class Projectile {
    private int x, y;
    private int targetX, targetY;
    private int speed;
    private int damage;
    private Enemy target;
    private boolean active = true;
    private BufferedImage image;
    private int drawSize = 16;

    public Projectile(int startX, int startY, Enemy target, int speed, int damage, BufferedImage image) {
        this.x = startX;
        this.y = startY;
        this.target = target;
        this.speed = speed;
        this.damage = damage;
        this.image = image;
        this.drawSize = (image != null) ? 32 : 16; // Larger size for image projectiles
        updateTargetPosition();
    }

    private void updateTargetPosition() {
        if (target != null) {
            targetX = target.getX();
            targetY = target.getY();
        }
    }

    public void update() {
        if (!active || target == null) return;

        updateTargetPosition();

        int deltaX = targetX - x;
        int deltaY = targetY - y;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance <= speed) {
            // Hit the target
            target.takeDamage(damage);
            active = false;
        } else {
            // Move towards target
            x += (int)(speed * (deltaX / distance));
            y += (int)(speed * (deltaY / distance));
        }
    }

    public void draw(Graphics g) {
        if (active) {
            if (image != null) {
                g.drawImage(image, x - drawSize/2, y - drawSize/2, drawSize, drawSize, null);
            } else {
                g.setColor(Color.YELLOW);
                g.fillOval(x - 3, y - 3, 6, 6);
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}