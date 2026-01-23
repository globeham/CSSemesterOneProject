/**
 * Ninja
 * 
 * Tower subclass representing a Ninja tower.
 * Extends Tower class with specific stats and projectile behavior.
 * Fires shuriken projectiles at enemies within range with balanced stats.
 * 
 * @author Abhineet Bhardwaj
 * @version 1.0
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ninja extends Tower {
    private BufferedImage image;
    // constructor for ninja tower
    public Ninja(int speed, int radius, int damage, int cost, BufferedImage image) {
        super(18, 110, 14, 200);
        try {
            this.image = ImageIO.read(new File("images/ninja.png"));
            setImage(this.image);
            BufferedImage shuriken = ImageIO.read(new File("images/shuriken.png"));
            setProjectileImage(shuriken);
        } catch (IOException e) {
            System.out.println("Could not load ninja image: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        // Additional drawing for ninja tower if needed
    }
}