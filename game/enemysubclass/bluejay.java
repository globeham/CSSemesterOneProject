import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class bluejay extends Enemy {
    private BufferedImage[] frames;
    private int currentFrame;
    private int frameCount;
    private long lastFrameTime;
    private static final int FRAME_DELAY = 150; // milliseconds between frames
    private static final int FRAME_WIDTH = 20; // width of each frame
    private static final int FRAME_HEIGHT = 20; // height of each frame

    public bluejay(int health, int reward, int speed, ArrayList<Point2D> path) {
        super(health, reward, speed, path, Color.BLUE); // fallback color
        loadSpriteSheet();
        currentFrame = 0;
        lastFrameTime = System.currentTimeMillis();
    }

    private void loadSpriteSheet() {
        try {
            BufferedImage spriteSheet = ImageIO.read(new File("images/bird_1_bluejay (1).png"));
            // Assume sprite sheet has multiple frames horizontally
            frameCount = spriteSheet.getWidth() / FRAME_WIDTH;
            frames = new BufferedImage[frameCount];

            for (int i = 0; i < frameCount; i++) {
                frames[i] = spriteSheet.getSubimage(i * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
            }
        } catch (IOException e) {
            System.out.println("Could not load bluejay sprite sheet: " + e.getMessage());
            // Fallback to single image if sprite sheet fails
            try {
                this.image = ImageIO.read(new File("images/bird_1_bluejay.png"));
            } catch (IOException e2) {
                System.out.println("Could not load bluejay image: " + e2.getMessage());
            }
        }
    }

    @Override
    public void move() {
        super.move();
        // Animate based on movement
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime > FRAME_DELAY) {
            currentFrame = (currentFrame + 1) % frameCount;
            lastFrameTime = currentTime;
        }
    }

    @Override
    public void draw(Graphics g) {
        if (frames != null && frames.length > 0) {
            g.drawImage(frames[currentFrame], getX() - 10, getY() - 10, 20, 20, null);
        } else if (image != null) {
            g.drawImage(image, getX() - 10, getY() - 10, 20, 20, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillOval(getX() - 10, getY() - 10, 20, 20);
        }
    }
}