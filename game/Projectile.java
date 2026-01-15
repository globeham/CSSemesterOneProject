import java.awt.*;

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

    public Projectile(int startX, int startY, Enemy target, int speed, int damage) {
        this.x = startX;
        this.y = startY;
        this.target = target;
        this.speed = speed;
        this.damage = damage;
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
            g.setColor(Color.YELLOW);
            g.fillOval(x - 3, y - 3, 6, 6);
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}