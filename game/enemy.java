import java.awt.*;
import java.util.ArrayList;

public class Enemy {
    private int health;
    private int reward;
    private int speed;
    private int x,y;
    private int pathIndex;
    private ArrayList<point2D> path;
    private Color color;

    public Enemy(int health, int reward, int speed, ArrayList<point2D> path, Color color) {
        this.health = health;
        this.reward = reward;
        this.speed = speed;
        this.path = path;
        this.color = color;
        this.pathIndex = 0;

        point2D start = path.get(0);
        this.x = start.getX();
        this.y = start.getY();
    }

    public void move() {
        if (pathIndex < path.size() - 1) {
            point2D target = path.get(pathIndex + 1);
            int targetX = target.getX();
            int targetY = target.getY();

            int deltaX = targetX - x;
            int deltaY = targetY - y;
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            if (distance <= speed) {
                x = targetX;
                y = targetY;
                pathIndex++;
            } else {
                x += (int)(speed * (deltaX / distance));
                y += (int)(speed * (deltaY / distance));
            }
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x - 10, y - 10, 20, 20);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<point2D> getPath() {
        return path;
    }
}