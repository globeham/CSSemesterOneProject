/**
 * King
 * 
 * Tower subclass representing a King tower.
 * Extends Tower class with specific stats and projectile behavior.
 * Fires orb projectiles at enemies within range with high damage output.
 * 
 * @author Abhineet Bhardwaj
 * @version 1.0
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class King extends Tower {
    private BufferedImage image;
    // constructor for king tower
    public King(int speed, int radius, int damage, int cost, BufferedImage image) {
        super(20, 160, 25, 500);
        try {
            this.image = ImageIO.read(new File("images/king.png"));
            setImage(this.image);
            BufferedImage orbImage = ImageIO.read(new File("images/orb.png"));
            setProjectileImage(orbImage);
        } catch (IOException e) {
            System.out.println("Could not load king or orb image: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        // Additional drawing for king tower if needed
    }
}