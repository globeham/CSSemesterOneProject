import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class cardinal extends Enemy {
    public cardinal(int health, int reward, int speed, ArrayList<point2D> path) {
        super(health, reward, speed, path, Color.red); // fallback color
        try {
            this.image = ImageIO.read(new File("images/bird_2_cardinal.png"));
        } catch (IOException e) {
            System.out.println("Could not load bluejay image: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}