/*
Knight subclass of Tower
*/
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Knight extends Tower {
    private BufferedImage image;
    // constructor for knight tower
    public Knight(int speed, int radius, int damage, int cost, BufferedImage image) {
        super(28, 120, 12, 100);
        try {
            this.image = ImageIO.read(new File("images/knight.png"));
            setImage(this.image);
        } catch (IOException e) {
            System.out.println("Could not load knight image: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        // Additional drawing for knight tower if needed
    }
}