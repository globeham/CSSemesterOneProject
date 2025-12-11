/* 
enemy subclass for robin
*/
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class robin extends enemy {
    public robin(int health, int reward, int speed, ArrayList<point2D> path, 
    BufferedImage image) {
        super(health, reward, speed, path, image);
        try {
            this.image = ImageIO.read(new File("images/bird_3_robin.png"));
        } catch (IOException e) {
            System.out.println("Could not load robin image: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}