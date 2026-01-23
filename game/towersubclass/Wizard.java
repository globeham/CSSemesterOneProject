/**
 * Wizard
 * 
 * Tower subclass representing a Wizard tower.
 * Extends Tower class with specific stats and projectile behavior.
 * Fires fireball projectiles at enemies within range with solid damage output.
 * 
 * @author Abhineet Bhardwaj
 * @version 1.0
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Wizard extends Tower {
    private BufferedImage image;
    // constructor for wizard tower
    public Wizard(int speed, int radius, int damage, int cost, BufferedImage image) {
        super(26, 130, 16, 300);
        try {
            this.image = ImageIO.read(new File("images/wizard.png"));
            setImage(this.image);
        } catch (IOException e) {
            System.out.println("Could not load wizard image: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        // Additional drawing for wizard tower if needed
    }
}