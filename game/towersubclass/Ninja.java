/*
ninja subclass of Tower
*/
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Ninja extends Tower {
    private BufferedImage image;
    public Ninja(int speed, int radius, int damage, int cost, BufferedImage image) {
        super(30, 100, 15, 75); // speed, radius, damage, cost, color
        try {
            this.image = ImageIO.read(new File("images/ninja.gif"));
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