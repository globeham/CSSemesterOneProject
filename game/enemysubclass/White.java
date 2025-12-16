import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class White extends Enemy {
    public White(int health, int reward, int speed, ArrayList<Point2D> path) {
        super(health, reward, speed, path, Color.white); // fallback color
        try {
            this.image = ImageIO.read(new File("images/bird_2_white.png"));
        } catch (IOException e) {
            System.out.println("Could not load white image: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}