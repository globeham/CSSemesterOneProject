import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Cardinal extends Enemy {
    public Cardinal(int health, int reward, int speed, ArrayList<Point2D> path) {
        super(health, reward, speed, path, Color.red); // fallback color
        try {
            this.image = ImageIO.read(new File("images/bird_2_Cardinal.png"));
        } catch (IOException e) {
            System.out.println("Could not load Cardinal image: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}