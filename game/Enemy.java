/*
Super class for all enemies in the game
*/

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Enemy {
    private int health;
    private int maxHealth;
    private int reward;
    private int speed;
    private int x,y;
    private int pathIndex;
    private ArrayList<Point2D> path;
    private Color color;
    protected BufferedImage image;
    protected int drawSize = 32;

    // constructor for enemy class
    public Enemy(int health, int reward, int speed, ArrayList<Point2D> path, Color color) {
        this.health = health;
        this.maxHealth = health;
        this.reward = reward;
        this.speed = speed;
        this.path = path;
        this.color = color;
        this.pathIndex = 0;

        Point2D start = path.get(0);
        this.x = start.getX();
        this.y = start.getY();
    }


    // moves the enemy along the path
    public void move() {
        if (pathIndex < path.size() - 1) {
            Point2D target = path.get(pathIndex + 1);
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

    // takes damage and reduces health
    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    // returns true if enemy is alive
    public boolean isAlive() {
        return health > 0;
    }

    // basic draw method for enemy (just a circle)
    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x - 10, y - 10, 20, 20, null);
        } else {
            g.setColor(color);
            g.fillOval(x - 10, y - 10, 20, 20);
        }

        
    }

    // draws health bar above enemy
    public void drawHealthBar(Graphics g, int centerX, int topY, int width) {
    
        int barH = 4;
        int barX = centerX - width / 2;
        int barY = topY - 8;
    
        // background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(barX, barY, width, barH);
    
        // fill
        double pct = (double) health / maxHealth;
        int fillW = (int) Math.round(width * pct);

        if(pct < 0.3) {
            g.setColor(Color.RED);
        } else if (pct < 0.8) {
            g.setColor(Color.YELLOW);
        } else {
            g.setColor(Color.GREEN);
        }

        g.fillRect(barX, barY, fillW, barH);
    
        // border
        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, width, barH);
    }
    



    // returns true if enemy has reached the end of the path
    public boolean reachedEnd() {
        return pathIndex >= path.size() - 1;
    }

    // returns x value
    public int getX() {
        return x;
    }

    // returns y value
    public int getY() {
        return y;
    }
    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    // sets distance traveled by enemy
    public void setDistanceTraveled(int distance) {
        int traveled = 0;
        pathIndex = 0;
        x = path.get(0).getX();
        y = path.get(0).getY();

        while (pathIndex < path.size() - 1 && traveled < distance) {
            Point2D current = path.get(pathIndex);
            Point2D next = path.get(pathIndex + 1);
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

    // getter for path
    public ArrayList<Point2D> getPath() {
        return path;
    }

    // getter for health
    public int getHealth() {
        return health;      
    }

    // setter for health
    public void setHealth(int health) {
        this.health = health;
        if(this.health > this.maxHealth) {
            this.maxHealth = this.health;
        }
    }
}