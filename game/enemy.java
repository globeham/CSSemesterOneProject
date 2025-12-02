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

    public boolean reachedEnd() {
        return pathIndex >= path.size() - 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDistanceTraveled(int distance) {
        int traveled = 0;
        pathIndex = 0;
        x = path.get(0).getX();
        y = path.get(0).getY();

        while (pathIndex < path.size() - 1 && traveled < distance) {
            point2D current = path.get(pathIndex);
            point2D next = path.get(pathIndex + 1);
            int segmentLength = (int)Math.sqrt(Math.pow(next.getX() - current.getX(), 2) + Math.pow(next.getY() - current.getY(), 2));

            if (traveled + segmentLength <= distance) {
                traveled += segmentLength;
                x = next.getX();
                y = next.getY();
                pathIndex++;
            } else {
                int remaining = distance - traveled;
                int deltaX = next.getX() - current.getX();
                int deltaY = next.getY() - current.getY();
                double ratio = (double)remaining / segmentLength;
                x = current.getX() + (int)(deltaX * ratio);
                y = current.getY() + (int)(deltaY * ratio);
                traveled += remaining;
            }
        }
    }

    public ArrayList<point2D> getPath() {
        return path;
    }
}