/* 
enemy subclass for sparrow
*/
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class sparrow extends enemy {
    public sparrow(int health, int reward, int speed, ArrayList<point2D> path, 
    BufferedImage image) {
        super(health, reward, speed, path, image);
        try {
            this.image = ImageIO.read(new File("images/bird_3_sparrow.png"));
        } catch (IOException e) {
            System.out.println("Could not load sparrow image: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}