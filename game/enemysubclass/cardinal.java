/*
enemy subclass for cardinals
*/

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class cardinal extends enemy {
    public cardinal(int health, int reward, int speed, ArrayList<point2D> path, 
    BufferedImage image) {
        super(health, reward, speed, path, image);
        try {
            this.image = ImageIO.read(new File("images/bird_2_cardinal.png"));
        } catch (IOException e) {
            System.out.println("Could not load cardinal image: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}