/* 
enemy subclass for bluejay
*/
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class bluejay extends Enemy {
    public bluejay(int health, int reward, int speed, ArrayList<point2D> path, 
    BufferedImage image) {
        super(health, reward, speed, path, image);
        try {
            this.image = ImageIO.read(new File("images/bird_1_bluejay.png"));
        } catch (IOException e) {
            System.out.println("Could not load bluejay image: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}