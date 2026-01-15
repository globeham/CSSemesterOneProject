/*
King subclass of Tower
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
        } catch (IOException e) {
            System.out.println("Could not load king image: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        // Additional drawing for king tower if needed
    }
}