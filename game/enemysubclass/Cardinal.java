/**
 * Cardinal
 * 
 * Enemy subclass representing a Cardinal bird enemy.
 * Extends Enemy class with animated sprite rendering and specific gameplay characteristics.
 * 
 * @author Abhineet Bhardwaj
 * @version 1.0
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;

public class Cardinal extends Enemy {
    private BufferedImage[] frames;
    private int currentFrame;
    private int frameCount;
    private long lastFrameTime;
    private static final int FRAME_DELAY = 100;
    private int frameWidth;
    private int frameHeight;
    private int drawSize = 36;
    private int lastX;
    private int lastY;
    private boolean facingRight = true;
    private int verticalDirection = 0;
    private int vx = 0;
    private int vy = 0;


    // constructor for cardinal
    public Cardinal(int health, int reward, int speed, ArrayList<Point2D> path) {
        super(health, reward, speed, path, Color.red);
        loadSpriteSheet();
        currentFrame = 0;
        lastFrameTime = System.currentTimeMillis();
        lastX = getX();
        lastY = getY();
    }

    // method loads cardinal's sprite sheet
    private void loadSpriteSheet() {
        try {
            BufferedImage spriteSheet = ImageIO.read(new File("images/bird_2_Cardinal.png"));
            frameWidth = 32;
            frameHeight = 32;
            int cols = spriteSheet.getWidth() / frameWidth;
            int rows = spriteSheet.getHeight() / frameHeight;
            frameCount = cols * rows;
            frames = new BufferedImage[frameCount];

            int idx = 0;
            // iterating through sprite sheet
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    int sx = c * frameWidth;
                    int sy = r * frameHeight;
                    frames[idx++] = spriteSheet.getSubimage(sx, sy, frameWidth, frameHeight);
                }
            }
        } catch (IOException e) {
            // fallback to single image if sprite sheet fails
            System.out.println("Could not load Cardinal sprite sheet: " + e.getMessage());
            try {
                this.image = ImageIO.read(new File("images/bird_2_Cardinal.png"));
            } catch (IOException e2) {
                System.out.println("Could not load Cardinal image: " + e2.getMessage());
            }
        }
    }

    @Override
    public void move() {
        super.move();
        long currentTime = System.currentTimeMillis();
        if (frameCount > 0 && currentTime - lastFrameTime > FRAME_DELAY) {
            currentFrame = (currentFrame + 1) % frameCount;
            lastFrameTime = currentTime;
        }

        // calculate velocity
        int x = getX();
        int y = getY();

        vx = x - lastX;
        vy = y - lastY;

        if (vx > 0) facingRight = true;
        else if (vx < 0) facingRight = false;

        if (vy > 0) verticalDirection = 1;
        else if (vy < 0) verticalDirection = -1;
        else verticalDirection = 0;

        lastX = x;
        lastY = y;
    }

    @Override
    public void draw(Graphics g) {
        int x = getX();
        int y = getY();

        Graphics2D g2d = (Graphics2D) g.create();
        try {
            if (frames != null && frames.length > 0) {
                // select row based on vertical direction
                int rowStart = 9;
                if (verticalDirection > 0) {
                    rowStart = 6;
                } else if (verticalDirection < 0) {
                    rowStart = 3;
                }
                int displayFrame = rowStart + (currentFrame % 3);
                BufferedImage img = frames[displayFrame];
                int dx = x - drawSize / 2;
                int dy = y - drawSize / 2;
                // animates the cardinal based on direction
                if (!facingRight) {
                    AffineTransform at = AffineTransform.getTranslateInstance(dx + drawSize, dy);
                    at.scale(-1, 1);
                    g2d.drawImage(img, at, null);
                } else {
                    g2d.drawImage(img, dx, dy, drawSize, drawSize, null);
                }
            } else if (image != null) {
                int dx = x - drawSize / 2;
                int dy = y - drawSize / 2;
                if (!facingRight) {
                    AffineTransform at = AffineTransform.getTranslateInstance(dx + drawSize, dy);
                    at.scale(-1, 1);
                    g2d.drawImage(image, at, null);
                } else {
                    g2d.drawImage(image, dx, dy, drawSize, drawSize, null);
                }
            } else {
                g2d.setColor(Color.red);
                g2d.fillOval(x - 8, y - 8, 16, 16);
            }
            drawHealthBar(g, x, y - drawSize / 2, drawSize);
        } finally {
            g2d.dispose();
        }
    }
}