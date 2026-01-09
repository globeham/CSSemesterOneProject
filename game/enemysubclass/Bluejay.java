import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;

public class Bluejay extends Enemy {
    private BufferedImage[] frames;
    private int currentFrame;
    private int frameCount;
    private long lastFrameTime;
    private static final int FRAME_DELAY = 100; // milliseconds between frames
    private int frameWidth; // actual width of each frame (auto-detected)
    private int frameHeight; // actual height of each frame (auto-detected)
    private int drawSize = 36; // size to draw the bird
    private int lastX; // used to determine facing direction
    private int lastY; // used to determine vertical direction
    private boolean facingRight = true;
    private int verticalDirection = 0; // 1 for down, -1 for up, 0 for neutral

    public Bluejay(int health, int reward, int speed, ArrayList<Point2D> path) {
        super(health, reward, speed, path, Color.BLUE); // fallback color
        loadSpriteSheet();
        currentFrame = 0;
        lastFrameTime = System.currentTimeMillis();
        lastX = getX();
        lastY = getY();
    }

    private void loadSpriteSheet() {
        try {
            BufferedImage spriteSheet = ImageIO.read(new File("images/bird_1_bluejay (1).png"));
            // Auto-detect frame dimensions: sprite is 96x256, so assume 4 columns (96/4=24) and multiple rows (256/32=8)
            // Try common heights: 32, 24, 20; pick the one that divides evenly
            frameWidth = 32;  // 96 / 3 = 32 pixels per frame
            frameHeight = 32; // 256 / 8 = 32 pixels per frame
            
            int cols = spriteSheet.getWidth() / frameWidth;
            int rows = spriteSheet.getHeight() / frameHeight;
            frameCount = cols * rows;
            frames = new BufferedImage[frameCount];
            
            System.out.println("Bluejay sprite: " + spriteSheet.getWidth() + "x" + spriteSheet.getHeight() + 
                             " -> frames: " + frameWidth + "x" + frameHeight + ", total: " + frameCount + 
                             " (" + cols + " cols x " + rows + " rows)");
            
            int idx = 0;
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    int sx = c * frameWidth;
                    int sy = r * frameHeight;
                    frames[idx++] = spriteSheet.getSubimage(sx, sy, frameWidth, frameHeight);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load Bluejay sprite sheet: " + e.getMessage());
            // Fallback to single image if sprite sheet fails
            try {
                this.image = ImageIO.read(new File("images/bird_1_bluejay.png"));
            } catch (IOException e2) {
                System.out.println("Could not load Bluejay image: " + e2.getMessage());
            }
        }
    }

    @Override
    public void move() {
        super.move();
        // Animate based on movement
        long currentTime = System.currentTimeMillis();
        if (frameCount > 0 && currentTime - lastFrameTime > FRAME_DELAY) {
            currentFrame = (currentFrame + 1) % frameCount;
            lastFrameTime = currentTime;
        }

        // update facing direction and vertical movement
        int x = getX();
        int y = getY();
        if (x > lastX) facingRight = true;
        else if (x < lastX) facingRight = false;
        lastX = x;
        
        // track vertical direction
        if (y > lastY) verticalDirection = 1; // moving down
        else if (y < lastY) verticalDirection = -1; // moving up
        else verticalDirection = 0; // not moving vertically
        lastY = y;
    }

    @Override
    public void draw(Graphics g) {
        int x = getX();
        int y = getY();

        Graphics2D g2d = (Graphics2D) g.create();
        try {
            if (frames != null && frames.length > 0) {
                // Select row based on vertical direction (3 frames per row for 32x32 layout)
                // 2nd row (frames 3-5) for going up, 3rd row (frames 6-8) for going down, 4th row (frames 9-11) for neutral
                int rowStart = 9; // default to 4th row (neutral/horizontal)
                if (verticalDirection > 0) {
                    rowStart = 6; // 3rd row for going down
                } else if (verticalDirection < 0) {
                    rowStart = 3; // 2nd row for going up
                }
                int displayFrame = rowStart + (currentFrame % 3);
                BufferedImage img = frames[displayFrame];
                int dx = x - drawSize / 2;
                int dy = y - drawSize / 2;
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
                g2d.setColor(Color.BLUE);
                g2d.fillOval(x - 8, y - 8, 16, 16);
            }
        } finally {
            g2d.dispose();
        }
    }
}