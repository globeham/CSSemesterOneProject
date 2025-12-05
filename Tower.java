import java.awt.*;

public class Tower {
    private int speed;
    private int radius;
    private int damage;
    private int x,y;
    private Color color;

    public Tower(int speed, int radius, int damage, Color color) {
        this.speed = speed;
        this.radius = radius;
        this.damage = damage;
        this.color = color;

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
}